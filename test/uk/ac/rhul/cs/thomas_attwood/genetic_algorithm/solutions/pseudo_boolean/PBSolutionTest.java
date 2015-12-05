package uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.solutions.pseudo_boolean;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.fail;

import java.util.Random;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.problem.generators.pbf.QuadraticPbFunctionGenerator;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.problem.solutions.pbf.BitString;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.problem.solutions.pbf.PsuedoBooleanFunction;

public class PBSolutionTest {
  @BeforeClass
  public static void setUpBeforeClass() {
    random = new Random();
  }

  private static Random random;

  private PsuedoBooleanFunction function;
  private QuadraticPbFunctionGenerator quadraticPbFunctionGenerator;

  private int randomSize;

  @Test
  public void Equals_DifferentType_False() {
    final BitString sol =
        new BitString(new boolean[] { true, false, false, true, true },
            function);

    assertNotEquals(sol, new Object());
  }

  @Test
  public void Equals_Equal_True() {
    final BitString sol1 =
        new BitString(new boolean[] { true, false, false, true, true },
            function);

    final BitString sol2 =
        new BitString(new boolean[] { true, false, false, true, true },
            function);

    assertEquals(sol1, sol2);
  }

  @Test
  public void Equals_Null_False() {
    final BitString sol =
        new BitString(new boolean[] { true, false, false, true, true },
            function);

    assertNotEquals(sol, null);
  }

  @Test
  public void Equals_SameReference_True() {
    final BitString sol =
        new BitString(new boolean[] { true, false, false, true, true },
            function);

    assertEquals(sol, sol);
  }

  @Test
  public void Equals_SameTypeNotEqual_False() {
    final BitString sol1 =
        new BitString(new boolean[] { false, false, false, true, true },
            function);

    final BitString sol2 =
        new BitString(new boolean[] { true, false, false, true, true },
            function);

    assertNotEquals(sol1, sol2);
  }

  @Test
  public void FlipBit_Bit4_CorrectBit() {
    final int flipBitI = 3;
    final BitString bfOriginal =
        new BitString(new boolean[] { true, true, true, true, true }, function);
    final BitString expected =
        new BitString(new boolean[] { true, true, true, false, true },
            function);

    assertEquals(expected, bfOriginal.flipBit(flipBitI));

  }

  @Test
  public void FlipBit_RandomBit_CorrectBit() {
    final int size = 5;
    final int flipBitI = random.nextInt(size);
    final BitString bfOriginal =
        new BitString(new boolean[] { true, true, true, true, true }, function);

    final boolean[] expectedBitString = new boolean[bfOriginal.size()];
    for (int i = 0; i < bfOriginal.size(); i++) {
      if (i != flipBitI) {
        expectedBitString[i] = bfOriginal.get(i);
      } else {
        expectedBitString[i] = !bfOriginal.get(i);
      }
    }
    final BitString expected = new BitString(expectedBitString, function);

    assertEquals(expected, bfOriginal.flipBit(flipBitI));

  }

  @Test
  public void FlipBit_RandomStringRandomBit_CorrectBit() {
    final int size = 5;
    final int flipBitI = random.nextInt(size);

    final boolean[] originalBitString = new boolean[size];
    for (int i = 0; i < size; i++) {
      originalBitString[i] = random.nextBoolean();
    }
    final BitString bfOriginal =
        new BitString(new boolean[] { true, true, true, true, true }, function);

    final boolean[] expectedBitString = new boolean[bfOriginal.size()];
    for (int i = 0; i < bfOriginal.size(); i++) {
      if (i != flipBitI) {
        expectedBitString[i] = bfOriginal.get(i);
      } else {
        expectedBitString[i] = !bfOriginal.get(i);
      }
    }
    final BitString expected = new BitString(expectedBitString, function);

    assertEquals(expected, bfOriginal.flipBit(flipBitI));

  }

  @Test
  public void FlipBit_RandomStringRandomSizeRandomBit_CorrectBit() {
    final int size = random.nextInt(100) + 5;
    final int flipBitI = random.nextInt(size);

    final boolean[] originalBitString = new boolean[size];
    for (int i = 0; i < size; i++) {
      originalBitString[i] = random.nextBoolean();
    }
    final BitString bfOriginal =
        new BitString(new boolean[] { true, true, true, true, true }, function);

    final boolean[] expectedBitString = new boolean[bfOriginal.size()];
    for (int i = 0; i < bfOriginal.size(); i++) {
      if (i != flipBitI) {
        expectedBitString[i] = bfOriginal.get(i);
      } else {
        expectedBitString[i] = !bfOriginal.get(i);
      }
    }
    final BitString expected = new BitString(expectedBitString, function);

    assertEquals(expected, bfOriginal.flipBit(flipBitI));

  }

  @Test
  public void getActualValue_randomSolution_correctValue() {
    final BitString bitString =
        new BitString(PbTests.createRandomBoolArr(randomSize), function);
    assertEquals(function.actualValue(bitString),
        bitString.getObjectiveValue());
  }

  @Test
  public void getFitness_randomSolution_correctValue() {
    final BitString bitString =
        new BitString(PbTests.createRandomBoolArr(randomSize), function);
    assertEquals(function.evaluateFitness(bitString), bitString.getFitness());
  }

  @Test
  public void getFunctionFactory_randomFunction_correctReturned() {

    final BitString bitString =
        new BitString(PbTests.createRandomBoolArr(randomSize), function);

    assertEquals(function, bitString.getFitnessFunction());
  }

  @Before
  public void setUp() {
    randomSize = random.nextInt(20) + 3;
    quadraticPbFunctionGenerator = new QuadraticPbFunctionGenerator(randomSize);
    function = quadraticPbFunctionGenerator.generateFunction();
  }

