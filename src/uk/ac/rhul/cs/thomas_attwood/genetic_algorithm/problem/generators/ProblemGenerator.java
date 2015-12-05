package uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.problem.generators;

import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.problem.Problem;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.problem.solutions.Solution;

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