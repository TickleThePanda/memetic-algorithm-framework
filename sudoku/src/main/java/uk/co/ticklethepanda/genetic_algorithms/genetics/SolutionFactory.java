package uk.co.ticklethepanda.genetic_algorithms.genetics;

import java.util.ArrayList;

public interface SolutionFactory<T extends Solution> {
	public abstract T generateSolution();
	public abstract T combineSolutions(ArrayList<T> solution);
	
	public abstract int getPoolSize();
}
