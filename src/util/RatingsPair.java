

package util;

public class RatingsPair
{
	private Double actual; // the actual rating
	private Double predicted; // the predicted rating 
	
	/**
	 * constructor - creates a new RatingsPair object
	 * @param actual - the actual rating
	 * @param predicted - the predicted rating
	 */
	public RatingsPair(final Double actual, final Double predicted)
	{
		this.actual = actual;
		this.predicted = predicted;
	}
	
	/**
	 * @returns the actual rating
	 */
	public Double getActualRating()
	{
		return actual;
	}

	/**
	 * @returns the predicted rating
	 */
	public Double getPredictedRating()
	{
		return predicted;
	}
}