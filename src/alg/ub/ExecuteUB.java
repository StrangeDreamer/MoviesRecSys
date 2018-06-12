package alg.ub;

import java.io.File;

import alg.ub.neighbourhood.*;
import alg.ub.predictor.*;
import similarity.metric.*;
import util.evaluator.Evaluator;
import util.reader.DatasetReader;

/**
 * 系统评估
 * 
 * @author wangjin
 *
 */
public class ExecuteUB {
	// 临时变量
	private static double minRMSE = 2.0, minMAE = 2.0, mintsMAE = 0, mintsRMSE = 0;
	private static int MAXneiRMSE = 0, MAXneiMAE = 0, MINneiRMSE = 0, MINneiMAE = 0, simparaRMSE = 0, simparaMAE = 0;

	public static void main(String[] args) {
		String itemFile = "FRT dataset" + File.separator + "r.item";
		String trainFile = "FRT dataset" + File.separator + "r.train";
		String testFile = "FRT dataset" + File.separator + "r.probe";
		// String testFile = "FRT dataset" + File.separator + "r.test";
		String outputFile = "results" + File.separator + "predictions.txt";
		for (int k = 4; k <= 4; k++) {
			Predictor predictor = new Resnick(k);
			// Predictor predictor=new MeanPredictor();
			for (int i = 52; i <= 52; i += 10) {// 52
				Neighbourhood neighbourhood = new NearestNeighbourhood(i);
				for (int j = 1; j <= 1; j += 10) {
					SimilarityMetric metric = new Cosine(j);
					DatasetReader reader = new DatasetReader(itemFile, trainFile, testFile);
					UserBasedCF ubcf = new UserBasedCF(predictor, neighbourhood, metric, reader);
					// neighbourhood有啥用？
					// 通过UserBasedCF对象hybrid将neighborhood参数传给KNN
					// 调用Resnick预测函数(调用KNN)，并计算实际值与预测值之间的误差
					Evaluator eval = new Evaluator(ubcf, reader.getTestData());// 将预测结果存入resulthashmap中

					// eval.printResult(52907,reader.getItems());//用户123

					eval.writeResults(outputFile);
					Double RMSE = eval.getRMSE();
					Double MAE = eval.getMAE();

					if (RMSE != null)
						System.out.println("RMSE: " + RMSE);
					if (MAE != null)
						System.out.println("MAE: " + MAE);

					if (RMSE < minRMSE) {
						minRMSE = RMSE;
						// mintsRMSE=ts;
						MAXneiRMSE = i;
						MINneiRMSE = k;
						simparaRMSE = j;
					}
					if (MAE < minMAE) {
						minMAE = MAE;
						// mintsMAE=ts;
						MAXneiMAE = i;
						MINneiMAE = k;
						simparaMAE = j;
					}

					double Coverage = eval.getCoverage();
					System.out.println("Coverage: " + Coverage + "%");
					// 覆盖率表示推荐系统能够推荐出来的的物品占总物品的比例
					// 从一定程度上代表推荐系统对长尾物品的发掘能力
				}
			}
		}
		System.out.println("minRMSE: " + minRMSE + "   MAXneiRMSE:" + MAXneiRMSE + "   MINneiRMSE:" + MINneiRMSE
				+ "   Simpara:" + simparaRMSE + " mintsRMSE:" + mintsRMSE);
		System.out.println("minMAE: " + minMAE + "   MAXneiMAE:" + MAXneiMAE + "   MINneiMAE:" + MINneiMAE
				+ "   Simpara:" + simparaMAE + " mintsMAE:" + mintsMAE);
	}
}
