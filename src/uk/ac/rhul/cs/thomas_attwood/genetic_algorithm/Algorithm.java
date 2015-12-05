package uk.ac.rhul.cs.thomas_attwood.genetic_algorithm;

import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.solutions.Problem;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.solutions.Solution;

/**
 * A generic algorithm.
 *
 * @param <E>
 *            The solution type that the evolutionary algorithm is optimising.
 */
public interface Algorithm<E extends Solution<E>> {

	/**
	 * Completes a whole step (or generation) for the algorithm.
	 *
	 * @return whether the algorithm is complete
	 */
	boolean doAlgorithmStep();

	/**
	 * Gets the algorithm type.
	 * 
	 * @return the algorithm type
	 */
	AlgorithmType getAlgorithmType();

	/**
	 * Gets the best solution that the algorithm has generated.
	 *
	 * @return the best solution that the algorithm has generated
	 */
	E getBestSolution();

	/**
	 * Gets the fitness function that the algorithm is creating a solution for.
	 * 
	 * @return the fitness function
	 */
	Problem<E> getFitnessFunction();

	/**
	 * Gets the problem type that the algorithm is creating a solution for.
	 * 
	 * @return the problem type
	 */
	ProblemType getProblemType();

}