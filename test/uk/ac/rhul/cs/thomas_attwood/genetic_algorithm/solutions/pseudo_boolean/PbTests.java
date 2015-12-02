package uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.solutions.pseudo_boolean;

import java.util.Random;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ BooleanOperationTest.class, PbFitFuncOperatorTest.class,
    PbFitFuncTest.class, PbLocImprovTest.class, PBSolutionFactoryTest.class,
    PBSolutionTest.class, QuadraticPBFitFuncFactoryTest.class,
    LinearPBFitFuncFactoryTest.class, TermTest.class })
public class PbTests {

  public static boolean[] createRandomBoolArr(final int randomSize) {
    final boolean[] bools = new boolean[randomSize];

    for (int i = 0; i < randomSize; i++) {
      bools[i] = random.nextBoolean();
    }
    return bools;
  }

  private static Random random = new Random();

}
