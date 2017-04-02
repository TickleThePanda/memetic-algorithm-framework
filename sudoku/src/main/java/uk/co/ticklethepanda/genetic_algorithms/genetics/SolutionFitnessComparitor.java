package uk.co.ticklethepanda.genetic_algorithms.genetics;

import java.util.Comparator;

public final class SolutionFitnessComparitor<E extends Solution> implements Comparator<E> {
	private final FitnessFunction<E> fitnessFunction;
	
	public SolutionFitnessComparitor(FitnessFunction<E> fitnessFunction) {
		this.fitnessFunction = fitnessFunction;
	}
	
	@Override
	public int compare(E o1, E o2) {
		return fitnessFunction.evaluateFitness(o2)
				- fitnessFunction.evaluateFitness(o1);
	}
}