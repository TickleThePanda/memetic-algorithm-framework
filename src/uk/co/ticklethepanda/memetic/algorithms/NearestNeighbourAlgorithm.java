package uk.co.ticklethepanda.memetic.algorithms;

import java.util.ArrayList;
import java.util.List;

import uk.co.ticklethepanda.memetic.problem.Problem;
import uk.co.ticklethepanda.memetic.problem.solutions.tsp.Cities;
import uk.co.ticklethepanda.memetic.problem.solutions.tsp.Tour;

/**
 * An implementation of Nearest Neighbour on EUCLID_TSP.
 */
public class NearestNeighbourAlgorithm implements Algorithm<Tour> {

  /**
   * The function that the algorithm has to find a solution for.
   */
  private final Cities<?> cities;

  /**
   * The list of unvisited cities.
   */
  List<Integer> unvisitedCities = new ArrayList<Integer>();
  /**
   * The list of visited cities.
   */
  List<Integer> visitedCities = new ArrayList<Integer>();

  /**
   * Creates a new TspNnAlgorithm with the EUCLID_TSP function, and the default starting location of
   * 0.
   * 
   * @param function
   *          the function to be used to find a solution for
   */
  public NearestNeighbourAlgorithm(final Cities<?> function) {
    this(function, 0);
  }

  /**
   * Creates a new TspNnAlgorithm with the EUCLID_TSP function, and the starting location.
   * 
   * @param function
   *          the function to be used to find a solution for
   * @param startingCity
   *          the starting location for algorithm
   */
  public NearestNeighbourAlgorithm(final Cities<?> function, final int startingCity) {
    cities = function;
    for (int i = 0; i < function.size(); i++) {
      unvisitedCities.add(i);
    }
    visitedCities.add(unvisitedCities.remove(startingCity));
  }

  /**
   * Create a solution using the visited cities and then the unvisited cities.
   * 
   * @return the solution
   */
  private Tour createSolution() {
    final List<Integer> tour = new ArrayList<>(visitedCities.size() + unvisitedCities.size());
    for (final int city : visitedCities) {
      tour.add(city);
    }
    for (final int city : unvisitedCities) {
      tour.add(city);
    }
    return new Tour(tour, cities);
  }

  /**
   * Runs the whole nearest neighbour and then returns the solution.
   * 
   * @return the solution
   */
  public Tour doAlgorithm() {
    while (isAlgorithmComplete()) {
      doAlgorithmStep();
    }
    return createSolution();
  }

  @Override
  public void doAlgorithmStep() {
    if (isAlgorithmComplete()) {
      return;
    }

    int bestCity = unvisitedCities.get(0);

    final int currCity = visitedCities.get(visitedCities.size() - 1);

    for (final int newCity : unvisitedCities) {
      if (cities.getDistanceBetweenCities(currCity, newCity) < cities
          .getDistanceBetweenCities(currCity, bestCity)) {
        bestCity = newCity;
      }
    }

    unvisitedCities.remove((Integer) bestCity);
    visitedCities.add(bestCity);
  }

  @Override
  public Tour getBestSolution() {
    return createSolution();
  }

  @Override
  public Problem<Tour> getFitnessFunction() {
    return cities;
  }

  @Override
  public boolean isAlgorithmComplete() {
    return unvisitedCities.isEmpty();
  }

}
