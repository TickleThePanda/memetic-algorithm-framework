package uk.co.ticklethepanda.memetic.algorithms;

import uk.co.ticklethepanda.memetic.problem.solutions.Solution;

/**
 * Contains methods for an evolutionary algorithm.
 *
 * @param <E>
 */
public interface EvoAlgorithm<E extends Solution<E>> extends Algorithm<E> {

  @Override
  default E getBestSolution() {
    this.sortByFitness();
    return this.getSolution(0);
  }

  /**
   * Gets the settings that the algorithm is using.
   *
   * @return the settings that the algorithm is using
   */
  EvoAlgorithmSettings getSettings();

  /**
   * Gets the solution at the index within the population.
   *
   * @param index
   *          the index of the solution to get
   * @return the solution at the index
   */
  E getSolution(int index);

  /**
   * Gets the total fitness of the population.
   *
   * @return the total fitness of the population
   */
  default int getTotalFitness() {
    int sum = 0;
    for (int i = 0; i < size(); i++) {
      sum += getSolution(i).getFitness();
    }
    return sum;
  }

  /**
   * Gets the total fitness of the population.
   *
   * @return the total fitness of the population
   */
  default int getTotalValue() {
    int sum = 0;
    for (int i = 0; i < size(); i++) {
      sum += getSolution(i).getObjectiveValue();
    }
    return sum;
  }

  /**
   * Gets the size of the population.
   *
   * @return the size of the population
   */
  int size();

  /**
   * Sort by fitness in ascending order (best solution first).
   */
  void sortByFitness();

}
