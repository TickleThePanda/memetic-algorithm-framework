package uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.solutions.pseudo_boolean;

import static org.junit.Assert.assertEquals;

import java.util.Random;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.function_generators.pbf.QuadraticPbFunctionGenerator;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.solutions.Problem;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.solutions.Solution.Generator;

public class PBSolutionFactoryTest {

  @BeforeClass
  public static void setUpBeforeClass() {
    random = new Random();
  }

  private static Random random;

  private Problem<BitString> function;

  @Test
  public void GetFitnessFunction_RandomFitnessFunction_CorrectFitnessFunction() {
    final int seed = 0;
    final Generator<BitString> pbsg =
        new BitStringFactory(seed, function);

    assertEquals(function, pbsg.getFitnessFunction());
  }

  @Before
  public void setUp() {
    function = new QuadraticPbFunctionGenerator(10).generateFunction();
  }

  @Test
  public void TestCrossover_DiffSizesFirstSmaller_ChildCorrect() {
    final int maxSize = 6;
    final long seed = 0;

    final Problem<BitString> function =
        new QuadraticPbFunctionGenerator(maxSize).generateFunction();

    final BitString bf1 =
        new BitString(new boolean[] { true, false, true, false, true },
            function);
    final BitString bf2 =
        new BitString(new boolean[] { false, true, false, true, false, true },
            function);

    final BitString child =
        new BitStringFactory(seed, function).crossover(bf1, bf2);

    final Random seededRandom = new Random(seed);

    final int crossoverPoint = seededRandom.nextInt(bf1.size());

    for (int i = 0; i < bf1.size() && i < bf2.size(); i++) {
      if (i < crossoverPoint) {
        assertEquals(bf1.get(i), child.get(i));
      } else {
        assertEquals(bf2.get(i), child.get(i));
      }
    }
  }

  @Test
  public void TestCrossover_DiffSizesSecondSmaller_ChildCorrect() {
    final int maxSize = 5;
    final long seed = 0;

    final Problem<BitString> function =
        new QuadraticPbFunctionGenerator(maxSize).generateFunction();

    final BitString bf1 =
        new BitString(new boolean[] { true, false, true, false, true },
            function);
    final BitString bf2 =
        new BitString(new boolean[] { false, true, false, true }, function);

    final BitString child =
        new BitStringFactory(seed, function).crossover(bf1, bf2);

    final Random seededRandom = new Random(seed);

    final int crossoverPoint = seededRandom.nextInt(bf2.size());

    for (int i = 0; i < bf1.size() && i < bf2.size(); i++) {
      if (i < crossoverPoint) {
        assertEquals(bf1.get(i), child.get(i));
      } else if (i < bf2.size()) {
        assertEquals(bf2.get(i), child.get(i));
      } else {
        assertEquals(bf1.get(i), child.get(i));
      }
    }
  }

  @Test
  public void TestCrossover_RandomSeeds_ChildrenCorrect() {
    final long seed = random.nextLong();
    final int maxChildren = 15;
    final int minChildren = 10;
    final int nChildren =
        random.nextInt(maxChildren - minChildren) + minChildren;
    final int size = 4;

    final Problem<BitString> function =
        new QuadraticPbFunctionGenerator(size).generateFunction();

    final boolean[] bools1 = new boolean[] { true, true, false, false };
    final boolean[] bools2 = new boolean[] { false, true, true, false };

    final BitString bf1 = new BitString(bools1, function);
    final BitString bf2 = new BitString(bools2, function);

    final BitStringFactory bitStringFactory =
        new BitStringFactory(seed, function);

    final BitString[] children = new BitString[nChildren];

    for (int i = 0; i < nChildren; i++) {
      children[i] = bitStringFactory.crossover(bf1, bf2);
    }

    final Random seededRandom = new Random(seed);

    for (int childIndex = 0; childIndex < nChildren; childIndex++) {
      final int crossoverPoint = seededRandom.nextInt(size);
      // throw away random long for mutation seed
      seededRandom.nextLong();
      for (int solIndex = 0; solIndex < bf1.size() && solIndex < bf2.size(); solIndex++) {
        if (solIndex < crossoverPoint) {
          assertEquals(bools1[solIndex], children[childIndex].get(solIndex));
        } else {
          assertEquals(bools2[solIndex], children[childIndex].get(solIndex));
        }
      }
    }
  }

