package alg.ub;

import java.io.File;

import alg.ub.neighbourhood.*;
import alg.ub.predictor.*;
import similarity.metric.*;
import util.evaluator.Evaluator;
import util.reader.DatasetReader;

/**
 * ϵͳ����
 * 
 * @author wangjin
 *
 */
public class ExecuteUB {
	// ��ʱ����
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
					// neighbourhood��ɶ�ã�
					// ͨ��UserBasedCF����hybrid��neighborhood��������KNN
					// ����ResnickԤ�⺯��(����KNN)��������ʵ��ֵ��Ԥ��ֵ֮������
					Evaluator eval = new Evaluator(ubcf, reader.getTestData());// ��Ԥ��������resulthashmap��

					// eval.printResult(52907,reader.getItems());//�û�123

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
					// �����ʱ�ʾ�Ƽ�ϵͳ�ܹ��Ƽ������ĵ���Ʒռ����Ʒ�ı���
					// ��һ���̶��ϴ����Ƽ�ϵͳ�Գ�β��Ʒ�ķ�������
				}
			}
		}
		System.out.println("minRMSE: " + minRMSE + "   MAXneiRMSE:" + MAXneiRMSE + "   MINneiRMSE:" + MINneiRMSE
				+ "   Simpara:" + simparaRMSE + " mintsRMSE:" + mintsRMSE);
		System.out.println("minMAE: " + minMAE + "   MAXneiMAE:" + MAXneiMAE + "   MINneiMAE:" + MINneiMAE
				+ "   Simpara:" + simparaMAE + " mintsMAE:" + mintsMAE);
	}
}
