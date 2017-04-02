package uk.co.ticklethepanda.memetic.problem.solutions.pbf;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import uk.co.ticklethepanda.memetic.problem.solutions.LocalImprovement;

public class PbLocImprovTest {

  private PsuedoBooleanFunction simpleFitnessFunction;

  @Test
  public void GetCompleteImprovedSolution_NoImprovementPossible_OriginalRetured() {

    final boolean[] originalBitSolution = new boolean[] { true, true, true, true, true };

    final BitString solution = new BitString(originalBitSolution, simpleFitnessFunction);

    final LocalImprovement<BitString> locImprov = new FlipBitLocalImprovement();

    assertEquals(solution, locImprov.getCompleteImprovedSolution(solution));
  }

  @Test
  public void GetCompleteImprovedSolution_SingleImprovementPossible_CorrectBitFlipped() {

    final boolean[] originalBitSolution = new boolean[] { true, true, false, true, true };

    final BitString solution = new BitString(originalBitSolution, simpleFitnessFunction);

    final LocalImprovement<BitString> locImprov = new FlipBitLocalImprovement();

    final boolean[] expectedBitSolution = new boolean[] { true, true, true, true, true };

    final BitString expectedSolution = new BitString(expectedBitSolution, simpleFitnessFunction);
    for (int i = 0; i < 10; i++) {
      assertEquals(expectedSolution, locImprov.getCompleteImprovedSolution(solution));
    }
  }

  @Test
  public void GetCompleteImprovedSolution_TwoImprovementsPossible_BothBitsFlipped() {

    final boolean[] originalBitSolution = new boolean[] { true, false, false, true, true };

    final BitString solution = new BitString(originalBitSolution, simpleFitnessFunction);

    final LocalImprovement<BitString> locImprov = new FlipBitLocalImprovement();

    final boolean[] expectedBitSolution = new boolean[] { true, true, true, true, true };

    final BitString expectedSolution = new BitString(expectedBitSolution, simpleFitnessFunction);
    assertEquals(expectedSolution, locImprov.getCompleteImprovedSolution(solution));
  }

  @Test
  public void GetNeighbourhoodImprovedSolution_NoImprovementPossible_OriginalRetured() {

    final boolean[] originalBitSolution = new boolean[] { true, true, true, true, true };

    final BitString solution = new BitString(originalBitSolution, simpleFitnessFunction);

    final LocalImprovement<BitString> locImprov = new FlipBitLocalImprovement();

    assertEquals(solution, locImprov.getNeighbourhoodImprovedSolution(solution));
  }

  @Test
  public void GetNeighbourhoodImprovedSolution_OnePossible_BestBitFlipped() {

    final boolean[] originalBitSolution = new boolean[] { true, true, false, false, true };

    final BitString solution = new BitString(originalBitSolution, simpleFitnessFunction);

    final LocalImprovement<BitString> locImprov = new FlipBitLocalImprovement();

    final boolean[] expectedBitSolution = new boolean[] { true, true, true, false, true };

    final BitString expectedSolution = new BitString(expectedBitSolution, simpleFitnessFunction);
    for (int i = 0; i < 10; i++) {
      assertEquals(expectedSolution, locImprov.getNeighbourhoodImprovedSolution(solution));
    }
  }

  @Test
  public void GetNeighbourhoodImprovedSolution_SingleImprovementPossible_CorrectBitFlipped() {

    final boolean[] originalBitSolution = new boolean[] { true, true, false, true, true };

    final BitString solution = new BitString(originalBitSolution, simpleFitnessFunction);

    final LocalImprovement<BitString> locImprov = new FlipBitLocalImprovement();

    final boolean[] expectedBitSolution = new boolean[] { true, true, true, true, true };

    final BitString expectedSolution = new BitString(expectedBitSolution, simpleFitnessFunction);
    for (int i = 0; i < 10; i++) {
      assertEquals(expectedSolution, locImprov.getNeighbourhoodImprovedSolution(solution));
    }
  }

  @Test
  public void GetNeighbourhoodImprovedSolution_TwoImprovementsPossible_SingleBitFlipped() {

    final boolean[] originalBitSolution = new boolean[] { true, false, false, true, true };

    final BitString solution = new BitString(originalBitSolution, simpleFitnessFunction);

    final LocalImprovement<BitString> locImprov = new FlipBitLocalImprovement();

    final boolean[] expectedBitSolution = new boolean[] { true, true, false, true, true };

    final BitString expectedSolution = new BitString(expectedBitSolution, simpleFitnessFunction);
    for (int i = 0; i < 10; i++) {
      assertEquals(expectedSolution, locImprov.getNeighbourhoodImprovedSolution(solution));
    }
  }

