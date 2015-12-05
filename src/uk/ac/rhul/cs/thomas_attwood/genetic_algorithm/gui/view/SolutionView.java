package uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.gui.view;

import javax.swing.JPanel;

import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.problem.solutions.Solution;

public abstract class SolutionView<E extends Solution<E>> extends JPanel {

  /**
   *
   */
  private static final long serialVersionUID = 1L;

  public abstract void setSolution(E solution);

}
