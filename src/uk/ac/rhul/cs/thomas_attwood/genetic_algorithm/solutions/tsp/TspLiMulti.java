package uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.solutions.tsp;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.solutions.LocalImprovement;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.solutions.LocalImprovementType;

public class TspLiMulti implements LocalImprovement<TspTour> {

	private ArrayList<LocalImprovement<TspTour>> improvements = new ArrayList<LocalImprovement<TspTour>>();
	
	private LocalImprovementType type;

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

	public TspLiMulti() {
		this(LocalImprovementType.SINGLE_RANDOM);
	}

	public TspLiMulti(LocalImprovementType type) {
		this.type = type;
		improvements.add(new TspLiTwoOpt(type));
		improvements.add(new TspLiInsert(type));
		improvements.add(new TspLiSwap(type));
	}

	@Override
	public TspTour getImprovedSolution(TspTour solution) {
		return improvementCache.getUnchecked(solution);
	}

	@Override
	public LocalImprovementType getType() {
		return this.type;
	}

	@Override
	public TspTour getSingleRandomImprovedSolution(TspTour original) {
		final int solFitness = original.getFitness();
		for(LocalImprovement<TspTour> improvement : improvements) {
			TspTour best = improvement.getImprovedSolution(original);
			if(best.getFitness() > solFitness) 
				return best;
		}
		return original;
	}

	@Override
	public TspTour getSingleOrderedImprovedSolution(TspTour original) {
		TspTour best = original;
		for(LocalImprovement<TspTour> improvement : improvements) {
			best = improvement.getImprovedSolution(best);
			if(best != original) 
				return best;
		}
		return best;
	}

	@Override
	public TspTour getNeighbourhoodImprovedSolution(TspTour original) {
		TspTour best = original;
		for(LocalImprovement<TspTour> improvement : improvements) {
			best = improvement.getImprovedSolution(best);
			if(best != original) 
				return best;
		}
		return best;
	}

	@Override
	public TspTour getCompleteImprovedSolution(TspTour original) {
		TspTour best = original;
		for(LocalImprovement<TspTour> improvement : improvements) {
			best = improvement.getImprovedSolution(best);
			if(best != original) 
				return best;
		}
		return best;
	}

}
