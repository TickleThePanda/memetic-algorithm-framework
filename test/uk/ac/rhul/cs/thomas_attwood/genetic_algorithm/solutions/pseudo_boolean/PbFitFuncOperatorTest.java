package uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.solutions.pseudo_boolean;

import static org.junit.Assert.assertEquals;

import java.util.Random;

import org.junit.BeforeClass;
import org.junit.Test;

import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.problem.solutions.pbf.BitString;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.problem.solutions.pbf.PseudoBooleanTerm;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.problem.solutions.pbf.PsuedoBooleanFunction;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.problem.solutions.pbf.PsuedoBooleanIndeterminant;

public class PbFitFuncOperatorTest {

  @BeforeClass
  public static void setUpBeforeClass() {
    random = new Random();
  }

  private static Random random;

  @Test
  public void TestDoOperation_BaseCase_GetCorrectValue() {
    final PseudoBooleanTerm pseudoBooleanTerm =
        new PseudoBooleanTerm(1, new PsuedoBooleanIndeterminant[] { new PsuedoBooleanIndeterminant(0,
            false) });
    assertEquals(1, pseudoBooleanTerm.calculateValue(new BitString(new boolean[] { true },
        new PsuedoBooleanFunction(new PseudoBooleanTerm[] { pseudoBooleanTerm }))));
  }

  @Test
  public void TestDoOperation_FlippedBools_CorrectValue() {
    final PseudoBooleanTerm pseudoBooleanTerm =
        new PseudoBooleanTerm(1, new PsuedoBooleanIndeterminant[] {
            new PsuedoBooleanIndeterminant(0, false),
            new PsuedoBooleanIndeterminant(1, false) });
    assertEquals(1, pseudoBooleanTerm.calculateValue(new BitString(new boolean[] { true,
        true }, new PsuedoBooleanFunction(new PseudoBooleanTerm[] { pseudoBooleanTerm }))));
  }

  @Test
  public void TestDoOperation_MultileBooleanOperationsTF_CorrectValue() {
    final PseudoBooleanTerm pseudoBooleanTerm =
        new PseudoBooleanTerm(1, new PsuedoBooleanIndeterminant[] {
            new PsuedoBooleanIndeterminant(0, false),
            new PsuedoBooleanIndeterminant(1, false) });
    assertEquals(0, pseudoBooleanTerm.calculateValue(new BitString(new boolean[] { true,
        false }, new PsuedoBooleanFunction(new PseudoBooleanTerm[] { pseudoBooleanTerm }))));
  }

  @Test
  public void TestDoOperation_MultileBooleanOperationsTT_CorrectValue() {
    final PseudoBooleanTerm pseudoBooleanTerm =
        new PseudoBooleanTerm(1, new PsuedoBooleanIndeterminant[] {
            new PsuedoBooleanIndeterminant(0, false),
            new PsuedoBooleanIndeterminant(1, false) });
    assertEquals(1, pseudoBooleanTerm.calculateValue(new BitString(new boolean[] { true,
        true }, new PsuedoBooleanFunction(new PseudoBooleanTerm[] { pseudoBooleanTerm }))));
  }

  @Test
  public void TestDoOperation_RandomInputs_GetCorrectValue() {
    final int solutionSize = 10;
    final int randomModifierMaxSize = 100;

    final int randomModifier = random.nextInt(randomModifierMaxSize);
    final int randomLocation = random.nextInt(solutionSize);

    int expectedAnswer = 0;

    final PseudoBooleanTerm pseudoBooleanTerm =
        new PseudoBooleanTerm(randomModifier,
            new PsuedoBooleanIndeterminant[] { new PsuedoBooleanIndeterminant(
                randomLocation, false) });

    final BitString pbs =
        new BitString(PbTests.createRandomBoolArr(solutionSize),
            new PsuedoBooleanFunction(new PseudoBooleanTerm[] { pseudoBooleanTerm }));

    if (pbs.get(randomLocation)) {
      expectedAnswer = randomModifier;
    }
    assertEquals(expectedAnswer, pseudoBooleanTerm.calculateValue(pbs));
  }
}
