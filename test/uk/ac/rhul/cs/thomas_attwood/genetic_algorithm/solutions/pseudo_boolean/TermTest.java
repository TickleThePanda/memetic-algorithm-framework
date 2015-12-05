package uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.solutions.pseudo_boolean;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.Random;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.problem.solutions.pbf.BitString;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.problem.solutions.pbf.PseudoBooleanTerm;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.problem.solutions.pbf.PsuedoBooleanFunction;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.problem.solutions.pbf.PsuedoBooleanIndeterminant;

public class TermTest {

  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    random = new Random();
  }

  static Random random;

  @Test(expected = IllegalArgumentException.class)
  public void doOperation_nullSolution_correctOperation() {
    int i = random.nextInt();
    PsuedoBooleanIndeterminant o = new PsuedoBooleanIndeterminant(0, false);
    PseudoBooleanTerm t =
        new PseudoBooleanTerm(i, new PsuedoBooleanIndeterminant[] { o,
            new PsuedoBooleanIndeterminant(0, true) });

    t.calculateValue(null);
  }

  @Test
  public void doOperation_randomOperation_correctAnswer() {
    int i = random.nextInt();
    PseudoBooleanTerm t =
        new PseudoBooleanTerm(i, new PsuedoBooleanIndeterminant[] { new PsuedoBooleanIndeterminant(0,
            false) });

    BitString p =
        new BitString(new boolean[] { true }, new PsuedoBooleanFunction(new PseudoBooleanTerm[] { t }));

    assertEquals(i, t.calculateValue(p));
  }

  @Test
  public void doOperation_randomOperationNoMatch_correctAnswer() {
    int i = random.nextInt();
    PseudoBooleanTerm t =
        new PseudoBooleanTerm(i, new PsuedoBooleanIndeterminant[] {
            new PsuedoBooleanIndeterminant(0, false),
            new PsuedoBooleanIndeterminant(0, true) });

    BitString p =
        new BitString(new boolean[] { true }, new PsuedoBooleanFunction(new PseudoBooleanTerm[] { t }));

    assertEquals(0, t.calculateValue(p));
  }

  @Test
  public void doOperation_simpleOperation_correctAnswer() {
    PseudoBooleanTerm t =
        new PseudoBooleanTerm(100, new PsuedoBooleanIndeterminant[] { new PsuedoBooleanIndeterminant(0,
            false) });

    BitString p =
        new BitString(new boolean[] { true }, new PsuedoBooleanFunction(new PseudoBooleanTerm[] { t }));

    assertEquals(100, t.calculateValue(p));
  }

  @Test
  public void Equals_DifferentType_False() {
    int i = random.nextInt();
    PsuedoBooleanIndeterminant o = new PsuedoBooleanIndeterminant(0, false);
    PseudoBooleanTerm t = new PseudoBooleanTerm(i, new PsuedoBooleanIndeterminant[] { o });

    assertNotEquals(t, new Object());
  }

  @Test
  public void Equals_Equal_True() {
    int i1 = random.nextInt();
    PsuedoBooleanIndeterminant o1 = new PsuedoBooleanIndeterminant(0, false);
    PseudoBooleanTerm t1 = new PseudoBooleanTerm(i1, new PsuedoBooleanIndeterminant[] { o1 });

    int i2 = i1;
    PsuedoBooleanIndeterminant o2 = new PsuedoBooleanIndeterminant(0, false);
    PseudoBooleanTerm t2 = new PseudoBooleanTerm(i2, new PsuedoBooleanIndeterminant[] { o2 });

    assertEquals(t1, t2);
  }

  @Test
  public void Equals_Null_False() {
    int i = random.nextInt();
    PsuedoBooleanIndeterminant o = new PsuedoBooleanIndeterminant(0, false);
    PseudoBooleanTerm t = new PseudoBooleanTerm(i, new PsuedoBooleanIndeterminant[] { o });

    assertNotEquals(t, null);
  }

  @Test
  public void Equals_SameReference_True() {
    int i = random.nextInt();
    PsuedoBooleanIndeterminant o = new PsuedoBooleanIndeterminant(0, false);
    PseudoBooleanTerm t = new PseudoBooleanTerm(i, new PsuedoBooleanIndeterminant[] { o });

    assertEquals(t, t);
  }

  @Test
  public void Equals_SameTypeNotEqual_False() {
    int i1 = random.nextInt();
    PsuedoBooleanIndeterminant o1 = new PsuedoBooleanIndeterminant(0, false);
    PseudoBooleanTerm t1 = new PseudoBooleanTerm(i1, new PsuedoBooleanIndeterminant[] { o1 });

    int i2 = random.nextInt();
    PsuedoBooleanIndeterminant o2 = new PsuedoBooleanIndeterminant(0, false);
    PseudoBooleanTerm t2 = new PseudoBooleanTerm(i2, new PsuedoBooleanIndeterminant[] { o2 });

    assertNotEquals(t1, t2);
  }

  @Test
  public void getOperation_firstOperation_correctOperation() {
    int i = random.nextInt();
    PsuedoBooleanIndeterminant o = new PsuedoBooleanIndeterminant(0, false);
    PseudoBooleanTerm t =
        new PseudoBooleanTerm(i, new PsuedoBooleanIndeterminant[] { o,
            new PsuedoBooleanIndeterminant(0, true) });

    assertEquals(o, t.getBooleanIndeterminant(0));
  }

  @Test(expected = ArrayIndexOutOfBoundsException.class)
  public void getOperation_outOfBoundsOperation_correctOperation() {
    int i = random.nextInt();
    PsuedoBooleanIndeterminant o = new PsuedoBooleanIndeterminant(0, false);
    PseudoBooleanTerm t =
        new PseudoBooleanTerm(i, new PsuedoBooleanIndeterminant[] { o,
            new PsuedoBooleanIndeterminant(0, true) });

    t.getBooleanIndeterminant(2);
  }

  @Test
  public void hasCorrectLocations_doesNotHaveCorrectLocations_false() {
    int i = random.nextInt();
    PsuedoBooleanIndeterminant o = new PsuedoBooleanIndeterminant(0, false);
    PseudoBooleanTerm t = new PseudoBooleanTerm(i, new PsuedoBooleanIndeterminant[] { o });

    BitString p =
        new BitString(new boolean[] { false },
            new PsuedoBooleanFunction(new PseudoBooleanTerm[] { t }));

    assertEquals(false, t.hasCorrectLocations(p));
  }

  @Test
  public void hasCorrectLocations_hasCorrectLocations_true() {
    int i = random.nextInt();
    PsuedoBooleanIndeterminant o = new PsuedoBooleanIndeterminant(0, false);
    PseudoBooleanTerm t = new PseudoBooleanTerm(i, new PsuedoBooleanIndeterminant[] { o });

    BitString p =
        new BitString(new boolean[] { true }, new PsuedoBooleanFunction(new PseudoBooleanTerm[] { t }));

    assertEquals(true, t.hasCorrectLocations(p));
  }

  @Before
  public void setUp() throws Exception {
  }

  @Test(expected = IllegalArgumentException.class)
  public void TermIntOperation_operationNull_false() {
    int i = random.nextInt();
    PsuedoBooleanIndeterminant o = null;
    new PseudoBooleanTerm(i, new PsuedoBooleanIndeterminant[] { o });
  }

}
