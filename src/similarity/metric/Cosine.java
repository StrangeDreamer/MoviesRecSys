package similarity.metric;

import java.util.Set;

import profile.Profile;

public class Cosine implements SimilarityMetric
{
	private double max; 
	public Cosine(double max){this.max = max;}
	public double getSimilarity(final Profile p1, final Profile p2)
	{
        double top = 0;   
        Set<Integer> common = p1.getCommonIds(p2);
		for(Integer id: common)
		{
			double r1 = p1.getValue(id).doubleValue();
			double r2 = p2.getValue(id).doubleValue();
			top += r1 * r2;
		}
		double n1 = p1.getNorm();
		double n2 = p2.getNorm();
		//若没有共同评价的电影，则相似度为0
		double ans = (n1 > 0 && n2 > 0) ? (top / (n1 * n2)) 
				* (Math.min(Math.abs(common.size()), max)/max ) : 0;
		return ans;
	}
}
