
//按照评分排序
package util;

public class ScoredThingDsc implements Comparable<Object>
{
	public double score;
	public Object thing;
	public boolean abs;

	public ScoredThingDsc(double s, Object t)
	{
		score = s;
		thing = t;
		abs = false;
	}

	public ScoredThingDsc(double s, Object t, boolean a)
	{
		score = s;
		thing = t;
		abs = a;
	}
	/**
	 * @return  返回 1 那么当前的值会排在 被比较者 后面
	 */
	public int compareTo(Object o)
	{
		ScoredThingDsc st = (ScoredThingDsc) o;
		if(abs)
		{
			return (Math.abs(score) > Math.abs(st.score)) ? -1 : +1;
		}
			
		else
		{			
			return (score > st.score) ? -1 : +1;
		}
			
	}

	public String toString()
	{
		return "[" + score + "; " + thing + "]";
	}
}
