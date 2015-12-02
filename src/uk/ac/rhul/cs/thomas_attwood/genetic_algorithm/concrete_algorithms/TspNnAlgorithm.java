package uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.concrete_algorithms;

import java.util.ArrayList;

import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.Algorithm;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.AlgorithmType;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.ProblemType;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.solutions.FitnessFunction;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.solutions.tsp.TspFunction;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.solutions.tsp.TspTour;

/**
 * An implementation of Nearest Neighbour on EUCLID_TSP.
 */
public class TspNnAlgorithm implements Algorithm<TspTour> {

	/**
	 * The function that the algorithm has to find a solution for.
	 */
	private final TspFunction<?> function;

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
	public TspNnAlgorithm(final TspFunction<?> function) {
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
	public TspNnAlgorithm(final TspFunction<?> function, final int startingCity) {
		this.function = function;
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
	private TspTour createSolution() {
		final int[] order = new int[visitedCities.size()
				+ unvisitedCities.size()];
		int solutionCityIndex = 0;
		for (final int city : visitedCities) {
			order[solutionCityIndex++] = city;
		}
		for (final int city : unvisitedCities) {
			order[solutionCityIndex++] = city;
		}
		return new TspTour(order, function);
	}

	/**
	 * Runs the whole nearest neighbour and then returns the solution.
	 * 
	 * @return the solution
	 */
	public TspTour doAlgorithm() {
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
			if (function.distance(currCity, newCity) < function.distance(
					currCity, bestCity)) {
				bestCity = newCity;
			}
		}

		unvisitedCities.remove((Integer) bestCity);
		visitedCities.add(bestCity);
		return false;
	}

	@Override
	public AlgorithmType getAlgorithmType() {
		return AlgorithmType.NEAREST_NEIGHBOUR;
	}

	@Override
	public TspTour getBestSolution() {
		return createSolution();
	}

	@Override
	public FitnessFunction<TspTour> getFitnessFunction() {
		return function;
	}

	@Override
	public ProblemType getProblemType() {
		return ProblemType.EUCLID_TSP;
	}

}
