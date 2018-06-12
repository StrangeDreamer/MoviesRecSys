package alg.ub.predictor;

import java.util.ArrayList;
import java.util.Map;

import alg.ub.neighbourhood.Neighbourhood;
import similarity.SimilarityMap;
import profile.Profile;

/**
 *�Բ��Լ�����Ԥ�⼯���û�-��Ʒ��������Ԥ��
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
		//�������ھ��б�
		ArrayList<Integer> neighbours = neighbourhood
				.getNeighbours(userId, itemId, itemProfileMap, simMap);
		Profile u = userProfileMap.get(userId);
		double u_mean = u.getMeanValue();//�û�userId�ĵ�Ӱ���־�ֵ
		double u_sd = u.getStandardDeviation();	//�û�userId�ĵ�Ӱ���ֱ�׼��
		for(int i = 0; i < neighbours.size(); i++) // �����û�userId�������ھ�
		{//��õ�i���ھӶԵ�ӰitemId������
			Double n_rating = userProfileMap.get(neighbours.get(i)).getValue(itemId); 
			if(n_rating == null)
			{
				System.out.println("Error - rating is null!");
				System.exit(1);
			}
			double n_mean = userProfileMap.get(neighbours.get(i)).getMeanValue(); 
			double n_sd =userProfileMap.get(neighbours.get(i)).getStandardDeviation(); 			
			if(n_sd == 0) // �����ĸΪ0
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
