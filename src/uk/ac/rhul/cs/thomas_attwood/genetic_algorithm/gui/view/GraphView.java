package uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.gui.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Shape;
import java.awt.geom.Line2D;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class GraphView extends JPanel {

  public static class GraphViewData {
    private final XYSeries maxFitnessSeries;

    public GraphViewData() {
      maxFitnessSeries = new XYSeries("Maximum Fitness");
    }

    public void addNextValues(final long timeValue, final int maxFitness) {
      maxFitnessSeries.add(timeValue, maxFitness);
    }

    public final XYSeries getMaxFitnessSeries() {
      return maxFitnessSeries;
    }

    public void reset() {
      maxFitnessSeries.clear();
    }
  }

  /**
   * The SerialVersionUID.
   */
  private static final long serialVersionUID = 1L;

  private final GraphViewData data = new GraphViewData();

  private JFreeChart jfc;

  /**
   * Create the panel.
   */
  public GraphView() {
    initialize();
  }

  public void addNextValues(final long runningTime, final int maxFitness) {
    data.addNextValues(runningTime, maxFitness);
  }

  private void initialize() {
    setPreferredSize(new Dimension(300, 300));
    setSize(new Dimension(300, 300));

    this.setLayout(new BorderLayout(0, 0));

    final XYSeriesCollection dataset = new XYSeriesCollection();

    dataset.addSeries(data.getMaxFitnessSeries());

    jfc =
        ChartFactory.createXYLineChart("", "Total Running Time",
            "Objective Value", dataset);

    final ChartPanel chartPanel = new ChartPanel(jfc);

    this.add(chartPanel);
  }

  public void resetData() {
    data.reset();
  }

  public void setTitle(String title) {
    jfc.setTitle(title);

  }
}
