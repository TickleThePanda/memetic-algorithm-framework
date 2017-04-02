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
public class TwoOptLocalImprovement implements LocalImprovement<Tour> {

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
  public TwoOptLocalImprovement() {
    type = LocalImprovementExtent.SINGLE_RANDOM;
  }

  /**
   * Creates a new TspTwoOpt with the type.
   * 
   * @param type
   *          the type of local improvement to use
   */
  public TwoOptLocalImprovement(final LocalImprovementExtent type) {
    this.type = type;
  }

  /**
   * Reverses order of cities of the solution, between firstIndex and secondIndex, if distance is
   * gained by doing this. Returns the old solution if no distance is gained.
   * 
   * @param solution
   *          the solution to be swapped
   * @param index1
   *          the index of the start of the swap
   * @param index2
   *          the index of the end of the swap
   * @return the new order of cities
   */
  public Tour doLazySingleSwap(final Tour solution, final int index1, final int index2) {
    final Cities<?> func = solution.getFitnessFunction();
    // if the distance changes improves

    int beforeIndex1 = (index1 - 1) % solution.size();
    if (beforeIndex1 < 0) {
      beforeIndex1 = solution.size() - 1;
    }
    final int afterIndex2 = (index2 + 1) % solution.size();

    final int city1 = solution.get(beforeIndex1);
    final int city2 = solution.get(index1);
    final int city3 = solution.get(index2);
    final int city4 = solution.get(afterIndex2);

    final int newDistance = func.getDistanceBetweenCities(city1, city3) + func.getDistanceBetweenCities(city2, city4);
    final int oldDistance = func.getDistanceBetweenCities(city1, city2) + func.getDistanceBetweenCities(city3, city4);

    final int change = newDistance - oldDistance;

    if (change < 0) {
      return doSingleSwap(solution, index1, index2);
    }
    return solution;
  }

  /**
   * Reverses order of cities of the solution, between firstIndex and secondIndex.
   * 
   * @param solution
   *          the solution to be swapped
   * @param index1
   *          the index of the start of the swap
   * @param index2
   *          the index of the end of the swap
   * @return the new order of cities
   */
  public Tour doSingleSwap(final Tour solution, final int index1, final int index2) {
    final Integer[] newOrder = new Integer[solution.size()];

    for (int i = 0; i < newOrder.length; i++) {
      if (i >= index1 && i <= index2) {
        newOrder[i] = solution.get(index2 + index1 - i);
      } else {
        newOrder[i] = solution.get(i);
      }
    }
    return new Tour(newOrder, solution.getFitnessFunction());
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

  @Override
  public Tour getImprovedSolution(final Tour solution) {
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

  @Override
  public Tour getNeighbourhoodImprovedSolution(final Tour solution) {
    Tour bestSolution = solution;

    for (int i = 0; i < solution.size(); i++) {
      for (int j = i + 1; j < solution.size(); j++) {
        final Tour improved = doLazySingleSwap(solution, i, j);

        if (improved.getFitness() > bestSolution.getFitness()) {
          bestSolution = improved;
        }
      }
    }
    return bestSolution;
  }

  @Override
  public Tour getSingleOrderedImprovedSolution(final Tour solution) {
    final int solFitness = solution.getFitness();
    for (int i = 0; i < solution.size(); i++) {
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
  public LocalImprovementExtent getType() {
    return type;
  }

}
