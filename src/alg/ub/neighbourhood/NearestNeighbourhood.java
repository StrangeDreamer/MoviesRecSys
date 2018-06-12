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
	{//���ϣ��洢�������ƶȽ�����û�ID��key:�û�������ƶȣ�value:��Ӧ���û�ID
		SortedSet<ScoredThingDsc> ss = new TreeSet<ScoredThingDsc>(); 
		if(itemProfileMap.containsKey(itemId))
		{//ѭ����ĿitemId���û�ID����
			for(Iterator<Integer> it = itemProfileMap.get(itemId).getIds().iterator();
					it.hasNext(); )
			{
				Integer id = it.next();
				double sim = simMap.getSimilarity(userId, id);
				if(sim > 0.005)//ѡȡ����ص��û�ID�뼯��
                    ss.add(new ScoredThingDsc(sim, id));
			}
		}		
		ArrayList<Integer> neighbours = new ArrayList<Integer>();
		int counter = 0;
		//�õ�K������ھ�
		for(Iterator<ScoredThingDsc> it = ss.iterator(); it.hasNext() && counter < k;)
		{
			ScoredThingDsc st = it.next();
			neighbours.add((Integer)st.thing);
			counter++;
		}
		return neighbours;
	}
}
