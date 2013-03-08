package model;

import java.sql.Date;

public class Booking 
{
	//Class variables
	private String hotelID;
	private String roomNo;
	private String guestID;
	private Date startdate;
	private Date enddate;
	
	public Booking()
	{
		
	}
	public Booking(String hotelID, String roomNo, String guestID,
			Date startdate, Date enddate) 
	{
		super();
		this.hotelID = hotelID;
		this.roomNo = roomNo;
		this.guestID = guestID;
		this.startdate = startdate;
		this.enddate = enddate;
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
	public Date getStartdate() {
		return startdate;
	}
	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}
	public Date getEnddate() {
		return enddate;
	}
	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}
}
