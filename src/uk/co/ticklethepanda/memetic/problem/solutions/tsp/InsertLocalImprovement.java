package uk.co.ticklethepanda.memetic.problem.solutions.tsp;

import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import uk.co.ticklethepanda.memetic.problem.solutions.LocalImprovement;
import uk.co.ticklethepanda.memetic.problem.solutions.LocalImprovementExtent;

/**
 * Improves a TSPTour with the well known improvement two-opt.
 *
 */
public class InsertLocalImprovement implements LocalImprovement<Tour> {

	/**
	 * The default cache size.
	 */
	private static final int DEFAULT_CACHE_SIZE = 50;

	/**
	 * The default concurrency level.
	 */
	private static final int DEFAULT_CONCURRENCY_LEVEL = 1;

	/**
	 * The default time limit in seconds.
	 */
	private static final int DEFAULT_TIME_LIMIT = 1000;

	/**
	 * The cache used for getting previous completed improvements.
	 */
	LoadingCache<Tour, Tour> improvementCache = CacheBuilder.newBuilder()
			.maximumSize(DEFAULT_CACHE_SIZE)
			.expireAfterWrite(DEFAULT_TIME_LIMIT, TimeUnit.MILLISECONDS)
			.concurrencyLevel(DEFAULT_CONCURRENCY_LEVEL)
			.build(new CacheLoader<Tour, Tour>() {

				@Override
				public Tour load(final Tour solution) throws Exception {
					switch (type) {
					case COMPLETE:
						return getCompleteImprovedSolution(solution);
					case NEIGHBOURHOOD:
						return getNeighbourhoodImprovedSolution(solution);
					case SINGLE_ORDERED:
						return getSingleOrderedImprovedSolution(solution);
					case SINGLE_RANDOM:
						return getSingleRandomImprovedSolution(solution);
					default:
						return solution;
					}
				}
			});

	/**
	 * Random used for choosing the starting index of the improvement.
	 */
	private final Random random = new Random();
	/**
	 * The local improvement type that should be completed.
	 */
	private final LocalImprovementExtent type;

	/**
	 * Creates a new TspTwoOpt of SINGLE_RANDOM type.
	 */
	public InsertLocalImprovement() {
		type = LocalImprovementExtent.SINGLE_RANDOM;
	}

	/**
	 * Creates a new TspTwoOpt with the type.
	 * 
	 * @param type
	 *            the type of local improvement to use
	 */
	public InsertLocalImprovement(final LocalImprovementExtent type) {
		this.type = type;
	}

	@Override
	public Tour getCompleteImprovedSolution(final Tour solution) {
		Tour bestSolution = solution;

		while (true) {
			final Tour nextSolution = getNeighbourhoodImprovedSolution(bestSolution);
			if (nextSolution == bestSolution) {
				break;
			}
			bestSolution = nextSolution;
		}

		return getNeighbourhoodImprovedSolution(bestSolution);
	}

	/**
	 * Reverses order of cities of the solution, between firstIndex and
	 * secondIndex, if distance is gained by doing this. Returns the old
	 * solution if no distance is gained.
	 * 
	 * @param solution
	 *            the solution to be swapped
	 * @param index1
	 *            the index of the start of the swap
	 * @param index2
	 *            the index of the end of the swap
	 * @return the new order of cities
	 */
	public Tour doLazySingleSwap(final Tour solution, final int index1,
			final int index2) {
		Cities<?> func = solution.getFitnessFunction();

		int beforeIndex1 = (index1 - 1) % solution.size();
		if (beforeIndex1 < 0) {
			beforeIndex1 = solution.size() - 1;
		}
		int afterIndex1 = (index1 + 1) % solution.size();

		int afterIndex2 = (index2 + 1) % solution.size();

		int city11 = solution.get(beforeIndex1);
		int city12 = solution.get(index1);
		int city13 = solution.get(afterIndex1);
		
		int city21 = solution.get(index2);
		int city23 = solution.get(afterIndex2);

		//distance (city11 -> city12 -> city13) + distance (city21 -> city22)
		int oldDistance = func.distance(city11, city12)
				+ func.distance(city12, city13) + func.distance(city21, city23);
		
		//distance (city11 -> city13) + distance (city21 -> city12 -> city22)
		int newDistance = func.distance(city11, city13) + func.distance(city21, city12)
				+ func.distance(city12, city23);

		int change = newDistance - oldDistance;

		if (change < 0) {
			return doSingleSwap(solution, index1, index2);
		}
		return solution;
	}

	/**
	 * Reverses order of cities of the solution, between firstIndex and
	 * secondIndex.
	 * 
	 * @param solution
	 *            the solution to be swapped
	 * @param index1
	 *            the index of the start of the swap
	 * @param index2
	 *            the index of the end of the swap
	 * @return the new order of cities
	 */
	public Tour doSingleSwap(final Tour solution, final int index1,
			final int index2) {
		final int[] newOrder = new int[solution.size()];

		int city1 = solution.get(index1);

		for (int i = 0; i < newOrder.length; i++) {
			if (i >= index1 && i < index2) {
				newOrder[i] = solution.get(i+1);
			} else if (i == index2){
				newOrder[i] = city1;
			} else {
				newOrder[i] = solution.get(i);
			}
		}
		return new Tour(newOrder, solution.getFitnessFunction());
	}

	@Override
	public Tour getImprovedSolution(final Tour solution) {
		try {
			return improvementCache.get(solution);
		} catch (final ExecutionException e) {
			e.printStackTrace();
		}
		return solution;
	}

	public LocalImprovementExtent getType() {
		return type;
	}

	@Override
	public Tour getNeighbourhoodImprovedSolution(final Tour solution) {
		Tour bestSolution = solution;

		for (int i = 0; i < solution.size(); i++) {
			for (int j = i + 2; j < solution.size(); j++) {
				final Tour improved = doLazySingleSwap(solution, i, j);

				if (improved.getFitness() > bestSolution.getFitness()) {
					bestSolution = improved;
				}
			}
		}
		return bestSolution;
	}

	@Override
	public Tour getSingleRandomImprovedSolution(final Tour solution) {
		final int start = random.nextInt(solution.size());
		final int solFitness = solution.getFitness();

		for (int i = start; i < solution.size(); i++) {
			for (int j = i + 1; j < solution.size(); j++) {
				final Tour improved = doLazySingleSwap(solution, i, j);

				if (improved.getFitness() > solFitness) {
					return improved;
				}
			}
		}

		for (int i = 0; i < start; i++) {
			for (int j = i + 1; j < solution.size(); j++) {
				final Tour improved = doLazySingleSwap(solution, i, j);

				if (improved.getFitness() > solFitness) {
					return improved;
				}
			}
		}
		return solution;
	}

	@Override
	public Tour getSingleOrderedImprovedSolution(final Tour solution) {
		for (int i = 0; i < solution.size(); i++) {
			for (int j = i + 2; j < solution.size(); j++) {
				final Tour improved = doLazySingleSwap(solution, i, j);

				if (improved.getFitness() > solution.getFitness()) {
					return improved;
				}
			}
		}
		return solution;
	}

}
