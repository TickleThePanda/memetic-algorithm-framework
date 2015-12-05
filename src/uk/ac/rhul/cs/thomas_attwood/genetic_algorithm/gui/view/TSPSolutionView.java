package uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.gui.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;

import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.problem.solutions.tsp.Cities;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.problem.solutions.tsp.City;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.problem.solutions.tsp.Tour;

public class TSPSolutionView extends SolutionView<Tour> {
	/**
   *
   */
	private static final long serialVersionUID = 1L;

	private static final int DEFAULT_SIZE = 500;

	private static final float DEFAULT_CITY_SIZE = 2.5f / DEFAULT_SIZE; // px

	private Tour solution;

	public TSPSolutionView() {
		setPreferredSize(new Dimension(DEFAULT_SIZE, DEFAULT_SIZE));
		solution = null;
	}

	@Override
  public void paint(final Graphics g) {
    super.paint(g);

    // Set anti-alias!
    ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,
        RenderingHints.VALUE_ANTIALIAS_ON);

    g.setColor(Color.GRAY);

    if (solution != null) {
      final int xmargin = 30;
      final int ymargin = 30;

      final int width = getWidth() - xmargin;
      final int height = getHeight() - ymargin;

      final Cities<?> ff = solution.getFitnessFunction();

      int minX = Integer.MAX_VALUE;
      int minY = Integer.MAX_VALUE;

      int maxX = Integer.MIN_VALUE;
      int maxY = Integer.MIN_VALUE;

      for (final City<?> c : ff) {
        if (c.getX() < minX) {
          minX = (int) c.getX();
        } else if (c.getX() > maxX) {
          maxX = (int) c.getX();
        }

        if (c.getY() < minY) {
          minY = (int) c.getY();
        } else if (c.getY() > maxY) {
          maxY = (int) c.getY();
        }
      }

      final int diffX = maxX - minX;
      final int diffY = maxY - minY;

      final float scaleFactorX = (float) width / (float) diffX;
      final float scaleFactorY = (float) height / (float) diffY;

      final float scaleFactor = Math.min(scaleFactorX, scaleFactorY);

      for (int i = 0; i < solution.size(); i++) {
    	  
        final double startx = (int) ff.getCity(solution.get(i)).getX();
        final double starty = (int) ff.getCity(solution.get(i)).getY();
        
        final double endx = ff.getCity(solution.get((i + 1) % solution.size())).getX();
        final double endy = ff.getCity(solution.get((i + 1) % solution.size())).getY();

        final double drawStartx = startx * scaleFactor + xmargin / 2f;
        final double drawStarty = starty * scaleFactor + ymargin / 2f;
        final double drawEndx = endx * scaleFactor + xmargin / 2f;
        final double drawEndy = endy * scaleFactor + ymargin / 2f;

        final Shape line =
            new Line2D.Double(drawStartx, drawStarty, drawEndx, drawEndy);
        ((Graphics2D) g).draw(line);
      }

      g.setColor(Color.BLACK);

      for (final City<?> c : ff) {
        double startx = c.getX();
        double starty = c.getY();

        final double drawStartx = startx * scaleFactor + xmargin / 2f;
        final double drawStarty = starty * scaleFactor + ymargin / 2f;

        final double size = Math.min(width, height);

        final double actualCitySize = DEFAULT_CITY_SIZE * size;

        final Shape circle =
            new Ellipse2D.Double(drawStartx - actualCitySize / 2f, drawStarty
                - actualCitySize / 2f, actualCitySize, actualCitySize);
        ((Graphics2D) g).fill(circle);
      }
    }
  }

	@Override
	public void setSolution(final Tour solution) {
		this.solution = solution;
		this.repaint();
	}

}
