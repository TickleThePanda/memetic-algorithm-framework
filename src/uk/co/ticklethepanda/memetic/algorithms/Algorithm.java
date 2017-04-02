package uk.co.ticklethepanda.memetic.algorithms;

import uk.co.ticklethepanda.memetic.problem.Problem;
import uk.co.ticklethepanda.memetic.problem.solutions.Solution;

/**
 * A generic algorithm.
 *
 * @param <E>
 *          The solution type that the evolutionary algorithm is optimising.
 */
public interface Algorithm<E extends Solution<E>> {

  /**
   * Completes a whole step (or generation) for the algorithm.
   */
  void doAlgorithmStep();

  /**
   * Returns whether algorithm is complete.
   */
  boolean isAlgorithmComplete();

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