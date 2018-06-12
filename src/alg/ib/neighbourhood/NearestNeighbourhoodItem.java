package alg.ib.neighbourhood;

import similarity.SimilarityMap;
import util.ScoredThingDsc;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Map;

import profile.Profile;

public class NearestNeighbourhoodItem implements NeighbourhoodItem 
{
	private final int k; // the number of neighbours in the neighbourhood
	//private final double threshold;
	/**
	 * constructor - creates a new NearestNeighbourhood object
	 * @param k - the number of neighbours in the neighbourhood
	 */
	public NearestNeighbourhoodItem(final int k)
	{
		this.k = k;
		//this.threshold=threshold;
	}
	
	/**
	 * @returns the neighbour IDs
	 * @param itemId - the numerid ID of the target item
	 * @param userId - the numeric ID of the target user
	 * @param userProfileMap - a map containing user profiles
	 * @param simMap - a map containing user-user similarities
	 */
	public ArrayList<Integer> getNeighbours(final Integer itemId, final Integer userId, 
			final Map<Integer,Profile> userProfileMap, final SimilarityMap simMap)
	{
		//集合：存储按照相似度降序的用户ID
		SortedSet<ScoredThingDsc> ss = new TreeSet<ScoredThingDsc>(); 
		if(userProfileMap.containsKey(userId))
		{// iterate over each user in the item profile
			for(Iterator<Integer> it = userProfileMap.get(userId).getIds().iterator(); it.hasNext(); ) 
			{
				Integer id = it.next();
				double sim = simMap.getSimilarity(itemId, id);
				if(sim > 0)
                    ss.add(new ScoredThingDsc(sim, id));		
			}
		}	
		ArrayList<Integer> neighbours = new ArrayList<Integer>();
		int counter = 0;
		 // get the k most similar neighbours
		for(Iterator<ScoredThingDsc> it = ss.iterator(); it.hasNext() && counter < k; )
		{
			ScoredThingDsc st = it.next();
			neighbours.add((Integer)st.thing);
			counter++;
		}
		
		return neighbours;
	}
}
