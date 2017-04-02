package uk.co.ticklethepanda.memetic.problem.solutions.pbf;

import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import uk.co.ticklethepanda.memetic.problem.solutions.LocalImprovement;
import uk.co.ticklethepanda.memetic.problem.solutions.LocalImprovementExtent;

/**
 * Defines the local improvement that can be completed on Pseudo-Boolean functions.
 *
 */
public class FlipBitLocalImprovement implements LocalImprovement<BitString> {

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
  LoadingCache<BitString, BitString> fitnessCache = CacheBuilder.newBuilder()
      .maximumSize(DEFAULT_CACHE_SIZE).expireAfterAccess(DEFAULT_TIME_LIMIT, TimeUnit.SECONDS)
      .concurrencyLevel(DEFAULT_CONCURRENCY_LEVEL).build(new CacheLoader<BitString, BitString>() {

        @Override
        public BitString load(final BitString solution) throws Exception {
          switch (type) {

          default:
            return FlipBitLocalImprovement.this.getSingleRandomImprovedSolution(solution);
          }
        }
      });

  /**
   * The local improvement type to use.
   */
  private final LocalImprovementExtent type;

  /**
   * Creates a PbLocImprov using the default improvement type, SINGLE_RANDOM.
   */
  public FlipBitLocalImprovement() {
    this(LocalImprovementExtent.SINGLE_RANDOM);
  }

  /**
   * Creates a PbLocImprov using the local improvement type specified.
   *
   * @param type
   */
  public FlipBitLocalImprovement(final LocalImprovementExtent type) {
    this.type = type;
  }

  @Override
  public BitString getCompleteImprovedSolution(final BitString original) {

    BitString best = original;

    while (true) {
      final BitString newBest = getNeighbourhoodImprovedSolution(best);
      if (newBest == best) {
        break;
      }
      best = newBest;
    }

    return best;
  }

  @Override
  public BitString getImprovedSolution(final BitString solution) {
    try {
      return fitnessCache.get(solution);
    } catch (final ExecutionException e) {
      e.printStackTrace();
    }
    return solution;
  }

  @Override
  public BitString getNeighbourhoodImprovedSolution(final BitString original) {

    BitString best = original;

    for (int bitIndex = 0; bitIndex < original.size(); bitIndex++) {

      final BitString flippedSolution = original.flipBit(bitIndex);

      if (flippedSolution.getFitness() > best.getFitness()) {
        best = flippedSolution;
      }
    }

    return best;
  }

  @Override
  public BitString getSingleOrderedImprovedSolution(final BitString original) {

    for (int bitIndex = 0; bitIndex < original.size(); bitIndex++) {

      final BitString flippedSolution = original.flipBit(bitIndex);

      if (flippedSolution.getFitness() > original.getFitness()) {
        return flippedSolution;
      }
    }

    return original;
  }

  @Override
  public BitString getSingleRandomImprovedSolution(final BitString original) {

    final int start = new Random().nextInt(original.size());

    for (int bitIndex = start; bitIndex < original.size(); bitIndex++) {

      final BitString flippedSolution = original.flipBit(bitIndex);

      if (flippedSolution.getFitness() > original.getFitness()) {
        return flippedSolution;
      }
    }

    for (int bitIndex = 0; bitIndex < start; bitIndex++) {

      final BitString flippedSolution = original.flipBit(bitIndex);

      if (flippedSolution.getFitness() > original.getFitness()) {
        return flippedSolution;
      }
    }

    return original;
  }

  @Override
  public LocalImprovementExtent getType() {
    return type;
  }
}
