package uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.comparison;

import java.util.ArrayList;

import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.EvoAlgorithm;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.EvoAlgorithmSettings.Builder;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.concrete_algorithms.MemeticAlgorithm;

/**
 * Logs an {@link uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.Algorithm
 * Algorithm} with certain types of data expected.
 */
public final class ComparisonLogger {

	/**
	 * Defines a method to return required data as a string.
	 */
	private interface DataLocation {
		/**
		 * Gets the required data as a string.
		 * 
		 * @param completedAlgorithm
		 *            the completed algorithm to retrieve the data from
		 *
		 * @return the required data as a string
		 */
		String getData(CompletedAlgorithm completedAlgorithm);
	}

	/**
	 * Helps to create a logger with default values and specified changes to
	 * these.
	 */
	public static class Builder {

		// DEFAULT TRUE
		/**
		 * The log item that defines the problem id and the location of the
		 * data. Default value defines that this should be printed.
		 */
		private LogItem problemId = new LogItem(true, "problemID",
				(CompletedAlgorithm completedAlgorithm) -> String
						.valueOf(completedAlgorithm.getAlgorithm()
								.getFitnessFunction().getProblemName()));

		/**
		 * The log item that defines the algorithm type and the location of the
		 * data. Default value defines that this should be printed.
		 */
		private LogItem algorithmType = new LogItem(true, "algorithmType",
				(CompletedAlgorithm completedAlgorithm) -> String
						.valueOf(completedAlgorithm.getAlgorithm()
								.getAlgorithmType()));

		/**
		 * The log item that defines the problem size and the location of the
		 * data. Default value defines that this should be printed.
		 */
		private LogItem problemSize = new LogItem(true, "problemSize",
				(CompletedAlgorithm completedAlgorithm) -> String
						.valueOf(completedAlgorithm.getAlgorithm()
								.getFitnessFunction().size()));

		/**
		 * The log item that defines the best value and the location of the
		 * data. Default value defines that this should be printed.
		 */
		private LogItem bestValue = new LogItem(true, "bestValue",
				(CompletedAlgorithm completedAlgorithm) -> String
						.valueOf(completedAlgorithm.getAlgorithm()
								.getBestSolution().getObjectiveValue()));

		/**
		 * The log item that defines the running time and the location of the
		 * data. Default value defines that this should be printed.
		 */
		private LogItem runningTime = new LogItem(true, "runningTime",
				(CompletedAlgorithm completedAlgorithm) -> String
						.valueOf(completedAlgorithm.getAlgorithmMetrics()
								.getRunningTime()));

		// DEFAULT FALSE
		/**
		 * The log item that defines the problem type and the location of the
		 * data. Default value defines that this should not be printed.
		 */
		private LogItem problemType = new LogItem(false, "problemType",
				(CompletedAlgorithm completedAlgorithm) -> String
						.valueOf(completedAlgorithm.getAlgorithmMetrics()
								.getRunningTime()));

		/**
		 * The log item that defines the cached fitness and the location of the
		 * data. Default value defines that this should not be printed.
		 */
		private LogItem cachedFitness = new LogItem(false, "cachedFitness",
				(CompletedAlgorithm completedAlgorithm) -> String
						.valueOf(completedAlgorithm.getAlgorithm()
								.getFitnessFunction().hasCache()));

		/**
		 * The log item that defines the elite size and the location of the
		 * data. Default value defines that this should not be printed.
		 */
		private LogItem eliteSize = new LogItem(
				false,
				"eliteSize",
				(CompletedAlgorithm completedAlgorithm) -> {
					if (completedAlgorithm.getAlgorithm() instanceof EvoAlgorithm<?>) {
						EvoAlgorithm<?> algo = (EvoAlgorithm<?>) completedAlgorithm
								.getAlgorithm();
						return String.valueOf(algo.getSettings()
								.getElitismSize());
					}
					return "";
				});

		/**
		 * The log item that defines the elite size and the location of the
		 * data. Default value defines that this should not be printed.
		 */
		private LogItem averageValue = new LogItem(
				false,
				"averageValue",
				(CompletedAlgorithm completedAlgorithm) -> {
					if (completedAlgorithm.getAlgorithm() instanceof EvoAlgorithm<?>) {
						EvoAlgorithm<?> algo = (EvoAlgorithm<?>) completedAlgorithm
								.getAlgorithm();
						return String.valueOf(algo.getTotalValue()
								/ algo.size());
					}
					return "";
				});

