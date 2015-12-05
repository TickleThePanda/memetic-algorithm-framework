package uk.co.ticklethepanda.memetic.problem.solutions;

import uk.co.ticklethepanda.memetic.gui.ProblemType;
import uk.co.ticklethepanda.memetic.problem.Problem;

/**
 * Defines the methods to store, mutate and find the fitness of a solution.
 *
 * @param <E>
 *          the solution type
 */
public interface Solution<E extends Solution<E>> {

  /**
   * Creates randomly generated solutions and crossover between them.
   *
   * @param <E>
   *          the solution to be generated
   */
  public static interface Generator<E extends Solution<E>> {
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
    Problem<E> getFitnessFunction();

    /**
     * Gets the type of problem that the factory is generating for.
     * 
     * @return the type of problem tha the factory is generating for.
     */
    ProblemType getProblemType();

  }

  /**
   * Gets the objective value of the solution according to fitness function.
   * 
   * @return the objective value of the solution
   */
  int getObjectiveValue();

  /**
   * Gets the fitness value of the solution according to fitness function.
   * 
   * @return the fitness value of the solution
   */
  int getFitness();

  /**
   * Gets the fitness function that defines the solution's fitness.
   * 
   * @return the fitness value of the solution
   */
  Problem<E> getFitnessFunction();

  /**
   * Gets a new solution which is mutated.
   * 
   * @return a new solution which is mutated.
   */
  E getMutated();

  /**
   * Gets the size of the solution.
   * 
   * @return the size of the solution.
   */
  int size();
}