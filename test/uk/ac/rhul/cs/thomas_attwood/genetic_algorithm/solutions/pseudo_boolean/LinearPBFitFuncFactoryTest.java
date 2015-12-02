package uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.solutions.pseudo_boolean;

import static org.junit.Assert.assertEquals;

import java.util.Random;

import org.junit.BeforeClass;
import org.junit.Test;

public class LinearPBFitFuncFactoryTest {

  @BeforeClass
  public static void setUpBeforeClass() {
    random = new Random();
  }

  private static Random random;

  @Test(expected = IllegalArgumentException.class)
  public void LinearPbFuncctionFactory_size0_ErrorThrown() {
    new LinearPbFunctionFactory(0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void LinearPbFuncctionFactoryTwoArguments_size0_ErrorThrown() {
    new LinearPbFunctionFactory(0, 0);
  }

  @Test
  public void TestGenerateProblem_RandomSeed_CorrectSequence() {
    final int size = 10;
    final long seed = random.nextLong();
    final LinearPbFunctionFactory sbProbFact =
        new LinearPbFunctionFactory(seed, size);
    final PbFunction sbProb = sbProbFact.generateFunction();

    final Random testRandom = new Random(seed);

    for (final Term op : sbProb) {
      assertEquals(testRandom.nextInt(1000) - 500, op.getCoefficient());
    }
  }

  @Test
  public void TestGenerateProblem_RandomSize_CorrectOperationValues() {
    final int size = random.nextInt(5) + 2;
    final LinearPbFunctionFactory sbProbFact =
        new LinearPbFunctionFactory(size);
    final PbFunction sbProb = sbProbFact.generateFunction();

    int usedValues = 0;

    int count = 0;
    for (final Term op : sbProb) {
      if (op.getBooleanIndeterminant(0).getIndex() == count++) {
        usedValues++;
      }
    }
    assertEquals(size, usedValues);
  }

  @Test
  public void TestGenerateProblem_Seed0_CorrectSequence() {
    final int size = 10;
    final long seed = 0;
    final LinearPbFunctionFactory sbProbFact =
        new LinearPbFunctionFactory(seed, size);
    final PbFunction sbProb = sbProbFact.generateFunction();

    final Random testRandom = new Random(seed);

    for (final Term op : sbProb) {
      assertEquals(testRandom.nextInt(1000) - 500, op.getCoefficient());
    }
  }

  @Test
  public void TestGenerateProblem_size5_CorrectOperationValues() {
    final int size = 5;
    final LinearPbFunctionFactory sbProbFact =
        new LinearPbFunctionFactory(size);
    final PbFunction sbProb = sbProbFact.generateFunction();

    int usedValues = 0;

    int count = 0;
    for (final Term op : sbProb) {
      if (op.getBooleanIndeterminant(0).getIndex() == count++) {
        usedValues++;
      }
    }
    assertEquals(size, usedValues);
  }
}
