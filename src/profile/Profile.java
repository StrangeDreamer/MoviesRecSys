package profile;

import java.util.HashMap;
import java.util.Map;
import java.util.HashSet;
import java.util.Set;
import java.util.Iterator;
/**存储以下4种：
 * 评分矩阵某一行
 * 某一列
 * 基于用户的CF相似度矩阵
 * 基于物品的相似度矩阵
 * @author wangjin
 *
 */
public class Profile
{
	private Integer id; 
	private Map<Integer,Double> dataMap; 
	
	/**
	 * constructor - creates a new Profile object
	 * @param id
	 */
	public Profile(final Integer id)
	{
		this.id = id;
		this.dataMap = new HashMap<Integer,Double>();
	}

	/**
	 * @returns the profile ID
	 */
	public Integer getId() 
	{
		return id;
	}

	/**
	 * @returns the profile size
	 */
	public int getSize()
	{
		return dataMap.size();
	}
	
	/**
	 * @returns true if the ID is in the profile
	 */
	public boolean contains(final Integer id)
	{
		return dataMap.containsKey(id);
	}
	
	/**
	 * @returns the value for the ID (or null if ID is not in profile)
	 */
	public Double getValue(final Integer id)
	{
		return dataMap.get(id);
	}

	/**
	 * @returns the mean value over all values in the profile
	 */
	public double getMeanValue()
	{
		double total = 0;

		for(Double r: dataMap.values())
			total += r.doubleValue();

		return getSize() > 0 ? total / getSize() : 0;
	}

	/**
	 * @returns the sd value over all values in the profile
	 */
	public double getStandardDeviation()
	{
		double total = 0;
		double mean = getMeanValue();

		for(Double r: dataMap.values())
			total += Math.pow(r.doubleValue() - mean, 2);

		return getSize() > 0 ? Math.sqrt(total / getSize()) : 0;
	}

	
	
	/**
	 * @returns the norm of all values in the profile
	 */
	public double getNorm()
	{
		double sumsq = 0;

		for(Double r: dataMap.values())
			sumsq += Math.pow(r.doubleValue(), 2);

		return Math.sqrt(sumsq);
	}
	
	/**
	 * @returns the set of IDs in the profile
	 */
	public Set<Integer> getIds()
	{
		return dataMap.keySet();
	}
	
	/**
	 * @returns a set of IDs that two profiles have in common
	 */
	public Set<Integer> getCommonIds(final Profile other)
	{
		Set<Integer> common = new HashSet<Integer>();
		
		for(Integer id: getIds())
			if(other.contains(id))
				common.add(id);
	
		return common;
	}

	/**
	 * @param the ID to be added to the profile
	 * @param the corresponding value
	 */
	public void addValue(final Integer id, final Double value)
	{
		dataMap.put(id, value);
	}
	
	/**
	 * @returns a string representation of this object
	 */
	public String toString()
	{
		StringBuffer buf = new StringBuffer();		
		
		for(Iterator<Map.Entry<Integer,Double>> it = dataMap.entrySet().iterator(); it.hasNext(); ) 
		{
			Map.Entry<Integer,Double> entry = (Map.Entry<Integer,Double>)it.next();
			Integer id = entry.getKey();
			Double value = entry.getValue();
			buf.append(new String(getId() + " " + id + " " + value + "\n"));
		}
		
		return buf.toString();
	}
}