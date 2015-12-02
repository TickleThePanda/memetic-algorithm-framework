package uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.solutions.pseudo_boolean;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.Random;

import org.junit.BeforeClass;
import org.junit.Test;

public class PbFitFuncTest {

  @BeforeClass
  public static void setUpBeforeTest() {
    random = new Random();
  }

  private static Random random;

  @Test
  public void Equals_DifferentType_False() {
    final int numberOperations = 5;
    final int maxModifier = 10;
    final Term[] spboList = new Term[numberOperations];

    for (int i = 0; i < numberOperations; i++) {
      final int modifier = random.nextInt(maxModifier);
      final int location = 0;
      spboList[i] =
          new Term(modifier,
              new BooleanIndeterminant[] { new BooleanIndeterminant(location,
                  false) });
    }

    final PbFunction spbp = new PbFunction(spboList);

    assertNotEquals(spbp, new Object());
  }

  @Test
  public void Equals_Equal_True() {
    final int numberOperations = 5;
    final int maxModifier = 10;
    final Term[] spboList = new Term[numberOperations];

    for (int i = 0; i < numberOperations; i++) {
      final int modifier = random.nextInt(maxModifier);
      final int location = 0;
      spboList[i] =
          new Term(modifier,
              new BooleanIndeterminant[] { new BooleanIndeterminant(location,
                  false) });
    }

    final PbFunction spbp = new PbFunction(spboList);
    final PbFunction spbp2 = new PbFunction(spboList);

    assertEquals(spbp, spbp2);
  }

  @Test
  public void Equals_Null_False() {
    final int numberOperations = 5;
    final int maxModifier = 10;
    final Term[] spboList = new Term[numberOperations];

    for (int i = 0; i < numberOperations; i++) {
      final int modifier = random.nextInt(maxModifier);
      final int location = 0;
      spboList[i] =
          new Term(modifier,
              new BooleanIndeterminant[] { new BooleanIndeterminant(location,
                  false) });
    }

    final PbFunction spbp = new PbFunction(spboList);

    assertNotEquals(spbp, null);
  }

  @Test
  public void Equals_SameReference_True() {
    final int numberOperations = 5;
    final int maxModifier = 10;
    final Term[] spboList = new Term[numberOperations];

    for (int i = 0; i < numberOperations; i++) {
      final int modifier = random.nextInt(maxModifier);
      final int location = 0;
      spboList[i] =
          new Term(modifier,
              new BooleanIndeterminant[] { new BooleanIndeterminant(location,
                  false) });
    }

    final PbFunction spbp = new PbFunction(spboList);

    assertEquals(spbp, spbp);
  }

  @Test
  public void Equals_SameTypeNotEqual_False() {
    final int numberOperations = 5;
    final int maxModifier = 10;
    final Term[] spboList = new Term[numberOperations];

    for (int i = 0; i < numberOperations; i++) {
      final int modifier = random.nextInt(maxModifier);
      final int location = 0;
      spboList[i] =
          new Term(modifier,
              new BooleanIndeterminant[] { new BooleanIndeterminant(location,
                  false) });
    }

    final int numberOperations2 = 5;
    final int maxModifier2 = 10;
    final Term[] spboList2 = new Term[numberOperations2];

    for (int i = 0; i < numberOperations2; i++) {
      final int modifier2 = random.nextInt(maxModifier2);
      final int location2 = 0;
      spboList2[i] =
          new Term(modifier2,
              new BooleanIndeterminant[] { new BooleanIndeterminant(location2,
                  false) });
    }

    final PbFunction spbp = new PbFunction(spboList);
    final PbFunction spbp2 = new PbFunction(spboList2);

    assertNotEquals(spbp, spbp2);
  }

  @Test
  public void TestEvaluateFitness_2operations_CorrectFitness() {
    final int answer = 3;
    final Term spbo1 =
        new Term(1, new BooleanIndeterminant[] { new BooleanIndeterminant(1,
            false) });
    final Term spbo2 =
        new Term(2, new BooleanIndeterminant[] { new BooleanIndeterminant(1,
            false) });

    final PbFunction spbp = new PbFunction(new Term[] { spbo1, spbo2 });
    assertEquals(answer, spbp.evaluateFitness(new PbSolution(new boolean[] {
        true, true }, spbp)));
  }

  @Test
  public void TestEvaluateFitness_2RandomOperations_CorrectFitness() {
    final int maxModifier = 100;

    final int random1 = random.nextInt(maxModifier);
    final int random2 = random.nextInt(maxModifier);
    final int sum = random1 + random2;
    final Term spbo1 =
        new Term(random1,
            new BooleanIndeterminant[] { new BooleanIndeterminant(0, false) });
    final Term spbo2 =
        new Term(random2,
            new BooleanIndeterminant[] { new BooleanIndeterminant(1, false) });

    final PbFunction spbp = new PbFunction(new Term[] { spbo1, spbo2 });
    assertEquals(sum, spbp.evaluateFitness(new PbSolution(new boolean[] { true,
        true }, spbp)));
  }

