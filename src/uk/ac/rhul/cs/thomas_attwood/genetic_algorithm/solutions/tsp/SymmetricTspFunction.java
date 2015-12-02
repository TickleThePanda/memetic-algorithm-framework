package uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.solutions.tsp;

import java.awt.Point;
import java.util.Arrays;
import java.util.Iterator;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.ProblemType;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.solutions.FitnessFunction;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

/**
 * Defines a symmetric TSP and gives the fitness of a tour.
 *
 */
public class SymmetricTspFunction implements FitnessFunction<TspTour>,
    Iterable<Point> {

  /**
   * Iterates over each city in.
   *
   */
  private class CityIterator implements Iterator<Point> {

    /**
     * The current index of the iterator.
     */
    int index = 0;

    @Override
    public boolean hasNext() {
      return index < cities.length;
    }

    @Override
    public Point next() {
      return cities[index++];
    }

  }

  /**
   * Calculated distances for the problem.
   */
  private final int[][] cd;

  /**
   * The cities used for the problem.
   */
  private final Point[] cities;

  /**
   * The maximum distance between two cities. Gives a fitness value.
   */
  private final int maximumDistance;

  /**
   * Creates a symmetric TSP using the cities and using the default cache value.
   * 
   * @param cities
   *          the cities to use for the problem
   */
  public SymmetricTspFunction(final Point[] cities) {
    this.cities = cities.clone();
    int maxDist = 0;
    for (int i = 0; i < cities.length; i++) {
      for (int j = i + 1; j < cities.length; j++) {
        final int dist = (int) cities[i].distance(cities[j]);
        if (maxDist < dist) {
          maxDist = dist;
        }
      }
    }
    maximumDistance = maxDist;
    cd = new int[cities.length][cities.length];
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
      int dist = 0;
      if (cd[cityIndexA][cityIndexB] != 0) {
        dist = cd[cityIndexA][cityIndexB];
      } else {
        final Point cityA = cities[cityIndexA];
        final Point cityB = cities[cityIndexB];
        dist = (int) cityA.distance(cityB);
        cd[cityIndexA][cityIndexB] = dist;
      }
      sum += dist;
    }
    return sum;
  }

  @Override
  public SymmetricTspFunction copy() {
    return new SymmetricTspFunction(this.cities);
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
    final SymmetricTspFunction other = (SymmetricTspFunction) obj;
    if (!Arrays.equals(cities, other.cities)) {
      return false;
    }
    return true;
  }

  @Override
  public int evaluateFitness(final TspTour tspTour) {
    return maximumDistance * cities.length - actualValue(tspTour);
  }

  public Point getCity(final int index) {
    return cities[index];
  }

  @Override
  public ProblemType getProblemType() {
    return ProblemType.SYMMETRIC_TSP;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + Arrays.hashCode(cities);
    return result;
  }

  @Override
  public boolean hasCache() {
    return false;
  }
  
  @Override
  public Iterator<Point> iterator() {
    return new CityIterator();
  }

  @Override
  public int size() {
    return cities.length;
  }

}
