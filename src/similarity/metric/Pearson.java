/**
 * Compute the Pearson Correlation similarity between profiles
 * 
 * Derek Organ
 * 06/02/2012
 */

package similarity.metric;

import java.util.Set;

import profile.Profile;
//传入一个参数max，用于调控
/*两个用户之间的相似性由经用户共同评分的项目决定，
在用户评分数据极端稀疏的情况下，经两个用户共同评分的项目非常少，
经常只有一两项目，用户即使在这些项目上的评分非常相似，
也不一定就是最近邻居。如果采用余弦相似性，
则用户对所有未评分的项目都为0，也是不合理的
最好：首先通过项目之间的相似性初步预测用户对未评分项目的评分
使得用户之间共同评价的项目比较多
然后通过余弦相似性计算用户之间的相似性*/

/*如果只有一个重叠项则无法计算相关性
        从数学上讲，若只有一个重叠的记录，那么至少有一组记录的标准差为0，导致分母为0
        从这一点也可以看出，pearson系数不适用与小的或者非常稀疏的数据集。
        当然，这一特性也有它的好处，无法计算pearson系数可以认为这两组数据没有任何相关性。*/
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
		Math.sqrt(base2)))*(Math.min(Math.abs(common.size()), max)/max ):0;                                 //若没用共同评价的电影，则相似度为0
		return ans;
	}	
}
