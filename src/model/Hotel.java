package model;

public class Hotel 
{	
	//Class variables
	private String hotelID;
	private String hotelName;
	private String city;
	
	//Constructor 1
	public Hotel()
	{
		
	}
	//Constructor 2
	public Hotel(String hotelID, String hotelName, String city)
	{
		this.hotelID=hotelID;
		this.hotelName=hotelName;
		this.city=city;
	}
	
	//Getters and setters
	public String getHotelID() 
	{
		return hotelID;
	}

	public void setHotelID(String hotelID) 
	{
		this.hotelID = hotelID;
	}

	public String getHotelName() 
	{
		return hotelName;
	}

	public void setHotelName(String hotelName) 
	{
		this.hotelName = hotelName;
	}

	public String getCity() 
	{
		return city;
	}

	public void setCity(String city) 
	{
		this.city = city;
	}
}
