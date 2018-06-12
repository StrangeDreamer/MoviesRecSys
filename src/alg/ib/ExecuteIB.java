package alg.ib;

import java.io.File;

import alg.ib.neighbourhood.*;
import alg.ib.predictor.*;
import alg.ub.predictor.Predictor;
import similarity.metric.*;
import util.evaluator.Evaluator;
import util.reader.DatasetReader;
/**
 * 系统算法评估
 * @author wangjin
 *
 */
public class ExecuteIB {
	private static double minRMSE=2.0,minMAE=2.0,mintsMAE=0,mintsRMSE=0;
	private static int MAXneiRMSE=0, MAXneiMAE=0,MINneiRMSE=0,MINneiMAE=0,simparaRMSE=0,simparaMAE=0;
	public static void main(String[] args) {
		String itemFile = "FRT dataset" + File.separator + "r.item";
		String trainFile = "FRT dataset" + File.separator + "r.train";
		String testFile = "FRT dataset" + File.separator + "r.probe";
		// String testFile = "FRT dataset" + File.separator + "r.test";
		String outputFile = "results" + File.separator + "predictions.txt";
		for (int k = 12; k <=12; k++) {//0的时候，coverage=100% 12最好
		PredictorItem predictor = new ResnickItem(k);
		for (int i = 14; i <=14 ; i+=10) {
			NeighbourhoodItem neighbourhood = new NearestNeighbourhoodItem(i);		//14
			for (int j = 1; j <= 1; j++) {
				SimilarityMetric metric = new Cosine(j);//1最好
				//SimilarityMetric metric = new Pearson(j);
				DatasetReader reader = new DatasetReader(itemFile, trainFile, testFile);
				ItemBasedCF ibcf = new ItemBasedCF(predictor, neighbourhood, metric, reader);

				// 由Evaluator构造函数产生预测结果集hashmap，大小为:5433
				Evaluator eval = new Evaluator(ibcf, reader.getTestData());

				//eval.printResult(52907, reader.getItems());

				eval.writeResults(outputFile);
				Double RMSE = eval.getRMSE();

				Double MAE = eval.getMAE();
				if (RMSE != null)	System.out.println("RMSE: " + RMSE);
				if (MAE != null)	System.out.println("MAE: " + MAE);
				if(RMSE<minRMSE)
				{
					minRMSE=RMSE;
					//mintsRMSE=ts;
					MAXneiRMSE=i;
					MINneiRMSE=k;
					simparaRMSE=j;
				}
				if(MAE <minMAE)
				{
					minMAE=MAE;
					//mintsMAE=ts;
					MAXneiMAE=i;
					MINneiMAE=k;
					simparaMAE=j;
				}
				double coverage = eval.getCoverage();
				System.out.println("Coverage: " + coverage + "%");


			}
		}
		}
		System.out.println("minRMSE: "+minRMSE+"   MAXneiRMSE:"+MAXneiRMSE+"   MINneiRMSE:"+MINneiRMSE+"   Simpara:"+simparaRMSE+" mintsRMSE:"+mintsRMSE);
		System.out.println("minMAE: "+minMAE+"   MAXneiMAE:"+MAXneiMAE+"   MINneiMAE:"+MINneiMAE+"   Simpara:"+simparaMAE+" mintsMAE:"+mintsMAE);
	}
}
