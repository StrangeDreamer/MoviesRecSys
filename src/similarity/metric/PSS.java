package similarity.metric;

import java.util.Map;
import java.util.Set;

import profile.Profile;

public class PSS implements SimilarityMetric {
	private double med;
	private Map<Integer, Profile> itemProfiles;
	public PSS(double med,Map<Integer, Profile> itemProfiles) {
		this.med=med;
		this.itemProfiles=itemProfiles;
	}
	public PSS(Map<Integer, Profile> itemProfiles) {
		this.med=2.5;//Ĭ��Ϊ2.5
		this.itemProfiles=itemProfiles;
	}

	@Override
	//�û�p1,p2֮������ƶ�
	public double getSimilarity(Profile p1, Profile p2) {

		double proximity=0;
		double signifcance=0;
		double singularity=0;
		double pss=0;
		Set<Integer> common = p1.getCommonIds(p2);
		for(Integer id: common)
		{
			
			double idavg=itemProfiles.get(id).getMeanValue();
			proximity=1-(1/(1+Math.exp(-Math.abs(p1.getValue(id).doubleValue()-p2.getValue(id).doubleValue()))));
			signifcance=1/(1+Math.exp(-Math.abs(p1.getValue(id).doubleValue()-med)*Math.abs(p2.getValue(id).doubleValue()-med)));
			singularity=1-(1/(1+Math.exp(-Math.abs((p1.getValue(id).doubleValue()+p2.getValue(id).doubleValue())/2-idavg))));
			pss+=proximity*signifcance*singularity;
		}
		
		return pss;
	}



}
