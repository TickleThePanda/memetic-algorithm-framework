package uk.co.ticklethepanda.memetic.problem.solutions.pbf;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.Random;

import org.junit.BeforeClass;
import org.junit.Test;

public class PbFitFuncTest {

  private static Random random;

  @BeforeClass
  public static void setUpBeforeTest() {
    random = new Random();
  }

  @Test
  public void Equals_DifferentType_False() {
    final int numberOperations = 5;
    final int maxModifier = 10;
    final PseudoBooleanTerm[] spboList = new PseudoBooleanTerm[numberOperations];

    for (int i = 0; i < numberOperations; i++) {
      final int modifier = random.nextInt(maxModifier);
      final int location = 0;
      spboList[i] = new PseudoBooleanTerm(modifier,
          new PsuedoBooleanIndeterminant[] { new PsuedoBooleanIndeterminant(location, false) });
    }

    final PsuedoBooleanFunction spbp = new PsuedoBooleanFunction(spboList);

    assertNotEquals(spbp, new Object());
  }

  @Test
  public void Equals_Equal_True() {
    final int numberOperations = 5;
    final int maxModifier = 10;
    final PseudoBooleanTerm[] spboList = new PseudoBooleanTerm[numberOperations];

    for (int i = 0; i < numberOperations; i++) {
      final int modifier = random.nextInt(maxModifier);
      final int location = 0;
      spboList[i] = new PseudoBooleanTerm(modifier,
          new PsuedoBooleanIndeterminant[] { new PsuedoBooleanIndeterminant(location, false) });
    }

    final PsuedoBooleanFunction spbp = new PsuedoBooleanFunction(spboList);
    final PsuedoBooleanFunction spbp2 = new PsuedoBooleanFunction(spboList);

    assertEquals(spbp, spbp2);
  }

  @Test
  public void Equals_Null_False() {
    final int numberOperations = 5;
    final int maxModifier = 10;
    final PseudoBooleanTerm[] spboList = new PseudoBooleanTerm[numberOperations];

    for (int i = 0; i < numberOperations; i++) {
      final int modifier = random.nextInt(maxModifier);
      final int location = 0;
      spboList[i] = new PseudoBooleanTerm(modifier,
          new PsuedoBooleanIndeterminant[] { new PsuedoBooleanIndeterminant(location, false) });
    }

    final PsuedoBooleanFunction spbp = new PsuedoBooleanFunction(spboList);

    assertNotEquals(spbp, null);
  }

  @Test
  public void Equals_SameReference_True() {
    final int numberOperations = 5;
    final int maxModifier = 10;
    final PseudoBooleanTerm[] spboList = new PseudoBooleanTerm[numberOperations];

    for (int i = 0; i < numberOperations; i++) {
      final int modifier = random.nextInt(maxModifier);
      final int location = 0;
      spboList[i] = new PseudoBooleanTerm(modifier,
          new PsuedoBooleanIndeterminant[] { new PsuedoBooleanIndeterminant(location, false) });
    }

    final PsuedoBooleanFunction spbp = new PsuedoBooleanFunction(spboList);

    assertEquals(spbp, spbp);
  }

  @Test
  public void Equals_SameTypeNotEqual_False() {
    final int numberOperations = 5;
    final int maxModifier = 10;
    final PseudoBooleanTerm[] spboList = new PseudoBooleanTerm[numberOperations];

    for (int i = 0; i < numberOperations; i++) {
      final int modifier = random.nextInt(maxModifier);
      final int location = 0;
      spboList[i] = new PseudoBooleanTerm(modifier,
          new PsuedoBooleanIndeterminant[] { new PsuedoBooleanIndeterminant(location, false) });
    }

    final int numberOperations2 = 5;
    final int maxModifier2 = 10;
    final PseudoBooleanTerm[] spboList2 = new PseudoBooleanTerm[numberOperations2];

    for (int i = 0; i < numberOperations2; i++) {
      final int modifier2 = random.nextInt(maxModifier2);
      final int location2 = 0;
      spboList2[i] = new PseudoBooleanTerm(modifier2,
          new PsuedoBooleanIndeterminant[] { new PsuedoBooleanIndeterminant(location2, false) });
    }

    final PsuedoBooleanFunction spbp = new PsuedoBooleanFunction(spboList);
    final PsuedoBooleanFunction spbp2 = new PsuedoBooleanFunction(spboList2);

    assertNotEquals(spbp, spbp2);
  }

  @Test
  public void TestEvaluateFitness_2operations_CorrectFitness() {
    final int answer = 3;
    final PseudoBooleanTerm spbo1 = new PseudoBooleanTerm(1,
        new PsuedoBooleanIndeterminant[] { new PsuedoBooleanIndeterminant(1, false) });
    final PseudoBooleanTerm spbo2 = new PseudoBooleanTerm(2,
        new PsuedoBooleanIndeterminant[] { new PsuedoBooleanIndeterminant(1, false) });

    final PsuedoBooleanFunction spbp =
        new PsuedoBooleanFunction(new PseudoBooleanTerm[] { spbo1, spbo2 });
    assertEquals(answer, spbp.evaluateFitness(new BitString(new boolean[] { true, true }, spbp)));
  }

  @Test
  public void TestEvaluateFitness_2RandomOperations_CorrectFitness() {
    final int maxModifier = 100;

    final int random1 = random.nextInt(maxModifier);
    final int random2 = random.nextInt(maxModifier);
    final int sum = random1 + random2;
    final PseudoBooleanTerm spbo1 = new PseudoBooleanTerm(random1,
        new PsuedoBooleanIndeterminant[] { new PsuedoBooleanIndeterminant(0, false) });
    final PseudoBooleanTerm spbo2 = new PseudoBooleanTerm(random2,
        new PsuedoBooleanIndeterminant[] { new PsuedoBooleanIndeterminant(1, false) });

    final PsuedoBooleanFunction spbp =
        new PsuedoBooleanFunction(new PseudoBooleanTerm[] { spbo1, spbo2 });
    assertEquals(sum, spbp.evaluateFitness(new BitString(new boolean[] { true, true }, spbp)));
  }

