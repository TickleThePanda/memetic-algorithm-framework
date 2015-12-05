package uk.co.ticklethepanda.memetic.algorithms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

import uk.co.ticklethepanda.memetic.problem.Problem;
import uk.co.ticklethepanda.memetic.problem.solutions.Solution;

/**
 * An implementation of an
 * {@link uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.EvolutionaryAlgorithm
 * EvolutionaryAlgorithm}. It is one of the core classes of this project. It
 * optimises a solution without a step of local improvement.
 *
 * @param <E>
 *            The type of solution that the algorithm improves.
 */
public class GeneticAlgorithm<E extends Solution<E>> implements EvoAlgorithm<E> {

	/**
	 * Compares the fitness of two solutions.
	 */
	public final class SolutionComparitor implements Comparator<E> {
		@Override
		public int compare(final E arg0, final E arg1) {
			return arg1.getFitness() - arg0.getFitness();
		}
	}

	/**
	 * The number of generations left before duplicates are removed.
	 */
	private int countToRemoveDuplicates;

	/**
	 * The genetic algorithm settings.
	 */
	private final EvoAlgorithmSettings settings;

	/**
	 * This RNG is used to select parents.
	 */
	private final Random random;

	/**
	 * Completes the crossover and initialises the population.
	 */
	private final Solution.Generator<E> solutionFactory;

	/**
	 * The current population of solutions that the algorithm is improving.
	 */
	protected ArrayList<E> population;

	/**
	 * Constructs a new GeneticAlgorithm with a random seed, the
	 * {@link uk.uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.algorithms.EvoAlgorithmSettings
	 * EvoAlgorithmSettings}, and the
	 * {@link uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.solutions.Generator
	 * SolutionFactory}. The seed is to generate random numbers for selection.
	 *
	 * @param settings
	 *            the settings that the genetic algorithm uses to run
	 * @param solutionFactory
	 *            the solution factory to use to generate and crossover
	 *            solutions
	 */
	public GeneticAlgorithm(final EvoAlgorithmSettings settings,
			final Solution.Generator<E> solutionFactory) {
		this(System.nanoTime(), settings, solutionFactory);
	}

	/**
	 * Constructs a new GeneticAlgorithm with the seed, the
	 * {@link uk.uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.algorithms.EvoAlgorithmSettings
	 * EvoAlgorithmSettings}, and the
	 * {@link uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.solutions.Generator
	 * SolutionFactory}.
	 *
	 * @param seed
	 *            the seed that the algorithm uses for selection
	 * @param settings
	 *            the settings that the genetic algorithm uses to run
	 * @param solutionFactory
	 *            the solution factory to use to generate and crossover
	 *            solutions
	 */
	public GeneticAlgorithm(final long seed,
			final EvoAlgorithmSettings settings,
			final Solution.Generator<E> solutionFactory) {
		this.settings = settings;
		this.random = new Random(seed);
		this.solutionFactory = solutionFactory;
		this.countToRemoveDuplicates = settings.getStepsBeforeRemDups();
	}

	/**
	 * Constructs a new GeneticAlgorithm with the seed, the
	 * {@link uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.solutions.Generator
	 * SolutionFactory}, and the default
	 * {@link uk.uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.algorithms.EvoAlgorithmSettings
	 * EvoAlgorithmSettings}. The seed is to generate random numbers for
	 * selection.
	 *
	 * @param seed
	 *            the seed that the algorithm uses for selection
	 * @param solutionFactory
	 *            the solution factory to use to generate and crossover
	 *            solutions
	 */
	public GeneticAlgorithm(final long seed,
			final Solution.Generator<E> solutionFactory) {
		this(seed, EvoAlgorithmSettings.DEFAULT_SETTINGS, solutionFactory);
	}

