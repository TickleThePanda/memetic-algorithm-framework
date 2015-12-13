package uk.co.ticklethepanda.memetic.problem.solutions.pbf;

import java.util.Random;

public class PbTestHelper {

  private static Random random = new Random();

  public static boolean[] createRandomBoolArr(final int randomSize) {
    final boolean[] bools = new boolean[randomSize];

    for (int i = 0; i < randomSize; i++) {
      bools[i] = random.nextBoolean();
    }
    return bools;
  }

}
