package uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.gui.helpers;

import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.algorithms.EvoAlgorithmSettings;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.algorithms.GeneticAlgorithm;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.algorithms.MemeticAlgorithm;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.algorithms.TspNnAlgorithm;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.problem.generators.pbf.LinearPbFunctionGenerator;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.problem.generators.pbf.QuadraticPbFunctionGenerator;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.problem.generators.tsp.EuclidianCitiesGenerator;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.problem.solutions.LocalImprovement;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.problem.solutions.pbf.BitString;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.problem.solutions.pbf.BitStringFactory;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.problem.solutions.pbf.FlipBitLocalImprovement;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.problem.solutions.pbf.PsuedoBooleanFunction;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.problem.solutions.tsp.Cities;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.problem.solutions.tsp.MultiLocalImprovement;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.problem.solutions.tsp.Tour;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.problem.solutions.tsp.City.Euclidian;

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
  public GeneticAlgorithm<BitString> createLinearPbGeneticAlgorithm(
      final long seed, final int size, final EvoAlgorithmSettings settings) {
    final PsuedoBooleanFunction function =
        new LinearPbFunctionGenerator(seed, size).generateFunction();
    final BitStringFactory solFactory = new BitStringFactory(function);
    return new GeneticAlgorithm<BitString>(solFactory);
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
  public MemeticAlgorithm<BitString> createLinearPbMemeticAlgorithm(
      final long seed, final int size, final EvoAlgorithmSettings settings) {
    final PsuedoBooleanFunction function =
        new LinearPbFunctionGenerator(seed, size).generateFunction();
    final BitStringFactory solFactory = new BitStringFactory(function);
    final LocalImprovement<BitString> improv = new FlipBitLocalImprovement();
    return new MemeticAlgorithm<BitString>(solFactory, improv);
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
  public GeneticAlgorithm<BitString> createQuadraticPbGeneticAlgorithm(
      final long seed, final int size, final EvoAlgorithmSettings settings) {
    final PsuedoBooleanFunction function =
        new QuadraticPbFunctionGenerator(seed, size).generateFunction();
    final BitStringFactory solFactory = new BitStringFactory(function);
    return new GeneticAlgorithm<BitString>(solFactory);
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
  public MemeticAlgorithm<BitString> createQuadraticPbMemeticAlgorithm(
      final long seed, final int size, final EvoAlgorithmSettings settings) {
    final PsuedoBooleanFunction function =
        new QuadraticPbFunctionGenerator(seed, size).generateFunction();
    final BitStringFactory solFactory = new BitStringFactory(function);
    final LocalImprovement<BitString> improv = new FlipBitLocalImprovement();
    return new MemeticAlgorithm<BitString>(solFactory, improv);
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
  public GeneticAlgorithm<Tour> createTspGeneticAlgorithm(final long seed,
      final int size, final EvoAlgorithmSettings settings) {
    final Cities<Euclidian> function =
        new EuclidianCitiesGenerator(seed, size).generateFunction();
    final Tour.Generator solFactory = new Tour.Generator(function);
    return new GeneticAlgorithm<Tour>(solFactory);
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
  public MemeticAlgorithm<Tour> createTspMemeticAlgorithm(final long seed,
      final int size, final EvoAlgorithmSettings settings) {
    final Cities<Euclidian> function =
        new EuclidianCitiesGenerator(seed, size).generateFunction();
    final Tour.Generator solFactory = new Tour.Generator(function);
    return new MemeticAlgorithm<Tour>(solFactory, new MultiLocalImprovement());
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
    final Cities<Euclidian> function =
        new EuclidianCitiesGenerator(seed, size).generateFunction();
    return new TspNnAlgorithm(function);
  }

}
