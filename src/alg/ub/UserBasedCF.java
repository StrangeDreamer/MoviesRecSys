package alg.ub;

import java.util.Map;

import profile.Profile;
import alg.CFAlgorithm;
import alg.ub.neighbourhood.Neighbourhood;
import alg.ub.predictor.Predictor;
import similarity.SimilarityMap;
import similarity.metric.SimilarityMetric;
import util.reader.DatasetReader;

public class UserBasedCF implements CFAlgorithm
{
	private Predictor predictor; // the predictor technique  
	public Neighbourhood neighbourhood; // the neighbourhood technique
	private DatasetReader reader; // dataset reader
	private SimilarityMap simMap; // similarity map - stores all user-user similarities
	
	/**
	 * constructor - creates a new UserBasedCF object
	 * @param predictor - the predictor technique
	 * @param neighbourhood - the neighbourhood technique
	 * @param metric - the user-user similarity metric
	 * @param reader - dataset reader
	 */
	public UserBasedCF(final Predictor predictor, final Neighbourhood neighbourhood, final SimilarityMetric metric, final DatasetReader reader)
	{
		this.predictor = predictor;
		this.neighbourhood = neighbourhood;
		this.reader = reader;
		this.simMap = new SimilarityMap(reader.getUserProfiles(), metric); // compute all <<<<<user-user>>>> similarities
	}
	
	/**
	 * @returns the target user's predicted rating for the target item or null if a prediction cannot be computed
	 * @param userId - the target user ID
	 * @param itemId - the target item ID
	 */
	public Double getPrediction(final Integer userId, final Integer itemId)
	{	
		return predictor.getPrediction(userId, itemId, reader.getUserProfiles(), reader.getItemProfiles(), neighbourhood, simMap);
	}
	
	/**
	 * Returns all the user profiles loaded.
	 * @return a HashMap containing user profiles
	 */
	public Map<Integer,Profile> getUserProfiles()
	{
		return reader.getUserProfiles();
	}
}