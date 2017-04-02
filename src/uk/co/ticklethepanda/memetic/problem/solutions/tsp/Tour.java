package uk.co.ticklethepanda.memetic.problem.solutions.tsp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import uk.co.ticklethepanda.memetic.algorithms.NearestNeighbourAlgorithm;
import uk.co.ticklethepanda.memetic.gui.ProblemType;
import uk.co.ticklethepanda.memetic.problem.Problem;
import uk.co.ticklethepanda.memetic.problem.solutions.Solution;

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
     * Creates a TspTourFactory with the seed <code>System.nanoTime()</code> and the function.
     *
     * @param function
     *          the function that the solutions are being generated for
     */
    public Generator(final Cities<?> function) {
      this(System.nanoTime(), function);
    }

    public Generator(final Cities<?> function, final boolean useNearestNeighbour) {
      this(System.nanoTime(), function, useNearestNeighbour);
    }

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

    public Generator(final long seed, final Cities<?> function, final boolean useNearestNeighbour) {
      random = new Random(seed);
      cities = function;
      this.useNearestNeighbour = useNearestNeighbour;
    }

    @Override
    public Tour crossover(final Tour mother, final Tour father) {
      final List<Integer> child = new ArrayList<Integer>(mother.size());

      // crossover length
      final int crossoverStart = random.nextInt(cities.size() - 2);
      final int crossoverEnd = crossoverStart + random.nextInt(cities.size() - crossoverStart);
      
      child.addAll(mother.subTour(crossoverStart, crossoverEnd));

      for(int i = 0; i < father.size(); i++) {
        final int city = father.get(i);
        if (!child.contains(city)) {
          child.add(city);
        }
      }

      return new Tour(child, cities);
    }

    /**
     * Generates a random solution.
     *
     * @return a random solution
     */
    public Tour generateRandomTour() {
      final List<Integer> newTour = new ArrayList<>(cities.size());
      for (int i = 0; i < this.cities.size(); i++) {
        newTour.add(i);
      }

      for (int i = 0; i < this.cities.size(); i++) {
        final int swapLoc = random.nextInt(this.cities.size());
        final Integer temp = newTour.get(swapLoc);
        newTour.set(swapLoc, newTour.get(i));
        newTour.set(i, temp);
      }

      final Tour solution = new Tour(newTour, this.cities);
      return solution;
    }

    @Override
    public Tour generateSolution() {
      Tour solution;
      if (useNearestNeighbour) {
        solution = new NearestNeighbourAlgorithm(cities, random.nextInt(cities.size())).doAlgorithm();
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
  private final List<Integer> tour;
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
   *          the actual tour
   * @param function
   *          the function to evaluate the tour by
   */
  @Deprecated
  public Tour(final Integer[] tour, final Cities<?> function) {
    this(System.nanoTime(), Arrays.asList(tour), function);
  }
  
  /**
   * Creates the TspTour with the tour and the function, the seed is set as
   * <code>System.nanoTime()</code>.
   * 
   * @param tour
   *          the actual tour
   * @param function
   *          the function to evaluate the tour by
   */
  public Tour(final List<Integer> tour, final Cities<?> function) {
    this(System.nanoTime(), tour, function);
  }

  /**
   * Creates the TspTour with the seed, the tour and the function.
   * 
   * @param seed
   *          the seed for mutations to be generated with
   * @param tour
   *          the actual tour
   * @param function
   *          the function to evaluate the tour by
   */
  @Deprecated
  public Tour(final long seed, final Integer[] tour, final Cities<?> function) {
    this(seed, Arrays.asList(tour), function);
  }
  
  /**
   * Creates the TspTour with the seed, the tour and the function.
   * 
   * @param seed
   *          the seed for mutations to be generated with
   * @param tour
   *          the actual tour
   * @param function
   *          the function to evaluate the tour by
   */
  public Tour(final long seed, final List<Integer> tour, final Cities<?> function) {
    cities = function;
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
    return this.cities.equals(((Tour) obj).cities);
  }


  /**
   * Gets the specified "city index" the index.
   * 
   * @param index
   *          the index of the city index
   * @return the specified "city index" the index
   */
  public int get(final int index) {
    return tour.get(index);
  }

  @Override
  public int getFitness() {
    if (fitness == 0) {
      fitness = cities.evaluateFitness(this);
    }
    return fitness;
  }

  @Override
  public Cities<?> getFitnessFunction() {
    return cities;
  }

  @Override
  public Tour getMutated() {
    final int swap1 = random.nextInt(tour.size());
    final int swap2 = random.nextInt(tour.size());

    final List<Integer> mutatedTour = new ArrayList<Integer>(tour);
    
    Integer value1 = tour.get(swap1);
    Integer value2 = tour.get(swap2);
    
    mutatedTour.set(swap2, value1);
    mutatedTour.set(swap1, value2);

    return new Tour(mutatedTour, cities);
  }

  @Override
  public int getObjectiveValue() {
    if (objectiveValue == 0) {
      objectiveValue = cities.actualValue(this);
    }
    return objectiveValue;
  }

  @Override
  public int hashCode() {
    if (hash != 0) {
      return hash;
    } else {
      final int prime = 31;
      int result = 1;
      result = prime * result + tour.hashCode();
      hash = result;
      return result;
    }
  }

  @Override
  public int size() {
    return tour.size();
  }

  public List<Integer> subTour(int crossoverStart, int crossoverEnd) {
    return tour.subList(crossoverStart, crossoverEnd);
  }

  @Override
  public String toString() {
    return "TSPSolution [array=" + (tour != null
        ? tour.toString() : null) + "]";
  }

}
