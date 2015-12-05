package uk.co.ticklethepanda.memetic.problem.generators;

import uk.co.ticklethepanda.memetic.problem.Problem;
import uk.co.ticklethepanda.memetic.problem.solutions.Solution;

/**
 * A class that generates a fitness function for a solution.
 *
 * @param <S>
 *          the type of solution that the fitness function acts upon.
 * @param <E>
 *          the type of fitness function that generated.
 */
public interface ProblemGenerator<S extends Solution<S>, E extends Problem<S>> {
  /**
   * this method generates a random a fitness function.
   *
   * @return a random fitness function.
   */
  E generateFunction();
}