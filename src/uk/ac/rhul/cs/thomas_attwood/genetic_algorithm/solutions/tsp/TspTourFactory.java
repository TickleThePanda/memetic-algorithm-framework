package uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.solutions.tsp;

import java.util.ArrayList;
import java.util.Random;

import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.ProblemType;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.concrete_algorithms.TspNnAlgorithm;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.solutions.FitnessFunction;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.solutions.SolutionFactory;

/**
 * Defines a TspTourFactory that generates TspTours.
 */
public class TspTourFactory implements SolutionFactory<TspTour> {

  /**
   * The fitness function the tours are being created for.
   */
  private final TspFunction<?> function;
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
  public TspTourFactory(final long seed, final TspFunction<?> function) {
    this(seed, function, true);
  }

  /**
   * Creates a TspTourFactory with the seed <code>System.nanoTime()</code> and the function.
   * 
   * @param function
   *          the function that the solutions are being generated for
   */
  public TspTourFactory(final TspFunction<?> function) {
    this(System.nanoTime(), function);
  }

  public TspTourFactory(EuclidTspFunction function, boolean useNearestNeighbour) {
    this(System.nanoTime(), function, useNearestNeighbour);
  }

  public TspTourFactory(final long seed, final TspFunction<?> function, boolean useNearestNeighbour) {
    random = new Random(seed);
    this.function = function;
    this.useNearestNeighbour = useNearestNeighbour;
  }

  @Override
  public TspTour crossover(final TspTour parent1, final TspTour parent2) {

    final int[] child = new int[function.size()];

    // crossover length
    final int crossoverPoint1 = random.nextInt(function.size() - 2);
    final int crossoverPoint2 = crossoverPoint1 + random.nextInt(function.size() - crossoverPoint1);

    // find point at which parent2 has the same value as in parent1[0]
    int startingLoc = 0;
    for (int i = 0; i < parent2.size(); i++) {
      if (parent2.get(i) == parent1.get(0)) {
        startingLoc = i;
        break;
      }
    }

    final int diff = crossoverPoint2 - crossoverPoint1;

    final ArrayList<Integer> visited = new ArrayList<Integer>();
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
          (startingLoc + crossoverPoint2 + parentIndex + 1) % function.size();
      final int city = parent2.get(realParentIndex);

      if (visited.contains(city) == false) {
        child[childIndex++] = city;
      }

      parentIndex++;
    }

    return new TspTour(child, function);
  }

  /**
   * Generates a random solution.
   * 
   * @return a random solution
   */
  public TspTour generateRandomSolution() {
    final int[] cities = new int[function.size()];
    for (int i = 0; i < function.size(); i++) {
      cities[i] = i;
    }

    for (int i = 0; i < function.size(); i++) {
      int swapLoc = random.nextInt(function.size());
      int temp = cities[swapLoc];
      cities[swapLoc] = cities[i];
      cities[i] = temp;
    }

    final TspTour solution = new TspTour(cities, function);
    return solution;
  }

  @Override
  public TspTour generateSolution() {
    TspTour solution;
    if (useNearestNeighbour) {
      solution = new TspNnAlgorithm(function, random.nextInt(function.size())).doAlgorithm();
    } else {
      solution = generateRandomSolution();
    }
    return solution;
  }

  @Override
  public FitnessFunction<TspTour> getFitnessFunction() {
    return function;
  }

  @Override
  public ProblemType getProblemType() {
    return function.getProblemType();
  }

}
