package uk.co.ticklethepanda.memetic.gui.view;

import javax.swing.JPanel;

import uk.co.ticklethepanda.memetic.problem.solutions.Solution;

public abstract class SolutionView<E extends Solution<E>> extends JPanel {

  /**
   *
   */
  private static final long serialVersionUID = 1L;

  public abstract void setSolution(E solution);

}
