package model;

public class Booking 
{
	//Class variables
	private int bookingID;
	private String hotelID;
	private String roomNo;
	private String guestID;
	private String startdate;
	private String enddate;
	
	public Booking()
	{
		
	}
	public Booking(String hotelID, String roomNo, String guestID,
			String startdate, String enddate) 
	{
		super();
		this.hotelID = hotelID;
		this.roomNo = roomNo;
		this.guestID = guestID;
		this.startdate = startdate;
		this.enddate = enddate;
	}
	
	// Getters and Setters
	public int getBookingID() {
		return bookingID;
	}
	public void setBookingID(int bookingID) {
		this.bookingID = bookingID;
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
	public String getGuestID() {
		return guestID;
	}
	public void setGuestID(String guestID) {
		this.guestID = guestID;
	}
	public String getStartdate() {
		return startdate;
	}
	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}
	public String getEnddate() {
		return enddate;
	}
	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}
}