		/**
		 * The log item that defines the crossover size and the location of the
		 * data. Default value defines that this should not be printed.
		 */
		private LogItem crossoverSize = new LogItem(
				false,
				"crossoverSize",
				(CompletedAlgorithm completedAlgorithm) -> {
					if (completedAlgorithm.getAlgorithm() instanceof EvoAlgorithm<?>) {
						EvoAlgorithm<?> algo = (EvoAlgorithm<?>) completedAlgorithm
								.getAlgorithm();
						return String.valueOf(algo.getSettings()
								.getCrossoverSize());
					}
					return "";
				});

		/**
		 * The log item that defines the population size and the location of the
		 * data. Default value defines that this should not be printed.
		 */
		private LogItem populationSize = new LogItem(
				false,
				"populationSize",
				(CompletedAlgorithm completedAlgorithm) -> {
					if (completedAlgorithm.getAlgorithm() instanceof EvoAlgorithm<?>) {
						EvoAlgorithm<?> algo = (EvoAlgorithm<?>) completedAlgorithm
								.getAlgorithm();
						return String.valueOf(algo.size());
					}
					return "";
				});

		/**
		 * The log item that defines the mutation rate and the location of the
		 * data. Default value defines that this should not be printed.
		 */
		private LogItem mutationRate = new LogItem(
				false,
				"mutationRate",
				(CompletedAlgorithm completedAlgorithm) -> {
					if (completedAlgorithm.getAlgorithm() instanceof EvoAlgorithm<?>) {
						EvoAlgorithm<?> algo = (EvoAlgorithm<?>) completedAlgorithm
								.getAlgorithm();
						return String.valueOf(algo.getSettings()
								.getMutationRate());
					}
					return "";
				});

		/**
		 * The log item that defines the generation count and the location of
		 * the data. Default value defines that this should not be printed.
		 */
		private LogItem generationCount = new LogItem(false, "generationCount",
				(CompletedAlgorithm completedAlgorithm) -> String
						.valueOf(completedAlgorithm.getAlgorithmMetrics()
								.getGenerationCount()));

		/**
		 * The log item that defines the best fitness and the location of the
		 * data. Default value defines that this should not be printed.
		 */
		private LogItem bestFitness = new LogItem(false, "bestFitness",
				(CompletedAlgorithm completedAlgorithm) -> String
						.valueOf(completedAlgorithm.getAlgorithm()
								.getBestSolution().getFitness()));

		/**
		 * The log item that defines the local improvement type and the location
		 * of the data. Default value defines that this should not be printed.
		 */
		private LogItem locImprovType = new LogItem(
				false,
				"locImprovType",
				(CompletedAlgorithm completedAlgorithm) -> {
					if (completedAlgorithm.getAlgorithm() instanceof MemeticAlgorithm<?>) {
						MemeticAlgorithm<?> algo = (MemeticAlgorithm<?>) completedAlgorithm
								.getAlgorithm();
						return String.valueOf(algo.getLocalImprovement()
								.getType());
					}
					return "";
				});

		/**
		 * The additional data expected to be added.
		 */
		ArrayList<LogItem> logList = new ArrayList<LogItem>();

		/**
		 * Adds some aditional data to the builder.
		 * 
		 * @param logItem
		 *            the log item that is to be added.
		 * @return this builder
		 */
		public Builder addNewLogItem(LogItem logItem) {
			logList.add(logItem);
			return this;
		}

		/**
		 * Builds a new comparison logger with the settings defined. If settings
		 * haven't been defined, default values will be used.
		 * 
		 * @return a new comparison logger with the settings defined
		 */
		public ComparisonLogger build() {
			ComparisonLogger log = new ComparisonLogger();
			log.logList.add(problemId);
			log.logList.add(algorithmType);
			log.logList.add(problemSize);
			log.logList.add(bestValue);
			log.logList.add(runningTime);
			log.logList.add(problemType);
			log.logList.add(cachedFitness);
			log.logList.add(eliteSize);
			log.logList.add(averageValue);
			log.logList.add(crossoverSize);
			log.logList.add(populationSize);
			log.logList.add(mutationRate);
			log.logList.add(generationCount);
			log.logList.add(bestFitness);
			log.logList.add(locImprovType);

			log.logList.addAll(logList);
			return log;
		}

