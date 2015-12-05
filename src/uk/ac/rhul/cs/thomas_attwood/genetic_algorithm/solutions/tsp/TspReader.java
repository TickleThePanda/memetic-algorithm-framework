package uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.solutions.tsp;

import java.awt.geom.Point2D;
import java.io.File;
import java.io.IOException;

import org.moeaframework.problem.tsplib.DistanceTable;
import org.moeaframework.problem.tsplib.NodeCoordinates;
import org.moeaframework.problem.tsplib.TSPInstance;

public class TspReader {

	public TspReader() {

	}

	public Cities<?> readProblem(File file) {
		TSPInstance instance = null;
		try {
			instance = new TSPInstance(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		DistanceTable dt = instance.getDistanceTable();
		Cities fit = null;
		switch (instance.getEdgeWeightType()) {
		case EUC_2D: {
			NodeCoordinates nc = ((NodeCoordinates) dt);
			City.Euclidian[] points = new City.Euclidian[nc.size()];

			for (int i = 1; i <= nc.size(); i++) {
				double x = nc.get(i).getPosition()[0];
				double y = nc.get(i).getPosition()[1];
				City.Euclidian point = new City.Euclidian(x, y);
				points[i - 1] = point;
			}

			fit = new Cities<City.Euclidian>(points, file.getName());
			break;
		}
		case GEO: {
			NodeCoordinates nc = ((NodeCoordinates) dt);
			City.Geo[] points = new City.Geo[nc.size()];
			for (int i = 1; i <= nc.size(); i++) {
				double x = nc.get(i).getPosition()[0];
				double y = nc.get(i).getPosition()[1];
				City.Geo point = new City.Geo(x, y);
				
				points[i - 1] = point;
			}
			fit = new Cities<City.Geo>(points, file.getName());
			break;
		}
		}
		return fit;
	}
}
