package uk.co.ticklethepanda.genetic_algorithms.sudoku.fitness;

import uk.co.ticklethepanda.genetic_algorithms.genetics.FitnessFunction;
import uk.co.ticklethepanda.genetic_algorithms.sudoku.SudokuSolution;
import uk.co.ticklethepanda.genetic_algorithms.sudoku.structure.SudokuGrid;

public class SquaredSudokuFitnessFunction implements
		FitnessFunction<SudokuSolution> {

	public static final int LOWEST_CELL_FITNESS_ROW = SudokuGrid.GRID_WIDTH - 1;
	public static final int LOWEST_CELL_FITNESS_COL = SudokuGrid.GRID_HEIGHT - 1;
	public static final int LOWEST_CELL_FITNESS_BOX = ((SudokuGrid.GRID_WIDTH / SudokuGrid.BOXES_HORIZ) * (SudokuGrid.GRID_HEIGHT / SudokuGrid.BOXES_VERTI)) - 1;
	public static final int LOWEST_CELL_FITNESS = LOWEST_CELL_FITNESS_ROW
			+ LOWEST_CELL_FITNESS_COL + LOWEST_CELL_FITNESS_BOX;
	public static final int LOWEST_GRID_FITNESS = LOWEST_CELL_FITNESS
			* SudokuGrid.GRID_COUNT - 1710;
	public static final int LOWEST_GIRD_FITNESS_SQUARED = LOWEST_GRID_FITNESS
			* LOWEST_GRID_FITNESS;

	@Override
	public int evaluateFitness(SudokuSolution sudokuGrid) {
		int duplicates = 0;
		for (int x = 0; x < SudokuGrid.GRID_WIDTH; x++) {
			for (int y = 0; y < SudokuGrid.GRID_HEIGHT; y++) {
				duplicates += evaluateRowFitness(sudokuGrid, x, y)
						+ evaluateColFitness(sudokuGrid, x, y)
						+ evaluateBoxFitness(sudokuGrid, x, y);
			}
		}
		
		int duplicatesSquared = duplicates * duplicates;
		
		return LOWEST_GIRD_FITNESS_SQUARED - duplicatesSquared;
	}

	private int evaluateBoxFitness(SudokuSolution sudokuGrid, final int x,
			final int y) {
		int duplicates = 0;

		final int value = sudokuGrid.getGrid().getGrid()[x][y].getCellValue();
		final int xBox = x / SudokuGrid.BOXES_HORIZ;
		final int xStart = xBox * SudokuGrid.BOXES_HORIZ;
		final int xEnd = xStart + SudokuGrid.BOXES_WIDTH;

		final int yBox = y / SudokuGrid.BOXES_VERTI;
		final int yStart = yBox * SudokuGrid.BOXES_VERTI;
		final int yEnd = yStart + SudokuGrid.BOXES_HEIGHT;

		for (int varX = xStart; varX < xEnd; varX++) {
			for (int varY = yStart; varY < yEnd; varY++) {
				if (!(varX == x && varY == y)) {
					if (sudokuGrid.getGrid().getGrid()[varX][varY].getCellValue() == value) {
						duplicates++;
					}
				}
			}
		}
		return duplicates;
	}

	private int evaluateColFitness(SudokuSolution sudokuGrid, final int x,
			final int y) {
		int duplicates = 0;

		final int value = sudokuGrid.getGrid().getGrid()[x][y].getCellValue();
		for (int varY = 0; varY < SudokuGrid.GRID_WIDTH; varY++) {
			if (varY != y) {
				if (sudokuGrid.getGrid().getGrid()[x][varY].getCellValue() == value) {
					duplicates++;
				}
			}
		}
		return duplicates;
	}

	private int evaluateRowFitness(SudokuSolution sudokuGrid, final int x,
			final int y) {
		int duplicates = 0;
		final int value = sudokuGrid.getGrid().getGrid()[x][y].getCellValue();
		for (int varX = 0; varX < SudokuGrid.GRID_WIDTH; varX++) {
			if (varX != x) {
				if (sudokuGrid.getGrid().getGrid()[varX][y].getCellValue() == value) {
					duplicates++;
				}
			}
		}
		return duplicates;
	}

	@Override
	public int maximumFitness() {
		return LOWEST_GIRD_FITNESS_SQUARED;
	}

}
