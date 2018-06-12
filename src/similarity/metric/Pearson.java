/**
 * Compute the Pearson Correlation similarity between profiles
 * 
 * Derek Organ
 * 06/02/2012
 */

package similarity.metric;

import java.util.Set;

import profile.Profile;
//����һ������max�����ڵ���
/*�����û�֮����������ɾ��û���ͬ���ֵ���Ŀ������
���û��������ݼ���ϡ�������£��������û���ͬ���ֵ���Ŀ�ǳ��٣�
����ֻ��һ����Ŀ���û���ʹ����Щ��Ŀ�ϵ����ַǳ����ƣ�
Ҳ��һ����������ھӡ�����������������ԣ�
���û�������δ���ֵ���Ŀ��Ϊ0��Ҳ�ǲ������
��ã�����ͨ����Ŀ֮��������Գ���Ԥ���û���δ������Ŀ������
ʹ���û�֮�乲ͬ���۵���Ŀ�Ƚ϶�
Ȼ��ͨ�����������Լ����û�֮���������*/

/*���ֻ��һ���ص������޷����������
        ����ѧ�Ͻ�����ֻ��һ���ص��ļ�¼����ô������һ���¼�ı�׼��Ϊ0�����·�ĸΪ0
        ����һ��Ҳ���Կ�����pearsonϵ����������С�Ļ��߷ǳ�ϡ������ݼ���
        ��Ȼ����һ����Ҳ�����ĺô����޷�����pearsonϵ��������Ϊ����������û���κ�����ԡ�*/
public class Pearson implements SimilarityMetric
{
	private double max;
	public Pearson(double max){this.max = max;}
	public double getSimilarity(final Profile p1, final Profile p2)
	{
		double top = 0;
		double base1 = 0;
		double base2 = 0;
		Set<Integer> common = p1.getCommonIds(p2);
		
		for(Integer id: common)
		{
			double r1 = p1.getValue(id).doubleValue() - p1.getMeanValue();
			double r2 = p2.getValue(id).doubleValue() - p2.getMeanValue();
			top += r1 * r2;
			base1 += Math.pow(r1, 2);
			base2 += Math.pow(r2, 2);
		}
		double ans = (base1 > 0 && base2 > 0) ? (top / (Math.sqrt(base1)*
		Math.sqrt(base2)))*(Math.min(Math.abs(common.size()), max)/max ):0;                                 //��û�ù�ͬ���۵ĵ�Ӱ�������ƶ�Ϊ0
		return ans;
	}	
}
