package uk.co.ticklethepanda.memetic.problem.solutions.pbf;

import java.util.Arrays;
import java.util.Iterator;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import uk.co.ticklethepanda.memetic.gui.ProblemType;
import uk.co.ticklethepanda.memetic.problem.Problem;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

/**
 * Defines a generalised Pseudo-Boolean function of any polynomial degree.
 *
 */
public class PsuedoBooleanFunction implements Problem<BitString>, Iterable<PseudoBooleanTerm> {

	/**
	 * Iterator for the terms in the function.
	 *
	 */
	public final class TermIterator implements Iterator<PseudoBooleanTerm> {

		/**
		 * The current index.
		 */
		private int index = 0;

		@Override
		public boolean hasNext() {
			return index < pseudoBooleanTerms.length;
		}

		@Override
		public PseudoBooleanTerm next() {
			return pseudoBooleanTerms[index++];
		}

	}

	/**
	 * The default cache size for fitness cache.
	 */
	private static final int DEFAULT_CACHE_SIZE = 10000;

	/**
	 * No concurrency required.
	 */
	private static final int DEFAULT_CONCURRENCY_LEVEL = 1;

	/**
	 * The number of seconds that the cache has at max.
	 */
	private static final int DEFAULT_TIME_LIMIT = 1;

	/**
	 * Whether to use the cache.
	 */
	private final boolean doCache;

	/**
	 * The cache to use to store fitness functions.
	 */
	LoadingCache<BitString, Integer> fitnessCache = CacheBuilder.newBuilder()
			.maximumSize(DEFAULT_CACHE_SIZE)
			.expireAfterAccess(DEFAULT_TIME_LIMIT, TimeUnit.SECONDS)
			.concurrencyLevel(DEFAULT_CONCURRENCY_LEVEL)
			.build(new CacheLoader<BitString, Integer>() {

				@Override
				public Integer load(final BitString arg0) throws Exception {
					return PsuedoBooleanFunction.this.calculateValue(arg0);
				}
			});
	/**
	 * The terms of the psuedo-boolean function.
	 */
	private final PseudoBooleanTerm[] pseudoBooleanTerms;
	/**
	 * The problem type of the function.
	 */
	private ProblemType problemType;

	/**
	 * Creates a new PbFunction using the terms provided and enabling the hash
	 * depending on <code>doHash</code>.
	 * 
	 * @param doHash
	 *            whether to hash the pseudo-boolean fitness
	 * @param term
	 *            the terms to use
	 */
	public PsuedoBooleanFunction(final boolean doHash, final PseudoBooleanTerm[] term) {
		ProblemType problemType = ProblemType.PSEUDO_BOOLEAN_LINEAR;
		for (final PseudoBooleanTerm ffo : term) {
			if (ffo.getSize() == 2) {
				problemType = ProblemType.PSEUDO_BOOLEAN_QUADRATIC;
			}
		}

		this.problemType = problemType;
		this.doCache = doHash;
		this.pseudoBooleanTerms = term;
	}

	/**
	 * Creates a new PbFunction using the terms provided and enabling the hash.
	 * 
	 * @param term
	 *            the terms to use
	 */
	public PsuedoBooleanFunction(final PseudoBooleanTerm[] term) {
		this(true, term);
	}

	@Override
	public int actualValue(final BitString solution) {
		return evaluateFitness(solution);
	}

	/**
	 * Calculates the fitness value for the solution.
	 * 
	 * @param solution
	 *            the solution to evaluate.
	 * @return the calculated value for the fitness
	 */
	private int calculateValue(final BitString solution) {
		int sum = 0;
		for (final PseudoBooleanTerm element : pseudoBooleanTerms) {
			sum += element.calculateValue(solution);
		}

		return sum;
	}

	@Override
	public PsuedoBooleanFunction copy() {
		return new PsuedoBooleanFunction(this.pseudoBooleanTerms);
	}
	
  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (this.getClass() != obj.getClass()) {
      return false;
    }
    final PsuedoBooleanFunction other = (PsuedoBooleanFunction) obj;
    if (!Arrays.equals(pseudoBooleanTerms, other.pseudoBooleanTerms)) {
      return false;
    }
    return true;
  }
  
	@Override
	public int evaluateFitness(final BitString solution) {

		if (doCache) {
			Integer cachedFitness = null;
			try {
				cachedFitness = fitnessCache.get(solution);
			} catch (final ExecutionException e) {
				e.printStackTrace();
			}

			return cachedFitness;
		} else {
			return calculateValue(solution);
		}

	}

	/**
	 * Gets the Term at index in the function.
	 * 
	 * @param index
	 *            the Term to get
	 * @return the Term
	 */
	public PseudoBooleanTerm getTerm(final int index) {
		return pseudoBooleanTerms[index];
	}

	@Override
	public ProblemType getProblemType() {
		return problemType;
	}

	@Override
	public boolean hasCache() {
		return doCache;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(pseudoBooleanTerms);
		return result;
	}

	@Override
	public Iterator<PseudoBooleanTerm> iterator() {
		return new TermIterator();
	}

	@Override
	public int size() {
		return pseudoBooleanTerms.length;
	}

	@Override
	public String toString() {
		final int maxLen = 3;
		return "PBFitFunc [terms="
				+ (pseudoBooleanTerms != null ? Arrays.asList(pseudoBooleanTerms).subList(0,
						Math.min(pseudoBooleanTerms.length, maxLen)) : null) + "]";
	}

	@Override
	public String getProblemName() {
		return String.valueOf(this.hashCode());
	}

  @Override
  public Problem<BitString> copy(boolean cacheEnabled) {
    return new PsuedoBooleanFunction(cacheEnabled, pseudoBooleanTerms);
  }

}
