package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import model.Booking;
import model.Guest;

public class DataAccessObject 
{
	private Connection connectionHotel = null;
	private ServiceProvider serviceProvider = null;

	public DataAccessObject() 
	{
		this.serviceProvider = new ServiceProvider();
		this.connectionHotel=this.serviceProvider.getConnection();
	}

	//Required DB operation methods
	//Module 1 - Guest registration
	public boolean registerNewGuest(Guest guest)
	{
		// A booking agent registers a new guest and enters their information,
		// including the name and address. The guestID is auto-generated, either
		// through the API or by the DBMS itself.

		final String sql = "INSERT INTO Guest(guestname,guestaddress,guestaffiliation) VALUES (?,?,?)";

		try{

			PreparedStatement statement = connectionHotel.prepareStatement(sql);

			statement.setString(1, guest.getGuestName());
			statement.setString(2, guest.getGuestAddress());
			statement.setString(3, guest.getGuestAffiliation());
			statement.execute();

			return true;

		}catch(SQLException e){
			e.printStackTrace();
		}
		return false;
	}

	public boolean updateExistingGuest(Guest guest)
	{
		// The agent can also update the information for an existing guest
		// (except for the guestID), and can also delete the entry for a
		// specific guest if requested.
		final String sql = "UPDATE Guest " +
				"SET guestname = ?, " +
				"guestaddress = ?, " +
				"guestaffiliation = ? " +
				"WHERE guestid = ? ";
		try
		{

			PreparedStatement statement = connectionHotel.prepareStatement(sql);

			statement.setString(1, guest.getGuestName());
			statement.setString(2, guest.getGuestAddress());
			statement.setString(3, guest.getGuestAffiliation());
			statement.setInt(4, guest.getGuestID());
			statement.execute();

			return true;

		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		return false;
	}

	public boolean deleteExistingGuest(Guest existingGuest) 
	{
		// The agent can also delete the information for an existing guest, if required.
		final String sql = "DELETE FROM Guest " +
				"WHERE guestid = ? ";
		final String sql2 = "DELETE FROM Booking WHERE guestid = ? ";

		try
		{
			PreparedStatement statement2 = connectionHotel.prepareStatement(sql2);
			statement2.setInt(1, existingGuest.getGuestID());
			statement2.execute();

			PreparedStatement statement = connectionHotel.prepareStatement(sql);
			statement.setInt(1, existingGuest.getGuestID());
			statement.execute();

			return true;

		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		return false;
	}

	//Module 2 - Booking query
	public ArrayList<String> getAvailableHotels(String startdate, String enddate, String hotelname, String city, String roomprice, String roomtype)
	{
		// Once the guest information has been entered, the agent can then query
		// hotels for available rooms on specified dates. That is, the agent
		// enters one or all of the following: startDate, endDate, hotelName,
		// city, room price, and room type. For any entry that is left blank,
		// the corresponding condition is not applied (e.g., if city is left
		// blank, all cities are considered).

		DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
		ArrayList<String> availableHotelNames = new ArrayList<String>();	
		String sql;
		int index=0;

		sql = "SELECT H.hotelid,H.hotelName, H.city, R.roomno,R.price,R.roomtype " +
				"FROM HOTEL H "+
				"JOIN Room R ON H.hotelid = R.hotelid "+
				"JOIN Booking B ON H.hotelid = B.hotelid "+
				"WHERE TRUE ";

		try{
			PreparedStatement statement = connectionHotel.prepareStatement(sql);

			if((!startdate.isEmpty()) && (!(enddate.isEmpty()))) 
			{
				sql += "AND (( CAST(? AS DATE) NOT BETWEEN B.startdate AND B.enddate) AND " +"(CAST(? AS DATE) NOT BETWEEN B.startdate AND B.enddate) ";
				statement.setString(++index, startdate);
				statement.setString(++index, enddate);
			}

			if(!hotelname.isEmpty())
			{
				sql+= "AND H.hotelName = ? ";
				statement.setString(++index, hotelname);
			}


			if(!city.isEmpty())
			{
				sql += "AND H.city = ? ";
				statement.setString(++index, city);
			}


			if(!roomprice.isEmpty())
			{
				sql+="AND R.price = ROUND(CAST(? AS NUMERIC),2) ";
				statement.setString(++index, roomprice);
			}


			if(!roomtype.isEmpty())
			{
				sql+="AND R.roomtype = ? ";
				statement.setString(++index, roomtype);
			}

			ResultSet resultSet = statement.executeQuery();
			while(resultSet.next()){
				String result = "H.hotelid = " + resultSet.getString("hotelid") +
						" H.hotelName = " + resultSet.getString("hotelname") +
						" H.city = " + resultSet.getString("city") +
						" R.roomno = " + resultSet.getString("roomno") +
						" R.price = " + resultSet.getString("roomprice") +
						" R.roomtype = " + resultSet.getString("roomtype");
				availableHotelNames.add(result);
			}


		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		return availableHotelNames;
	}

	//Module 3 - Booking registration
	//TODO : Update web.xml
	public int registerNewBooking(Booking booking)
	{
		// Once an available room has been found, the agent then registers a new
		// booking by entering the hotelID, roomNo, guestID, startDate, and
		// endDate. If the booking was successful, the system returns the
		// bookingID that the agent can give to the customer as confirmation.
		// The bookingID is auto-generated, either through the API or by the
		// DBMS itself.The booking registration should also ensure that bookings
		// for the same room do not overlap
		int newBookingID=-1;
		boolean flag1 = false;
		boolean flag2 = false;

		//Query to fetch all available rooms
		String sql1 = "SELECT hotelID,roomNo,startDate,endDate FROM Booking WHERE hotelID = ? AND roomNo = ? AND startDate = CAST(? AS DATE) AND enddate = CAST(? AS DATE)";
		String sql2 = "INSERT INTO Booking (hotelID,roomNo,guestID,startDate,endDate) VALUES (?,?,?,CAST(? AS DATE),CAST(? AS DATE))";
		String sql3 = "SELECT bookingID FROM Booking WHERE hotelID = ? AND roomNo = ? AND guestID = ? AND startDate = CAST(? AS DATE) AND endDate = CAST(? AS DATE)";

		try {
			PreparedStatement statement1 = connectionHotel.prepareStatement(sql1);
			statement1.setString(1, booking.getHotelID());
			statement1.setString(2, booking.getRoomNo());
			statement1.setInt(3, booking.getGuestID());
			statement1.setString(4, booking.getStartdate());
			statement1.setString(5, booking.getEnddate());
		} catch (SQLException e1) {
			flag1 = true;
		}

		if(flag1){
			try {
				PreparedStatement statement2 = connectionHotel.prepareStatement(sql2);
				statement2.setString(1, booking.getHotelID());
				statement2.setString(2, booking.getRoomNo());
				statement2.setInt(3, booking.getGuestID());
				statement2.setString(4, booking.getStartdate());
				statement2.setString(5, booking.getEnddate());
				statement2.execute();
				flag2 = true;
			} 		
			catch (SQLException e2) {
				e2.printStackTrace();
			}

			if(flag2){
				try {
					PreparedStatement statement3 = connectionHotel.prepareStatement(sql3);
					statement3.setString(1, booking.getHotelID());
					statement3.setString(2, booking.getRoomNo());
					statement3.setInt(3, booking.getGuestID());
					statement3.setString(4, booking.getStartdate());
					statement3.setString(5, booking.getEnddate());
					ResultSet resultSet2 = statement3.executeQuery();
					while(resultSet2.next()){
						newBookingID = resultSet2.getInt(1);
						System.out.println("New booking ID is = " + newBookingID);
					}

				} catch (SQLException e3) {
					e3.printStackTrace();
				}			
			}
		}
		else{
			return -1;
		}
		return newBookingID;
	}

	/**Module 4 - Room Maintenance and Billing
	 * 
	The administrative staff can use a separate interface to list all the
	arrivals (i.e., the current date equals startDate) and all the
	departures (i.e., the current date equals endDate) on a specific day
	to ensure that the rooms are maintained before and after guest
	arrivals, respectively. 

	Finally, the administrative staff can print
	bills for the departing guests (display on screen is sufficient),
	based on the number of days stayed and the applicable room price.
	Each bill printed should also be logged into the billing log for the
	accounting purposes. */

	public ArrayList<String> getArrivals(String HotelID){

		//Assuming user enters the hotel id for which we are getting arrivals and departures

		boolean flag1 = false;
		ArrayList<String> arrivalList = new ArrayList<String>();
		ResultSet arrivalResultSet;

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar currentDate = Calendar.getInstance();
		//String formattedDate = dateFormat.format(currentDate.getTime().toString());
		System.out.println(dateFormat.format(currentDate.getTime()));

		//Query to fetch arrivals
		String sql1 = "SELECT G.guestid, G.guestname, R.roomno, R.roomtype, R.price, B.startdate, B.enddate " +
				"FROM Guest G " +
				"JOIN Booking B ON G.guestid = B.guestid " +
				"JOIN Room R ON B.roomno = R.roomno " +
				"WHERE B.startdate = CAST(? AS DATE) AND R.hotelid = ? ";

		try {
			PreparedStatement statement1 = connectionHotel.prepareStatement(sql1);
			statement1.setString(1, dateFormat.format(currentDate.getTime()));
			statement1.setString(2, HotelID);
			flag1 = true;

			arrivalResultSet = statement1.executeQuery();
			while(arrivalResultSet.next()){
				String arrivalResultString = "G.guestID = " + arrivalResultSet.getString(1) +
						" G.guestname = " + arrivalResultSet.getString(2) +
						" R.roomno = " + arrivalResultSet.getString(3) +
						" R.price = " + arrivalResultSet.getString(4) +
						" R.roomtype = " + arrivalResultSet.getString(5)+
						" B.startdate = " + arrivalResultSet.getDate(6).toString()+
						" B.enddate = " + arrivalResultSet.getDate(7).toString();	
				arrivalList.add(arrivalResultString);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
			//Flag is false and -1 is returned to servlet to send noarrivals.jsp to user
		}

		if(!flag1){
			arrivalList.add("-1");
			return arrivalList;
		}
		else
			return arrivalList;
	}

	public ArrayList<String> getDepartures(String HotelID){

		boolean flag1 = false;
		double billingAmount;
		long totalDaysStayed;

		ArrayList<String> departureList = new ArrayList<String>();
		ResultSet departureResultSet;

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar currentDate = Calendar.getInstance();
		//String formattedDate = dateFormat.format(currentDate.getTime().toString());
		System.out.println(dateFormat.format(currentDate.getTime()));

		//Query to fetch departures
		String sql1 = "SELECT G.guestid, G.guestname, R.roomno, R.roomtype, R.price, B.startdate, B.enddate,B.hotelID,B.bookingID " +
				"FROM Guest G " +
				"JOIN Booking B ON G.guestid = B.guestid " +
				"JOIN Room R ON B.roomno = R.roomno " +
				"WHERE B.enddate = CAST(? AS DATE) AND R.hotelid = ? ";

		String sql2 = "INSERT INTO BillingLog(bookingID,guestID,hotelID,roomNo,startDate,endDate,billPrice) VALUES(?,?,?,?,?,?,?)";

		try {
			PreparedStatement statement1 = connectionHotel.prepareStatement(sql1);
			statement1.setString(1, dateFormat.format(currentDate.getTime()));
			statement1.setString(2, HotelID);
			flag1 = true;

			departureResultSet = statement1.executeQuery();
			while(departureResultSet.next()){

				totalDaysStayed = (departureResultSet.getDate(7).getTime() - departureResultSet.getDate(6).getTime()) / 86400000;
				billingAmount = totalDaysStayed * departureResultSet.getDouble(5);

				String departureResultString = "G.guestID = " + departureResultSet.getString(1) +
						" G.guestname = " + departureResultSet.getString(2) +
						" R.roomno = " + departureResultSet.getString(3) +
						" R.price = " + departureResultSet.getString(4) +
						" R.roomtype = " + departureResultSet.getString(5)+
						" B.startdate = " + departureResultSet.getDate(6).toString()+
						" B.enddate = " + departureResultSet.getDate(7).toString()+
						" B.hotelID = " + departureResultSet.getString(8)+
						" B.bookingID = " + departureResultSet.getInt(9)+
						" Bill Price = " + billingAmount;

				departureList.add(departureResultString); //Add result set data into departure arraylist

				//Insert data into billing log table
				try{
					PreparedStatement statement2 = connectionHotel.prepareStatement(sql2);
					statement2.setLong(1, departureResultSet.getInt(9));
					statement2.setLong(2, departureResultSet.getInt(1));
					statement2.setString(3, departureResultSet.getString(8));
					statement2.setString(4, departureResultSet.getString(3));
					statement2.setDate(5, departureResultSet.getDate(6));
					statement2.setDate(6, departureResultSet.getDate(7));
					statement2.setDouble(7, billingAmount);
					statement2.execute();
				} catch (SQLException e2){
					System.out.println("Unable to insert data into billing log");
				}

			}
		} catch (SQLException e1) {
			//Flag is false and -1 is returned to servlet to send nodepartures.jsp to user
		}

		if(!flag1){
			departureList.add("-1");
			return departureList;
		}
		else
			return departureList;
	}
}
