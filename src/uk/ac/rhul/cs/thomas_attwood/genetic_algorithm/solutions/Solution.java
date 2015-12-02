package uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.solutions;

/**
 * Defines the methods to store, mutate and find the fitness of a solution.
 *
 * @param <E>
 *          the solution type
 */
public interface Solution<E extends Solution<E>> {

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
  FitnessFunction<E> getFitnessFunction();

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