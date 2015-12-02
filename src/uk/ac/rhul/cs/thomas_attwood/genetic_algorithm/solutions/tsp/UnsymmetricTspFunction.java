package uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.solutions.tsp;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.ProblemType;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.solutions.FitnessFunction;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

/**
 * Defines a TSP problem and gives the fitness of a tour.
 *
 */
public class UnsymmetricTspFunction implements FitnessFunction<TspTour> {

  /**
   * Calculated distances for the problem.
   */
  private final int[][] weightedConnectivityGraph;

  /**
   * The maximum distance between two cities.
   */
  private final int maximumDistance;

  /**
   * Create a new UnsymmetricTspFunction with the weighted connectivity graph.
   * 
   * @param weightedConnectivityGraph
   *          the weighted connectivity graph for the fitness function.
   */
  public UnsymmetricTspFunction(final int[][] weightedConnectivityGraph) {
    this.weightedConnectivityGraph = weightedConnectivityGraph.clone();
    int maxDist = 0;
    for (int i = 0; i < weightedConnectivityGraph.length; i++) {
      for (int j = 0; j < weightedConnectivityGraph[i].length; j++) {
        if (weightedConnectivityGraph[i][j] > maxDist) {
          maxDist = weightedConnectivityGraph[i][j];
        }
      }
    }
    maximumDistance = maxDist;
  }

  @Override
  public int actualValue(final TspTour solution) {
      return calculateValue(solution);
  }

  /**
   * Calculates the actual fitness of the solution.
   * 
   * @param solution
   *          the solution to calculate the value for
   * @return the fitness of the solution
   */
  private int calculateValue(final TspTour solution) {
    int sum = 0;

    for (int i = 0; i < solution.size(); i++) {
      final int cityIndexA = solution.get(i);
      final int cityIndexB = solution.get((i + 1) % solution.size());
      sum += weightedConnectivityGraph[cityIndexA][cityIndexB];
    }
    return sum;
  }

  @Override
  public UnsymmetricTspFunction copy() {
    return new UnsymmetricTspFunction(this.weightedConnectivityGraph);
  }

  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (this.getClass() != obj.getClass()) {
      return false;
    }
    final UnsymmetricTspFunction other = (UnsymmetricTspFunction) obj;
    if (!Arrays.equals(weightedConnectivityGraph,
        other.weightedConnectivityGraph)) {
      return false;
    }
    return true;
  }

  @Override
  public int evaluateFitness(final TspTour tspTour) {
    return maximumDistance * weightedConnectivityGraph.length
        - actualValue(tspTour);
  }

  @Override
  public ProblemType getProblemType() {
    return ProblemType.UNSYMMETRIC_TSP;
  }

  @Override
  public boolean hasCache() {
    return false;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + Arrays.hashCode(weightedConnectivityGraph);
    return result;
  }

  @Override
  public int size() {
    return weightedConnectivityGraph.length;
  }

}
