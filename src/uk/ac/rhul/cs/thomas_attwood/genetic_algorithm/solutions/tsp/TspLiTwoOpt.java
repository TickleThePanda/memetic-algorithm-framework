package uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.solutions.tsp;

import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.solutions.LocalImprovement;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.solutions.LocalImprovementType;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

/**
 * Improves a TSPTour with the well known improvement two-opt.
 *
 */
public class TspLiTwoOpt implements LocalImprovement<TspTour> {

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
	private static final int DEFAULT_TIME_LIMIT = 100;

	/**
	 * The cache used for getting previous completed improvements.
	 */
	LoadingCache<TspTour, TspTour> improvementCache = CacheBuilder.newBuilder()
			.maximumSize(DEFAULT_CACHE_SIZE)
			.expireAfterWrite(DEFAULT_TIME_LIMIT, TimeUnit.MILLISECONDS)
			.concurrencyLevel(DEFAULT_CONCURRENCY_LEVEL)
			.build(new CacheLoader<TspTour, TspTour>() {

				@Override
				public TspTour load(final TspTour solution) throws Exception {
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
	private final LocalImprovementType type;

	/**
	 * Creates a new TspTwoOpt of SINGLE_RANDOM type.
	 */
	public TspLiTwoOpt() {
		type = LocalImprovementType.SINGLE_RANDOM;
	}

	/**
	 * Creates a new TspTwoOpt with the type.
	 * 
	 * @param type
	 *            the type of local improvement to use
	 */
	public TspLiTwoOpt(final LocalImprovementType type) {
		this.type = type;
	}

	@Override
	public TspTour getCompleteImprovedSolution(final TspTour solution) {
		TspTour bestSolution = solution;

		while (true) {
			final TspTour nextSolution = getNeighbourhoodImprovedSolution(bestSolution);
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
	public TspTour doLazySingleSwap(final TspTour solution, final int index1,
			final int index2) {
		TspFunction<?> func = solution.getFitnessFunction();
		// if the distance changes improves

		int beforeIndex1 = (index1 - 1) % solution.size();
		if (beforeIndex1 < 0) {
			beforeIndex1 = solution.size() - 1;
		}
		int afterIndex2 = (index2 + 1) % solution.size();

		int city1 = solution.get(beforeIndex1);
		int city2 = solution.get(index1);
		int city3 = solution.get(index2);
		int city4 = solution.get(afterIndex2);

		int newDistance = func.distance(city1, city3)
				+ func.distance(city2, city4);
		int oldDistance = func.distance(city1, city2)
				+ func.distance(city3, city4);

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
	public TspTour doSingleSwap(final TspTour solution, final int index1,
			final int index2) {
		final int[] newOrder = new int[solution.size()];

		for (int i = 0; i < newOrder.length; i++) {
			if (i >= index1 && i <= index2) {
				newOrder[i] = solution.get(index2 + index1 - i);
			} else {
				newOrder[i] = solution.get(i);
			}
		}
		return new TspTour(newOrder, solution.getFitnessFunction());
	}

	@Override
	public TspTour getImprovedSolution(final TspTour solution) {
		try {
			return improvementCache.get(solution);
		} catch (final ExecutionException e) {
			e.printStackTrace();
		}
		return solution;
	}

	public LocalImprovementType getType() {
		return type;
	}

	@Override
	public TspTour getNeighbourhoodImprovedSolution(final TspTour solution) {
		TspTour bestSolution = solution;

		for (int i = 0; i < solution.size(); i++) {
			for (int j = i + 1; j < solution.size(); j++) {
				final TspTour improved = doLazySingleSwap(solution, i, j);

				if (improved.getFitness() > bestSolution.getFitness()) {
					bestSolution = improved;
				}
			}
		}
		return bestSolution;
	}

	@Override
	public TspTour getSingleRandomImprovedSolution(final TspTour solution) {
		final int start = random.nextInt(solution.size());
		final int solFitness = solution.getFitness();

		for (int i = start; i < solution.size(); i++) {
			for (int j = i + 1; j < solution.size(); j++) {
				final TspTour improved = doLazySingleSwap(solution, i, j);

				if (improved.getFitness() > solFitness) {
					return improved;
				}
			}
		}

		for (int i = 0; i < start; i++) {
			for (int j = i + 1; j < solution.size(); j++) {
				final TspTour improved = doLazySingleSwap(solution, i, j);

				if (improved.getFitness() > solFitness) {
					return improved;
				}
			}
		}
		return solution;
	}

	@Override
	public TspTour getSingleOrderedImprovedSolution(final TspTour solution) {
		final int solFitness = solution.getFitness();
		for (int i = 0; i < solution.size(); i++) {
			for (int j = i + 1; j < solution.size(); j++) {
				final TspTour improved = doLazySingleSwap(solution, i, j);

				if (improved.getFitness() > solFitness) {
					return improved;
				}
			}
		}
		return solution;
	}

}
