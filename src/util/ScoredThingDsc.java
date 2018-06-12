
//������������
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
	 * @return  ���� 1 ��ô��ǰ��ֵ������ ���Ƚ��� ����
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
