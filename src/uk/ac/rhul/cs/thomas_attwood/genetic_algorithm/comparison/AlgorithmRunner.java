package uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.comparison;

import java.util.concurrent.Callable;

import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.Algorithm;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.solutions.Solution;

/**
 * Runs an {@link uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.Algorithm
 * Algorithm} recording metrics and enforces certain constraints.
 *
 * @param <E>
 *            the type of problem that the algorithm runner will be running on
 */
public class AlgorithmRunner<E extends Solution<E>> implements
		Callable<CompletedAlgorithm> {

	/**
	 * The default value of <code>repeats</code>, symbolising the algorithm will
	 * not check for a repetition of the same solution.
	 */
	private static final int NO_REPEATS = -1;
	/**
	 * The time budget in milliseconds for the algorithm.
	 */
	private final int budget;
	/**
	 * The algorithm that this is running.
	 */
	private final Algorithm<E> algorithm;
	/**
	 * The number of times the algorithm can have the same best solution before
	 * it ends.
	 */
	private final int repeats;

	public int getSize() {
		return algorithm.getFitnessFunction().size();
	}

	public Algorithm<E> getAlgorithm() {
		return algorithm;
	}

	/**
	 * Constructs a new AlgorithmRunner with the algorithm that it will be
	 * running, the budget constraint, but no number of repeats constraint.
	 * 
	 * @param algorithm
	 *            the algorithm that this will be running on
	 * @param budget
	 *            the time budget in milliseconds for the algorithm
	 */
	public AlgorithmRunner(final Algorithm<E> algorithm, final int budget) {
		this(algorithm, budget, NO_REPEATS);
	}

	/**
	 * Constructs a new AlgorithmRunner with the algorithm that it will be
	 * running, the budget constraint, and the number of repeats constraint.
	 * 
	 * @param algorithm
	 *            the algorithm that this will be running on
	 * @param budget
	 *            the time budget in milliseconds for the algorithm
	 * @param repeats
	 *            the number of times the algorithm can have the same best
	 *            solution before it ends
	 */
	public AlgorithmRunner(final Algorithm<E> algorithm, final int budget,
			final int repeats) {
		this.repeats = repeats;
		this.budget = budget;
		this.algorithm = algorithm;
	}

	@Override
	public CompletedAlgorithm call() throws Exception {
		final long startTime = System.currentTimeMillis();

		long finishTime = System.currentTimeMillis();
		int generationCount = 0;
		int repeatCount = 0;
		int lastFitness = 0;

		boolean finished = false;

		do {
			// do genetation
			finished = this.algorithm.doAlgorithmStep();
			generationCount++;

			if (this.algorithm.getBestSolution().getFitness() == lastFitness
					&& this.repeats > 0) {
				repeatCount++;
			} else {
				repeatCount = 0;
			}

			lastFitness = this.algorithm.getBestSolution().getFitness();
			finishTime = System.currentTimeMillis();

		} while (finishTime - startTime < this.budget
				&& (repeatCount < this.repeats || this.repeats == NO_REPEATS)
				&& !finished);

		final long timeTaken = finishTime - startTime;

		return new CompletedAlgorithm(algorithm, new AlgorithmMetrics(
				timeTaken, generationCount));

	}
}
