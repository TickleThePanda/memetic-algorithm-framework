package uk.co.ticklethepanda.memetic.gui.helpers;

import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingWorker;

import uk.co.ticklethepanda.memetic.algorithms.Algorithm;
import uk.co.ticklethepanda.memetic.problem.solutions.Solution;

/**
 * Model for an algorithm to run asynchronously from the GUI.
 *
 * @param <E>
 *          the type of solution that the algortithm is running on
 */
public class SimulationModel<E extends Solution<E>> extends SwingWorker<Void, GenerationData<E>> {

  /**
   * The algorithm that the model will be running.
   */
  private final Algorithm<E> algorithm;
  /**
   * The generation listeners that will be reported to whenever a generation is completed.
   */
  List<GenerationListener<E>> generationListeners = new ArrayList<GenerationListener<E>>();

  /**
   * Whether the algorithm is running or not.
   */
  private boolean run = true;

  /**
   * Creates a new SimulationModel with the algorithm.
   *
   * @param algorithm
   *          the algorithm that will be ran
   */
  public SimulationModel(final Algorithm<E> algorithm) {
    this.algorithm = algorithm;
  }

  /**
   * Adds a listener that will be activated whenever a generation is completed.
   *
   * @param toAdd
   */
  public void addGenerationListener(final GenerationListener<E> toAdd) {
    this.generationListeners.add(toAdd);
  }

  @Override
  protected Void doInBackground() {
    final long startTime = System.currentTimeMillis();
    while (this.run) {

      this.algorithm.doAlgorithmStep();

      // create step data
      final GenerationData<E> generationData = new GenerationData<E>(
          this.algorithm.getBestSolution(), System.currentTimeMillis() - startTime);

      // publish step data
      publish(generationData);

    }
    return null;
  }

  @Override
  protected void done() {

  }

  /**
   * Notifies generation listeners that a generation is complete.
   *
   * @param
   */
  private void notifyGenerationListeners(final GenerationData<E> generationData) {
    for (final GenerationListener<E> gl : this.generationListeners) {
      gl.generationComplete(generationData);
    }
  }

  @Override
  protected void process(final List<GenerationData<E>> generationDataList) {
    for (final GenerationData<E> generationData : generationDataList) {
      this.notifyGenerationListeners(generationData);
    }
  }

  /**
   * Sets the state of running of this algorithm. Algorithm will stop when the next generation is
   * complete.
   *
   * @param bool
   *          whether the algorithm is to run
   */
  public void setRunning(final boolean bool) {
    this.run = false;

  }
}
