package uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.algorithms;

import java.util.ArrayList;

import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.problem.solutions.LocalImprovement;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.problem.solutions.Solution;

/**
 * Adds local improvement functionality to
 * {@link uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.algorithms.GeneticAlgorithm
 * GeneticAlgorithm}. It is one of the core classes of this project. It optimises a solution
 * including local improvement.
 *
 * @param <E>
 *          The type of solution that the algorithm improves.
 */
public class MemeticAlgorithm<E extends Solution<E>> extends
    GeneticAlgorithm<E> {

  /**
   * The local improvement to use on the solutions.
   */
  private final LocalImprovement<E> localImprovement;

  /**
   * Creates a MemeticAlgortihm with the seed, the
   * {@link uk.uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.algorithms.EvoAlgorithmSettings
   * EvoAlgorithmSettings}, the
   * {@link uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.solutions.Generator
   * SolutionFactory}, and the
   * {@link uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.problem.solutions.LocalImprovement
   * LocalImprovement}.
   * 
   * @param seed
   *          the seed to use to generate children
   * @param settings
   *          the settings that the memetic algorithm uses to run
   * @param solutionFactory
   *          the solution factory to use to generate and crossover solutions
   * @param localImprovement
   *          the local improvement to use on the solutions
   */
  public MemeticAlgorithm(final long seed, final EvoAlgorithmSettings settings,
      final Solution.Generator<E> solutionFactory,
      final LocalImprovement<E> localImprovement) {
    super(seed, settings, solutionFactory);
    this.localImprovement = localImprovement;
  }

  /**
   * Creates a MemeticAlgortihm with a random seed, the
   * {@link uk.uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.algorithms.EvoAlgorithmSettings
   * EvoAlgorithmSettings}, the
   * {@link uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.solutions.Generator
   * SolutionFactory}, and the
   * {@link uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.problem.solutions.LocalImprovement
   * LocalImprovement}.
   * 
   * @param settings
   *          the settings that the memetic algorithm uses to run
   * @param solutionFactory
   *          the solution factory to use to generate and crossover solutions
   * @param localImprovement
   *          the local improvement to use on the solutions
   */
  public MemeticAlgorithm(final EvoAlgorithmSettings settings,
      final Solution.Generator<E> solutionFactory,
      final LocalImprovement<E> localImprovement) {
    this(System.nanoTime(), settings, solutionFactory, localImprovement);
  }

  /**
   * Creates a MemeticAlgortihm with a random seed, the default
   * {@link uk.uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.algorithms.EvoAlgorithmSettings
   * EvoAlgorithmSettings}, the
   * {@link uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.solutions.Generator
   * SolutionFactory}, and the
   * {@link uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.problem.solutions.LocalImprovement
   * LocalImprovement}.
   * 
   * @param solutionFactory
   *          the solution factory to use to generate and crossover solutions
   * @param localImprovement
   *          the local improvement to use on the solutions
   */
  public MemeticAlgorithm(final Solution.Generator<E> solutionFactory,
      final LocalImprovement<E> localImprovement) {
    this(EvoAlgorithmSettings.DEFAULT_SETTINGS_TSP, solutionFactory,
        localImprovement);
  }

  @Override
  public boolean doAlgorithmStep() {
    super.doAlgorithmStep();
    this.doImprovements();

    return false;
  }

  /**
   * Do improvements to the solution pool using the
   * {@link uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.problem.solutions.LocalImprovement
   * LocalImprovement} provided.
   */
  public void doImprovements() {
    final ArrayList<E> improvedPool = new ArrayList<E>();
    for (int i = 0; i < super.getSettings().getPoolSize(); i++) {
      improvedPool.add(this.localImprovement
          .getImprovedSolution(super.population.get(i)));
    }
    super.population = improvedPool;
  }

  /**
   * Get the local {@link uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.problem.solutions.LocalImprovement
   * LocalImprovement} method provided.
   * 
   * @return
   */
  public LocalImprovement<E> getLocalImprovement() {
    return localImprovement;
  }

}
