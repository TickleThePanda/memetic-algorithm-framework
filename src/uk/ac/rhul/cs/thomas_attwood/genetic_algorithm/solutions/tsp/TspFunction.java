package uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.solutions.tsp;

import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.solutions.FitnessFunction;

public interface TspFunction<E extends TspPoint<E>> extends FitnessFunction<TspTour>, Iterable<E> {

	int distance(int currCity, int newCity);

	E getCity(int i);
	
}
