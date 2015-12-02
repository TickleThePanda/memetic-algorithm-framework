package uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.gui.view;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JSplitPane;

import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.gui.model.GenerationData;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.solutions.Solution;

public class SimulationView<E extends Solution<E>> extends JPanel {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  /**
   * Create the panel.
   */
  private final SolutionView<E> sv;
  private GraphView gv;

  long prevTime = 0;

  public SimulationView(final SolutionView<E> sv) {
    gv = new GraphView();
    this.sv = sv;

    setLayout(new BorderLayout(0, 0));

    JSplitPane splitPane = new JSplitPane();
    splitPane.setResizeWeight(0.8);
    add(splitPane);
    splitPane.setLeftComponent(gv);
    splitPane.setRightComponent(sv);

  }

  public void setTitle(String title) {
    gv.setTitle(title);
  }

  public void updateGenerationData(final GenerationData<E> gd) {

    this.gv.addNextValues(gd.getTotalRunningTime(), gd.getBestSolution()
        .getObjectiveValue());
    if (System.currentTimeMillis() - prevTime > 100) {
      this.sv.setSolution(gd.getBestSolution());
      prevTime = System.currentTimeMillis();
    }
  }

}
