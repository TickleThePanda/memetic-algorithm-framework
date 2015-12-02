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
public class TspTwoOpt implements LocalImprovement<TspTour> {

  /**
   * The default cache size.
   */
  private static final int DEFAULT_CACHE_SIZE = 1000;

  /**
   * The default concurrency level.
   */
  private static final int DEFAULT_CONCURRENCY_LEVEL = 1;

  /**
   * The default time limit in seconds.
   */
  private static final int DEFAULT_TIME_LIMIT = 1;

  /**
   * The cache used for getting previous completed improvements.
   */
  LoadingCache<TspTour, TspTour> improvementCache = CacheBuilder.newBuilder()
      .maximumSize(DEFAULT_CACHE_SIZE)
      .expireAfterWrite(DEFAULT_TIME_LIMIT, TimeUnit.SECONDS)
      .concurrencyLevel(DEFAULT_CONCURRENCY_LEVEL)
      .build(new CacheLoader<TspTour, TspTour>() {

        @Override
        public TspTour load(final TspTour solution) throws Exception {
          switch (type) {
          case COMPLETE:
            return TspTwoOpt.this.getCompleteImprovedSolution(solution);
          case NEIGHBOURHOOD:
            return TspTwoOpt.this.getNeighbourhoodImprovedSolution(solution);
          case SINGLE_ORDERED:
            return TspTwoOpt.this.getSingleOrderedImprovedSolution(solution);
          case SINGLE_RANDOM:
            return TspTwoOpt.this.getSingleRandomImprovedSolution(solution);
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
  public TspTwoOpt() {
    type = LocalImprovementType.SINGLE_RANDOM;
  }

  /**
   * Creates a new TspTwoOpt with the type.
   * 
   * @param type
   *          the type of local improvement to use
   */
  public TspTwoOpt(final LocalImprovementType type) {
    this.type = type;
  }

  @Override
  public TspTour getCompleteImprovedSolution(final TspTour solution) {
    TspTour bestSolution = solution;

    while (true) {
      final TspTour nextSolution =
          getNeighbourhoodImprovedSolution(bestSolution);
      if (nextSolution == bestSolution) {
        break;
      }
      bestSolution = nextSolution;
    }

    return getNeighbourhoodImprovedSolution(bestSolution);
  }

  /**
   * Reverses order of cities, on the solution, between firstIndex and secondIndex.
   * 
   * @param solution
   *          the solution to be swapped
   * @param firstIndex
   *          the index of the start of the swap
   * @param secondIndex
   *          the index of the end of the swap
   * @return the new order of cities
   */
  public TspTour doSingleSwap(final TspTour solution, final int firstIndex,
      final int secondIndex) {

    final int[] newOrder = new int[solution.size()];

    for (int i = 0; i < newOrder.length; i++) {
      if (i >= firstIndex && i <= secondIndex) {
        final int reverseIndex = secondIndex + firstIndex - i;
        newOrder[i] = solution.get(reverseIndex);
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
        final TspTour improved = doSingleSwap(solution, i, j);

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

    for (int i = start; i < solution.size(); i++) {
      for (int j = i + 1; j < solution.size(); j++) {
        final TspTour improved = doSingleSwap(solution, i, j);

        if (improved.getFitness() > solution.getFitness()) {
          return improved;
        }
      }
    }

    for (int i = 0; i < start; i++) {
      for (int j = i + 1; j < solution.size(); j++) {
        final TspTour improved = doSingleSwap(solution, i, j);

        if (improved.getFitness() > solution.getFitness()) {
          return improved;
        }
      }
    }
    return solution;
  }

  @Override
  public TspTour getSingleOrderedImprovedSolution(final TspTour solution) {
    for (int i = 0; i < solution.size(); i++) {
      for (int j = i + 1; j < solution.size(); j++) {
        final TspTour improved = doSingleSwap(solution, i, j);

        if (improved.getFitness() > solution.getFitness()) {
          return improved;
        }
      }
    }
    return solution;
  }

}