  @Test
  public void TestEvaluateFitness_CacheFalse2operations_CorrectFitness() {
    final int answer = 3;
    final PseudoBooleanTerm spbo1 = new PseudoBooleanTerm(1,
        new PsuedoBooleanIndeterminant[] { new PsuedoBooleanIndeterminant(0, false) });
    final PseudoBooleanTerm spbo2 = new PseudoBooleanTerm(2,
        new PsuedoBooleanIndeterminant[] { new PsuedoBooleanIndeterminant(1, false) });

    final PsuedoBooleanFunction spbp =
        new PsuedoBooleanFunction(false, new PseudoBooleanTerm[] { spbo1, spbo2 });
    assertEquals(answer, spbp.evaluateFitness(new BitString(new boolean[] { true, true }, spbp)));
  }

  @Test
  public void TestEvaluateFitness_CacheFalse2RandomOperations_CorrectFitness() {
    final int maxModifier = 100;

    final int random1 = random.nextInt(maxModifier);
    final int random2 = random.nextInt(maxModifier);
    final int sum = random1 + random2;
    final PseudoBooleanTerm spbo1 = new PseudoBooleanTerm(random1,
        new PsuedoBooleanIndeterminant[] { new PsuedoBooleanIndeterminant(0, false) });
    final PseudoBooleanTerm spbo2 = new PseudoBooleanTerm(random2,
        new PsuedoBooleanIndeterminant[] { new PsuedoBooleanIndeterminant(1, false) });

    final PsuedoBooleanFunction spbp =
        new PsuedoBooleanFunction(false, new PseudoBooleanTerm[] { spbo1, spbo2 });
    assertEquals(sum, spbp.evaluateFitness(new BitString(new boolean[] { true, true }, spbp)));
  }

  @Test
  public void TestEvaluateFitness_CacheFalseRandomNRandomOperations_CorrectFitness() {
    final int nOperations = 10;
    final int solutionSize = 10;
    final int maxModifierSize = 10;

    final PseudoBooleanTerm[] spboList = new PseudoBooleanTerm[nOperations];

    for (int i = 0; i < nOperations; i++) {
      final int modifier = random.nextInt(maxModifierSize);
      final int location = random.nextInt(solutionSize);
      spboList[i] = new PseudoBooleanTerm(modifier,
          new PsuedoBooleanIndeterminant[] { new PsuedoBooleanIndeterminant(location, false) });
    }

    final PsuedoBooleanFunction spbp = new PsuedoBooleanFunction(false, spboList);

    final BitString pbs = new BitString(PbTestHelper.createRandomBoolArr(nOperations), spbp);

    int sum = 0;
    for (final PseudoBooleanTerm element : spboList) {
      sum += element.calculateValue(pbs);
    }

    assertEquals(sum, spbp.evaluateFitness(pbs));
  }

  @Test
  public void TestEvaluateFitness_RandomNRandomOperations_CorrectFitness() {
    final int nOperations = 10;
    final int solutionSize = 10;
    final int maxModifierSize = 10;

    final PseudoBooleanTerm[] spboList = new PseudoBooleanTerm[nOperations];

    for (int i = 0; i < nOperations; i++) {
      final int modifier = random.nextInt(maxModifierSize);
      final int location = random.nextInt(solutionSize);
      spboList[i] = new PseudoBooleanTerm(modifier,
          new PsuedoBooleanIndeterminant[] { new PsuedoBooleanIndeterminant(location, false) });
    }

    final PsuedoBooleanFunction spbp = new PsuedoBooleanFunction(spboList);

    final BitString pbs = new BitString(PbTestHelper.createRandomBoolArr(nOperations), spbp);

    int sum = 0;
    for (final PseudoBooleanTerm element : spboList) {
      sum += element.calculateValue(pbs);
    }

    assertEquals(sum, spbp.evaluateFitness(pbs));
  }

  @Test
  public void TestSize_RandomSize_CorrectSizeReturned() {
    final int maxOperations = 5;
    final int nOperations = random.nextInt(maxOperations) + 1;
    final int maxModifier = 10;
    final PseudoBooleanTerm[] spboList = new PseudoBooleanTerm[nOperations];

    for (int i = 0; i < nOperations; i++) {
      final int modifier = random.nextInt(maxModifier);
      final int location = 0;
      spboList[i] = new PseudoBooleanTerm(modifier,
          new PsuedoBooleanIndeterminant[] { new PsuedoBooleanIndeterminant(location, false) });
    }

    final PsuedoBooleanFunction spbp = new PsuedoBooleanFunction(spboList);

    assertEquals(nOperations, spbp.size());
  }

  @Test
  public void TestSize_Size5_CorrectSizeReturned() {
    final int numberOperations = 5;
    final int maxModifier = 10;
    final PseudoBooleanTerm[] spboList = new PseudoBooleanTerm[numberOperations];

    for (int i = 0; i < numberOperations; i++) {
      final int modifier = random.nextInt(maxModifier);
      final int location = 0;
      spboList[i] = new PseudoBooleanTerm(modifier,
          new PsuedoBooleanIndeterminant[] { new PsuedoBooleanIndeterminant(location, false) });
    }

    final PsuedoBooleanFunction spbp = new PsuedoBooleanFunction(spboList);

    assertEquals(numberOperations, spbp.size());
  }

}
