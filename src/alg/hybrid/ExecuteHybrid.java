package alg.hybrid;

import java.io.File;

import alg.ib.neighbourhood.*;
import alg.ib.predictor.*;
import alg.ub.neighbourhood.*;
import alg.ub.predictor.*;
import similarity.metric.*;
import util.evaluator.Evaluator;
import util.reader.DatasetReader;

/**
 * 混合的CF系统评估
 * 
 * @author wangjin
 *
 */
public class ExecuteHybrid {
	private static double minRMSE = 2.0, minMAE = 2.0;
	private static double userWeiRMSE = 0, itemWeiRMSE = 0;
	private static double userWeiMAE = 0, itemWeiMAE = 0;
	private static double aRMSE = 0, bRMSE = 0;
	private static double aMAE = 0, bMAE = 0;

	public static void main(String[] args) {
		/*
		 * File.separator才是用来分隔同一个路径字符串中的目录的，例如： C:/Program Files/Common Files
		 * 就是指“/”
		 */
		String itemFile = "FRT dataset" + File.separator + "r.item";
		String trainFile = "FRT dataset" + File.separator + "r.train";
		String testFile = "FRT dataset" + File.separator + "r.probe";
		// String testFile = "FRT dataset" + File.separator + "r.test";
		DatasetReader reader = new DatasetReader(itemFile, trainFile, testFile);

		for (double i = 4; i <= 4; i += 1) {//
			double userWeight = i;
			double itemWeight = 10 - i;
			for (double a = 4; a <= 4; a += 1) {// 0-4，1.5-2.0 1.9
				for (double b = 1; b <= 1; b += 1) {// 0-2，0-0.5 0.1-0.5
					Predictor userPredictor = new Resnick(4);
					Neighbourhood userNeighbourhood = new NearestNeighbourhood(52);
					// SimilarityMetric userMetric = new CosinePearsonHybrid(90,
					// 2, 8);
					// SimilarityMetric userMetric = new CosinePearsonHybrid(1,
					// 2, 8);
					SimilarityMetric userMetric = new Cosine(1);

					PredictorItem itemPredictor = new ResnickItem(12);
					NeighbourhoodItem itemNeighbourhood = new NearestNeighbourhoodItem(14);
					// SimilarityMetric itemMetric = new Pearson(80); // 80 is
					// the max value for significance weighting
					// SimilarityMetric itemMetric = new CosinePearsonHybrid(80,
					// 8, 2);
					// SimilarityMetric itemMetric = new
					// CosinePearsonHybrid(90,8,2);
					SimilarityMetric itemMetric = new Cosine(1);

					String outputFile = "results" + File.separator + "predictions.txt";

					HybridCF hybrid = new HybridCF(userPredictor, itemPredictor, userNeighbourhood, itemNeighbourhood,
							userMetric, itemMetric, reader, userWeight, itemWeight, a, b);
					Evaluator eval = new Evaluator(hybrid, reader.getTestData());
					// eval.printResult(52907,reader.getItems());
					eval.writeResults(outputFile);
					Double RMSE = eval.getRMSE();
					Double MAE = eval.getMAE();
					if (RMSE != null)
						System.out.println("RMSE: " + RMSE);
					if (MAE != null)
						System.out.println("MAE: " + MAE);
					if (RMSE < minRMSE) {
						minRMSE = RMSE;
						userWeiRMSE = i;
						itemWeiRMSE = 10 - i;
						aRMSE = a;
						bRMSE = b;
					}
					if (MAE < minMAE) {
						minMAE = MAE;
						userWeiMAE = i;
						itemWeiMAE = 10 - i;
						aMAE = a;
						bMAE = b;
					}
					double coverage = eval.getCoverage();
					System.out.println("Coverage: " + coverage + "%");
				}
			}
		}

		System.out.println("minMAE:" + minMAE + "  minRMSE:" + minRMSE + "  userWeiMAE:" + userWeiMAE + "  itemWeiMAE:"
				+ itemWeiMAE + "  userWeiRMSE:" + userWeiRMSE + "  itemWeiRMSE:" + itemWeiRMSE);
		System.out.println("aMAE:" + aMAE + "  bMAE:" + bMAE + "  aRMSE:" + aRMSE + "  bRMSE:" + bRMSE);

	}
}
