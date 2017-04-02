package uk.co.ticklethepanda.memetic.gui.controller;

import javax.swing.SwingUtilities;

import uk.co.ticklethepanda.memetic.algorithms.Algorithm;
import uk.co.ticklethepanda.memetic.gui.helpers.GenerationData;
import uk.co.ticklethepanda.memetic.gui.helpers.GenerationListener;
import uk.co.ticklethepanda.memetic.gui.helpers.SimulationModel;
import uk.co.ticklethepanda.memetic.gui.view.SimulationView;
import uk.co.ticklethepanda.memetic.gui.view.SolutionView;
import uk.co.ticklethepanda.memetic.problem.solutions.Solution;

/**
 * Presents the progress of the algorithm that is currently running.
 *
 * @param <E>
 *          the type of problem being completed
 */
public class SimulationPresenter<E extends Solution<E>> {

  /**
   * Listener for the generations being completed to update the GUI.
   */
  private final class GenerationComplete implements GenerationListener<E> {
    @Override
    public void generationComplete(final GenerationData<E> gd) {
      SimulationPresenter.this.view.updateGenerationData(gd);
    }
  }

  /**
   * The SimulationModel that is running in the background.
   */
  private final SimulationModel<E> model;

  /**
   * The SimulationView that is being used to display the GUI.
   */
  private final SimulationView<E> view;

  /**
   * Creates a new simulation with the algorithm and solution view. The algorithm does not run
   * automatically and is started with <code>model.run</code>
   *
   * @param algorithm
   *          the algorithm to run
   * @param solutionView
   *          the view that will show the current solution
   */
  public SimulationPresenter(final Algorithm<E> algorithm, final SolutionView<E> solutionView) {

    this.view = new SimulationView<E>(solutionView);

    model = new SimulationModel<E>(algorithm);

    model.addGenerationListener(new GenerationComplete());
  }

  /**
   * Gets the simulation model.
   *
   * @return the simulation model.
   */
  public SimulationModel<E> getModel() {
    return model;
  }

  /**
   * Gets the simulation view.
   *
   * @return the simulation view.
   */
  public SimulationView<E> getView() {
    return view;
  }

  /**
   * Sets the visability of the simulation view.
   *
   * @param bool
   *          whether the view is visible
   */
  public void setVisible(final boolean bool) {
    SwingUtilities.invokeLater(() -> SwingUtilities.getRoot(view).setVisible(true));
  }

  /**
   * Stops the simulation from running.
   */
  public void stopSimulation() {
    model.setRunning(false);
  }

}
