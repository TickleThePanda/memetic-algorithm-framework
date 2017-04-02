package uk.co.ticklethepanda.memetic.problem.solutions.pbf;

import static org.junit.Assert.assertEquals;

import java.util.Random;

import org.junit.BeforeClass;
import org.junit.Test;

public class PbFitFuncOperatorTest {

  private static Random random;

  @BeforeClass
  public static void setUpBeforeClass() {
    random = new Random();
  }

  @Test
  public void TestDoOperation_BaseCase_GetCorrectValue() {
    final PseudoBooleanTerm pseudoBooleanTerm = new PseudoBooleanTerm(1,
        new PsuedoBooleanIndeterminant[] { new PsuedoBooleanIndeterminant(0, false) });
    assertEquals(1, pseudoBooleanTerm.calculateValue(new BitString(new boolean[] { true },
        new PsuedoBooleanFunction(new PseudoBooleanTerm[] { pseudoBooleanTerm }))));
  }

  @Test
  public void TestDoOperation_FlippedBools_CorrectValue() {
    final PseudoBooleanTerm pseudoBooleanTerm =
        new PseudoBooleanTerm(1, new PsuedoBooleanIndeterminant[] {
            new PsuedoBooleanIndeterminant(0, false), new PsuedoBooleanIndeterminant(1, false) });
    assertEquals(1, pseudoBooleanTerm.calculateValue(new BitString(new boolean[] { true, true },
        new PsuedoBooleanFunction(new PseudoBooleanTerm[] { pseudoBooleanTerm }))));
  }

  @Test
  public void TestDoOperation_MultileBooleanOperationsTF_CorrectValue() {
    final PseudoBooleanTerm pseudoBooleanTerm =
        new PseudoBooleanTerm(1, new PsuedoBooleanIndeterminant[] {
            new PsuedoBooleanIndeterminant(0, false), new PsuedoBooleanIndeterminant(1, false) });
    assertEquals(0, pseudoBooleanTerm.calculateValue(new BitString(new boolean[] { true, false },
        new PsuedoBooleanFunction(new PseudoBooleanTerm[] { pseudoBooleanTerm }))));
  }

  @Test
  public void TestDoOperation_MultileBooleanOperationsTT_CorrectValue() {
    final PseudoBooleanTerm pseudoBooleanTerm =
        new PseudoBooleanTerm(1, new PsuedoBooleanIndeterminant[] {
            new PsuedoBooleanIndeterminant(0, false), new PsuedoBooleanIndeterminant(1, false) });
    assertEquals(1, pseudoBooleanTerm.calculateValue(new BitString(new boolean[] { true, true },
        new PsuedoBooleanFunction(new PseudoBooleanTerm[] { pseudoBooleanTerm }))));
  }

  @Test
  public void TestDoOperation_RandomInputs_GetCorrectValue() {
    final int solutionSize = 10;
    final int randomModifierMaxSize = 100;

    final int randomModifier = random.nextInt(randomModifierMaxSize);
    final int randomLocation = random.nextInt(solutionSize);

    int expectedAnswer = 0;

    final PseudoBooleanTerm pseudoBooleanTerm = new PseudoBooleanTerm(randomModifier,
        new PsuedoBooleanIndeterminant[] { new PsuedoBooleanIndeterminant(randomLocation, false) });

    final BitString pbs = new BitString(PbTestHelper.createRandomBoolArr(solutionSize),
        new PsuedoBooleanFunction(new PseudoBooleanTerm[] { pseudoBooleanTerm }));

    if (pbs.get(randomLocation)) {
      expectedAnswer = randomModifier;
    }
    assertEquals(expectedAnswer, pseudoBooleanTerm.calculateValue(pbs));
  }
}
