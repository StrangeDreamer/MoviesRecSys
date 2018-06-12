/**
 * A collaborative filtering algorithm interface
 * 
 * Michael O'Mahony
 * 20/01/2011
 */

package alg;

public interface CFAlgorithm 
{
	/**
	 * @returns the target user's predicted rating for the target item or null if a prediction cannot be computed
	 * @param userId - the target user ID
	 * @param itemId - the target item ID
	 */
	public Double getPrediction(final Integer userId, final Integer itemId);
}
