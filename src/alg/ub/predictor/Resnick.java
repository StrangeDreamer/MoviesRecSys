package alg.ub.predictor;

import java.util.ArrayList;
import java.util.Map;

import alg.ub.neighbourhood.Neighbourhood;
import similarity.SimilarityMap;
import profile.Profile;

/**
 *对测试集或者预测集的用户-物品进行评分预测
 * @author wangjin
 *
 */
public class Resnick implements Predictor {
	/**
	 * constructor - creates a new SimpleAveragePredictor object
	 */
	private int MINnei;
	public Resnick(int minnei)
	{
		MINnei=minnei;	
	}
	
	public Double getPrediction(final Integer userId,final Integer itemId,
			final Map<Integer,Profile>userProfileMap,
			final Map<Integer,Profile>itemProfileMap,
			final Neighbourhood neighbourhood,final SimilarityMap simMap)
	{		
		double above = 0, below = 0;
		//获得最近邻居列表
		ArrayList<Integer> neighbours = neighbourhood
				.getNeighbours(userId, itemId, itemProfileMap, simMap);
		Profile u = userProfileMap.get(userId);
		double u_mean = u.getMeanValue();//用户userId的电影评分均值
		double u_sd = u.getStandardDeviation();	//用户userId的电影评分标准差
		for(int i = 0; i < neighbours.size(); i++) // 遍历用户userId的所有邻居
		{//获得第i个邻居对电影itemId的评分
			Double n_rating = userProfileMap.get(neighbours.get(i)).getValue(itemId); 
			if(n_rating == null)
			{
				System.out.println("Error - rating is null!");
				System.exit(1);
			}
			double n_mean = userProfileMap.get(neighbours.get(i)).getMeanValue(); 
			double n_sd =userProfileMap.get(neighbours.get(i)).getStandardDeviation(); 			
			if(n_sd == 0) // 避免分母为0
				n_sd = 0.0001;			
			double n_diff = n_rating - n_mean;
			double sim = simMap.getSimilarity(userId,neighbours.get(i));
			above += n_diff * sim / n_sd;
			below += Math.abs(sim); 
		}
		if(below==0)	return null;
		double ans=u_mean + (u_sd * (above / below));
		if(ans<0)	ans=0.0;
		if(ans>5)	ans=5.0;
		if (neighbours.size()>MINnei) 	return ans;
		else 	return null;
	}
}
