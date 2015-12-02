package uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.gui.model;

import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.EvoAlgorithmSettings;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.concrete_algorithms.GeneticAlgorithm;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.concrete_algorithms.MemeticAlgorithm;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.concrete_algorithms.TspNnAlgorithm;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.solutions.LocalImprovement;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.solutions.pseudo_boolean.LinearPbFunctionFactory;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.solutions.pseudo_boolean.PbFunction;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.solutions.pseudo_boolean.PbLocImprov;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.solutions.pseudo_boolean.PbSolution;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.solutions.pseudo_boolean.PbSolutionFactory;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.solutions.pseudo_boolean.QuadraticPbFuncFactory;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.solutions.tsp.EuclidTspFunction;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.solutions.tsp.EuclidTspFunctionFactory;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.solutions.tsp.TspLiMulti;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.solutions.tsp.TspTour;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.solutions.tsp.TspTourFactory;

/**
 * Generates each concrete algorithm on a random problem. Provided to keep type safety when type is
 * unknown at runtime.
 */
public class AlgorithmFactory {

  /**
   * Generates a genetic algorithm, with the settings, on a random linear pseudo-boolean function,
   * with the seed and the size.
   * 
   * @param seed
   *          the seed to generate the problem
   * @param size
   *          the size of the problem to generate
   * @param settings
   *          the settings for the Genetic Algorithm to use.
   * @return a genetic algorithm, with the settings, on a random linear pseudo-boolean function,
   *         with the seed and the size.
   */
  public GeneticAlgorithm<PbSolution> createLinearPbGeneticAlgorithm(
      final long seed, final int size, final EvoAlgorithmSettings settings) {
    final PbFunction function =
        new LinearPbFunctionFactory(seed, size).generateFunction();
    final PbSolutionFactory solFactory = new PbSolutionFactory(function);
    return new GeneticAlgorithm<PbSolution>(solFactory);
  }

  /**
   * Generates a memetic algorithm, with the settings, on a random linear pseudo-boolean function,
   * with the seed and the size.
   * 
   * @param seed
   *          the seed to generate the problem
   * @param size
   *          the size of the problem to generate
   * @param settings
   *          the settings for the memetic algorithm to use.
   * @return a memetic algorithm, with the settings, on a random linear pseudo-boolean function,
   *         with the seed and the size.
   */
  public MemeticAlgorithm<PbSolution> createLinearPbMemeticAlgorithm(
      final long seed, final int size, final EvoAlgorithmSettings settings) {
    final PbFunction function =
        new LinearPbFunctionFactory(seed, size).generateFunction();
    final PbSolutionFactory solFactory = new PbSolutionFactory(function);
    final LocalImprovement<PbSolution> improv = new PbLocImprov();
    return new MemeticAlgorithm<PbSolution>(solFactory, improv);
  }

  /**
   * Generates a genetic algorithm, with the settings, on a random quadratic pseudo-boolean
   * function, with the seed and the size.
   * 
   * @param seed
   *          the seed to generate the problem
   * @param size
   *          the size of the problem to generate
   * @param settings
   *          the settings for the Genetic Algorithm to use.
   * @return a genetic algorithm, with the settings, on a random quadratic pseudo-boolean function,
   *         with the seed and the size.
   */
  public GeneticAlgorithm<PbSolution> createQuadraticPbGeneticAlgorithm(
      final long seed, final int size, final EvoAlgorithmSettings settings) {
    final PbFunction function =
        new QuadraticPbFuncFactory(seed, size).generateFunction();
    final PbSolutionFactory solFactory = new PbSolutionFactory(function);
    return new GeneticAlgorithm<PbSolution>(solFactory);
  }

  /**
   * Generates a memetic algorithm, with the settings, on a random quadratic pseudo-boolean
   * function, with the seed and the size.
   * 
   * @param seed
   *          the seed to generate the problem
   * @param size
   *          the size of the problem to generate
   * @param settings
   *          the settings for the memetic algorithm to use.
   * @return a memetic algorithm, with the settings, on a random quadratic pseudo-boolean function,
   *         with the seed and the size.
   */
  public MemeticAlgorithm<PbSolution> createQuadraticPbMemeticAlgorithm(
      final long seed, final int size, final EvoAlgorithmSettings settings) {
    final PbFunction function =
        new QuadraticPbFuncFactory(seed, size).generateFunction();
    final PbSolutionFactory solFactory = new PbSolutionFactory(function);
    final LocalImprovement<PbSolution> improv = new PbLocImprov();
    return new MemeticAlgorithm<PbSolution>(solFactory, improv);
  }

  /**
   * Generates a genetic algorithm, with the settings, on a random EUCLID_TSP problem, with the seed and
   * the size.
   * 
   * @param seed
   *          the seed to generate the problem
   * @param size
   *          the size of the problem to generate
   * @param settings
   *          the settings for the genetic algorithm to use.
   * @return a genetic algorithm, with the settings, on a random EUCLID_TSP problem, with the seed and the
   *         size.
   */
  public GeneticAlgorithm<TspTour> createTspGeneticAlgorithm(final long seed,
      final int size, final EvoAlgorithmSettings settings) {
    final EuclidTspFunction function =
        new EuclidTspFunctionFactory(seed, size).generateFunction();
    final TspTourFactory solFactory = new TspTourFactory(function);
    return new GeneticAlgorithm<TspTour>(solFactory);
  }

  /**
   * Generates a memetic algorithm, with the settings, on a random EUCLID_TSP problem, with the seed and
   * the size.
   * 
   * @param seed
   *          the seed to generate the problem
   * @param size
   *          the size of the problem to generate
   * @param settings
   *          the settings for the memetic algorithm to use.
   * @return a memetic algorithm, with the settings, on a random EUCLID_TSP problem, with the seed and the
   *         size.
   */
  public MemeticAlgorithm<TspTour> createTspMemeticAlgorithm(final long seed,
      final int size, final EvoAlgorithmSettings settings) {
    final EuclidTspFunction function =
        new EuclidTspFunctionFactory(seed, size).generateFunction();
    final TspTourFactory solFactory = new TspTourFactory(function);
    return new MemeticAlgorithm<TspTour>(solFactory, new TspLiMulti());
  }

  /**
   * Generates a nearest neighbour algorithm, with the settings, on a random EUCLID_TSP problem, with the
   * seed and the size.
   * 
   * @param seed
   *          the seed to generate the problem
   * @param size
   *          the size of the problem to generate
   * @return a nearest neigbour algorithm, with the settings, on a random EUCLID_TSP problem, with the seed
   *         and the size.
   */
  public TspNnAlgorithm createTspNnAlgorithm(final long seed, final int size) {
    final EuclidTspFunction function =
        new EuclidTspFunctionFactory(seed, size).generateFunction();
    return new TspNnAlgorithm(function);
  }

}
