package uk.co.ticklethepanda.genetic_algorithms.sudoku;

import java.util.Arrays;
import java.util.Random;

import uk.co.ticklethepanda.genetic_algorithms.genetics.Solution;
import uk.co.ticklethepanda.genetic_algorithms.sudoku.structure.SudokuCell;
import uk.co.ticklethepanda.genetic_algorithms.sudoku.structure.SudokuGrid;
import uk.co.ticklethepanda.genetic_algorithms.sudoku.structure.SudokuPoint;

public class SudokuSolution implements Solution {

	private static final Random random = new Random();

	public static SudokuSolution combineSolutions(SudokuSolution parent1,
			SudokuSolution parent2) {
		int combinationPoint = random.nextInt(SudokuGrid.GRID_WIDTH
				* SudokuGrid.GRID_HEIGHT);

		SudokuSolution newGrid = new SudokuSolution();

		for (int x = 0; x < SudokuGrid.GRID_WIDTH; x++) {
			for (int y = 0; y < SudokuGrid.GRID_HEIGHT; y++) {
				if (SudokuGrid.GRID_WIDTH * x + y < combinationPoint) {
					newGrid.sudokuGrid.getGrid()[x][y] = parent1.sudokuGrid.getGrid()[x][y];
				} else {
					newGrid.sudokuGrid.getGrid()[x][y] = parent2.sudokuGrid.getGrid()[x][y];
				}
			}
		}
		newGrid.setFixedCount(parent1.getFixedCount());
		return newGrid;
	}

	@Override
	public void mutateSolution(float mutationProbablility) {
		int selectValue = random.nextInt(SudokuGrid.GRID_COUNT - fixedCount);
		if (random.nextFloat() < mutationProbablility) {
			int count = 0;
			for (int x = 0; x < SudokuGrid.GRID_WIDTH; x++) {
				for (int y = 0; y < SudokuGrid.GRID_HEIGHT; y++) {
					if (!sudokuGrid.getGrid()[x][y].isFixed()) {
						count++;
					}
					if (count == selectValue) {
						int value = random.nextInt(9) + 1;
						sudokuGrid.getGrid()[x][y] = new SudokuCell(value, false);
						return;
					}
				}
			}
		}
	}

	private SudokuGrid sudokuGrid = new SudokuGrid();

	private int fixedCount;

	SudokuSolution() {
		sudokuGrid = new SudokuGrid();
	}

	public void addFixedPoint(SudokuPoint sp) {
		sudokuGrid.getGrid()[sp.getPoint().x][sp.getPoint().y] = new SudokuCell(
				sp.getValue(), true);
	}

	public void printSolution() {
		for (int x = 0; x < SudokuGrid.GRID_WIDTH; x++) {
			for (int y = 0; y < SudokuGrid.GRID_HEIGHT; y++) {
				System.out.print(sudokuGrid.getGrid()[x][y].getCellValue());
			}
			System.out.println();
		}
	}

	public int getFixedCount() {
		return fixedCount;
	}

	public SudokuGrid getGrid() {
		return sudokuGrid;
	}

	public void setFixedCount(int fixedCount) {
		this.fixedCount = fixedCount;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(sudokuGrid.getGrid());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SudokuSolution other = (SudokuSolution) obj;
		if (!Arrays.deepEquals(sudokuGrid.getGrid(), other.sudokuGrid.getGrid()))
			return false;
		return true;
	}

	@SuppressWarnings("unchecked")
	public SudokuSolution duplicate() {
		SudokuSolution ss = new SudokuSolution();
		for (int x = 0; x < SudokuGrid.GRID_WIDTH; x++) {
			for (int y = 0; y < SudokuGrid.GRID_HEIGHT; y++) {
				ss.getGrid().getGrid()[x][y] = sudokuGrid.getGrid()[x][y].duplicate();
			}
		}
		return ss;
	}

}
