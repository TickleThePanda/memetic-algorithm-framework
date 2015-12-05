package uk.co.ticklethepanda.memetic.problem.solutions.pbf;

import static org.junit.Assert.assertEquals;

import java.util.Random;

import org.junit.BeforeClass;
import org.junit.Test;

import uk.co.ticklethepanda.memetic.problem.generators.pbf.LinearPbFunctionGenerator;
import uk.co.ticklethepanda.memetic.problem.solutions.pbf.PseudoBooleanTerm;
import uk.co.ticklethepanda.memetic.problem.solutions.pbf.PsuedoBooleanFunction;

public class LinearPBFitFuncFactoryTest {

  @BeforeClass
  public static void setUpBeforeClass() {
    random = new Random();
  }

  private static Random random;

  @Test(expected = IllegalArgumentException.class)
  public void LinearPbFuncctionFactory_size0_ErrorThrown() {
    new LinearPbFunctionGenerator(0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void LinearPbFuncctionFactoryTwoArguments_size0_ErrorThrown() {
    new LinearPbFunctionGenerator(0, 0);
  }

  @Test
  public void TestGenerateProblem_RandomSeed_CorrectSequence() {
    final int size = 10;
    final long seed = random.nextLong();
    final LinearPbFunctionGenerator sbProbFact =
        new LinearPbFunctionGenerator(seed, size);
    final PsuedoBooleanFunction sbProb = sbProbFact.generateFunction();

    final Random testRandom = new Random(seed);

    for (final PseudoBooleanTerm op : sbProb) {
      assertEquals(testRandom.nextInt(1000) - 500, op.getCoefficient());
    }
  }

  @Test
  public void TestGenerateProblem_RandomSize_CorrectOperationValues() {
    final int size = random.nextInt(5) + 2;
    final LinearPbFunctionGenerator sbProbFact =
        new LinearPbFunctionGenerator(size);
    final PsuedoBooleanFunction sbProb = sbProbFact.generateFunction();

    int usedValues = 0;

    int count = 0;
    for (final PseudoBooleanTerm op : sbProb) {
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
    final LinearPbFunctionGenerator sbProbFact =
        new LinearPbFunctionGenerator(seed, size);
    final PsuedoBooleanFunction sbProb = sbProbFact.generateFunction();

    final Random testRandom = new Random(seed);

    for (final PseudoBooleanTerm op : sbProb) {
      assertEquals(testRandom.nextInt(1000) - 500, op.getCoefficient());
    }
  }

  @Test
  public void TestGenerateProblem_size5_CorrectOperationValues() {
    final int size = 5;
    final LinearPbFunctionGenerator sbProbFact =
        new LinearPbFunctionGenerator(size);
    final PsuedoBooleanFunction sbProb = sbProbFact.generateFunction();

    int usedValues = 0;

    int count = 0;
    for (final PseudoBooleanTerm op : sbProb) {
      if (op.getBooleanIndeterminant(0).getIndex() == count++) {
        usedValues++;
      }
    }
    assertEquals(size, usedValues);
  }
}
