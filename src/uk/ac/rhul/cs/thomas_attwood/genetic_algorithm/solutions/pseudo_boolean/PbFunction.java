package uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.solutions.pseudo_boolean;

import java.util.Arrays;
import java.util.Iterator;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.ProblemType;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.solutions.FitnessFunction;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

/**
 * Defines a generalised Pseudo-Boolean function of any polynomial degree.
 *
 */
public class PbFunction implements FitnessFunction<PbSolution>, Iterable<Term> {

	/**
	 * Iterator for the terms in the function.
	 *
	 */
	public final class TermIterator implements Iterator<Term> {

		/**
		 * The current index.
		 */
		private int index = 0;

		@Override
		public boolean hasNext() {
			return index < terms.length;
		}

		@Override
		public Term next() {
			return terms[index++];
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
	LoadingCache<PbSolution, Integer> fitnessCache = CacheBuilder.newBuilder()
			.maximumSize(DEFAULT_CACHE_SIZE)
			.expireAfterAccess(DEFAULT_TIME_LIMIT, TimeUnit.SECONDS)
			.concurrencyLevel(DEFAULT_CONCURRENCY_LEVEL)
			.build(new CacheLoader<PbSolution, Integer>() {

				@Override
				public Integer load(final PbSolution arg0) throws Exception {
					return PbFunction.this.calculateValue(arg0);
				}
			});
	/**
	 * The terms of the psuedo-boolean function.
	 */
	private final Term[] terms;
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
	public PbFunction(final boolean doHash, final Term[] term) {
		ProblemType problemType = ProblemType.PSEUDO_BOOLEAN_LINEAR;
		for (final Term ffo : term) {
			if (ffo.getSize() == 2) {
				problemType = ProblemType.PSEUDO_BOOLEAN_QUADRATIC;
			}
		}

		this.problemType = problemType;
		this.doCache = doHash;
		this.terms = term;
	}

	/**
	 * Creates a new PbFunction using the terms provided and enabling the hash.
	 * 
	 * @param term
	 *            the terms to use
	 */
	public PbFunction(final Term[] term) {
		this(true, term);
	}

	@Override
	public int actualValue(final PbSolution solution) {
		return evaluateFitness(solution);
	}

	/**
	 * Calculates the fitness value for the solution.
	 * 
	 * @param solution
	 *            the solution to evaluate.
	 * @return the calculated value for the fitness
	 */
	private int calculateValue(final PbSolution solution) {
		int sum = 0;
		for (final Term element : terms) {
			sum += element.calculateValue(solution);
		}

		return sum;
	}

	@Override
	public PbFunction copy() {
		return new PbFunction(this.terms);
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
    final PbFunction other = (PbFunction) obj;
    if (!Arrays.equals(terms, other.terms)) {
      return false;
    }
    return true;
  }
  
	@Override
	public int evaluateFitness(final PbSolution solution) {

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
	public Term getTerm(final int index) {
		return terms[index];
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
		result = prime * result + Arrays.hashCode(terms);
		return result;
	}

	@Override
	public Iterator<Term> iterator() {
		return new TermIterator();
	}

	@Override
	public int size() {
		return terms.length;
	}

	@Override
	public String toString() {
		final int maxLen = 3;
		return "PBFitFunc [terms="
				+ (terms != null ? Arrays.asList(terms).subList(0,
						Math.min(terms.length, maxLen)) : null) + "]";
	}

	@Override
	public String getProblemName() {
		return String.valueOf(this.hashCode());
	}

  @Override
  public FitnessFunction<PbSolution> copy(boolean cacheEnabled) {
    return new PbFunction(cacheEnabled, terms);
  }

}
