package util.reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import profile.Profile;

import util.Item;
import util.UserItemPair;
/**
 * 读数据，由训练数据建立用户模型和物品模型，物品清单，读测试数据
 * prediction.txt is from r.probe
 * @author wangjin
 *
 */
public class DatasetReader 
{
	private Map<Integer,Profile> userProfileMap;
	private Map<Integer,Profile> itemProfileMap;
	private Map<Integer,Item> itemMap;
	private Map<UserItemPair,Double> testData;

	/** 
	 * Constructs a DatasetReader from the MovieLens profiles  
	 * @param itemFile the path of the file containing item descriptions  eg. 11003|Weekend at Bernies|1989|Comedy
	 * @param trainFile the path of the file containing the training user-item ratings  eg. 65046 8166 4.5
	 * @param testFile the path of the file containing the test user-item ratings    eg. 65046 3141
	 */
	public DatasetReader(final String itemFile, final String trainFile, final String testFile)
	{
		userProfileMap = new HashMap<Integer,Profile>();
		itemProfileMap = new HashMap<Integer,Profile>();
		
		loadItems(itemFile); // must be called before loadProfiles()
		loadProfiles(trainFile);
		//如果预测集合包含r.test将r.probe作为训练集一起读进去
		if(testFile.contains("r.test")) 
		{
			System.out.println("将 r.probe加入到训练集中");
			
			loadProfiles("C:\\Users\\wangjin\\Desktop\\cf-0.18\\FRT dataset\\r.probe"); // the formation is the same as training set
		}
		
		loadTestData(testFile);
	}

	/**
	 * Returns all items loaded.
	 * @return a HashMap containing items
	 */
	public Map<Integer,Item> getItems()
	{
		return itemMap;
	}

	/**
	 * Returns an item.
	 * @return an Item object
	 * @param the numeric ID of the Item object
	 */
	public Item getItem(Integer id)
	{
		return itemMap.get(id);
	}
	
	/**
	 * Returns all the user profiles loaded.
	 * @return a HashMap containing user profiles
	 */
	public Map<Integer,Profile> getUserProfiles()
	{
		return userProfileMap;
	}

	/**
	 * Returns all the item profiles loaded.
	 * @return a HashMap containing item profiles
	 */
	public Map<Integer,Profile> getItemProfiles()
	{
		return itemProfileMap;
	}
	
	/**
	 * Returns the test date.
	 * @return a HashMap containing the test data
	 */
	public Map<UserItemPair,Double> getTestData()
	{
		//System.out.println("返回testData<(userId, itemId), rating>");
		return testData;
	}

	/**
	 * Loads all user and item profiles.
	 * @param the path of the file containing the training user-item ratings
	 */
	private void loadProfiles(final String filename) 
	{

		
		try
		{
			BufferedReader br = new BufferedReader(new FileReader(new File(filename)));
			String line;
			while ((line = br.readLine()) != null) 
			{
				StringTokenizer st = new StringTokenizer(line, ", \t\n\r\f");//以, \t\n\r\f 6中符号作为分隔符
				if(st.countTokens() != 3)
				{
					System.out.println("Error reading from file \"" + filename + "\"");
					System.exit(1);
				}
				
				Integer userId = Integer.valueOf(st.nextToken());
				Integer itemId = Integer.valueOf(st.nextToken());
				Double rating = Double.valueOf(st.nextToken());
				
				// add data to user profile map
				Profile up = userProfileMap.containsKey(userId) ? userProfileMap.get(userId) : new Profile(userId);
				up.addValue(itemId, rating);//相当于user-item矩阵某一行
				userProfileMap.put(userId, up);
				
				// add data to item profile map
				Profile ip = itemProfileMap.containsKey(itemId) ? itemProfileMap.get(itemId) : new Profile(itemId);
				ip.addValue(userId, rating);//相当于user-item矩阵某一列
				itemProfileMap.put(itemId, ip);
			}
			
			br.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	/**
	 * Loads all test data.
	 * @param the path of the file containing the training user-item ratings
	 */
	private void loadTestData(final String filename) 
	{
		testData = new HashMap<UserItemPair,Double>();
		
		try
		{
			BufferedReader br = new BufferedReader(new FileReader(new File(filename)));
			String line;
			while ((line = br.readLine()) != null) 
			{
				StringTokenizer st = new StringTokenizer(line, ", \t\n\r\f");
				if(st.countTokens() != 2 && st.countTokens() != 3)
				{//==2的情况是:r.test(没有rating值); ==3的情况是:r.probe(有rating值)
					System.out.println("Error reading from file \"" + filename + "\"");
					System.exit(1);
				}
				
				Integer userId = Integer.valueOf(st.nextToken());
				Integer itemId = Integer.valueOf(st.nextToken());
				Double rating = (st.countTokens() == 1) ? Double.valueOf(st.nextToken()) : null; // check to see if one more token (i.e. the rating) remains
				testData.put(new UserItemPair(userId, itemId), rating);	// add data to user test data map
			}
			
			br.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	private void loadItems(final String filename) 
	{
		itemMap = new HashMap<Integer,Item>();
		
		try
		{
			BufferedReader br = new BufferedReader(new FileReader(new File(filename)));
			String line;
			String genre ="";
			while ((line = br.readLine()) != null) 
			{
				StringTokenizer st = new StringTokenizer(line, "|");
				if(st.countTokens() < 3)
				{
					System.out.println("Error reading from file \"" + filename + "\"");
					System.exit(1);
				}
				
				Integer id = Integer.valueOf(st.nextToken());
				String name = st.nextToken();
				Integer year = Integer.valueOf(st.nextToken());
				genre=st.nextToken("");
				/*
				加上电影种类*/
				
				/*while (st.hasMoreElements()) {
					genre+=st.nextToken();
				}*/
				//Item item = new Item(id, name, year,genre.replace("|", "   "));
				Item item = new Item(id, name, year,genre);
				
				itemMap.put(id, item);
			}
			System.out.println("Total items:"+itemMap.size());

			br.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
			System.exit(0);
		}
	}
}
