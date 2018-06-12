package alg.ub.neighbourhood;

import java.util.ArrayList;
import java.util.Map;

import profile.Profile;
import similarity.SimilarityMap;

public interface Neighbourhood 
{
	/**
	 * @returns the neighbour IDs
	 * @param userId - the numeric ID of the target user
	 * @param itemId - the numeric ID of the target item
	 * @param itemProfileMap - a map containing item profiles
	 * @param simMap - a map containing user-user similarities
	 */
	public ArrayList<Integer> getNeighbours(final Integer userId, final Integer itemId, final Map<Integer,Profile> itemProfileMap, final SimilarityMap simMap);
}
