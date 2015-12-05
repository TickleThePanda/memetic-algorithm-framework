package uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.solutions.tsp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.ProblemType;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.concrete_algorithms.TspNnAlgorithm;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.solutions.Problem;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.solutions.Solution;

/**
 * Defines a tour for EUCLID_TSP.
 *
 */
public class Tour implements Solution<Tour> {
  
  /**
   * Defines a TspTourFactory that generates TspTours.
   */
  public static class Generator implements Solution.Generator<Tour> {

    /**
     * The fitness function the tours are being created for.
     */
    private final Cities<?> cities;
    /**
     * The RNG used to generate the tours.
     */
    private final Random random;
    private final boolean useNearestNeighbour;

    /**
     * Creates a TspTourFactory with the seed and the function.
     * 
     * @param seed
     *          the seed used to generate the children and crossover
     * @param function
     *          the function that the solutions are being generated for
     */
    public Generator(final long seed, final Cities<?> function) {
      this(seed, function, true);
    }

    /**
     * Creates a TspTourFactory with the seed <code>System.nanoTime()</code> and the function.
     * 
     * @param function
     *          the function that the solutions are being generated for
     */
    public Generator(final Cities<?> function) {
      this(System.nanoTime(), function);
    }

    public Generator(Cities<?> function, boolean useNearestNeighbour) {
      this(System.nanoTime(), function, useNearestNeighbour);
    }

    public Generator(final long seed, final Cities<?> function, boolean useNearestNeighbour) {
      this.random = new Random(seed);
      this.cities = function;
      this.useNearestNeighbour = useNearestNeighbour;
    }

    @Override
    public Tour crossover(final Tour parent1, final Tour parent2) {

      final int[] child = new int[cities.size()];

      // crossover length
      final int crossoverPoint1 = random.nextInt(cities.size() - 2);
      final int crossoverPoint2 = crossoverPoint1 + random.nextInt(cities.size() - crossoverPoint1);

      // find point at which parent2 has the same value as in parent1[0]
      int startingLoc = 0;
      for (int i = 0; i < parent2.size(); i++) {
        if (parent2.get(i) == parent1.get(0)) {
          startingLoc = i;
          break;
        }
      }

      final int diff = crossoverPoint2 - crossoverPoint1;

      final List<Integer> visited = new ArrayList<Integer>();
      // we want a list of cities that we have visited.
      for (int i = 0; i < diff; i++) {
        final int city = parent1.get(crossoverPoint1 + i);
        visited.add(city);
        child[i] = city;
      }

      int childIndex = diff;
      int parentIndex = 0;
      while (childIndex < child.length) {
        final int realParentIndex =
            (startingLoc + crossoverPoint2 + parentIndex + 1) % cities.size();
        final int city = parent2.get(realParentIndex);

        if (visited.contains(city) == false) {
          child[childIndex++] = city;
        }

        parentIndex++;
      }

      return new Tour(child, cities);
    }

    /**
     * Generates a random solution.
     * 
     * @return a random solution
     */
    public Tour generateRandomTour() {
      final int[] cities = new int[this.cities.size()];
      for (int i = 0; i < this.cities.size(); i++) {
        cities[i] = i;
      }

      for (int i = 0; i < this.cities.size(); i++) {
        int swapLoc = random.nextInt(this.cities.size());
        int temp = cities[swapLoc];
        cities[swapLoc] = cities[i];
        cities[i] = temp;
      }

      final Tour solution = new Tour(cities, this.cities);
      return solution;
    }

    @Override
    public Tour generateSolution() {
      Tour solution;
      if (useNearestNeighbour) {
        solution = new TspNnAlgorithm(cities, random.nextInt(cities.size())).doAlgorithm();
      } else {
        solution = generateRandomTour();
      }
      return solution;
    }

    @Override
    public Problem<Tour> getFitnessFunction() {
      return cities;
    }

    @Override
    public ProblemType getProblemType() {
      return cities.getProblemType();
    }

  }

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
	private final Cities<?> cities;

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
	public Tour(final int[] tour, final Cities<?> function) {
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
	public Tour(final long seed, final int[] tour,
			final Cities<?> function) {
		this.cities = function;
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
		final Tour other = (Tour) obj;
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
			objectiveValue = cities.actualValue(this);
		}
		return objectiveValue;
	}

	@Override
	public int getFitness() {
		if (fitness == 0) {
			fitness = cities.evaluateFitness(this);
		}
		return fitness;
	}

	@Override
	public Tour getMutated() {
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

		return new Tour(tspArrayMutated, cities);
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
	public Cities<?> getFitnessFunction() {
		return this.cities;
	}

}
