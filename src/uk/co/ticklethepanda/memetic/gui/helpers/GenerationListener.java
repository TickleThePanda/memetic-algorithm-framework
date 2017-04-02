package uk.co.ticklethepanda.memetic.gui.helpers;

import uk.co.ticklethepanda.memetic.problem.solutions.Solution;

/**
 * A listener that listens for the end of a generation in an evolutionary algorithm.
 *
 * @param <E>
 *          the type of solution to be listened to
 */
public interface GenerationListener<E extends Solution<E>> {

  /**
   * The method that is completed when the generation has completed.
   *
   * @param gd
   *          the data about the current generation.
   */
  void generationComplete(GenerationData<E> gd);
}