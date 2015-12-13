package uk.co.ticklethepanda.memetic.problem;

import uk.co.ticklethepanda.memetic.gui.ProblemType;
import uk.co.ticklethepanda.memetic.problem.solutions.Solution;

/**
 * this defines a generic fitness function that gives the fitness of a solution E.
 *
 * @param <E>
 *          the type of solution that is being evaluated.
 */
public interface Problem<E extends Solution<E>> {

  int actualValue(E solution);

  Problem<E> copy();

  /**
   *
   * @param solution
   *          the solution to be evaluated
   * @return the fitness of the solution.
   */
  int evaluateFitness(E solution);

  String getProblemName();

  ProblemType getProblemType();

  @Override
  int hashCode();

  int size();
}