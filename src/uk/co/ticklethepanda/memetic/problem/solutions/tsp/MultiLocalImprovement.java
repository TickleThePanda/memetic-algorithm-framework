package uk.co.ticklethepanda.memetic.problem.solutions.tsp;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import uk.co.ticklethepanda.memetic.problem.solutions.LocalImprovement;
import uk.co.ticklethepanda.memetic.problem.solutions.LocalImprovementExtent;

public class MultiLocalImprovement implements LocalImprovement<Tour> {

	private ArrayList<LocalImprovement<Tour>> improvements = new ArrayList<LocalImprovement<Tour>>();
	
	private LocalImprovementExtent type;

	/**
	 * The default cache size.
	 */
	private static final int DEFAULT_CACHE_SIZE = 75;

	/**
	 * The default concurrency level.
	 */
	private static final int DEFAULT_CONCURRENCY_LEVEL = 1;

	/**
	 * The default time limit in milliseconds.
	 */
	private static final int DEFAULT_TIME_LIMIT = 100;

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

	public MultiLocalImprovement() {
		this(LocalImprovementExtent.SINGLE_RANDOM);
	}

	public MultiLocalImprovement(LocalImprovementExtent type) {
		this.type = type;
		improvements.add(new TwoOptLocalImprovement(type));
		improvements.add(new InsertLocalImprovement(type));
		improvements.add(new SwapLocalImprovement(type));
	}

	@Override
	public Tour getImprovedSolution(Tour solution) {
		return improvementCache.getUnchecked(solution);
	}

	@Override
	public LocalImprovementExtent getType() {
		return this.type;
	}

	@Override
	public Tour getSingleRandomImprovedSolution(Tour original) {
		final int solFitness = original.getFitness();
		for(LocalImprovement<Tour> improvement : improvements) {
			Tour best = improvement.getImprovedSolution(original);
			if(best.getFitness() > solFitness) 
				return best;
		}
		return original;
	}

	@Override
	public Tour getSingleOrderedImprovedSolution(Tour original) {
		Tour best = original;
		for(LocalImprovement<Tour> improvement : improvements) {
			best = improvement.getImprovedSolution(best);
			if(best != original) 
				return best;
		}
		return best;
	}

	@Override
	public Tour getNeighbourhoodImprovedSolution(Tour original) {
		Tour best = original;
		for(LocalImprovement<Tour> improvement : improvements) {
			best = improvement.getImprovedSolution(best);
			if(best != original) 
				return best;
		}
		return best;
	}

	@Override
	public Tour getCompleteImprovedSolution(Tour original) {
		Tour best = original;
		for(LocalImprovement<Tour> improvement : improvements) {
			best = improvement.getImprovedSolution(best);
			if(best != original) 
				return best;
		}
		return best;
	}

}
