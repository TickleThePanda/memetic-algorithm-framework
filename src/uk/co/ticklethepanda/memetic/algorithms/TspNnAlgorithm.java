package uk.co.ticklethepanda.memetic.algorithms;

import java.util.ArrayList;

import uk.co.ticklethepanda.memetic.gui.ProblemType;
import uk.co.ticklethepanda.memetic.problem.Problem;
import uk.co.ticklethepanda.memetic.problem.solutions.tsp.Cities;
import uk.co.ticklethepanda.memetic.problem.solutions.tsp.Tour;

/**
 * An implementation of Nearest Neighbour on EUCLID_TSP.
 */
public class TspNnAlgorithm implements Algorithm<Tour> {

	/**
	 * The function that the algorithm has to find a solution for.
	 */
	private final Cities<?> cities;

	/**
	 * The list of unvisited cities.
	 */
	ArrayList<Integer> unvisitedCities = new ArrayList<Integer>();
	/**
	 * The list of visited cities.
	 */
	ArrayList<Integer> visitedCities = new ArrayList<Integer>();

	/**
	 * Creates a new TspNnAlgorithm with the EUCLID_TSP function, and the
	 * default starting location of 0.
	 * 
	 * @param function
	 *            the function to be used to find a solution for
	 */
	public TspNnAlgorithm(final Cities<?> function) {
		this(function, 0);
	}

	/**
	 * Creates a new TspNnAlgorithm with the EUCLID_TSP function, and the
	 * starting location.
	 * 
	 * @param function
	 *            the function to be used to find a solution for
	 * @param startingCity
	 *            the starting location for algorithm
	 */
	public TspNnAlgorithm(final Cities<?> function, final int startingCity) {
		this.cities = function;
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
		final int[] order = new int[visitedCities.size()
				+ unvisitedCities.size()];
		int solutionCityIndex = 0;
		for (final int city : visitedCities) {
			order[solutionCityIndex++] = city;
		}
		for (final int city : unvisitedCities) {
			order[solutionCityIndex++] = city;
		}
		return new Tour(order, cities);
	}

	/**
	 * Runs the whole nearest neighbour and then returns the solution.
	 * 
	 * @return the solution
	 */
	public Tour doAlgorithm() {
		while (true)
			if (doAlgorithmStep())
				break;
		return createSolution();
	}

	@Override
	public boolean doAlgorithmStep() {
		if (unvisitedCities.size() == 0) {
			return true;
		}

		int bestCity = unvisitedCities.get(0);

		final int currCity = visitedCities.get(visitedCities.size() - 1);

		for (final int newCity : unvisitedCities) {
			if (cities.distance(currCity, newCity) < cities.distance(
					currCity, bestCity)) {
				bestCity = newCity;
			}
		}

		unvisitedCities.remove((Integer) bestCity);
		visitedCities.add(bestCity);
		return false;
	}

	@Override
	public Tour getBestSolution() {
		return createSolution();
	}

	@Override
	public Problem<Tour> getFitnessFunction() {
		return cities;
	}

}
