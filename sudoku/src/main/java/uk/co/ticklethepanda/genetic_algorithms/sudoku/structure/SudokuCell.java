package uk.co.ticklethepanda.genetic_algorithms.sudoku.structure;

public class SudokuCell {
	private final int cellValue;
	private final boolean fixed;
	
	public SudokuCell(int cellValue, boolean fixed) {
		this.cellValue = cellValue;
		this.fixed = fixed;
	}

	public int getCellValue() {
		return cellValue;
	}

	public boolean isFixed() {
		return fixed;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + cellValue;
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
		SudokuCell other = (SudokuCell) obj;
		if (cellValue != other.cellValue)
			return false;
		return true;
	}
	
	public SudokuCell duplicate() {
		SudokuCell sc = new SudokuCell(cellValue, fixed);
		return sc;
	}
	
}
