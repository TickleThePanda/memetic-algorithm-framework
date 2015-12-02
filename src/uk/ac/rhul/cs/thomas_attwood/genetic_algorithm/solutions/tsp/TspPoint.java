package uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.solutions.tsp;

public interface TspPoint<E extends TspPoint<E>> {

	double getX();	
	double getY();
	
	int distance(E city);
}
