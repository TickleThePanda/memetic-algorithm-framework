package uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.solutions.pseudo_boolean;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.fail;

import java.util.Random;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class PBSolutionTest {
  @BeforeClass
  public static void setUpBeforeClass() {
    random = new Random();
  }

  private static Random random;

  private PbFunction function;
  private QuadraticPbFuncFactory quadraticPbFuncFactory;

  private int randomSize;

  @Test
  public void Equals_DifferentType_False() {
    final PbSolution sol =
        new PbSolution(new boolean[] { true, false, false, true, true },
            function);

    assertNotEquals(sol, new Object());
  }

  @Test
  public void Equals_Equal_True() {
    final PbSolution sol1 =
        new PbSolution(new boolean[] { true, false, false, true, true },
            function);

    final PbSolution sol2 =
        new PbSolution(new boolean[] { true, false, false, true, true },
            function);

    assertEquals(sol1, sol2);
  }

  @Test
  public void Equals_Null_False() {
    final PbSolution sol =
        new PbSolution(new boolean[] { true, false, false, true, true },
            function);

    assertNotEquals(sol, null);
  }

  @Test
  public void Equals_SameReference_True() {
    final PbSolution sol =
        new PbSolution(new boolean[] { true, false, false, true, true },
            function);

    assertEquals(sol, sol);
  }

  @Test
  public void Equals_SameTypeNotEqual_False() {
    final PbSolution sol1 =
        new PbSolution(new boolean[] { false, false, false, true, true },
            function);

    final PbSolution sol2 =
        new PbSolution(new boolean[] { true, false, false, true, true },
            function);

    assertNotEquals(sol1, sol2);
  }

  @Test
  public void FlipBit_Bit4_CorrectBit() {
    final int flipBitI = 3;
    final PbSolution bfOriginal =
        new PbSolution(new boolean[] { true, true, true, true, true }, function);
    final PbSolution expected =
        new PbSolution(new boolean[] { true, true, true, false, true },
            function);

    assertEquals(expected, bfOriginal.flipBit(flipBitI));

  }

  @Test
  public void FlipBit_RandomBit_CorrectBit() {
    final int size = 5;
    final int flipBitI = random.nextInt(size);
    final PbSolution bfOriginal =
        new PbSolution(new boolean[] { true, true, true, true, true }, function);

    final boolean[] expectedBitString = new boolean[bfOriginal.size()];
    for (int i = 0; i < bfOriginal.size(); i++) {
      if (i != flipBitI) {
        expectedBitString[i] = bfOriginal.get(i);
      } else {
        expectedBitString[i] = !bfOriginal.get(i);
      }
    }
    final PbSolution expected = new PbSolution(expectedBitString, function);

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
    final PbSolution bfOriginal =
        new PbSolution(new boolean[] { true, true, true, true, true }, function);

    final boolean[] expectedBitString = new boolean[bfOriginal.size()];
    for (int i = 0; i < bfOriginal.size(); i++) {
      if (i != flipBitI) {
        expectedBitString[i] = bfOriginal.get(i);
      } else {
        expectedBitString[i] = !bfOriginal.get(i);
      }
    }
    final PbSolution expected = new PbSolution(expectedBitString, function);

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
    final PbSolution bfOriginal =
        new PbSolution(new boolean[] { true, true, true, true, true }, function);

    final boolean[] expectedBitString = new boolean[bfOriginal.size()];
    for (int i = 0; i < bfOriginal.size(); i++) {
      if (i != flipBitI) {
        expectedBitString[i] = bfOriginal.get(i);
      } else {
        expectedBitString[i] = !bfOriginal.get(i);
      }
    }
    final PbSolution expected = new PbSolution(expectedBitString, function);

    assertEquals(expected, bfOriginal.flipBit(flipBitI));

  }

  @Test
  public void getActualValue_randomSolution_correctValue() {
    final PbSolution pbSolution =
        new PbSolution(PbTests.createRandomBoolArr(randomSize), function);
    assertEquals(function.actualValue(pbSolution),
        pbSolution.getObjectiveValue());
  }

  @Test
  public void getFitness_randomSolution_correctValue() {
    final PbSolution pbSolution =
        new PbSolution(PbTests.createRandomBoolArr(randomSize), function);
    assertEquals(function.evaluateFitness(pbSolution), pbSolution.getFitness());
  }

  @Test
  public void getFunctionFactory_randomFunction_correctReturned() {

    final PbSolution pbSolution =
        new PbSolution(PbTests.createRandomBoolArr(randomSize), function);

    assertEquals(function, pbSolution.getFitnessFunction());
  }

  @Before
  public void setUp() {
    randomSize = random.nextInt(20) + 3;
    quadraticPbFuncFactory = new QuadraticPbFuncFactory(randomSize);
    function = quadraticPbFuncFactory.generateFunction();
  }

  @Test
  public void TestComparator_DifferentItem_ReturnDiff() {
    final PbSolution bfOriginal =
        new PbSolution(new boolean[] { true, false, false, true }, function);
    final PbSolution bfDiff =
        new PbSolution(new boolean[] { false, true, true, false }, function);
    assertEquals(4, bfDiff.compareTo(bfOriginal));
  }

  @Test
  public void TestComparator_DiffSizeSameItems_DiffLength() {
    final PbSolution bfOriginal =
        new PbSolution(new boolean[] { true, false, false, true }, function);
    final PbSolution bfDiff =
        new PbSolution(
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

    final PbSolution bf = new PbSolution(bools, function);
    final PbSolution bf2 = new PbSolution(bools2, function);

    assertEquals(nchanges, bf.compareTo(bf2));
  }

  @Test
  public void TestComparator_SameItem_NoDiff() {
    final PbSolution bfOriginal =
        new PbSolution(new boolean[] { true, false, false, true }, function);
    final PbSolution bfSame =
        new PbSolution(new boolean[] { true, false, false, true }, function);
    assertEquals(0, bfSame.compareTo(bfOriginal));
  }

  @Test
  public void TestGet_GetInRange_CorrectValue() {
    final PbSolution bfOriginal =
        new PbSolution(new boolean[] { true, false, false, true }, function);
    assertEquals(true, bfOriginal.get(0));
  }

  @Test
  public void TestGet_GetOutOfRange_OutOfBounds() {
    final PbSolution bfOriginal = new PbSolution(new boolean[] {}, function);

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
    final PbSolution bfOriginal = new PbSolution(bools, function);
    final int randomValue = random.nextInt(5);
    assertEquals(bools[randomValue], bfOriginal.get(randomValue));
  }

  @Test
  public void TestGet_GetRandomOutOfRange_OutOfBounds() {
    final PbSolution bfOriginal = new PbSolution(new boolean[] {}, function);

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
    final PbSolution bf = new PbSolution(seed, testBools, function);

    final int flippedBitIndex = testRandom.nextInt(solutionSize);
    final boolean flippedBit = !bf.get(flippedBitIndex);

    assertEquals(flippedBit, bf.getMutated().get(flippedBitIndex));

  }

  @Test
  public void TestMutate_RandomValueInRange_SolutionMutated() {

    final int boolSize = 4;

    final boolean[] testBools = PbTests.createRandomBoolArr(boolSize);
    final PbSolution bf = new PbSolution(testBools, function);

    final boolean[] testBools2 = new boolean[boolSize];
    for (int i = 0; i < boolSize; i++) {
      testBools2[i] = testBools[i];
    }
    final PbSolution bfMutated =
        new PbSolution(testBools2, function).getMutated();
    assertEquals(1, bfMutated.compareTo(bf));
  }

  @Test
  public void TestMutate_Seed0_CorrectMutation() {
    final long seed = 0;
    final Random testRandom = new Random(seed);
    final int solutionSize = 4;

    final boolean[] testBools = PbTests.createRandomBoolArr(solutionSize);
    PbSolution bf = new PbSolution(seed, testBools, function);

    final int flippedBitIndex = testRandom.nextInt(solutionSize);
    final boolean flippedBit = !bf.get(flippedBitIndex);

    bf = bf.getMutated();
    assertEquals(flippedBit, bf.get(flippedBitIndex));
  }

  @Test
  public void TestMutate_TestItemMutated_SolutionMutated() {
    final PbSolution bfOriginal =
        new PbSolution(new boolean[] { true, false, false, true }, function);
    final PbSolution bfMutated =
        new PbSolution(new boolean[] { true, false, false, true }, function);

    assertEquals(1, bfMutated.getMutated().compareTo(bfOriginal));
  }

  @Test
  public void TestSize_RandomSize_CorrectSize() {
    final int randomSize = random.nextInt(20);
    final boolean[] bools = PbTests.createRandomBoolArr(randomSize);
    final PbSolution bfs = new PbSolution(bools, function);
    assertEquals(randomSize, bfs.size());
  }

  @Test
  public void TestSize_Size5_Returns5() {
    final PbSolution bfOriginal =
        new PbSolution(new boolean[] { true, false, false, true, true },
            function);
    assertEquals(5, bfOriginal.size());
  }
}
