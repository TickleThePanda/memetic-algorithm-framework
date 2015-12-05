package uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.algorithms;

import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.gui.ProblemType;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.problem.Problem;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.problem.solutions.Solution;

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

}