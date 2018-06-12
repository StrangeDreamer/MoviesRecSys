package util;


public class Item
{
	private Integer id;
	private String name; 
	private Integer year; 
	private String genre;

	public Item(final Integer id, final String name, final Integer year,final String genre)
	{
		this.id = id;
		this.name = name;
		this.year = year;
		this.genre=genre;
	}

	/**
	 * @return the id of the item
	 */
	public Integer getId() 
	{
		return id;
	}
	
	/**
	 * @return the name of the item
	 */
	public String getName() 
	{
		return name;
	}
	
	/**
	 * @return the name of the item
	 */
	public Integer getYear() 
	{
		return year;
	}
	
	/**
	 * @param set the ID of the item
	 */
	public void setId(final Integer id)
	{
		this.id = id;
	}
	
	/**
	 * @param set the name of the item
	 */
	public void setName(final String name)
	{
		this.name = name;
	}
	
	/**
	 * @param set the name of the item
	 */
	public void setYear(final Integer year)
	{
		this.year = year;
	}
	

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	
}
