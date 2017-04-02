package uk.co.ticklethepanda.genetic_algorithms.binary_functions;

import uk.co.ticklethepanda.genetic_algorithms.genetics.Solution;

public interface BinarySolution extends Solution {
	
	public static int boolToInt(boolean bool) {
		if (bool)
			return 1;
		else
			return 0;
	}

	public abstract int getEquationSize();
}