  @Test
  public void TestCrossover_Seed0_ChildrenCorrect() {
    final long seed = 0;
    final int maxChildren = 15;
    final int minChildren = 10;
    final int nChildren =
        random.nextInt(maxChildren - minChildren) + minChildren;

    final int size = 4;

    final Problem<BitString> function =
        new QuadraticPbFunctionGenerator(size).generateFunction();

    final boolean[] bools1 = new boolean[] { true, true, false, false };
    final boolean[] bools2 = new boolean[] { false, true, true, false };

    final BitString bf1 = new BitString(bools1, function);
    final BitString bf2 = new BitString(bools2, function);

    final BitStringFactory bitStringFactory =
        new BitStringFactory(seed, function);

    final BitString[] children = new BitString[nChildren];

    for (int i = 0; i < nChildren; i++) {
      children[i] = bitStringFactory.crossover(bf1, bf2);
    }

    final Random seededRandom = new Random(seed);

    for (int childIndex = 0; childIndex < nChildren; childIndex++) {
      final int crossoverPoint = seededRandom.nextInt(4);
      // throwaway random
      seededRandom.nextLong();
      for (int solIndex = 0; solIndex < bf1.size() && solIndex < bf2.size(); solIndex++) {
        if (solIndex < crossoverPoint) {
          assertEquals(bools1[solIndex], children[childIndex].get(solIndex));
        } else {
          assertEquals(bools2[solIndex], children[childIndex].get(solIndex));
        }
      }
    }
  }

  @Test
  public void TestCrossover_TwoRandomParents_ChildCorrect() {
    final int maxRandomSize = 30;
    final int minRandomSize = 10;
    final int randomSize =
        random.nextInt(maxRandomSize - minRandomSize) + minRandomSize;

    final Problem<BitString> function =
        new QuadraticPbFunctionGenerator(randomSize).generateFunction();

    final long seed = 0;

    final BitString bf1 =
        new BitString(PbTests.createRandomBoolArr(randomSize), function);
    final BitString bf2 =
        new BitString(PbTests.createRandomBoolArr(randomSize), function);

    final BitString child =
        new BitStringFactory(seed, function).crossover(bf1, bf2);

    final Random seededRandom = new Random(seed);

    final int crossoverPoint = seededRandom.nextInt(bf2.size());

    for (int i = 0; i < bf1.size() && i < bf2.size(); i++) {
      if (i < crossoverPoint) {
        assertEquals(bf1.get(i), child.get(i));
      } else if (i < bf2.size()) {
        assertEquals(bf2.get(i), child.get(i));
      } else {
        assertEquals(bf1.get(i), child.get(i));
      }
    }
  }

  @Test
  public void TestGenerateSolution_RandomSeed_SolutionCorrectlyGenerated() {
    final int size = 10;
    final long seed = random.nextLong();

    final Problem<BitString> function =
        new QuadraticPbFunctionGenerator(size).generateFunction();

    final Generator<BitString> pbsg =
        new BitStringFactory(seed, function);

    final Random testRandom = new Random(seed);

    final BitString solution = pbsg.generateSolution();

    // throwaway seed generation
    testRandom.nextLong();

    for (int i = 0; i < solution.size(); i++) {
      assertEquals(testRandom.nextBoolean(), solution.get(i));
    }
  }

  @Test
  public void TestGenerateSolution_RandomSeedMultipleSolutions_SolutionCorrectlyGenerated() {
    final int size = 10;
    final int nSols = 10;
    final long seed = random.nextLong();

    final Problem<BitString> function =
        new QuadraticPbFunctionGenerator(size).generateFunction();

    final Random testRandom = new Random(seed);
    final Generator<BitString> pbsg =
        new BitStringFactory(seed, function);

    for (int j = 0; j < nSols; j++) {

      final BitString solution = pbsg.generateSolution();

      // throwaway seed generation
      testRandom.nextLong();

      for (int i = 0; i < solution.size(); i++) {
        assertEquals(testRandom.nextBoolean(), solution.get(i));
      }
    }
  }

  @Test
  public void TestGenerateSolution_Seed0_SolutionCorrectlyGenerated() {
    final int seed = 0;
    final Generator<BitString> pbsg =
        new BitStringFactory(seed, function);

    final Random testRandom = new Random(seed);

    final BitString solution = pbsg.generateSolution();

    // throwaway seed generation
    testRandom.nextLong();

    for (int i = 0; i < solution.size(); i++) {
      assertEquals(testRandom.nextBoolean(), solution.get(i));
    }
  }

}
