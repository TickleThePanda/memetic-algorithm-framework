package uk.co.ticklethepanda.genetic_algorithms.genetics;

public class SimulationVariables {
	private int eliteSaved;
	private int generations;
	private int nParents;
	private float mutationRate;
	private float replaceRate;
	private int rerunCondition;

	public SimulationVariables(int eliteSaved, float mutationRate,
			float replaceRate, int generations, int nParents, int rerunCondition) {
		this.eliteSaved = eliteSaved;
		this.mutationRate = mutationRate;
		this.replaceRate = replaceRate;
		this.generations = generations;
		this.nParents = nParents;
		this.rerunCondition = rerunCondition;
	}

	public SimulationVariables(int eliteSaved, float mutationRate,
			float replaceRate, int generations, int nParents) {
		this.eliteSaved = eliteSaved;
		this.mutationRate = mutationRate;
		this.replaceRate = replaceRate;
		this.generations = generations;
		this.nParents = nParents;
		this.rerunCondition = -1;
	}

	public int getEliteSaved() {
		return eliteSaved;
	}

	public void setEliteSaved(int eliteSaved) {
		this.eliteSaved = eliteSaved;
	}

	public float getMutationRate() {
		return mutationRate;
	}

	public void setMutationRate(float mutationRate) {
		this.mutationRate = mutationRate;
	}

	public int getGenerations() {
		return generations;
	}

	public void setGenerations(int generations) {
		this.generations = generations;
	}

	public float getReplaceRate() {
		return replaceRate;
	}

	public void setReplaceRate(float replaceRate) {
		this.replaceRate = replaceRate;
	}

	public int getNumberOfParents() {
		return nParents;
	}

	public void setNumberOfParents(int parents) {
		this.nParents = parents;
	}

	/**
	 * @return the rerun condition, which defines the number of generations to
	 *         run where the maximum fitness is fixed before restarting the
	 *         simulation. A value of -1 means that the simulation is not rerun.
	 */
	public int getRerunCondition() {
		return rerunCondition;
	}

	/**
	 * @param rerunCondition
	 *            the rerunCondition to set
	 */
	public void setRerunCondition(int rerunCondition) {
		this.rerunCondition = rerunCondition;
	}
}