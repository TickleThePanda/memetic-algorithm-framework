package uk.co.ticklethepanda.memetic.problem;

import uk.co.ticklethepanda.memetic.gui.ProblemType;
import uk.co.ticklethepanda.memetic.problem.solutions.Solution;

/**
 * this defines a generic fitness function that gives the fitness of a solution
 * E.
 *
 * @param <E>
 *            the type of solution that is being evaluated.
 */
public interface Problem<E extends Solution<E>> {

	int actualValue(E solution);

	Problem<E> copy();

	Problem<E> copy(boolean cacheEnabled);
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