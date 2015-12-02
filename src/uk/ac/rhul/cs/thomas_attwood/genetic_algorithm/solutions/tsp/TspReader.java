package uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.solutions.tsp;

import java.awt.geom.Point2D;
import java.io.File;
import java.io.IOException;

import org.moeaframework.problem.tsplib.DistanceTable;
import org.moeaframework.problem.tsplib.NodeCoordinates;
import org.moeaframework.problem.tsplib.TSPInstance;

import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.solutions.FitnessFunction;

public class TspReader {

	public TspReader() {

	}

	public TspFunction readProblem(File file) {
		TSPInstance instance = null;
		try {
			instance = new TSPInstance(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		DistanceTable dt = instance.getDistanceTable();
		TspFunction fit = null;
		switch (instance.getEdgeWeightType()) {
		case EUC_2D: {
			NodeCoordinates nc = ((NodeCoordinates) dt);
			EuclidPoint[] points = new EuclidPoint[nc.size()];

			for (int i = 1; i <= nc.size(); i++) {
				double x = nc.get(i).getPosition()[0];
				double y = nc.get(i).getPosition()[1];
				EuclidPoint point = new EuclidPoint(x, y);
				points[i - 1] = point;
			}

			fit = new EuclidTspFunction(points, file.getName());
			break;
		}
		case GEO: {
			NodeCoordinates nc = ((NodeCoordinates) dt);
			Point2D[] points = new Point2D[nc.size()];
			for (int i = 1; i <= nc.size(); i++) {
				double x = nc.get(i).getPosition()[0];
				double y = nc.get(i).getPosition()[1];
				Point2D point = new Point2D.Double(x, y);
				points[i - 1] = point;
			}
			fit = new GeoTspFunction(points, file.getName());
			break;
		}
		}
		return fit;
	}
}
