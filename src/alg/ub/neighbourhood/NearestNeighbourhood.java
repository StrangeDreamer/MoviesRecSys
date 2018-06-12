package alg.ub.neighbourhood;

import similarity.SimilarityMap;
import util.ScoredThingDsc;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Map;

import profile.Profile;

public class NearestNeighbourhood implements Neighbourhood 
{
	private final int k; // the number of neighbours in the neighbourhood
	//private final double threshold;
	/**
	 * constructor - creates a new NearestNeighbourhood object
	 * @param k - the number of neighbours in the neighbourhood
	 */
	public NearestNeighbourhood(final int k)
	{
		this.k = k;
		//this.threshold=threshold;
	}
	
	/**
	 * @returns the neighbour IDs
	 * @param userId - the numeric ID of the target user
	 * @param itemId - the numerid ID of the target item
	 * @param itemProfileMap - a map containing item profiles
	 * @param simMap - a map containing user-user similarities
	 */
	public ArrayList<Integer> getNeighbours(final Integer userId,final Integer itemId,
			final Map<Integer,Profile> itemProfileMap, final SimilarityMap simMap)
	{//集合：存储按照相似度降序的用户ID，key:用户间的相似度，value:对应的用户ID
		SortedSet<ScoredThingDsc> ss = new TreeSet<ScoredThingDsc>(); 
		if(itemProfileMap.containsKey(itemId))
		{//循环项目itemId的用户ID集合
			for(Iterator<Integer> it = itemProfileMap.get(itemId).getIds().iterator();
					it.hasNext(); )
			{
				Integer id = it.next();
				double sim = simMap.getSimilarity(userId, id);
				if(sim > 0.005)//选取正相关的用户ID入集合
                    ss.add(new ScoredThingDsc(sim, id));
			}
		}		
		ArrayList<Integer> neighbours = new ArrayList<Integer>();
		int counter = 0;
		//得到K个最近邻居
		for(Iterator<ScoredThingDsc> it = ss.iterator(); it.hasNext() && counter < k;)
		{
			ScoredThingDsc st = it.next();
			neighbours.add((Integer)st.thing);
			counter++;
		}
		return neighbours;
	}
}
