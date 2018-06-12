package alg.ib.predictor;

import java.util.Map;

import alg.ib.neighbourhood.NeighbourhoodItem;
import similarity.SimilarityMap;
import profile.Profile;

public interface PredictorItem 
{
	/**
	 * @returns the target user's predicted rating for the target item or null if a prediction cannot be computed
	 * @param itemId - the numeric ID of the target item
	 * @param userId - the numeric ID of the target user
	 * @param itemProfileMap - a map containing item profiles
	 * @param userProfileMap - a map containing user profiles
	 * @param neighbourhood - a Neighbourhood object
	 * @param simMap - a map containing user-user similarities
	 */
	public Double getPrediction(final Integer itemId, final Integer userId, final Map<Integer,Profile> itemProfileMap, final Map<Integer,Profile> userProfileMap, final NeighbourhoodItem neighbourhood, final SimilarityMap simMap);
}
