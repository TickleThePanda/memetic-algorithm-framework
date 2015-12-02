package uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.solutions.pseudo_boolean;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.Random;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TermTest {

  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    random = new Random();
  }

  static Random random;

  @Test(expected = IllegalArgumentException.class)
  public void doOperation_nullSolution_correctOperation() {
    int i = random.nextInt();
    BooleanIndeterminant o = new BooleanIndeterminant(0, false);
    Term t =
        new Term(i, new BooleanIndeterminant[] { o,
            new BooleanIndeterminant(0, true) });

    t.calculateValue(null);
  }

  @Test
  public void doOperation_randomOperation_correctAnswer() {
    int i = random.nextInt();
    Term t =
        new Term(i, new BooleanIndeterminant[] { new BooleanIndeterminant(0,
            false) });

    PbSolution p =
        new PbSolution(new boolean[] { true }, new PbFunction(new Term[] { t }));

    assertEquals(i, t.calculateValue(p));
  }

  @Test
  public void doOperation_randomOperationNoMatch_correctAnswer() {
    int i = random.nextInt();
    Term t =
        new Term(i, new BooleanIndeterminant[] {
            new BooleanIndeterminant(0, false),
            new BooleanIndeterminant(0, true) });

    PbSolution p =
        new PbSolution(new boolean[] { true }, new PbFunction(new Term[] { t }));

    assertEquals(0, t.calculateValue(p));
  }

  @Test
  public void doOperation_simpleOperation_correctAnswer() {
    Term t =
        new Term(100, new BooleanIndeterminant[] { new BooleanIndeterminant(0,
            false) });

    PbSolution p =
        new PbSolution(new boolean[] { true }, new PbFunction(new Term[] { t }));

    assertEquals(100, t.calculateValue(p));
  }

  @Test
  public void Equals_DifferentType_False() {
    int i = random.nextInt();
    BooleanIndeterminant o = new BooleanIndeterminant(0, false);
    Term t = new Term(i, new BooleanIndeterminant[] { o });

    assertNotEquals(t, new Object());
  }

  @Test
  public void Equals_Equal_True() {
    int i1 = random.nextInt();
    BooleanIndeterminant o1 = new BooleanIndeterminant(0, false);
    Term t1 = new Term(i1, new BooleanIndeterminant[] { o1 });

    int i2 = i1;
    BooleanIndeterminant o2 = new BooleanIndeterminant(0, false);
    Term t2 = new Term(i2, new BooleanIndeterminant[] { o2 });

    assertEquals(t1, t2);
  }

  @Test
  public void Equals_Null_False() {
    int i = random.nextInt();
    BooleanIndeterminant o = new BooleanIndeterminant(0, false);
    Term t = new Term(i, new BooleanIndeterminant[] { o });

    assertNotEquals(t, null);
  }

  @Test
  public void Equals_SameReference_True() {
    int i = random.nextInt();
    BooleanIndeterminant o = new BooleanIndeterminant(0, false);
    Term t = new Term(i, new BooleanIndeterminant[] { o });

    assertEquals(t, t);
  }

  @Test
  public void Equals_SameTypeNotEqual_False() {
    int i1 = random.nextInt();
    BooleanIndeterminant o1 = new BooleanIndeterminant(0, false);
    Term t1 = new Term(i1, new BooleanIndeterminant[] { o1 });

    int i2 = random.nextInt();
    BooleanIndeterminant o2 = new BooleanIndeterminant(0, false);
    Term t2 = new Term(i2, new BooleanIndeterminant[] { o2 });

    assertNotEquals(t1, t2);
  }

  @Test
  public void getOperation_firstOperation_correctOperation() {
    int i = random.nextInt();
    BooleanIndeterminant o = new BooleanIndeterminant(0, false);
    Term t =
        new Term(i, new BooleanIndeterminant[] { o,
            new BooleanIndeterminant(0, true) });

    assertEquals(o, t.getBooleanIndeterminant(0));
  }

  @Test(expected = ArrayIndexOutOfBoundsException.class)
  public void getOperation_outOfBoundsOperation_correctOperation() {
    int i = random.nextInt();
    BooleanIndeterminant o = new BooleanIndeterminant(0, false);
    Term t =
        new Term(i, new BooleanIndeterminant[] { o,
            new BooleanIndeterminant(0, true) });

    t.getBooleanIndeterminant(2);
  }

  @Test
  public void hasCorrectLocations_doesNotHaveCorrectLocations_false() {
    int i = random.nextInt();
    BooleanIndeterminant o = new BooleanIndeterminant(0, false);
    Term t = new Term(i, new BooleanIndeterminant[] { o });

    PbSolution p =
        new PbSolution(new boolean[] { false },
            new PbFunction(new Term[] { t }));

    assertEquals(false, t.hasCorrectLocations(p));
  }

  @Test
  public void hasCorrectLocations_hasCorrectLocations_true() {
    int i = random.nextInt();
    BooleanIndeterminant o = new BooleanIndeterminant(0, false);
    Term t = new Term(i, new BooleanIndeterminant[] { o });

    PbSolution p =
        new PbSolution(new boolean[] { true }, new PbFunction(new Term[] { t }));

    assertEquals(true, t.hasCorrectLocations(p));
  }

  @Before
  public void setUp() throws Exception {
  }

  @Test(expected = IllegalArgumentException.class)
  public void TermIntOperation_operationNull_false() {
    int i = random.nextInt();
    BooleanIndeterminant o = null;
    new Term(i, new BooleanIndeterminant[] { o });
  }

}
