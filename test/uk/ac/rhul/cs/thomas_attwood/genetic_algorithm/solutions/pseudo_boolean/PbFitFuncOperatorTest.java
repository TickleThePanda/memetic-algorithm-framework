package uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.solutions.pseudo_boolean;

import static org.junit.Assert.assertEquals;

import java.util.Random;

import org.junit.BeforeClass;
import org.junit.Test;

public class PbFitFuncOperatorTest {

  @BeforeClass
  public static void setUpBeforeClass() {
    random = new Random();
  }

  private static Random random;

  @Test
  public void TestDoOperation_BaseCase_GetCorrectValue() {
    final Term term =
        new Term(1, new BooleanIndeterminant[] { new BooleanIndeterminant(0,
            false) });
    assertEquals(1, term.calculateValue(new PbSolution(new boolean[] { true },
        new PbFunction(new Term[] { term }))));
  }

  @Test
  public void TestDoOperation_FlippedBools_CorrectValue() {
    final Term term =
        new Term(1, new BooleanIndeterminant[] {
            new BooleanIndeterminant(0, false),
            new BooleanIndeterminant(1, false) });
    assertEquals(1, term.calculateValue(new PbSolution(new boolean[] { true,
        true }, new PbFunction(new Term[] { term }))));
  }

  @Test
  public void TestDoOperation_MultileBooleanOperationsTF_CorrectValue() {
    final Term term =
        new Term(1, new BooleanIndeterminant[] {
            new BooleanIndeterminant(0, false),
            new BooleanIndeterminant(1, false) });
    assertEquals(0, term.calculateValue(new PbSolution(new boolean[] { true,
        false }, new PbFunction(new Term[] { term }))));
  }

  @Test
  public void TestDoOperation_MultileBooleanOperationsTT_CorrectValue() {
    final Term term =
        new Term(1, new BooleanIndeterminant[] {
            new BooleanIndeterminant(0, false),
            new BooleanIndeterminant(1, false) });
    assertEquals(1, term.calculateValue(new PbSolution(new boolean[] { true,
        true }, new PbFunction(new Term[] { term }))));
  }

  @Test
  public void TestDoOperation_RandomInputs_GetCorrectValue() {
    final int solutionSize = 10;
    final int randomModifierMaxSize = 100;

    final int randomModifier = random.nextInt(randomModifierMaxSize);
    final int randomLocation = random.nextInt(solutionSize);

    int expectedAnswer = 0;

    final Term term =
        new Term(randomModifier,
            new BooleanIndeterminant[] { new BooleanIndeterminant(
                randomLocation, false) });

    final PbSolution pbs =
        new PbSolution(PbTests.createRandomBoolArr(solutionSize),
            new PbFunction(new Term[] { term }));

    if (pbs.get(randomLocation)) {
      expectedAnswer = randomModifier;
    }
    assertEquals(expectedAnswer, term.calculateValue(pbs));
  }
}
