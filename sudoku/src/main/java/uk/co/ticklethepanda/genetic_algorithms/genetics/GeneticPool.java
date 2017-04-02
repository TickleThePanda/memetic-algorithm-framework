package uk.co.ticklethepanda.genetic_algorithms.genetics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

public class GeneticPool<E extends Solution> {

	private final FitnessFunction<E> fitnessFunction;
	private final SolutionFactory<E> solutionFactory;
	private final SimulationVariables rates;

	ArrayList<E> pool;
	private static Random random = new Random();

	public GeneticPool(SolutionFactory<E> gpg, FitnessFunction<E> ff,
			SimulationVariables rates) {
		this.rates = rates;
		this.fitnessFunction = ff;
		this.solutionFactory = gpg;
		pool = new ArrayList<E>();
		for (int i = 0; i < gpg.getPoolSize(); i++) {
			pool.add(gpg.generateSolution());
		}
	}

	public GeneticPool(SolutionFactory<E> gpg, ArrayList<E> best,
			FitnessFunction<E> ff, SimulationVariables rates) {
		this.rates = rates;
		this.fitnessFunction = ff;
		this.solutionFactory = gpg;
		pool = new ArrayList<E>();
		for (E element : best) {
			System.out.println(element.hashCode());
			pool.add(element.duplicate());
		}
		for (int i = best.size(); i < gpg.getPoolSize(); i++) {
			pool.add(gpg.generateSolution());
		}
	}

	public void replaceRandomGrids() {
		for (int i = rates.getEliteSaved(); i < pool.size(); i++) {
			if (random.nextFloat() < rates.getReplaceRate())
				pool.set(i, solutionFactory.generateSolution());
		}
	}

	public void createNewGeneration() {
		ArrayList<E> parents = new ArrayList<E>();
		for (int i = 0; i < rates.getNumberOfParents(); i++) {
			parents.add(selectRandomParent());
		}

		for (int i = rates.getEliteSaved(); i < pool.size(); i++) {
			pool.set(i, solutionFactory.combineSolutions(parents));
		}
	}

	public int getAverageFitness() {
		int sum = 0;
		for (int i = 0; i < pool.size(); i++) {
			sum += fitnessFunction.evaluateFitness(pool.get(i));
		}
		return sum / pool.size();
	}

	public ArrayList<E> getTopValues(int lowerBound) {
		ArrayList<E> newList = new ArrayList<E>();
		for (int i = 0; i < lowerBound; i++) {
			newList.add(pool.get(i));
		}
		return newList;
	}

	public int getTotalFitness() {
		int totalFitness = 0;
		for (int i = 0; i < pool.size(); i++)
			totalFitness += fitnessFunction.evaluateFitness(pool.get(i));
		return totalFitness;
	}

	public void mutateGrids() {
		for (int i = rates.getEliteSaved(); i < pool.size(); i++) {
			pool.get(i).mutateSolution(rates.getMutationRate());
		}
	}

	public E selectRandomParent() {
		int totalFitness = getTotalFitness();
		int selectedFitness = random.nextInt(totalFitness);
		int sum = 0;

		for (int i = 0; i < pool.size(); i++) {
			sum += fitnessFunction.evaluateFitness(pool.get(i));
			if (sum > selectedFitness) {
				return pool.get(i);
			}
		}
		return null;
	}

	public void sortByFitness() {
		Comparator<E> c = new SolutionFitnessComparitor<E>(fitnessFunction);
		Collections.sort(pool, c);
	}

	public ArrayList<E> getPool() {
		return pool;
	}

	public void replaceDuplicates() {
		for (int i = 0; i < pool.size() - 1; i++) {
			if (pool.get(i).equals(pool.get(i + 1)))
				pool.set(i + 1, solutionFactory.generateSolution());
		}
	}

}
