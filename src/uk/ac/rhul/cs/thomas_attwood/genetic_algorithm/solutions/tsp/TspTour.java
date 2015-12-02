package uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.solutions.tsp;

import java.util.Arrays;
import java.util.Random;

import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.solutions.Solution;

/**
 * Defines a tour for EUCLID_TSP.
 *
 */
public class TspTour implements Solution<TspTour> {

	/**
	 * The cached actual value.
	 */
	private int objectiveValue;
	/**
	 * The tour order.
	 */
	private final int[] tour;
	/**
	 * The cached fitness.
	 */
	private int fitness;

	/**
	 * The function used to evaluate the fitness and objective values.
	 */
	private final TspFunction<?> function;

	/**
	 * The hash value for the solution.
	 */
	private int hash;
	/**
	 * The RNG for generating mutations.
	 */
	private final Random random;

	/**
	 * Creates the TspTour with the tour and the function, the seed is set as
	 * <code>System.nanoTime()</code>.
	 * 
	 * @param tour
	 *            the actual tour
	 * @param function
	 *            the function to evaluate the tour by
	 */
	public TspTour(final int[] tour, final TspFunction<?> function) {
		this(System.nanoTime(), tour, function);

	}

	/**
	 * Creates the TspTour with the seed, the tour and the function.
	 * 
	 * @param seed
	 *            the seed for mutations to be generated with
	 * @param tour
	 *            the actual tour
	 * @param function
	 *            the function to evaluate the tour by
	 */
	public TspTour(final long seed, final int[] tour,
			final TspFunction<?> function) {
		this.function = function;
		this.tour = tour;

		random = new Random(seed);
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		final TspTour other = (TspTour) obj;
		if (!Arrays.equals(tour, other.tour)) {
			return false;
		}
		return true;
	}

	/**
	 * Gets the specified "city index" the index.
	 * 
	 * @param index
	 *            the index of the city index
	 * @return the specified "city index" the index
	 */
	public int get(final int index) {
		return tour[index];
	}

	@Override
	public int getObjectiveValue() {
		if (objectiveValue == 0) {
			objectiveValue = function.actualValue(this);
		}
		return objectiveValue;
	}

	@Override
	public int getFitness() {
		if (fitness == 0) {
			fitness = function.evaluateFitness(this);
		}
		return fitness;
	}

	@Override
	public TspTour getMutated() {
		final int swap1 = random.nextInt(tour.length);
		final int swap2 = random.nextInt(tour.length);

		final int[] tspArrayMutated = new int[tour.length];

		for (int i = 0; i < tour.length; i++) {
			if (i == swap1) {
				tspArrayMutated[i] = tour[swap2];
			} else if (i == swap2) {
				tspArrayMutated[i] = tour[swap1];
			} else {
				tspArrayMutated[i] = tour[i];
			}
		}

		return new TspTour(tspArrayMutated, function);
	}

	@Override
	public int hashCode() {
		if (hash != 0) {
			return hash;
		} else {
			final int prime = 31;
			int result = 1;
			result = prime * result + Arrays.hashCode(tour);
			hash = result;
			return result;
		}
	}

	@Override
	public int size() {
		return tour.length;
	}

	@Override
	public String toString() {
		final int maxLen = 3;
		return "TSPSolution [array="
				+ (tour != null ? Arrays.toString(Arrays.copyOf(tour,
						Math.min(tour.length, maxLen))) : null) + "]";
	}

	@Override
	public TspFunction<?> getFitnessFunction() {
		return this.function;
	}

}
