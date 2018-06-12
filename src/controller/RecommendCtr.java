package controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import alg.hybrid.HybridCF;
import alg.ib.ItemBasedCF;
import alg.ib.neighbourhood.NearestNeighbourhoodItem;
import alg.ib.neighbourhood.NeighbourhoodItem;
import alg.ib.predictor.PredictorItem;
import alg.ib.predictor.ResnickItem;
import alg.ub.UserBasedCF;
import alg.ub.neighbourhood.NearestNeighbourhood;
import alg.ub.neighbourhood.Neighbourhood;
import alg.ub.predictor.Predictor;
import alg.ub.predictor.Resnick;
import similarity.metric.Cosine;
import similarity.metric.CosinePearsonHybrid;
import similarity.metric.Pearson;
import similarity.metric.SimilarityMetric;
import util.evaluator.Evaluator;
import util.reader.DatasetReader;

public class RecommendCtr extends HttpServlet{
//	String itemFile ="FRT dataset/r.item";
//	String trainFile = "FRT dataset" + File.separator + "r.train";
//	String testFile = "FRT dataset" + File.separator + "r.test";
//	String outputFile = "results" + File.separator + "predictions.txt";
	String itemFile ="C:\\Users\\wangjin\\Desktop\\cf-0.18\\FRT dataset\\r.item";
	String trainFile ="C:\\Users\\wangjin\\Desktop\\cf-0.18\\FRT dataset\\r.train";
	String testFile ="C:\\Users\\wangjin\\Desktop\\cf-0.18\\FRT dataset\\r.probe";
//	String testFile ="C:\\Users\\wangjin\\Desktop\\cf-0.18\\FRT dataset\\r.test";//没有实际评分,根据历史得到预测评分
	String outputFile ="C:\\Users\\wangjin\\Desktop\\cf-0.18\\results\\predictions.txt";
	DatasetReader reader = new DatasetReader(itemFile, trainFile, testFile);
	//Map<UserItemPair,Double > a;
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String type= request.getParameter("recommend");
		int username=Integer.parseInt(request.getParameter("username"));
	
		if(type.equals("UserbasedCF")){		
			Predictor predictor = new Resnick(4);
			Neighbourhood neighbourhood = new NearestNeighbourhood(52);
			SimilarityMetric metric = new Cosine(1);
			UserBasedCF ubcf = new UserBasedCF(predictor, neighbourhood, metric, reader);
			Evaluator eval = new Evaluator(ubcf, reader.getTestData());
			System.out.println("MAE:"+eval.getMAE()+" RMSE:"+eval.getRMSE()+" Cov:"+eval.getCoverage());
			request.setAttribute("recommendation", eval.getResult(username));
//			request.setAttribute("items", reader.getItems());
//			request.getRequestDispatcher("recommendUserCF.jsp").forward(request, response);
			
		}
		
		else if(type.equals("ItembasedCF")){
			PredictorItem predictor = new ResnickItem(12);
			NeighbourhoodItem neighbourhood = new NearestNeighbourhoodItem(14);
			SimilarityMetric metric = new Cosine(1);
			ItemBasedCF ibcf = new ItemBasedCF(predictor, neighbourhood, metric, reader);
			Evaluator eval = new Evaluator(ibcf, reader.getTestData());
			System.out.println("MAE:"+eval.getMAE()+" RMSE:"+eval.getRMSE()+" Cov:"+eval.getCoverage());
			request.setAttribute("recommendation", eval.getResult(username));
//			request.setAttribute("items", reader.getItems());
//			request.getRequestDispatcher("recommendItemCF.jsp").forward(request, response);

		}
		else if(type.equals("HybridCF"))
		{
			Predictor userPredictor = new Resnick(4);
			Neighbourhood userNeighbourhood = new NearestNeighbourhood(52);
			SimilarityMetric userMetric = new Cosine(1);
			
			PredictorItem itemPredictor = new ResnickItem(12);
			NeighbourhoodItem itemNeighbourhood = new NearestNeighbourhoodItem(14);
			SimilarityMetric itemMetric = new Cosine(1);
			
			int userWeight = 4;
			int itemWeight = 6;
			
			
			HybridCF hybrid = new HybridCF(userPredictor, itemPredictor, userNeighbourhood, itemNeighbourhood, userMetric, itemMetric, reader, userWeight, itemWeight,4,1);
			Evaluator eval = new Evaluator(hybrid, reader.getTestData());
			System.out.println("MAE:"+eval.getMAE()+" RMSE:"+eval.getRMSE()+" Cov:"+eval.getCoverage());
			request.setAttribute("recommendation", eval.getResult(username));
//			request.setAttribute("items", reader.getItems());
//			request.getRequestDispatcher("recommendHybriCF.jsp").forward(request, response);
			
		}
		request.setAttribute("username", username); 
		request.setAttribute("items", reader.getItems());
		request.getRequestDispatcher("predictionCF.jsp").forward(request, response);
		
	}
	
	
}
