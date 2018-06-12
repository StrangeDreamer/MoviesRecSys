package alg.ib;

import java.util.Map;

import profile.Profile;
import alg.CFAlgorithm;
import alg.ib.neighbourhood.NeighbourhoodItem;
import alg.ib.predictor.PredictorItem;
import similarity.SimilarityMap;
import similarity.metric.SimilarityMetric;
import util.reader.DatasetReader;

public class ItemBasedCF implements CFAlgorithm
{
	private PredictorItem predictor; // the predictor technique  
	private NeighbourhoodItem neighbourhood; // the neighbourhood technique
	private DatasetReader reader; // dataset reader
	private SimilarityMap simMap; // similarity map - stores all item-item similarities
	
	/**
	 * constructor - creates a new UserBasedCF object
	 * @param predictor - the predictor technique
	 * @param neighbourhood - the neighbourhood technique
	 * @param metric - the user-user similarity metric
	 * @param reader - dataset reader
	 */
	public ItemBasedCF(final PredictorItem predictor, final NeighbourhoodItem neighbourhood, final SimilarityMetric metric, final DatasetReader reader)
	{
		this.predictor = predictor;
		this.neighbourhood = neighbourhood;
		this.reader = reader;
		this.simMap = new SimilarityMap(reader.getItemProfiles(), metric); // compute all item-item similarities
	}
	
	/**
	 * @returns the target item's predicted rating for the target user or null if a prediction cannot be computed
	 * @param itemId - the target item ID
	 * @param userId - the target user ID

	 */
	public Double getPrediction(final Integer userId, final Integer itemId)
	{	
		
		return predictor.getPrediction(itemId, userId, reader.getItemProfiles(), reader.getUserProfiles(), neighbourhood, simMap);

	}
	
	/**
	 * Returns all the item profiles loaded.
	 * @return a HashMap containing item profiles
	 */
	public Map<Integer,Profile> getItemProfiles()
	{
		return reader.getItemProfiles();
	}
}