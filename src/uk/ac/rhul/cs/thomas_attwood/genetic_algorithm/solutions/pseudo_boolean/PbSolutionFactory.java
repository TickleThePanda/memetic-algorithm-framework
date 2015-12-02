package uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.solutions.pseudo_boolean;

import java.util.Random;

import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.ProblemType;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.solutions.FitnessFunction;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.solutions.SolutionFactory;

/**
 * Generates random PbSolutions.
 */
public class PbSolutionFactory implements SolutionFactory<PbSolution> {

  /**
   * The fitness function that the solutions are being generated for.
   */
  private final FitnessFunction<PbSolution> function;
  /**
   * The RNG to create the solutions and do the crossover with.
   */
  private final Random random;

  /**
   * Creates a new PbSoltionFactory with the function and a random seed.
   * 
   * @param function
   *          the function that the solutions are being generated for
   */
  public PbSolutionFactory(final FitnessFunction<PbSolution> function) {
    this(System.nanoTime(), function);
  }

  /**
   * Creates a new PbSolutionFactory with the seed and a random seed.
   * 
   * @param seed
   *          the seed used for the RNG to create solutions and crossover
   * @param function
   *          the function that the solutions are being generated for
   */
  public PbSolutionFactory(final long seed,
      final FitnessFunction<PbSolution> function) {
    random = new Random(seed);
    this.function = function;
  }

  @Override
  public PbSolution crossover(final PbSolution pbs1, final PbSolution pbs2) {

    final int crossoverpoint =
        random.nextInt(Math.min(pbs1.size(), pbs2.size()));

    final long seed = random.nextLong();

    final boolean[] bools = new boolean[function.size()];

    for (int i = 0; i < pbs1.size() && i < pbs2.size(); i++) {
      if (i < crossoverpoint) {
        bools[i] = pbs1.get(i);
      } else if (i < pbs2.size()) {
        bools[i] = pbs2.get(i);
      } else {
        bools[i] = pbs1.get(i);
      }
    }

    return new PbSolution(seed, bools, function);
  }

  @Override
  public PbSolution generateSolution() {
    final long seed = random.nextLong();

    final boolean[] bools = new boolean[function.size()];
    for (int i = 0; i < function.size(); i++) {
      bools[i] = random.nextBoolean();
    }
    return new PbSolution(seed, bools, function);
  }

  @Override
  public FitnessFunction<PbSolution> getFitnessFunction() {
    return function;
  }

  @Override
  public ProblemType getProblemType() {
    return this.function.getProblemType();
  }

}
