package uk.co.ticklethepanda.genetic_algorithms.genetics;

/**
 * This interface contains all of the methods required for analysing a solution
 * of a given type.
 *
 * @param <T>
 *            The GeneticSolition that the methods affect.
 */
public interface FitnessFunction<T extends Solution> {
	/**
	 * This method analyses a GeneticSolution's quality and returns an integer.
	 * A larger returned value signifies a better quality solution.
	 * 
	 * @param solution
	 *            the GeneticSolution to be analysed for it's quality.
	 * @return the quality of the solution. A larger value signifies a better
	 *         quality solution. This value should be positive.
	 */
	public abstract int evaluateFitness(T solution);

	/**
	 * This method returns the maximum fitness that is possible. If the maximum
	 * fitness is unknown, the value should be -1.
	 * 
	 * @return
	 */
	public abstract int maximumFitness();
}
