package uk.co.ticklethepanda.memetic.gui.helpers;

import uk.co.ticklethepanda.memetic.algorithms.EvoAlgorithmSettings;
import uk.co.ticklethepanda.memetic.algorithms.GeneticAlgorithm;
import uk.co.ticklethepanda.memetic.algorithms.MemeticAlgorithm;
import uk.co.ticklethepanda.memetic.algorithms.NearestNeighbourAlgorithm;
import uk.co.ticklethepanda.memetic.problem.generators.pbf.LinearPbFunctionGenerator;
import uk.co.ticklethepanda.memetic.problem.generators.pbf.QuadraticPbFunctionGenerator;
import uk.co.ticklethepanda.memetic.problem.generators.tsp.EuclidianCitiesGenerator;
import uk.co.ticklethepanda.memetic.problem.solutions.LocalImprovement;
import uk.co.ticklethepanda.memetic.problem.solutions.pbf.BitString;
import uk.co.ticklethepanda.memetic.problem.solutions.pbf.BitStringFactory;
import uk.co.ticklethepanda.memetic.problem.solutions.pbf.FlipBitLocalImprovement;
import uk.co.ticklethepanda.memetic.problem.solutions.pbf.PsuedoBooleanFunction;
import uk.co.ticklethepanda.memetic.problem.solutions.tsp.Cities;
import uk.co.ticklethepanda.memetic.problem.solutions.tsp.City.Euclidian;
import uk.co.ticklethepanda.memetic.problem.solutions.tsp.Tour;
import uk.co.ticklethepanda.memetic.problem.solutions.tsp.TwoOptLocalImprovement;

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
  public GeneticAlgorithm<BitString> createLinearPbGeneticAlgorithm(final long seed, final int size,
      final EvoAlgorithmSettings settings) {
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
  public MemeticAlgorithm<BitString> createLinearPbMemeticAlgorithm(final long seed, final int size,
      final EvoAlgorithmSettings settings) {
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
  public GeneticAlgorithm<BitString> createQuadraticPbGeneticAlgorithm(final long seed,
      final int size, final EvoAlgorithmSettings settings) {
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
  public MemeticAlgorithm<BitString> createQuadraticPbMemeticAlgorithm(final long seed,
      final int size, final EvoAlgorithmSettings settings) {
    final PsuedoBooleanFunction function =
        new QuadraticPbFunctionGenerator(seed, size).generateFunction();
    final BitStringFactory solFactory = new BitStringFactory(function);
    final LocalImprovement<BitString> improv = new FlipBitLocalImprovement();
    return new MemeticAlgorithm<BitString>(solFactory, improv);
  }

  /**
   * Generates a genetic algorithm, with the settings, on a random EUCLID_TSP problem, with the seed
   * and the size.
   *
   * @param seed
   *          the seed to generate the problem
   * @param size
   *          the size of the problem to generate
   * @param settings
   *          the settings for the genetic algorithm to use.
   * @return a genetic algorithm, with the settings, on a random EUCLID_TSP problem, with the seed
   *         and the size.
   */
  public GeneticAlgorithm<Tour> createTspGeneticAlgorithm(final long seed, final int size,
      final EvoAlgorithmSettings settings) {
    final Cities<Euclidian> function = new EuclidianCitiesGenerator(seed, size).generateFunction();
    final Tour.Generator solFactory = new Tour.Generator(function);
    return new GeneticAlgorithm<Tour>(solFactory);
  }

  /**
   * Generates a memetic algorithm, with the settings, on a random EUCLID_TSP problem, with the seed
   * and the size.
   *
   * @param seed
   *          the seed to generate the problem
   * @param size
   *          the size of the problem to generate
   * @param settings
   *          the settings for the memetic algorithm to use.
   * @return a memetic algorithm, with the settings, on a random EUCLID_TSP problem, with the seed
   *         and the size.
   */
  public MemeticAlgorithm<Tour> createTspMemeticAlgorithm(final long seed, final int size,
      final EvoAlgorithmSettings settings) {
    final Cities<Euclidian> function = new EuclidianCitiesGenerator(seed, size).generateFunction();
    final Tour.Generator solFactory = new Tour.Generator(function);
    return new MemeticAlgorithm<Tour>(solFactory, new TwoOptLocalImprovement());
  }

  /**
   * Generates a nearest neighbour algorithm, with the settings, on a random EUCLID_TSP problem,
   * with the seed and the size.
   *
   * @param seed
   *          the seed to generate the problem
   * @param size
   *          the size of the problem to generate
   * @return a nearest neigbour algorithm, with the settings, on a random EUCLID_TSP problem, with
   *         the seed and the size.
   */
  public NearestNeighbourAlgorithm createTspNnAlgorithm(final long seed, final int size) {
    final Cities<Euclidian> function = new EuclidianCitiesGenerator(seed, size).generateFunction();
    return new NearestNeighbourAlgorithm(function);
  }

}
