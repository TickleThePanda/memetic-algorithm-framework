//CHECKSTYLE:OFF
package uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.solutions.pseudo_boolean;

import static org.junit.Assert.assertEquals;

import java.util.Random;

import org.junit.BeforeClass;
import org.junit.Test;

public class BooleanOperationTest {

  @BeforeClass
  public static void SetupBeforeClass() {
    random = new Random();
  }

  static Random random;

  @Test
  public void TestGetLocation_Location5_CorrectLocation() {
    final BooleanIndeterminant bo = new BooleanIndeterminant(5, false);
    assertEquals(5, bo.getIndex());
  }

  @Test
  public void TestGetLocation_LocationRandom_CorrectLocation() {
    final int randomInt = random.nextInt(Integer.MAX_VALUE);
    final BooleanIndeterminant bo = new BooleanIndeterminant(randomInt, false);
    assertEquals(randomInt, bo.getIndex());
  }

  @Test
  public void TestIsFlipped_RandomBool_CorrectValue() {
    final boolean randomBool = random.nextBoolean();
    final BooleanIndeterminant bi = new BooleanIndeterminant(0, randomBool);
    assertEquals(randomBool, bi.getExpectedValue());
  }
}