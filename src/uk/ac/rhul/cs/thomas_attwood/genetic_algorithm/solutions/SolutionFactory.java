package uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.solutions;

import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.ProblemType;

/**
 * Creates randomly generated solutions and crossover between them.
 *
 * @param <E>
 *          the solution to be generated
 */
public interface SolutionFactory<E extends Solution<E>> {
  /**
   * Creates a crossover between the two solutions provided.
   * 
   * @param solution1
   *          the first parent solution
   * @param solution2
   *          the second parent solution
   * @return the crossover between
   */
  E crossover(E solution1, E solution2);

  /**
   * Generates a new solution using either heuristics or randomly.
   * 
   * @return a new solution
   */
  E generateSolution();

  /**
   * Gets the fitness function that the solutions are generated for.
   * 
   * @return the fitness function that the solutions are generated for
   */
  FitnessFunction<E> getFitnessFunction();

  /**
   * Gets the type of problem that the factory is generating for.
   * 
   * @return the type of problem tha the factory is generating for.
   */
  ProblemType getProblemType();

}