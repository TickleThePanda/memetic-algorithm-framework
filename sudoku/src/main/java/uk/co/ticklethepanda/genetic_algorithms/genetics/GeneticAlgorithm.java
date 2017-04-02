package uk.co.ticklethepanda.genetic_algorithms.genetics;

import java.util.ArrayList;

/**
 * This class contains the genetic algorithm loop.
 *
 * @param <T>
 *            The type of GeneticSolition that the algorithm will use.
 * @param <FitnessFunction<T>> The type of FitnessFunction that the algorithm
 *        will use.
 */
public class GeneticAlgorithm<T extends Solution> {
	/**
	 * The genetic solution that the algorithm will use.
	 */
	final SolutionFactory<T> geneticSolutionFactoryBase;
	/**
	 * The fitness function that the algorithm will use.
	 */
	final FitnessFunction<T> fitnessFunction;
	/**
	 * The genetic pool that the algorithm uses to store and sort its items.
	 */
	final GeneticPool<T> geneticPool;

	/**
	 * The generation that the algorithm has reached.
	 */
	int generation;

	T bestSolution;
	private final SimulationVariables variables;
	private int generationsSinceChange;

	/**
	 * The default constructor to create a genetic algorithm.
	 * 
	 * @param geneticSolutionFactoryBase
	 *            The genetic solution that the algorithm will use.
	 * @param fitnessFunction
	 *            The fitness function that the algorithm will use.
	 * @param variables
	 *            The simulation rates.
	 */
	public GeneticAlgorithm(SolutionFactory<T> geneticSolutionFactoryBase,
			FitnessFunction<T> fitnessFunction, SimulationVariables variables) {
		this.variables = variables;
		this.geneticSolutionFactoryBase = geneticSolutionFactoryBase;
		this.fitnessFunction = fitnessFunction;
		generation = 0;
		geneticPool = new GeneticPool<T>(geneticSolutionFactoryBase,
				fitnessFunction, variables);
	}

	public GeneticAlgorithm(SolutionFactory<T> geneticSolutionFactoryBase,
			ArrayList<T> best, FitnessFunction<T> fitnessFunction,
			SimulationVariables rates) {
		this.variables = rates;
		this.geneticSolutionFactoryBase = geneticSolutionFactoryBase;
		this.fitnessFunction = fitnessFunction;
		generation = 0;
		geneticPool = new GeneticPool<T>(geneticSolutionFactoryBase, best,
				fitnessFunction, rates);
	}

	/**
	 * Prints out the statistics to the console.
	 */
	private void displayStats() {
		T best = geneticPool.getPool().get(0);
		T worst = geneticPool.getPool().get(geneticPool.getPool().size() - 1);

		long bestFitness = fitnessFunction.evaluateFitness(best);
		long averageFitness = geneticPool.getAverageFitness();
		long worstFitness = fitnessFunction.evaluateFitness(worst);

		System.out.println("best: " + bestFitness);
		System.out.println("aver: " + averageFitness);
		System.out.println("wors: " + worstFitness);
		System.out.println("size: " + geneticPool.getPool().size());
		System.out.println("gen : " + generation);
		System.out.println("--------------------");

		// best.printSolution();
		// System.out.println("--------------------");
	}

	/**
	 * 
	 * @return whether the algorithm was successful in finding the correct
	 *         solution.
	 */
	public void runAlgorithm() {
		int highestFitness = 0;
		int previousHighestFitness = 0;
		generationsSinceChange = 0;
		do {
			geneticPool.createNewGeneration();
			geneticPool.mutateGrids();
			geneticPool.replaceRandomGrids();
			geneticPool.sortByFitness();

			displayStats();
			
			previousHighestFitness = highestFitness;
			highestFitness = fitnessFunction.evaluateFitness(geneticPool
					.getPool().get(0));
			generation++;
		} while (generation < variables.getGenerations()
				|| maximumFitnessReached(highestFitness)
				|| fitnessUnchanged(previousHighestFitness, highestFitness));
		bestSolution = geneticPool.getPool().get(0);
	}

	private boolean fitnessUnchanged(int previousHighestFitness,
			int highestFitness) {
		if (variables.getRerunCondition() != -1) {
			if (previousHighestFitness == highestFitness) {
				generationsSinceChange++;
			} else {
				generationsSinceChange = 0;
			}
			if (generationsSinceChange == variables.getRerunCondition()) {
				return true;
			}
		}
		return false;
	}

	private boolean maximumFitnessReached(int highestFitness) {
		if (fitnessFunction.maximumFitness() != -1
				&& highestFitness == fitnessFunction.maximumFitness()) {
			bestSolution = geneticPool.getPool().get(0);
			return true;
		}
		return false;
	}

	public T getBest() {
		return bestSolution;
	}

}