	/**
	 * Constructs a new GeneticAlgorithm with a random seed, the
	 * {@link uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.solutions.Generator
	 * SolutionFactory}, and the default
	 * {@link uk.uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.algorithms.EvoAlgorithmSettings
	 * EvoAlgorithmSettings} The seed is to generate random numbers for
	 * selection.
	 *
	 * @param solutionFactory
	 *            the solution factory to use to generate and crossover
	 *            solutions
	 */
	public GeneticAlgorithm(final Solution.Generator<E> solutionFactory) {
		this(EvoAlgorithmSettings.DEFAULT_SETTINGS, solutionFactory);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.EvoAlgorithm#doGenerationStep
	 * ()
	 */
	@Override
	public boolean doAlgorithmStep() {
		// on first step
		if(this.population == null) {
			generatePool(this.getSettings().getPoolSize());
		}
		
		this.mutatePool();
		this.generateChildren();

		if (this.countToRemoveDuplicates == 0) {
			this.removeDuplicates();
			this.countToRemoveDuplicates = this.getSettings()
					.getStepsBeforeRemDups();
		} else if (this.countToRemoveDuplicates > 0) {
			this.countToRemoveDuplicates--;
		}
		return false;
	}

	/**
	 * Generates the children for the algorithm.
	 */
	public void generateChildren() {
		this.sortByFitness();

		final ArrayList<E> childPool = new ArrayList<E>();

		for (int i = 0; i < this.getSettings().getElitismSize(); i++) {
			childPool.add(this.population.get(i));
		}

		for (int i = 0; i < this.getSettings().getCrossoverSize(); i++) {
			// select parents
			final E parent1 = this.selectSolutionRoulette();
			final E parent2 = this.selectSolutionRoulette();
			childPool.add(this.solutionFactory.crossover(parent1, parent2));
		}

		for (int i = childPool.size(); i < getSettings().getPoolSize(); i++) {
			childPool.add(this.selectSolutionRoulette());
		}

		this.population = childPool;
	}

	/**
	 * Generates the first population for the algorithm.
	 *
	 * @param size
	 *            this is the size of the population to be created.
	 */
	public void generatePool(final int size) {
		final ArrayList<E> pbs = new ArrayList<E>();
		for (int i = 0; i < size; i++) {
			pbs.add(this.solutionFactory.generateSolution());
		}
		this.population = pbs;
	}

	@Override
	public Problem<E> getFitnessFunction() {
		return this.solutionFactory.getFitnessFunction();
	}

	@Override
	public EvoAlgorithmSettings getSettings() {
		return settings;
	}

	@Override
	public E getSolution(final int index) {
		return this.population.get(index);
	}

	/**
	 * Replaces the population with a mutated population, without damaging the
	 * elite population.
	 */
	public void mutatePool() {
		for (int i = settings.getElitismSize(); i < this.population.size(); i++) {
			if (this.random.nextFloat() <= settings.getMutationRate()) {
				this.population.get(i).getMutated();
			}
		}
	}

	/**
	 * Removes duplicate solutions within the population.
	 */
	private void removeDuplicates() {
		this.sortByFitness();

		for (int i = 0; i < this.population.size(); i++) {
			for (int j = i + 1; j < this.population.size(); j++) {
				final int iFitness = this.population.get(i).getFitness();
				final int jFitness = this.population.get(j).getFitness();
				// assume if same fitness, same solution.
				if (iFitness == jFitness) {
					this.population.remove(j);
					j--;
				}
			}
		}

		for (int i = this.population.size(); i < this.getSettings()
				.getPoolSize(); i++) {
			this.population.add(this.solutionFactory.generateSolution());
		}
	}

	/**
	 * select a parent from the population using a weighted distribution, where
	 * the weight is the fitness of each solution.
	 *
	 * @return a parent from the population.
	 */
	private E selectSolutionRoulette() {
		final float totalFitness = getTotalFitness();
		int currentSum = 0;
		E parent = null;
		final int parent1CutoffFitness = (int) (this.random.nextFloat() * totalFitness);
		for (int i = 0; i < this.population.size(); i++) {
			currentSum += this.population.get(i).getFitness();
			if (currentSum >= parent1CutoffFitness) {
				parent = this.population.get(i);
				break;
			}
		}
		if (parent == null) {
			parent = this.population.get(this.population.size() - 1);
		}
		return parent;
	}

	@Override
	public int size() {
		return this.population.size();
	}

	@Override
	public void sortByFitness() {
		Collections.sort(this.population, new SolutionComparitor());
	}

}
