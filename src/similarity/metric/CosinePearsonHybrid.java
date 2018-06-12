package similarity.metric;

import java.util.Set;

import profile.Profile;

public class CosinePearsonHybrid implements SimilarityMetric
{
	private double max; // max value in significance weighting 
	private double cW = 1.0; // weight the averages for Cosine
	private double pW = 1.0; // weight the averages for Pearson
	
	/**
	 * constructor - creates a new CosineMetric object
	 */
	public CosinePearsonHybrid(double max)
	{
		this.max = max; // in this case max is only used Pearson similarity
	}
	
	/**
	 * constructor - creates a new CosineMetric object
	 */
	public CosinePearsonHybrid(double max, double cosineW, double pearsonW)
	{
		this.max = max; // in this case max is only used Pearson similarity
		this.cW = cosineW;
		this.pW = pearsonW;
	}
	
	/**
	 * computes the similarity between profiles
	 * @param profile 1 
	 * @param profile 2
	 */
	
	/**
	 * computes the similarity between profiles
	 * @param profile 1 
	 * @param profile 2
	 */
	public double getSimilarity(final Profile p1, final Profile p2)
	{
		double c = getCosineSimilarity(p1,p2);
		double p = getPearsonSimilarity(p1,p2); 
		
		return ((c*cW)+(p*pW))/(cW+pW);
		
	}
	
	public double getCosineSimilarity(final Profile p1, final Profile p2)
	{
        double dotProduct = 0;
        
        Set<Integer> common = p1.getCommonIds(p2);

		for(Integer id: common)
		{
			double r1 = p1.getValue(id).doubleValue();
			double r2 = p2.getValue(id).doubleValue();
			dotProduct += r1 * r2;
		}

		double n1 = p1.getNorm();
		double n2 = p2.getNorm();
		
		return (n1 > 0 && n2 > 0) ? (dotProduct / (n1 * n2))  : 0;
	}
	
	/**
	 * computes the similarity between profiles
	 * @param profile 1 
	 * @param profile 2
	 */
	public double getPearsonSimilarity(final Profile p1, final Profile p2)
	{

		double top = 0;
		double base1 = 0;
		double base2 = 0;
		
		Set<Integer> common = p1.getCommonIds(p2);
		for(Integer id: common)
		{
			double r1 = p1.getValue(id).doubleValue() - p1.getMeanValue();
			double r2 = p2.getValue(id).doubleValue() - p2.getMeanValue();
			top += r1 * r2;
			base1 += Math.pow(r1, 2);
			base2 += Math.pow(r2, 2);
		}
		
		//(Math.min(Math.abs((double)common.size()), max)/max < significance weighting 
		return (base1 > 0 && base2 > 0) ? (top / (Math.sqrt(base1) * Math.sqrt(base2)))  * (Math.min(Math.abs(common.size()), max)/max ) : 0;
	}
	

	
	
}
