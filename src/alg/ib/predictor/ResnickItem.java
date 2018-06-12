package alg.ib.predictor;

import java.util.ArrayList;
import java.util.Map;

import alg.ib.neighbourhood.NeighbourhoodItem;
import similarity.SimilarityMap;
import profile.Profile;

/**
 * 预测测试集数据
 * 
 * @author wangjin
 *
 */
public class ResnickItem implements PredictorItem {

	/**
	 * constructor - creates a new SimpleAveragePredictor object
	 */
	private int MINnei;

	public ResnickItem(int minnei) {
		MINnei = minnei;
	}

	public Double getPrediction(final Integer itemId, final Integer userId, final Map<Integer, Profile> itemProfileMap,
			final Map<Integer, Profile> userProfileMap, final NeighbourhoodItem neighbourhood,
			final SimilarityMap simMap) {
		double above = 0;
		double below = 0;
		// get the neighbours
		ArrayList<Integer> neighbours = neighbourhood.getNeighbours(itemId, userId, userProfileMap, simMap);
		// Get the mean rating for the item we are trying to get a prediction
		// for
		Profile i = itemProfileMap.get(itemId);
		double i_mean = i.getMeanValue();
		// iterate over each neighbour
		for (int j = 0; j < neighbours.size(); j++) {
			// get the neighbour's rating for the target user
			Double n_rating = itemProfileMap.get(neighbours.get(j)).getValue(userId);
			if (n_rating == null) {
				// this error should never occur since all neighbours by
				// definition have rated the target item!
				System.out.println("Error - rating is null!");
				System.exit(1);
			}
			// get the mean value of this neighbour
			double n_mean = itemProfileMap.get(neighbours.get(j)).getMeanValue();
			double n_diff = n_rating - n_mean;
			double sim = simMap.getSimilarity(itemId, neighbours.get(j));
			above += n_diff * sim;
			below += Math.abs(sim);
		}
		if (below == 0)
			return null;// 不应该执行
		double ans = i_mean + (above / below);
		if (ans < 0)
			ans = 0.0;// 不应该执行
		if (ans > 5)
			ans = 5.0;
		if (neighbours.size() > MINnei)
			return ans;
		else
			return null;
	}

}
