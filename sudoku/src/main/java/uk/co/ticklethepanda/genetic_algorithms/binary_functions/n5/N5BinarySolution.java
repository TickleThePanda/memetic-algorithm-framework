package uk.co.ticklethepanda.genetic_algorithms.binary_functions.n5;

import java.util.Random;

import uk.co.ticklethepanda.genetic_algorithms.binary_functions.BinarySolution;
import uk.co.ticklethepanda.genetic_algorithms.genetics.Solution;

public class N5BinarySolution implements BinarySolution {
	
	private final int size = 5;

	private boolean[] boolList;

	public boolean[] getBoolList() {
		return boolList;
	}

	private Random random;

	public N5BinarySolution() {
		boolList = new boolean[size];
		random = new Random();
	}

	@Override
	public void mutateSolution(float mutationProbablility) {
		int selectValue = random.nextInt(boolList.length);
		if (random.nextFloat() < mutationProbablility) {
			boolList[selectValue] = !boolList[selectValue];
		}
	}

	@Override
	public void printSolution() {
		for (int i = 0; i < boolList.length; i++) {
			if (boolList[i]) {
				System.out.print("1");
			} else {
				System.out.print("0");
			}
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public N5BinarySolution duplicate() {
		N5BinarySolution bs = new N5BinarySolution();
		for(int i = 0; i < this.getBoolList().length; i++) {
			bs.getBoolList()[i] = this.getBoolList()[i];
		}
		return bs;
	}

	@Override
	public int getEquationSize() {
		return size;
	}

}
