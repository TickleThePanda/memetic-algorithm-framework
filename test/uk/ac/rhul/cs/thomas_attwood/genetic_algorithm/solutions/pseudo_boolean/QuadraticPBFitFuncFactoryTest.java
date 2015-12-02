package uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.solutions.pseudo_boolean;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Random;

import org.junit.BeforeClass;
import org.junit.Test;

public class QuadraticPBFitFuncFactoryTest {

  @BeforeClass
  public static void SetUpRandom() {
    random = new Random();
  }

  private static Random random;

  @Test(expected = IllegalArgumentException.class)
  public void Constructor1_SizeTooSmall_ExceptionThrown() {
    int smallSize = 1;
    new QuadraticPbFuncFactory(smallSize);
  }

  @Test(expected = IllegalArgumentException.class)
  public void Constructor2_SizeTooSmall_ExceptionThrown() {
    int smallSize = 1;
    new QuadraticPbFuncFactory(0, smallSize);
  }

  private ArrayList<Integer> generateLocations(final int size) {
    final ArrayList<Integer> locations = new ArrayList<Integer>();

    for (int i = 0; i < size; i++) {
      locations.add(i);
    }
    return locations;
  }

  private ArrayList<BooleanIndeterminant> getBooleanOperatorList(
      final PbFunction pbff) {
    final ArrayList<BooleanIndeterminant> bos =
        new ArrayList<BooleanIndeterminant>();

    for (int i = 0; i < pbff.size(); i++) {
      final Term operation = pbff.getTerm(i);
      for (final BooleanIndeterminant bo : operation) {
        bos.add(bo);
      }
    }
    return bos;
  }

  @Test
  public void TestGenerateProblem_RandomSeedRandomSize_LocsDontRepeat() {

    final int size = random.nextInt(10) + 5;
    final long seed = random.nextLong();

    final PbFunctionFactory qpff = new QuadraticPbFuncFactory(seed, size);

    final PbFunction pbff = qpff.generateFunction();

    final ArrayList<Term> fos = new ArrayList<Term>();

    for (int i = 0; i < size; i++) {
      fos.add(pbff.getTerm(i));
    }

    for (int i = 0; i < fos.size(); i++) {
      for (int j = i + 1; j < fos.size(); j++) {
        assertNotEquals(fos.get(i), fos.get(j));
      }
    }

  }

  @Test
  public void TestGenerateProblem_RandomSeedRandomSizeRepeat10_HasAllLocs() {
    for (int i = 0; i < 100; i++) {
      final int maxRandomSize = 10;
      final int minRandomSize = 2;
      final int size =
          random.nextInt(maxRandomSize - minRandomSize) + minRandomSize; // should be
                                                                         // random
      final int seed = random.nextInt();
      final PbFunctionFactory qpff = new QuadraticPbFuncFactory(seed, size);

      final PbFunction pbff = qpff.generateFunction();

      final ArrayList<Integer> locations = generateLocations(size);
      final ArrayList<BooleanIndeterminant> bos = getBooleanOperatorList(pbff);

      for (final Integer location : locations) {
        assertTrue(bos.contains(new BooleanIndeterminant(location, true))
            || bos.contains(new BooleanIndeterminant(location, false)));
      }
    }
  }

  @Test
  public void TestGenerateProblem_RandomSeedSize5_HasAllLocs() {
    final int seed = random.nextInt();
    final int size = 5;
    final PbFunctionFactory qpff = new QuadraticPbFuncFactory(seed, size);

    final PbFunction pbff = qpff.generateFunction();

    final ArrayList<Integer> locations = generateLocations(size);
    final ArrayList<BooleanIndeterminant> bos = getBooleanOperatorList(pbff);

    for (final Integer location : locations) {
      assertTrue(bos.contains(new BooleanIndeterminant(location, true))
          || bos.contains(new BooleanIndeterminant(location, false)));
    }

  }

  @Test
  public void TestGenerateProblem_RandomSeedSize5_LocsDontRepeat() {
    final int size = 5;
    final long seed = random.nextLong();

    final PbFunctionFactory qpff = new QuadraticPbFuncFactory(seed, size);

    final PbFunction pbff = qpff.generateFunction();

    final ArrayList<Term> fos = new ArrayList<Term>();

    for (int i = 0; i < size; i++) {
      fos.add(pbff.getTerm(i));
    }

    for (int i = 0; i < fos.size(); i++) {
      for (int j = i + 1; j < fos.size(); j++) {
        assertNotEquals(fos.get(i), fos.get(j));
      }
    }

  }

  @Test
  public void TestGenerateProblem_RandomSeedSizeRandom_HasAllLocs() {
    final int maxRandomSize = 10;
    final int minRandomSize = 2;
    final int size =
        random.nextInt(maxRandomSize - minRandomSize) + minRandomSize; // should be
                                                                       // random
    final int seed = random.nextInt();
    final PbFunctionFactory qpff = new QuadraticPbFuncFactory(seed, size);

    final PbFunction pbff = qpff.generateFunction();

    final ArrayList<Integer> locations = generateLocations(size);
    final ArrayList<BooleanIndeterminant> bos = getBooleanOperatorList(pbff);

    for (final Integer location : locations) {
      assertTrue(bos.contains(new BooleanIndeterminant(location, true))
          || bos.contains(new BooleanIndeterminant(location, false)));
    }

  }
}
