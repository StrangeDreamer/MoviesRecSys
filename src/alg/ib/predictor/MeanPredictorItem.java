package alg.ib.predictor;

import java.util.ArrayList;
import java.util.Map;

import alg.ib.neighbourhood.NeighbourhoodItem;
import similarity.SimilarityMap;
import profile.Profile;
/**
 * Æ½¾ùÖµÔ¤²â
 * @author wangjin
 *
 */
public class MeanPredictorItem implements PredictorItem 
{
	/**
	 * constructor - creates a new SimpleAveragePredictor object
	 */
	public MeanPredictorItem()
	{
	}
	
	/**
	 * @returns the target user's predicted rating for the target item or null if a prediction cannot be computed
	 * @param userId - the numeric ID of the target user
	 * @param itemId - the numerid ID of the target item
	 * @param userProfileMap - a map containing user profiles
	 * @param itemProfileMap - a map containing item profiles
	 * @param neighbourhood - a Neighbourhood object
	 * @param simMap - a map containing user-user similarities
	 */
	public Double getPrediction(final Integer itemId, final Integer userId, final Map<Integer,Profile> itemProfileMap, final Map<Integer,Profile> userProfileMap, final NeighbourhoodItem neighbourhood, final SimilarityMap simMap)
	{
		double above = 0;
		
		ArrayList<Integer> neighbours = neighbourhood.getNeighbours(itemId, userId, userProfileMap, simMap); // get the neighbours
		
		for(int i = 0; i < neighbours.size(); i++) // iterate over each neighbour
		{
			Double rating = itemProfileMap.get(neighbours.get(i)).getValue(userId); // get the neighbour's rating for the target item
			if(rating == null)
			{
				System.out.println("Error - rating is null!"); // this error should never occur since all neighbours by definition have rated the target item!
				System.exit(1);
			}
			
			above += rating.doubleValue();
		}
				
		if(neighbours.size() > 0)
			return new Double(above / neighbours.size());
		else
			return null;
	}
}
