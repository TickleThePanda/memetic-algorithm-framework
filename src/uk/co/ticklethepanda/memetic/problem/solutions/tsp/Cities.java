package uk.co.ticklethepanda.memetic.problem.solutions.tsp;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import uk.co.ticklethepanda.memetic.gui.ProblemType;
import uk.co.ticklethepanda.memetic.problem.Problem;

public class Cities<E extends City<E>> implements Problem<Tour>, Iterable<E> {
  /**
   * Calculated distance matrix for the problem.
   */
  private final int[][] cityDistanceMatrix;

  /**
   * The cities used for the problem.
   */
  private final List<E> cities;

  /**
   * The maximum distance between any two cities. This is used to calculate a fitness value.
   */
  private final int maximumDistance;

  /**
   * The name given to the problem.
   */
  private final String problemName;

  /**
   * Creates a symmetric EUCLID_TSP using the cities and using the default cache value.
   *
   * @param cities
   *          the cities to use for the problem
   */
  public Cities(final List<E> cities) {
    this(cities, null);
  }

  /**
   * Creates a symmetric EUCLID_TSP using the cities and using the default cache value.
   *
   * @param points
   *          the cities to use for the problem
   * @param name
   *          the name given to the problem
   */
  public Cities(final List<E> points, final String problemName) {
    this.cities = new ArrayList<E>(points);
    this.maximumDistance = calculateMaximumDistance(points);
    this.cityDistanceMatrix = new int[points.size()][points.size()];
    this.problemName = problemName;
  }

  @Override
  public int actualValue(final Tour solution) {
    return calculateFitness(solution);
  }

  private int calculateMaximumDistance(final List<E> cities) {
    int maxDist = 0;
    for (int i = 0; i < cities.size(); i++) {
      for (int j = i + 1; j < cities.size(); j++) {
        final int dist = Math.round(cities.get(i).distance(cities.get(j)));
        if (maxDist < dist) {
          maxDist = dist;
        }
      }
    }
    return maxDist;
  }

  /**
   * Calculates the actual fitness of the solution.
   *
   * @param solution
   *          the solution to calculate the value for
   * @return the fitness of the solution
   */
  private int calculateFitness(final Tour solution) {
    int sum = 0;

    for (int i = 0; i < solution.size(); i++) {
      final int firstCity = solution.get(i);
      final int secondCity = solution.get((i + 1) % solution.size());
      
      sum += getDistanceBetweenCities(firstCity, secondCity);
    }
    
    return sum;
  }

  @Override
  public Cities<E> copy() {
    return new Cities<E>(this.cities, problemName);
  }

  public int getDistanceBetweenCities(final int firstCity, final int secondCity) {
    if (cityDistanceMatrix[firstCity][secondCity] == 0) {
      cityDistanceMatrix[firstCity][secondCity] = cities.get(firstCity).distance(cities.get(secondCity));
    }
    return cityDistanceMatrix[firstCity][secondCity];
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
    if (obj instanceof Cities) {
      @SuppressWarnings("unchecked")
      final Cities<E> other = (Cities<E>) obj;
      if (!cities.equals(other.cities)) {
        return false;
      }
    } else {
      return false;
    }
    return true;
  }

  @Override
  public int evaluateFitness(final Tour tour) {
    return maximumDistance * cities.size() - actualValue(tour);
  }

  public E getCity(final int index) {
    return cities.get(index);
  }

  @Override
  public String getProblemName() {
    if (this.problemName == null) {
      return String.valueOf(this.hashCode());
    }
    return problemName;
  }

  @Override
  public ProblemType getProblemType() {
    return ProblemType.EUCLID_TSP;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + cities.hashCode();
    return result;
  }

  @Override
  public Iterator<E> iterator() {
    return cities.iterator();
  }

  @Override
  public int size() {
    return cities.size();
  }
}
