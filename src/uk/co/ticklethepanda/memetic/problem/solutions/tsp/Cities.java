package uk.co.ticklethepanda.memetic.problem.solutions.tsp;

import java.util.Arrays;
import java.util.Iterator;

import uk.co.ticklethepanda.memetic.gui.ProblemType;
import uk.co.ticklethepanda.memetic.problem.Problem;

public class Cities<E extends City<E>> implements Problem<Tour>, Iterable<E> {
  /**
   * Calculated distances for the problem.
   */
  private final int[][] cd;

  /**
   * The cities used for the problem.
   */
  private final E[] cities;

  /**
   * The maximum distance between two cities. Gives a fitness value.
   */
  private final int maximumDistance;

  private final String problemName;

  /**
   * Creates a symmetric EUCLID_TSP using the cities and using the default cache value.
   * 
   * @param cities
   *          the cities to use for the problem
   */
  public Cities(final E[] cities) {
    this(cities, null);
  }

  /**
   * Creates a symmetric EUCLID_TSP using the cities and using the default cache value.
   * 
   * @param points
   *          the cities to use for the problem
   */
  public Cities(final E[] points, String problemName) {
    this.cities = points.clone();
    maximumDistance = calculateMaximumDistance(points);
    cd = new int[points.length][points.length];
    this.problemName = problemName;
  }

  private int calculateMaximumDistance(final E[] cities) {
    int maxDist = 0;
    for (int i = 0; i < cities.length; i++) {
      for (int j = i + 1; j < cities.length; j++) {
        final int dist = (int) Math.round(cities[i].distance(cities[j]));
        if (maxDist < dist) {
          maxDist = dist;
        }
      }
    }
    return maxDist;
  }

  @Override
  public int actualValue(final Tour solution) {
    return calculateValue(solution);
  }

  /**
   * Calculates the actual fitness of the solution.
   * 
   * @param solution
   *          the solution to calculate the value for
   * @return the fitness of the solution
   */
  private int calculateValue(final Tour solution) {
    int sum = 0;

    for (int i = 0; i < solution.size(); i++) {
      final int cityIndexA = solution.get(i);
      final int cityIndexB = solution.get((i + 1) % solution.size());
      int dist = 0;
      if (cd[cityIndexA][cityIndexB] != 0) {
        dist = cd[cityIndexA][cityIndexB];
      } else {
        final E cityA = cities[cityIndexA];
        final E cityB = cities[cityIndexB];
        dist = cityA.distance(cityB);
        cd[cityIndexA][cityIndexB] = dist;
      }
      sum += dist;
    }
    return sum;
  }

  @Override
  public Cities<E> copy() {
    return new Cities<E>(this.cities, problemName);
  }

  @Override
  public Cities<E> copy(boolean cacheEnabled) {
    return new Cities<E>(this.cities, problemName);
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
    final Cities<E> other = (Cities<E>) obj;
    if (!Arrays.equals(cities, other.cities)) {
      return false;
    }
    return true;
  }

  @Override
  public int evaluateFitness(final Tour tour) {
    return maximumDistance * cities.length - actualValue(tour);
  }

  public E getCity(final int index) {
    return cities[index];
  }

  @Override
  public ProblemType getProblemType() {
    return ProblemType.EUCLID_TSP;
  }

  @Override
  public boolean hasCache() {
    return false;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + Arrays.hashCode(cities);
    return result;
  }

  @Override
  public int size() {
    return cities.length;
  }

  @Override
  public String getProblemName() {
    if (this.problemName == null) {
      return String.valueOf(this.hashCode());
    }
    return problemName;
  }

  public int distance(int currCity, int newCity) {
    if (cd[currCity][newCity] == 0) {
      cd[currCity][newCity] = cities[currCity].distance(cities[newCity]);
    }
    return cd[currCity][newCity];
  }

  @Override
  public Iterator<E> iterator() {
    return new Iterator<E>() {
      int i = 0;

      @Override
      public E next() {
        return getCity(i++);
      }

      @Override
      public boolean hasNext() {
        return i < size();
      }
    };
  }
}
