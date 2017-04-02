package uk.co.ticklethepanda.genetic_algorithms.sudoku.fitness;

import uk.co.ticklethepanda.genetic_algorithms.genetics.FitnessFunction;
import uk.co.ticklethepanda.genetic_algorithms.sudoku.SudokuSolution;
import uk.co.ticklethepanda.genetic_algorithms.sudoku.structure.SudokuGrid;

public class BasicSudokuFitnessFunction implements
		FitnessFunction<SudokuSolution> {
	
	public final static int MAXIMUM_FITNESS = (SudokuGrid.GRID_HEIGHT
			* SudokuGrid.GRID_WIDTH * 3 - 170);

	@Override
	public int evaluateFitness(SudokuSolution solution) {
		int duplicates = evaluateRows(solution) + evaluateColumns(solution)
				+ evaluateBoxes(solution);
		return MAXIMUM_FITNESS - duplicates;
	}

	public int evaluateRows(SudokuSolution solution) {
		int duplicates = 0;
		for (int y = 0; y < SudokuGrid.GRID_HEIGHT; y++) {
			int[] numbers = new int[9];
			for (int x = 0; x < SudokuGrid.GRID_WIDTH; x++) {
				numbers[solution.getGrid().getGrid()[x][y].getCellValue() - 1]++;
			}
			for (int i = 0; i < SudokuGrid.GRID_WIDTH; i++) {
				if (numbers[i] > 1)
					duplicates++;
			}
		}
		return duplicates;
	}

	public int evaluateColumns(SudokuSolution solution) {
		int duplicates = 0;
		for (int x = 0; x < SudokuGrid.GRID_WIDTH; x++) {
			int[] numbers = new int[9];
			for (int y = 0; y < SudokuGrid.GRID_HEIGHT; y++) {
				numbers[solution.getGrid().getGrid()[x][y].getCellValue() - 1]++;
			}
			for (int i = 0; i < SudokuGrid.GRID_HEIGHT; i++) {
				if (numbers[i] > 1)
					duplicates++;
			}
		}
		return duplicates;
	}

	public int evaluateBoxes(SudokuSolution solution) {
		int duplicates = 0;
		for (int xBox = 0; xBox < SudokuGrid.BOXES_HORIZ; xBox++) {
			for (int yBox = 0; yBox < SudokuGrid.BOXES_VERTI; yBox++) {
				int cells = SudokuGrid.BOXES_WIDTH * SudokuGrid.BOXES_HEIGHT;
				int[] numbers = new int[cells];
				for (int x = xBox * SudokuGrid.BOXES_WIDTH; x < xBox
						* SudokuGrid.BOXES_WIDTH + SudokuGrid.BOXES_WIDTH; x++) {
					for (int y = yBox * SudokuGrid.BOXES_HEIGHT; y < yBox
							* SudokuGrid.BOXES_HEIGHT + SudokuGrid.BOXES_HEIGHT; y++) {
						numbers[solution.getGrid().getGrid()[x][y]
								.getCellValue() - 1]++;
					}
				}
				for (int i = 0; i < cells; i++) {
					if (numbers[i] > 1)
						duplicates++;
				}
			}
		}
		return duplicates;
	}

	@Override
	public int maximumFitness() {
		return MAXIMUM_FITNESS;
	}

}