  @Test
  public void TestEvaluateFitness_CacheFalse2operations_CorrectFitness() {
    final int answer = 3;
    final Term spbo1 =
        new Term(1, new BooleanIndeterminant[] { new BooleanIndeterminant(0,
            false) });
    final Term spbo2 =
        new Term(2, new BooleanIndeterminant[] { new BooleanIndeterminant(1,
            false) });

    final PbFunction spbp = new PbFunction(false, new Term[] { spbo1, spbo2 });
    assertEquals(answer, spbp.evaluateFitness(new PbSolution(new boolean[] {
        true, true }, spbp)));
  }

  @Test
  public void TestEvaluateFitness_CacheFalse2RandomOperations_CorrectFitness() {
    final int maxModifier = 100;

    final int random1 = random.nextInt(maxModifier);
    final int random2 = random.nextInt(maxModifier);
    final int sum = random1 + random2;
    final Term spbo1 =
        new Term(random1,
            new BooleanIndeterminant[] { new BooleanIndeterminant(0, false) });
    final Term spbo2 =
        new Term(random2,
            new BooleanIndeterminant[] { new BooleanIndeterminant(1, false) });

    final PbFunction spbp = new PbFunction(false, new Term[] { spbo1, spbo2 });
    assertEquals(sum, spbp.evaluateFitness(new PbSolution(new boolean[] { true,
        true }, spbp)));
  }

  @Test
  public void TestEvaluateFitness_CacheFalseRandomNRandomOperations_CorrectFitness() {
    final int nOperations = 10;
    final int solutionSize = 10;
    final int maxModifierSize = 10;

    final Term[] spboList = new Term[nOperations];

    for (int i = 0; i < nOperations; i++) {
      final int modifier = random.nextInt(maxModifierSize);
      final int location = random.nextInt(solutionSize);
      spboList[i] =
          new Term(modifier,
              new BooleanIndeterminant[] { new BooleanIndeterminant(location,
                  false) });
    }

    final PbFunction spbp = new PbFunction(false, spboList);

    final PbSolution pbs =
        new PbSolution(PbTests.createRandomBoolArr(nOperations), spbp);

    int sum = 0;
    for (final Term element : spboList) {
      sum += element.calculateValue(pbs);
    }

    assertEquals(sum, spbp.evaluateFitness(pbs));
  }

  @Test
  public void TestEvaluateFitness_RandomNRandomOperations_CorrectFitness() {
    final int nOperations = 10;
    final int solutionSize = 10;
    final int maxModifierSize = 10;

    final Term[] spboList = new Term[nOperations];

    for (int i = 0; i < nOperations; i++) {
      final int modifier = random.nextInt(maxModifierSize);
      final int location = random.nextInt(solutionSize);
      spboList[i] =
          new Term(modifier,
              new BooleanIndeterminant[] { new BooleanIndeterminant(location,
                  false) });
    }

    final PbFunction spbp = new PbFunction(spboList);

    final PbSolution pbs =
        new PbSolution(PbTests.createRandomBoolArr(nOperations), spbp);

    int sum = 0;
    for (final Term element : spboList) {
      sum += element.calculateValue(pbs);
    }

    assertEquals(sum, spbp.evaluateFitness(pbs));
  }

  @Test
  public void TestSize_RandomSize_CorrectSizeReturned() {
    final int maxOperations = 5;
    final int nOperations = random.nextInt(maxOperations) + 1;
    final int maxModifier = 10;
    final Term[] spboList = new Term[nOperations];

    for (int i = 0; i < nOperations; i++) {
      final int modifier = random.nextInt(maxModifier);
      final int location = 0;
      spboList[i] =
          new Term(modifier,
              new BooleanIndeterminant[] { new BooleanIndeterminant(location,
                  false) });
    }

    final PbFunction spbp = new PbFunction(spboList);

    assertEquals(nOperations, spbp.size());
  }

  @Test
  public void TestSize_Size5_CorrectSizeReturned() {
    final int numberOperations = 5;
    final int maxModifier = 10;
    final Term[] spboList = new Term[numberOperations];

    for (int i = 0; i < numberOperations; i++) {
      final int modifier = random.nextInt(maxModifier);
      final int location = 0;
      spboList[i] =
          new Term(modifier,
              new BooleanIndeterminant[] { new BooleanIndeterminant(location,
                  false) });
    }

    final PbFunction spbp = new PbFunction(spboList);

    assertEquals(numberOperations, spbp.size());
  }

}
