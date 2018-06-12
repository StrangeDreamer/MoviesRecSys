package similarity;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import profile.Profile;
import similarity.metric.SimilarityMetric;
/**
 * 存储两个用户（或物品）之间的相似度
 * @author wangjin
 *
 */
public class SimilarityMap 
{
	private Map<Integer,Profile> simMap; // stores profile-profile similarities

	/**
	 * constructor - creates a new SimilarityMap object
	 */
	public SimilarityMap()
	{
		simMap = new HashMap<Integer,Profile>();
	}
	
	/**
	 * constructor - creates a new SimilarityMap object
	 * @param profileMap
	 * @param metric
	 */
	public SimilarityMap(final Map<Integer,Profile> profileMap, final SimilarityMetric metric)
	{
		System.out.println("传到SimilarityMap参数userprofiles大小为："+profileMap.size());
		simMap = new HashMap<Integer,Profile>();
		//效率更高
        for(Entry<Integer, Profile> id1:profileMap.entrySet()) 
        	for(Entry<Integer, Profile> id2:profileMap.entrySet())
        		if(id2.getKey() < id1.getKey())//可以提高程序效率，因为a用户与b用户，b用户与a用户之间的相似度绝对值是一样的，没必要再计算一遍
				{                      
					double sim = metric.getSimilarity(id1.getValue(), id2.getValue());
					if(Math.abs(sim) > 0)//不要"=",目的筛选掉了4个彼此相似度为0的user
					{
						setSimilarity(id1.getKey(), id2.getKey(), sim);
						setSimilarity(id2.getKey(), id1.getKey(), sim);//此处代码表明通过if(id2.getKey() < id1.getKey())过滤
					}
				}
        System.out.println("产生相似矩阵simMap,其大小为"+simMap.size());
	}

	/**
	 * @returns the numeric IDs of the profiles
	 */
	public Set<Integer> getIds()
	{
		return simMap.keySet();
	}
	
	/**
	 * @returns the similarity profile
	 * @param the numeric ID of the profile
	 */
	public Profile getSimilarities(Integer id)
	{
		return simMap.get(id);
	}
	
	/**
	 * @returns the similarity between two profiles
	 * @param the numeric ID of the first profile
	 * @param the numeric ID of the second profile
	 */
	public double getSimilarity(final Integer id1, final Integer id2)
	{
		if(simMap.containsKey(id1))
			return (simMap.get(id1).contains(id2) ? simMap.get(id1).getValue(id2).doubleValue() : 0);
		else 
			return 0;
	}

	/**
	 * adds the similarity between two profiles to the map
	 * @param the numeric ID of the first profile
	 * @param the numeric ID of the second profile
	 */
	public void setSimilarity(final Integer id1, final Integer id2, final double sim)
	{
		Profile profile = simMap.containsKey(id1) ? simMap.get(id1) : new Profile(id1);
		profile.addValue(id2, new Double(sim));
		//System.out.println("设置"+id1+"与"+id2+"的相似度"+sim+"simMap<id1,(id2,similarity)>");
		simMap.put(id1, profile);
	}
	
	/**
	 * a string representation of all similarity values
	 */
	public String toString()
	{
		StringBuffer buf = new StringBuffer();
		
		for(Integer id: simMap.keySet())
			buf.append(simMap.get(id).toString());
	
		return buf.toString();
	}
}