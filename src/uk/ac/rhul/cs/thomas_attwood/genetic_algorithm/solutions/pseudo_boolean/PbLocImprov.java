package uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.solutions.pseudo_boolean;

import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.solutions.LocalImprovement;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.solutions.LocalImprovementType;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

/**
 * Defines the local improvement that can be completed on Pseudo-Boolean functions.
 *
 */
public class PbLocImprov implements LocalImprovement<PbSolution> {

  /**
   * The cache size for the solutions.
   */
  private static final int DEFAULT_CACHE_SIZE = 10000;
  /**
   * The concurrency level for the solutions.
   */
  private static final int DEFAULT_CONCURRENCY_LEVEL = 1;

  /**
   * The time limit that the cache items stay live for.
   */
  private static final int DEFAULT_TIME_LIMIT = 5;

  /**
   * The cache to be used to improve the fitness of solutions.
   */
  LoadingCache<PbSolution, PbSolution> fitnessCache = CacheBuilder.newBuilder()
      .maximumSize(DEFAULT_CACHE_SIZE)
      .expireAfterAccess(DEFAULT_TIME_LIMIT, TimeUnit.SECONDS)
      .concurrencyLevel(DEFAULT_CONCURRENCY_LEVEL)
      .build(new CacheLoader<PbSolution, PbSolution>() {

        @Override
        public PbSolution load(final PbSolution solution) throws Exception {
          switch (type) {

          default:
            return PbLocImprov.this.getSingleRandomImprovedSolution(solution);
          }
        }
      });

  /**
   * The local improvement type to use.
   */
  private final LocalImprovementType type;

  /**
   * Creates a PbLocImprov using the default improvement type, SINGLE_RANDOM.
   */
  public PbLocImprov() {
    this(LocalImprovementType.SINGLE_RANDOM);
  }

  /**
   * Creates a PbLocImprov using the local improvement type specified.
   * 
   * @param type
   */
  public PbLocImprov(final LocalImprovementType type) {
    this.type = type;
  }

  @Override
  public PbSolution getCompleteImprovedSolution(final PbSolution original) {

    PbSolution best = original;

    while (true) {
      final PbSolution newBest = getNeighbourhoodImprovedSolution(best);
      if (newBest == best) {
        break;
      }
      best = newBest;
    }

    return best;
  }

  @Override
  public PbSolution getImprovedSolution(final PbSolution solution) {
    try {
      return fitnessCache.get(solution);
    } catch (final ExecutionException e) {
      e.printStackTrace();
    }
    return solution;
  }

  @Override
  public PbSolution getNeighbourhoodImprovedSolution(final PbSolution original) {

    PbSolution best = original;

    for (int bitIndex = 0; bitIndex < original.size(); bitIndex++) {

      final PbSolution flippedSolution = original.flipBit(bitIndex);

      if (flippedSolution.getFitness() > best.getFitness()) {
        best = flippedSolution;
      }
    }

    return best;
  }

  @Override
  public PbSolution getSingleOrderedImprovedSolution(final PbSolution original) {

    for (int bitIndex = 0; bitIndex < original.size(); bitIndex++) {

      final PbSolution flippedSolution = original.flipBit(bitIndex);

      if (flippedSolution.getFitness() > original.getFitness()) {
        return flippedSolution;
      }
    }

    return original;
  }

  @Override
  public PbSolution getSingleRandomImprovedSolution(final PbSolution original) {

    int start = new Random().nextInt(original.size());

    for (int bitIndex = start; bitIndex < original.size(); bitIndex++) {

      final PbSolution flippedSolution = original.flipBit(bitIndex);

      if (flippedSolution.getFitness() > original.getFitness()) {
        return flippedSolution;
      }
    }

    for (int bitIndex = 0; bitIndex < start; bitIndex++) {

      final PbSolution flippedSolution = original.flipBit(bitIndex);

      if (flippedSolution.getFitness() > original.getFitness()) {
        return flippedSolution;
      }
    }

    return original;
  }

  @Override
  public LocalImprovementType getType() {
    return type;
  }
}
