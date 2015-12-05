package uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.function_generators;

import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.solutions.Problem;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.solutions.Solution;

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