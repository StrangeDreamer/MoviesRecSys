package util.evaluator;

import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;

import alg.CFAlgorithm;
import alg.ub.UserBasedCF;
import util.Item;
import util.RatingsPair;
import util.UserItemPair;
/**
 * 算法评估
 * @author wangjin
 *
 */
public class Evaluator 
{	
	//存储测试数据的预测值
	private Map<UserItemPair,RatingsPair> results;
	
	/**
	 * constructor - creates a new Evaluator object
	 * @param alf - the CF algorithm
	 * @param testData - a map containing the test data
	 */
	public Evaluator(final CFAlgorithm alg, final Map<UserItemPair,Double> testData)
	{
		results = new HashMap<UserItemPair,RatingsPair>(); // instantiate the results hash map
		// iterate over test data and make predictions for all user-item pairs
		for(Iterator<Map.Entry<UserItemPair,Double>> it = testData.entrySet().iterator(); it.hasNext(); ) 
		{
			Map.Entry<UserItemPair,Double> entry = (Map.Entry<UserItemPair,Double>)it.next();
			UserItemPair pair = entry.getKey();
			Double actualRating = entry.getValue();
			Double predictedRating = alg.getPrediction(pair.getUserId(), pair.getItemId());
			results.put(pair, new RatingsPair(actualRating, predictedRating));

			//System.out.println(pair.getUserId() + " " +  pair.getItemId() + " " + actualRating + " " + predictedRating);
		}
		System.out.println("由Evaluator构造函数产生预测结果集hashmap，大小为:"+results.size());
	}

	public Evaluator(final CFAlgorithm alg) {
		results = new HashMap<UserItemPair,RatingsPair>();
		

		
	}

	/**
	 * @returns the coverage (as a percentage)
	 */
	public double getCoverage()
	{
		int counter = 0;
		int k=0;
		for(RatingsPair ratings: results.values())
			if(ratings.getPredictedRating() != null)//
				counter++;
		return (results.size() > 0) ? counter * 100.0 / results.size() : 0;//results.size()5433
	}
	
	/**
	 * @returns 计算predictions.txt里面每行数据的实际打分和预测打分对之间的均方根误差 root mean square error (RMSE) or null if the actual ratings are not available
	 */
	public Double getRMSE()
	{
		int counter = 0;
		double squareError = 0;
		for(RatingsPair ratings: results.values())                                     //results 在Evaluator构造函数时就已经生成  results = new HashMap<UserItemPair,RatingsPair>()
		{	
			if(ratings.getActualRating() == null)                                      // actual ratings not available, exit loop
				break;
				
			if(ratings.getPredictedRating() != null)                                   // a predicted rating has been computed
			{
				squareError += Math.pow(ratings.getActualRating().doubleValue()
						- ratings.getPredictedRating().doubleValue(), 2);
				counter++;
			}
		}

		if(counter == 0)
			return null;
		else
			return new Double(Math.sqrt(squareError / counter));	
	}
	
	/**
	 * @returns 计算predictions.txt里面每行数据的实际打分和预测打分对之间的平均绝对误差the mean absolute error (MAE) or null if the actual ratings are not available
	 */
	public Double getMAE()
	{
		int counter = 0;
		double error = 0;
		for(RatingsPair ratings: results.values())
		{	
			if(ratings.getActualRating() == null)
				break;			
			if(ratings.getPredictedRating() != null)
			{
				error += Math.abs(ratings.getActualRating().doubleValue()
						- ratings.getPredictedRating().doubleValue());
				counter++;
			}
		}
		if(counter == 0)
			return null;
		else
			return new Double(error / counter);	
	}
	
	/**
	 * @param filename - the path and filename of the output file
	 */
	public void writeResults(final String outputFile)
	{
		int lines=0;
		try
        {
			PrintWriter pw = new PrintWriter(new FileWriter(outputFile)); // open output file for writing
		    //在Evaluator的构造函数就已经得到result
			for(Iterator<Map.Entry<UserItemPair,RatingsPair>> it = results.entrySet().iterator(); it.hasNext(); ) // iterate over all predictions
			{
				Map.Entry<UserItemPair,RatingsPair> entry = (Map.Entry<UserItemPair,RatingsPair>)it.next();
				UserItemPair pair = entry.getKey();
				RatingsPair ratings = entry.getValue();

				if(ratings.getPredictedRating() != null) // a predicted rating has been computed
				{
					pw.print(pair.toString() + " ");//输出user-movie pair
					if(ratings.getActualRating() != null) // print the actual rating if available
						pw.print(ratings.getActualRating() + " ");  //输出actualrating-predictedrating pair
					pw.println(ratings.getPredictedRating());
					lines++;
				}
			}
			System.out.println("predicted lines count:"+lines);
			pw.close(); // close output file
        }
		catch(IOException e)
		{
			System.out.println("Error writing to output file ...\n");
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	
	//由预测集（r.test或r.probe）提供需要测试的useriten对得到的结果
	public void printResult(int userid,Map<Integer, Item> items)
	{
		System.out.println(results.keySet().size());
		
		for(UserItemPair userItemPair: results.keySet())//results 在Evaluator构造函数时就已经生成  results = new HashMap<UserItemPair,RatingsPair>()
		{	
			if(userItemPair.getUserId() == userid) // a predicted rating has been computed
			{
				System.out.println(userid+" 预测"+items.get(userItemPair.getItemId()).getName()+
						"  评分:  "+results.get(userItemPair).getPredictedRating()+
						"  "+results.get(userItemPair).getActualRating()+"  "+
						items.get(userItemPair.getItemId()).getYear()+"  "+items.get(userItemPair.getItemId()).getGenre());
			}
		}
		
	}
	
	public Map<UserItemPair, Double> getResult(int userid)
	{
		Map<UserItemPair,Double > resultsByUseridMap=new HashMap<UserItemPair,Double>();
//		int lastsize=-1;
		
		for(UserItemPair userItemPair: results.keySet())//results 在Evaluator构造函数时就已经生成  results = new HashMap<UserItemPair,RatingsPair>()
		{	
			
			if(userItemPair.getUserId() == userid) // a predicted rating has been computed
			{
//				lastsize=resultsByUseridMap.size();
				resultsByUseridMap.put(userItemPair, results.get(userItemPair).getPredictedRating());
				
				//System.out.println(userid+" 预测"+items.get(userItemPair.getItemId()).getName()+"评分:"+results.get(userItemPair).getPredictedRating()+"  "+results.get(userItemPair).getActualRating());
			}
/*			if(lastsize==resultsByUseridMap.size())
				break;*/
		}
	return resultsByUseridMap;
	}

//	因为没有推荐，所以就没有准确率和召回率可言
/*	public double getPrecision() {
		// TODO Auto-generated method stub
		return 0;
	}

	public double getRecall() {
		// TODO Auto-generated method stub
		return 0;
	}
*/

}
