package model;

public class Room 
{
	//Class variables
	private String hotelID;
	private String roomNo;
	private String roomType;
	private Float price;
	
	//Constructor 1
	public Room()
	{
		
	}
	
	//Constructor 2
	public Room(String hotelID, String roomNo, String roomType, Float price)
	{
		this.hotelID=hotelID;
		this.roomNo=roomNo;
		this.roomType=roomType;
		this.price=price;
	}

	public String getHotelID() {
		return hotelID;
	}

	public void setHotelID(String hotelID) {
		this.hotelID = hotelID;
	}

	public String getRoomNo() {
		return roomNo;
	}

	public void setRoomNo(String roomNo) {
		this.roomNo = roomNo;
	}

	public String getRoomType() {
		return roomType;
	}

	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}
	
}