		/**
		 * Sets whether the comparison logger will print the
		 * completedAlgorithm.getAlgorithm() type.
		 * 
		 * @param bool
		 *            wether the comparison logger will print the
		 *            completedAlgorithm.getAlgorithm() type
		 * @return this builder
		 */
		public Builder setPrintAlgorithmType(boolean bool) {
			this.algorithmType.setPrint(bool);
			return this;
		}

		/**
		 * Sets whether the comparison logger will print the best fitness.
		 * 
		 * @param bool
		 *            wether the comparison logger will print the best fitness
		 * @return this builder
		 */
		public Builder setPrintBestFitness(boolean bool) {
			this.bestFitness.setPrint(bool);
			return this;
		}

		/**
		 * Sets whether the comparison logger will print the best value.
		 * 
		 * @param bool
		 *            wether the comparison logger will print the best value
		 * @return this builder
		 */
		public Builder setPrintBestValue(boolean bool) {
			this.bestValue.setPrint(bool);
			return this;
		}

		/**
		 * Sets whether the comparison logger will print the cached fitness
		 * status.
		 * 
		 * @param bool
		 *            wether the comparison logger will print the cached fitness
		 *            status
		 * @return this builder
		 */
		public Builder setPrintCachedFitness(boolean bool) {
			this.cachedFitness.setPrint(bool);
			return this;
		}

		/**
		 * Sets whether the comparison logger will print the crossover size.
		 * 
		 * @param bool
		 *            wether the comparison logger will print the crossover size
		 * @return this builder
		 */
		public Builder setPrintCrossoverSize(boolean bool) {
			this.crossoverSize.setPrint(bool);
			return this;
		}

		/**
		 * Sets whether the comparison logger will print the eilite size.
		 * 
		 * @param bool
		 *            wether the comparison logger will print the elite size
		 * @return this builder
		 */
		public Builder setPrintEliteSize(boolean bool) {
			this.eliteSize.setPrint(bool);
			return this;
		}

		/**
		 * Sets whether the comparison logger will print the generation count.
		 * 
		 * @param bool
		 *            wether the comparison logger will print the generation
		 *            count
		 * @return this builder
		 */
		public Builder setPrintGenerationCount(boolean bool) {
			this.generationCount.setPrint(bool);
			return this;
		}

		/**
		 * Sets whether the comparison logger will print the local improvement
		 * type.
		 * 
		 * @param bool
		 *            wether the comparison logger will print the local
		 *            improvemnt type
		 * @return this builder
		 */
		public Builder setPrintLocalImprovementType(boolean bool) {
			this.locImprovType.setPrint(bool);
			return this;
		}

		/**
		 * Sets whether the comparison logger will print the mutation rate.
		 * 
		 * @param bool
		 *            wether the comparison logger will print the mutation rate
		 * @return this builder
		 */
		public Builder setPrintMutationRate(boolean bool) {
			this.mutationRate.setPrint(bool);
			return this;
		}

		/**
		 * Sets whether the comparison logger will print the population size.
		 * 
		 * @param bool
		 *            wether the comparison logger will print the best
		 *            population size
		 * @return this builder
		 */
		public Builder setPrintPopulationSize(boolean bool) {
			this.populationSize.setPrint(bool);
			return this;
		}

		/**
		 * Sets whether the comparison logger will print the best problem id.
		 * 
		 * @param bool
		 *            wether the comparison logger will print the best problem
		 *            id
		 * @return this builder
		 */
		public Builder setPrintProblemId(boolean bool) {
			this.problemId.setPrint(bool);
			return this;
		}

		/**
		 * Sets whether the comparison logger will print the problem size.
		 * 
		 * @param bool
		 *            wether the comparison logger will print the problem size
		 * @return this builder
		 */
		public Builder setPrintProblemSize(boolean bool) {
			this.problemSize.setPrint(bool);
			return this;
		}

