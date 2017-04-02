package uk.co.ticklethepanda.genetic_algorithms.binary_functions.n5;

import uk.co.ticklethepanda.genetic_algorithms.genetics.GeneticAlgorithm;
import uk.co.ticklethepanda.genetic_algorithms.genetics.SimulationVariables;

public class N5BinaryFunctionDriver {
	public static final int GENERATIONS = 1000;
	public static final int POOL_SIZE = 10;
	public static final int ELITE_SAVE = 1;
	public static final int NUMBER_OF_PARENTS = 2;
	public static final float REPLACE_RATE = 0.1f;
	public static final float MUTATION_RATE = 0.5f;
	public static final int RERUN_FIXED_GENERATIONS = 100;

	public static void main(String args[]) {
		SimulationVariables sv = new SimulationVariables(ELITE_SAVE,
				MUTATION_RATE, REPLACE_RATE, GENERATIONS, NUMBER_OF_PARENTS,
				RERUN_FIXED_GENERATIONS);
		N5BinarySolutionFactory bsf = new N5BinarySolutionFactory(POOL_SIZE);
		N5BinaryFitnessFunction bff = new N5BinaryFitnessFunction();
		GeneticAlgorithm<N5BinarySolution> ga = new GeneticAlgorithm<N5BinarySolution>(
				bsf, bff, sv);
		ga.runAlgorithm();
		ga.getBest().printSolution();
	}
}
