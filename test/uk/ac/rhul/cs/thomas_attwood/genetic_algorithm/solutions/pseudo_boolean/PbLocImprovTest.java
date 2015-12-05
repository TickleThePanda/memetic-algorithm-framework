package uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.solutions.pseudo_boolean;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.problem.solutions.LocalImprovement;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.problem.solutions.pbf.BitString;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.problem.solutions.pbf.FlipBitLocalImprovement;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.problem.solutions.pbf.PseudoBooleanTerm;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.problem.solutions.pbf.PsuedoBooleanFunction;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.problem.solutions.pbf.PsuedoBooleanIndeterminant;

public class PbLocImprovTest {

  private PsuedoBooleanFunction simpleFitnessFunction;

  @Test
  public void GetCompleteImprovedSolution_NoImprovementPossible_OriginalRetured() {

    boolean[] originalBitSolution =
        new boolean[] { true, true, true, true, true };

    BitString solution =
        new BitString(originalBitSolution, simpleFitnessFunction);

    LocalImprovement<BitString> locImprov = new FlipBitLocalImprovement();

    assertEquals(solution, locImprov.getCompleteImprovedSolution(solution));
  }

  @Test
  public void GetCompleteImprovedSolution_SingleImprovementPossible_CorrectBitFlipped() {

    boolean[] originalBitSolution =
        new boolean[] { true, true, false, true, true };

    BitString solution =
        new BitString(originalBitSolution, simpleFitnessFunction);

    LocalImprovement<BitString> locImprov = new FlipBitLocalImprovement();

    boolean[] expectedBitSolution =
        new boolean[] { true, true, true, true, true };

    BitString expectedSolution =
        new BitString(expectedBitSolution, simpleFitnessFunction);
    for (int i = 0; i < 10; i++) {
      assertEquals(expectedSolution,
          locImprov.getCompleteImprovedSolution(solution));
    }
  }

  @Test
  public void GetCompleteImprovedSolution_TwoImprovementsPossible_BothBitsFlipped() {

    boolean[] originalBitSolution =
        new boolean[] { true, false, false, true, true };

    BitString solution =
        new BitString(originalBitSolution, simpleFitnessFunction);

    LocalImprovement<BitString> locImprov = new FlipBitLocalImprovement();

    boolean[] expectedBitSolution =
        new boolean[] { true, true, true, true, true };

    BitString expectedSolution =
        new BitString(expectedBitSolution, simpleFitnessFunction);
    assertEquals(expectedSolution,
        locImprov.getCompleteImprovedSolution(solution));
  }

  @Test
  public void GetNeighbourhoodImprovedSolution_NoImprovementPossible_OriginalRetured() {

    boolean[] originalBitSolution =
        new boolean[] { true, true, true, true, true };

    BitString solution =
        new BitString(originalBitSolution, simpleFitnessFunction);

    LocalImprovement<BitString> locImprov = new FlipBitLocalImprovement();

    assertEquals(solution, locImprov.getNeighbourhoodImprovedSolution(solution));
  }

  @Test
  public void GetNeighbourhoodImprovedSolution_OnePossible_BestBitFlipped() {

    boolean[] originalBitSolution =
        new boolean[] { true, true, false, false, true };

    BitString solution =
        new BitString(originalBitSolution, simpleFitnessFunction);

    LocalImprovement<BitString> locImprov = new FlipBitLocalImprovement();

    boolean[] expectedBitSolution =
        new boolean[] { true, true, true, false, true };

    BitString expectedSolution =
        new BitString(expectedBitSolution, simpleFitnessFunction);
    for (int i = 0; i < 10; i++) {
      assertEquals(expectedSolution,
          locImprov.getNeighbourhoodImprovedSolution(solution));
    }
  }

  @Test
  public void GetNeighbourhoodImprovedSolution_SingleImprovementPossible_CorrectBitFlipped() {

    boolean[] originalBitSolution =
        new boolean[] { true, true, false, true, true };

    BitString solution =
        new BitString(originalBitSolution, simpleFitnessFunction);

    LocalImprovement<BitString> locImprov = new FlipBitLocalImprovement();

    boolean[] expectedBitSolution =
        new boolean[] { true, true, true, true, true };

    BitString expectedSolution =
        new BitString(expectedBitSolution, simpleFitnessFunction);
    for (int i = 0; i < 10; i++) {
      assertEquals(expectedSolution,
          locImprov.getNeighbourhoodImprovedSolution(solution));
    }
  }

  @Test
  public void GetNeighbourhoodImprovedSolution_TwoImprovementsPossible_SingleBitFlipped() {

    boolean[] originalBitSolution =
        new boolean[] { true, false, false, true, true };

    BitString solution =
        new BitString(originalBitSolution, simpleFitnessFunction);

    LocalImprovement<BitString> locImprov = new FlipBitLocalImprovement();

    boolean[] expectedBitSolution =
        new boolean[] { true, true, false, true, true };

    BitString expectedSolution =
        new BitString(expectedBitSolution, simpleFitnessFunction);
    for (int i = 0; i < 10; i++) {
      assertEquals(expectedSolution,
          locImprov.getNeighbourhoodImprovedSolution(solution));
    }
  }

