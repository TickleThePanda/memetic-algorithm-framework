package uk.co.ticklethepanda.memetic.gui.helpers;

import uk.co.ticklethepanda.memetic.problem.solutions.Solution;

/**
 * Contains the data from a generation.
 *
 * @param <E>
 *          the type of solution running.
 */
public class GenerationData<E extends Solution<E>> {

  /**
   * The best solution from the generation.
   */
  private final E solution;

  /**
   * The total running time of the simulation until now.
   */
  private final long totalRunningTime;

  /**
   * Creates a new GenerationData with the solution and total running time.
   * 
   * @param solution
   *          the best solution from generation
   * @param totalRunningTime
   *          the total running time of the simulation
   */
  public GenerationData(final E solution, final long totalRunningTime) {
    this.totalRunningTime = totalRunningTime;
    this.solution = solution;
  }

  /**
   * Gets the best solution of the generation.
   * 
   * @return the best solution of the generation
   */
  public E getBestSolution() {
    return this.solution;
  }

  /**
   * Gets the total running time of the simulation.
   * 
   * @return the total running time of the simulation
   */
  public long getTotalRunningTime() {
    return this.totalRunningTime;
  }

}
