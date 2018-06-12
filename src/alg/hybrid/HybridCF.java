package alg.hybrid;

import alg.CFAlgorithm;
import alg.ub.neighbourhood.Neighbourhood;
import alg.ub.predictor.Predictor;
import alg.ib.neighbourhood.NeighbourhoodItem;
import alg.ib.predictor.PredictorItem;
import similarity.SimilarityMap;
import similarity.metric.SimilarityMetric;
import util.reader.DatasetReader;

public class HybridCF implements CFAlgorithm
{
	private Predictor uP; // the predictor technique for users 
	private PredictorItem iP; // the predictor technique for items
	private Neighbourhood userNeighbourhood; // the neighbourhood technique for users
	private NeighbourhoodItem itemNeighbourhood; // the neighbourhood technique for items
	private DatasetReader reader; // dataset reader
	private SimilarityMap userSimMap; // similarity map - stores all user-user similarities
	private SimilarityMap itemSimMap; // similarity map - stores all item-item similarities
	private double userWeight; // weight for user in averages
	private double itemWeight; // weight for item in averages
	private double a;
	private double b;
	
	/**
	 * constructor - creates a new UserBasedCF object
	 * @param uP - the predictor technique for user
	 * @param iP - the predictor technique for item
	 * @param userNeighbourhood - the neighbourhood technique for user
	 * @param itemNeighbourhood - the neighbourhood technique for items
	 * @param userMetric - the user-user similarity metric
	 * @param userMetric - the item-item similarity metric
	 * @param userWeight - weight for user average 
	 * @param itemWeight - weight for item average
	 * @param reader - dataset reader
	 */
	public HybridCF(final Predictor uP, final PredictorItem iP, 
			final Neighbourhood userNeighbourhood, final NeighbourhoodItem itemNeighbourhood, 
			final SimilarityMetric userMetric, final SimilarityMetric itemMetric, 
			final DatasetReader reader, final double userWeight, final double itemWeight,
			double a,double b)
	{
		this.uP = uP;
		this.iP = iP;
		this.userNeighbourhood = userNeighbourhood;
		this.itemNeighbourhood = itemNeighbourhood;
		this.reader = reader;
		this.userSimMap = new SimilarityMap(reader.getUserProfiles(), userMetric); // compute all user-user similarities
		this.itemSimMap = new SimilarityMap(reader.getItemProfiles(), itemMetric); // compute all item-item similarities
		this.userWeight = userWeight;
		this.itemWeight = itemWeight;
		this.a=a;
		this.b=b;
	}
	
	/**
	 * @returns the target user's predicted rating for the target item or null if a prediction cannot be computed
	 * @param userId - the target user ID
	 * @param itemId - the target item ID
	 */
	public Double getPrediction(final Integer userId, final Integer itemId){	
		Double uPrediction = uP.getPrediction(userId, itemId, reader.getUserProfiles(), 
				reader.getItemProfiles(), userNeighbourhood, userSimMap);
		Double iPrediction = iP.getPrediction(itemId, userId, reader.getItemProfiles(),
				reader.getUserProfiles(), itemNeighbourhood, itemSimMap);
		if ((uPrediction == null) && (iPrediction == null))		return null;			
		else if (uPrediction == null)	return iPrediction;
		else if (iPrediction == null)	return uPrediction;
		else	return weightedAverage(uPrediction, iPrediction,a,b);
	}	
	private Double weightedAverage(double uP, double iP,double a,double b){
		double p=((userWeight*uP)+(itemWeight*iP)+a)/(userWeight+itemWeight+b);
		if (p > 5) p = 5.0;
		return p ;
	}
	

}