  @Test
  public void TestComparator_DifferentItem_ReturnDiff() {
    final BitString bfOriginal =
        new BitString(new boolean[] { true, false, false, true }, function);
    final BitString bfDiff =
        new BitString(new boolean[] { false, true, true, false }, function);
    assertEquals(4, bfDiff.compareTo(bfOriginal));
  }

  @Test
  public void TestComparator_DiffSizeSameItems_DiffLength() {
    final BitString bfOriginal =
        new BitString(new boolean[] { true, false, false, true }, function);
    final BitString bfDiff =
        new BitString(
            new boolean[] { true, false, false, true, false, false }, function);
    assertEquals(2, bfDiff.compareTo(bfOriginal));
  }

  @Test
  public void TestComparator_RandomValues_ReturnDiff() {
    final int randomSize = random.nextInt(20);
    final int randomSize2 = random.nextInt(20);

    final boolean[] bools = PbTests.createRandomBoolArr(randomSize);
    final boolean[] bools2 = PbTests.createRandomBoolArr(randomSize2);

    int nchanges = Math.abs(bools.length - bools2.length);

    final int smallestBoolArr = Math.min(bools.length, bools2.length);
    for (int i = 0; i < smallestBoolArr; i++) {
      if (bools[i] != bools2[i]) {
        nchanges++;
      }
    }

    final BitString bf = new BitString(bools, function);
    final BitString bf2 = new BitString(bools2, function);

    assertEquals(nchanges, bf.compareTo(bf2));
  }

  @Test
  public void TestComparator_SameItem_NoDiff() {
    final BitString bfOriginal =
        new BitString(new boolean[] { true, false, false, true }, function);
    final BitString bfSame =
        new BitString(new boolean[] { true, false, false, true }, function);
    assertEquals(0, bfSame.compareTo(bfOriginal));
  }

  @Test
  public void TestGet_GetInRange_CorrectValue() {
    final BitString bfOriginal =
        new BitString(new boolean[] { true, false, false, true }, function);
    assertEquals(true, bfOriginal.get(0));
  }

  @Test
  public void TestGet_GetOutOfRange_OutOfBounds() {
    final BitString bfOriginal = new BitString(new boolean[] {}, function);

    try {
      bfOriginal.get(1);
    } catch (final IndexOutOfBoundsException e) {
      assertEquals(e.getMessage(), "Index 1 is out of bounds!");
      return;
    }
    fail("exception was not thrown");
  }

  @Test
  public void TestGet_GetRandomInRange_CorrectValue() {
    final boolean[] bools = new boolean[] { true, false, false, true, true };
    final BitString bfOriginal = new BitString(bools, function);
    final int randomValue = random.nextInt(5);
    assertEquals(bools[randomValue], bfOriginal.get(randomValue));
  }

  @Test
  public void TestGet_GetRandomOutOfRange_OutOfBounds() {
    final BitString bfOriginal = new BitString(new boolean[] {}, function);

    final int randomValue = random.nextInt(20) + 1;

    try {
      bfOriginal.get(randomValue);
    } catch (final IndexOutOfBoundsException e) {
      assertEquals(e.getMessage(), "Index " + randomValue
          + " is out of bounds!");
      return;
    }
    fail("exception was not thrown");
  }

  @Test
  public void TestMutate_RandomSeed_CorrectMutation() {
    final long seed = random.nextLong();

    final int solutionSize = 4;
    final Random testRandom = new Random(seed);

    final boolean[] testBools = PbTests.createRandomBoolArr(solutionSize);
    final BitString bf = new BitString(seed, testBools, function);

    final int flippedBitIndex = testRandom.nextInt(solutionSize);
    final boolean flippedBit = !bf.get(flippedBitIndex);

    assertEquals(flippedBit, bf.getMutated().get(flippedBitIndex));

  }

  @Test
  public void TestMutate_RandomValueInRange_SolutionMutated() {

    final int boolSize = 4;

    final boolean[] testBools = PbTests.createRandomBoolArr(boolSize);
    final BitString bf = new BitString(testBools, function);

    final boolean[] testBools2 = new boolean[boolSize];
    for (int i = 0; i < boolSize; i++) {
      testBools2[i] = testBools[i];
    }
    final BitString bfMutated =
        new BitString(testBools2, function).getMutated();
    assertEquals(1, bfMutated.compareTo(bf));
  }

  @Test
  public void TestMutate_Seed0_CorrectMutation() {
    final long seed = 0;
    final Random testRandom = new Random(seed);
    final int solutionSize = 4;

    final boolean[] testBools = PbTests.createRandomBoolArr(solutionSize);
    BitString bf = new BitString(seed, testBools, function);

    final int flippedBitIndex = testRandom.nextInt(solutionSize);
    final boolean flippedBit = !bf.get(flippedBitIndex);

    bf = bf.getMutated();
    assertEquals(flippedBit, bf.get(flippedBitIndex));
  }

  @Test
  public void TestMutate_TestItemMutated_SolutionMutated() {
    final BitString bfOriginal =
        new BitString(new boolean[] { true, false, false, true }, function);
    final BitString bfMutated =
        new BitString(new boolean[] { true, false, false, true }, function);

    assertEquals(1, bfMutated.getMutated().compareTo(bfOriginal));
  }

  @Test
  public void TestSize_RandomSize_CorrectSize() {
    final int randomSize = random.nextInt(20);
    final boolean[] bools = PbTests.createRandomBoolArr(randomSize);
    final BitString bfs = new BitString(bools, function);
    assertEquals(randomSize, bfs.size());
  }

  @Test
  public void TestSize_Size5_Returns5() {
    final BitString bfOriginal =
        new BitString(new boolean[] { true, false, false, true, true },
            function);
    assertEquals(5, bfOriginal.size());
  }
}