  @Test
  public void GetSingleOrderedImprovedSolution_NoImprovementPossible_OriginalRetured() {

    boolean[] originalBitSolution =
        new boolean[] { true, true, true, true, true };

    BitString solution =
        new BitString(originalBitSolution, simpleFitnessFunction);

    LocalImprovement<BitString> locImprov = new FlipBitLocalImprovement();

    assertEquals(solution, locImprov.getSingleOrderedImprovedSolution(solution));
  }

  @Test
  public void GetSingleOrderedImprovedSolution_SingleImprovementPossible_CorrectBitFlipped() {

    boolean[] originalBitSolution =
        new boolean[] { true, true, false, true, true };

    BitString solution =
        new BitString(originalBitSolution, simpleFitnessFunction);

    LocalImprovement<BitString> locImprov = new FlipBitLocalImprovement();

    boolean[] expectedBitSolution =
        new boolean[] { true, true, true, true, true };

    BitString expectedSolution =
        new BitString(expectedBitSolution, simpleFitnessFunction);
    for (int i = 0; i < 10; i++) {
      assertEquals(expectedSolution,
          locImprov.getSingleOrderedImprovedSolution(solution));
    }
  }

  @Test
  public void GetSingleOrderedImprovedSolution_TwoImprovementsPossible_SingleBitFlipped() {

    boolean[] originalBitSolution =
        new boolean[] { true, false, false, true, true };

    BitString solution =
        new BitString(originalBitSolution, simpleFitnessFunction);

    LocalImprovement<BitString> locImprov = new FlipBitLocalImprovement();

    boolean[] expectedBitSolution =
        new boolean[] { true, true, false, true, true };

    BitString expectedSolution =
        new BitString(expectedBitSolution, simpleFitnessFunction);
    for (int i = 0; i < 10; i++) {
      assertEquals(expectedSolution,
          locImprov.getSingleOrderedImprovedSolution(solution));
    }
  }

  @Test
  public void GetSingleRandomImprovedSolution_NoImprovementPossible_OriginalRetured() {

    boolean[] originalBitSolution =
        new boolean[] { true, true, true, true, true };

    BitString solution =
        new BitString(originalBitSolution, simpleFitnessFunction);

    LocalImprovement<BitString> locImprov = new FlipBitLocalImprovement();

    assertEquals(solution, locImprov.getSingleRandomImprovedSolution(solution));
  }

  @Test
  public void GetSingleRandomImprovedSolution_SingleImprovementPossible_CorrectBitFlipped() {

    boolean[] originalBitSolution =
        new boolean[] { true, true, false, true, true };

    BitString solution =
        new BitString(originalBitSolution, simpleFitnessFunction);

    LocalImprovement<BitString> locImprov = new FlipBitLocalImprovement();

    boolean[] expectedBitSolution =
        new boolean[] { true, true, true, true, true };

    BitString expectedSolution =
        new BitString(expectedBitSolution, simpleFitnessFunction);
    for (int i = 0; i < 10; i++) {
      assertEquals(expectedSolution,
          locImprov.getSingleRandomImprovedSolution(solution));
    }
  }

  @Test
  public void GetSingleRandomImprovedSolution_TwoImprovementsPossible_SingleBitFlipped() {

    boolean[] originalBitSolution =
        new boolean[] { true, false, false, true, true };

    BitString solution =
        new BitString(originalBitSolution, simpleFitnessFunction);

    LocalImprovement<BitString> locImprov = new FlipBitLocalImprovement();

    int expectedFitness = 400;

    for (int i = 0; i < 10; i++) {
      assertTrue("expected fitness == 400 for single improvment",
          expectedFitness == locImprov
              .getSingleRandomImprovedSolution(solution).getFitness());
    }
  }

  @Before
  public void setUpSimple() {
    final int coefficient = 100;
    PseudoBooleanTerm[] terms =
        new PseudoBooleanTerm[] {
            new PseudoBooleanTerm(
                coefficient,
                new PsuedoBooleanIndeterminant[] { new PsuedoBooleanIndeterminant(0, false) }),
            new PseudoBooleanTerm(
                coefficient,
                new PsuedoBooleanIndeterminant[] { new PsuedoBooleanIndeterminant(1, false) }),
            new PseudoBooleanTerm(
                coefficient,
                new PsuedoBooleanIndeterminant[] { new PsuedoBooleanIndeterminant(2, false) }),
            new PseudoBooleanTerm(
                coefficient,
                new PsuedoBooleanIndeterminant[] { new PsuedoBooleanIndeterminant(3, false) }),
            new PseudoBooleanTerm(
                coefficient,
                new PsuedoBooleanIndeterminant[] { new PsuedoBooleanIndeterminant(4, false) }) };
    simpleFitnessFunction = new PsuedoBooleanFunction(terms);
  }
}