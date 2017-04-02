package uk.co.ticklethepanda.genetic_algorithms.genetics;


public interface Solution {

	public abstract void mutateSolution(float mutationProbablility);
	public abstract void printSolution();
	public abstract <E extends Solution> E duplicate();

}