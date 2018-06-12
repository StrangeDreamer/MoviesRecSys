package util;
/**
 * ¥Ê¥¢”√ªß∆¿∑÷æÿ’Û
 * @author wangjin
 *
 */
public class UserItemPair
{
	private Integer userId; // the numeric user ID
	private Integer itemId; // the numeric item ID 
	
	/**
	 * constructor - creates a new UserItemPair object
	 * @param userId - the numeric user ID
	 * @param itemId - the numeric item ID
	 */
	public UserItemPair(final Integer userId, final Integer itemId)
	{
		this.userId = userId;
		this.itemId = itemId;
	}
	
	/**
	 * @returns the user ID
	 */
	public Integer getUserId()
	{
		return userId;
	}

	/**
	 * @returns the item ID
	 */
	public Integer getItemId()
	{
		return itemId;
	}

	/**
	 * @returns a string representation of this object
	 */
	public String toString()
	{
		return userId + " " + itemId;
	}
}