package uk.co.ticklethepanda.memetic.gui.view;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JSplitPane;

import uk.co.ticklethepanda.memetic.gui.helpers.GenerationData;
import uk.co.ticklethepanda.memetic.problem.solutions.Solution;

public class SimulationView<E extends Solution<E>> extends JPanel {

  /**
   *
   */
  private static final long serialVersionUID = 1L;
  /**
   * Create the panel.
   */
  private final SolutionView<E> sv;
  private final GraphView gv;

  long prevTime = 0;

  public SimulationView(final SolutionView<E> sv) {
    gv = new GraphView();
    this.sv = sv;

    setLayout(new BorderLayout(0, 0));

    final JSplitPane splitPane = new JSplitPane();
    splitPane.setResizeWeight(0.8);
    add(splitPane);
    splitPane.setLeftComponent(gv);
    splitPane.setRightComponent(sv);

  }

  public void setTitle(final String title) {
    gv.setTitle(title);
  }

  public void updateGenerationData(final GenerationData<E> gd) {

    this.gv.addNextValues(gd.getTotalRunningTime(), gd.getBestSolution().getObjectiveValue());
    if (System.currentTimeMillis() - prevTime > 100) {
      this.sv.setSolution(gd.getBestSolution());
      prevTime = System.currentTimeMillis();
    }
  }

}
