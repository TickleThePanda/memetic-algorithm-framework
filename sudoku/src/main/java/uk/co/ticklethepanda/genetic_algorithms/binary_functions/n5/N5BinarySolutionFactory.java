package uk.co.ticklethepanda.genetic_algorithms.binary_functions.n5;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import uk.co.ticklethepanda.genetic_algorithms.binary_functions.BinarySolutionFactory;
import uk.co.ticklethepanda.genetic_algorithms.genetics.SolutionFactory;

public class N5BinarySolutionFactory implements BinarySolutionFactory<N5BinarySolution> {
	private static final int binarySize = 5;
	Random random;
	private int poolSize;

	public N5BinarySolutionFactory(int poolSize) {
		this.poolSize = poolSize;
		random = new Random();
	}

	@Override
	public N5BinarySolution generateSolution() {
		N5BinarySolution bs = new N5BinarySolution();
		for (int i = 0; i < binarySize; i++) {
			bs.getBoolList()[i] = random.nextBoolean();
		}
		return bs;
	}

	@Override
	public N5BinarySolution combineSolutions(ArrayList<N5BinarySolution> solution) {
		int[] combinationPoint = new int[solution.size() - 1];
		for (int i = 0; i < combinationPoint.length; i++) {
			combinationPoint[i] = random.nextInt(binarySize);
		}
		Arrays.sort(combinationPoint);
		N5BinarySolution newSolution = new N5BinarySolution();

		int j = 0;
		for (int i = 0; i < binarySize; i++) {
			while (i > combinationPoint[j]) {
				if (j >= combinationPoint.length - 1) {
					break;
				} else {
					j++;
				}
				newSolution.getBoolList()[i] = solution.get(j).getBoolList()[i];
			}
		}
		return newSolution;
	}

	@Override
	public int getPoolSize() {
		return poolSize;
	}

}
