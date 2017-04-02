package uk.co.ticklethepanda.genetic_algorithms.binary_functions.n5;

import uk.co.ticklethepanda.genetic_algorithms.binary_functions.BinaryFitnessFunction;
import uk.co.ticklethepanda.genetic_algorithms.binary_functions.BinarySolution;

public class N5BinaryFitnessFunction implements
		BinaryFitnessFunction<N5BinarySolution> {
	
	private static final int[] function = new int[]{5, -10, 100, 15, -2};

	@Override
	public int evaluateFitness(N5BinarySolution solution) {
		int sum = 0;
		for(int i = 0; i < solution.getEquationSize(); i++)
			sum += BinarySolution.boolToInt(solution.getBoolList()[i]) * function[i];
		return sum + 12;
	}

	@Override
	public int maximumFitness() {
		return 132;
	}
}