  @Test
  public void GetSingleOrderedImprovedSolution_NoImprovementPossible_OriginalRetured() {

    final boolean[] originalBitSolution = new boolean[] { true, true, true, true, true };

    final BitString solution = new BitString(originalBitSolution, simpleFitnessFunction);

    final LocalImprovement<BitString> locImprov = new FlipBitLocalImprovement();

    assertEquals(solution, locImprov.getSingleOrderedImprovedSolution(solution));
  }

  @Test
  public void GetSingleOrderedImprovedSolution_SingleImprovementPossible_CorrectBitFlipped() {

    final boolean[] originalBitSolution = new boolean[] { true, true, false, true, true };

    final BitString solution = new BitString(originalBitSolution, simpleFitnessFunction);

    final LocalImprovement<BitString> locImprov = new FlipBitLocalImprovement();

    final boolean[] expectedBitSolution = new boolean[] { true, true, true, true, true };

    final BitString expectedSolution = new BitString(expectedBitSolution, simpleFitnessFunction);
    for (int i = 0; i < 10; i++) {
      assertEquals(expectedSolution, locImprov.getSingleOrderedImprovedSolution(solution));
    }
  }

  @Test
  public void GetSingleOrderedImprovedSolution_TwoImprovementsPossible_SingleBitFlipped() {

    final boolean[] originalBitSolution = new boolean[] { true, false, false, true, true };

    final BitString solution = new BitString(originalBitSolution, simpleFitnessFunction);

    final LocalImprovement<BitString> locImprov = new FlipBitLocalImprovement();

    final boolean[] expectedBitSolution = new boolean[] { true, true, false, true, true };

    final BitString expectedSolution = new BitString(expectedBitSolution, simpleFitnessFunction);
    for (int i = 0; i < 10; i++) {
      assertEquals(expectedSolution, locImprov.getSingleOrderedImprovedSolution(solution));
    }
  }

  @Test
  public void GetSingleRandomImprovedSolution_NoImprovementPossible_OriginalRetured() {

    final boolean[] originalBitSolution = new boolean[] { true, true, true, true, true };

    final BitString solution = new BitString(originalBitSolution, simpleFitnessFunction);

    final LocalImprovement<BitString> locImprov = new FlipBitLocalImprovement();

    assertEquals(solution, locImprov.getSingleRandomImprovedSolution(solution));
  }

  @Test
  public void GetSingleRandomImprovedSolution_SingleImprovementPossible_CorrectBitFlipped() {

    final boolean[] originalBitSolution = new boolean[] { true, true, false, true, true };

    final BitString solution = new BitString(originalBitSolution, simpleFitnessFunction);

    final LocalImprovement<BitString> locImprov = new FlipBitLocalImprovement();

    final boolean[] expectedBitSolution = new boolean[] { true, true, true, true, true };

    final BitString expectedSolution = new BitString(expectedBitSolution, simpleFitnessFunction);
    for (int i = 0; i < 10; i++) {
      assertEquals(expectedSolution, locImprov.getSingleRandomImprovedSolution(solution));
    }
  }

  @Test
  public void GetSingleRandomImprovedSolution_TwoImprovementsPossible_SingleBitFlipped() {

    final boolean[] originalBitSolution = new boolean[] { true, false, false, true, true };

    final BitString solution = new BitString(originalBitSolution, simpleFitnessFunction);

    final LocalImprovement<BitString> locImprov = new FlipBitLocalImprovement();

    final int expectedFitness = 400;

    for (int i = 0; i < 10; i++) {
      assertTrue("expected fitness == 400 for single improvment",
          expectedFitness == locImprov.getSingleRandomImprovedSolution(solution).getFitness());
    }
  }

  @Before
  public void setUpSimple() {
    final int coefficient = 100;
    final PseudoBooleanTerm[] terms = new PseudoBooleanTerm[] {
        new PseudoBooleanTerm(coefficient,
            new PsuedoBooleanIndeterminant[] { new PsuedoBooleanIndeterminant(0, false) }),
        new PseudoBooleanTerm(coefficient,
            new PsuedoBooleanIndeterminant[] { new PsuedoBooleanIndeterminant(1, false) }),
        new PseudoBooleanTerm(coefficient,
            new PsuedoBooleanIndeterminant[] { new PsuedoBooleanIndeterminant(2, false) }),
        new PseudoBooleanTerm(coefficient,
            new PsuedoBooleanIndeterminant[] { new PsuedoBooleanIndeterminant(3, false) }),
        new PseudoBooleanTerm(coefficient,
            new PsuedoBooleanIndeterminant[] { new PsuedoBooleanIndeterminant(4, false) }) };
    simpleFitnessFunction = new PsuedoBooleanFunction(terms);
  }
}