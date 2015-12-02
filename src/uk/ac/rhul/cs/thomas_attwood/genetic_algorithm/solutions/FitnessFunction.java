package uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.solutions;

import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.ProblemType;

/**
 * this defines a generic fitness function that gives the fitness of a solution
 * E.
 *
 * @param <E>
 *            the type of solution that is being evaluated.
 */
public interface FitnessFunction<E extends Solution<E>> {

	int actualValue(E solution);

	FitnessFunction<E> copy();

	FitnessFunction<E> copy(boolean cacheEnabled);
	/**
	 *
	 * @param solution
	 *            the solution to be evaluated
	 * @return the fitness of the solution.
	 */
	int evaluateFitness(E solution);

	ProblemType getProblemType();

	boolean hasCache();

	@Override
	int hashCode();

	int size();

	String getProblemName();
}