		/**
		 * Sets whether the comparison logger will print the problem type.
		 * 
		 * @param bool
		 *            wether the comparison logger will print the problem type
		 * @return this builder
		 */
		public Builder setPrintProblemType(boolean bool) {
			this.problemType.setPrint(bool);
			return this;
		}

		/**
		 * Sets whether the comparison logger will print the running time.
		 * 
		 * @param bool
		 *            wether the comparison logger will print the running time
		 * @return this builder
		 */
		public Builder setPrintRunningTime(boolean bool) {
			this.runningTime.setPrint(bool);
			return this;
		}

		public Builder setPrintAverageValue(boolean b) {
			this.averageValue.setPrint(b);
			return this;
		}
	}

	/**
	 * Defines a log item that contains whether it should be printed, the header
	 * for the item and the location of the data.
	 */
	public static final class LogItem {

		/**
		 * Whether to print the item.
		 */
		private boolean print;
		/**
		 * The item's header as a string.
		 */
		private String title;
		/**
		 * The data's location.
		 */
		private DataLocation location;

		/**
		 * Creates a new LogItem with whether it should be printed, the title
		 * for the data, and the data's location.
		 * 
		 * @param doItem
		 *            whether the data should be printed
		 * @param title
		 *            the title for the data
		 * @param location
		 *            the location of the data
		 */
		public LogItem(boolean doItem, String title, DataLocation location) {
			this.print = doItem;
			this.title = title;
			this.location = location;
		}

		/**
		 * Returns whether this data should be printed.
		 * 
		 * @return whether this data should be printed
		 */
		public boolean shouldPrint() {
			return print;
		}

		/**
		 * Gets the title for the data.
		 * 
		 * @return the title for the data
		 */
		public String getTitle() {
			return title;
		}

		/**
		 * Gets the value from the location as a string from either the
		 * completed algortithm.
		 * 
		 * @param completedAlgorithm
		 *            the completed algorithm that contains the data
		 * @return
		 */
		public String getValue(CompletedAlgorithm completedAlgorithm) {
			return location.getData(completedAlgorithm);
		}

		/**
		 * Sets whether the item should be printed.
		 * 
		 * @param bool
		 *            whether the item should be printed
		 */
		public void setPrint(boolean bool) {
			print = bool;
		}
	}

	/**
	 * The items that can be logged.
	 */
	private final ArrayList<LogItem> logList = new ArrayList<LogItem>();
	/**
	 * The delimiter for the the output.
	 */
	private static final String DELIM = ", ";

	private ComparisonLogger() {
	}

	/**
	 * Removes the final ", " from the output and adds a new line.
	 * 
	 * @param stringBuilder
	 */
	private void cleanup(StringBuilder stringBuilder) {
		// remove last DELIM and add new line
		if (stringBuilder.substring(stringBuilder.length() - 2,
				stringBuilder.length()).matches(", ")) {
			stringBuilder.delete(stringBuilder.length() - 2,
					stringBuilder.length()).append('\n');
		} else {
			stringBuilder.append('\n');
		}
	}

	/**
	 * Gets the titles of the data to be logged as a string.
	 * 
	 * @return the titles of the data to be logged as a string.
	 */
	public String getTitles() {

		StringBuilder stringBuilder = new StringBuilder();
		for (LogItem li : logList) {
			if (li.shouldPrint()) {
				stringBuilder.append(li.getTitle());
				stringBuilder.append(DELIM);
			}
		}
		cleanup(stringBuilder);
		return stringBuilder.toString();
	}

	/**
	 * Gets the values of the completed algorithm to be logged as a string.
	 * 
	 * @param completedAlgorithm
	 *            the completed algorithm to be logged as a string
	 *
	 * @return the values of the completed algorithm to be logged as a string.
	 */
	public String getValues(CompletedAlgorithm completedAlgorithm) {
		StringBuilder stringBuilder = new StringBuilder();
		for (LogItem li : logList) {
			if (li.shouldPrint()) {
				stringBuilder.append(li.getValue(completedAlgorithm));
				stringBuilder.append(DELIM);
			}
		}
		cleanup(stringBuilder);
		return stringBuilder.toString();
	}

}
