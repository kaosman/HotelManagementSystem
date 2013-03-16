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

import model.Guest;

public class DataAccessObject 
{
	private Connection connectionHotel = null;
	private ServiceProvider serviceProvider = null;
	private Date sdate;
	private Date edate;

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

        try
        {
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
	public ArrayList<String> getAvailableHotels(String startdate, String enddate, String hotelname, 
			String city, String roomprice, String roomtype)
	{
		// Once the guest information has been entered, the agent can then query
		// hotels for available rooms on specified dates. That is, the agent
		// enters one or all of the following: startDate, endDate, hotelName,
		// city, room price, and room type. For any entry that is left blank,
		// the corresponding condition is not applied (e.g., if city is left
		// blank, all cities are considered).
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
		ArrayList<String> availableHotelNames = new ArrayList<String>();		

		String sql = "SELECT H.hotelid,H.hotelName, H.city, R.roomno,R.price,R.roomtype " +
				"FROM HOTEL H "+
				"JOIN Room R ON H.hotelid = R.hotelid "+
				"JOIN Booking B ON H.hotelid = B.hotelid "+
				"WHERE (( CAST(? AS DATE) NOT BETWEEN B.startdate AND B.enddate) AND " +
				"(CAST(? AS DATE) NOT BETWEEN B.startdate AND B.enddate) " +
				"AND H.hotelName = ? "+
				"AND H.city = ? AND R.price = ROUND(CAST(? AS NUMERIC),2) AND R.roomtype = ?)";
		try{
			PreparedStatement statement = connectionHotel.prepareStatement(sql);

			if(startdate.isEmpty())
				statement.setNull(1, Types.NULL);
			else
				statement.setString(1, startdate);
			
			if(enddate.isEmpty())	
				statement.setNull(2, Types.NULL);
			else
				statement.setString(2, enddate);

			if(hotelname.isEmpty())
				statement.setNull(3, Types.NULL);
			else
				statement.setString(3, hotelname);
			
			if(city.isEmpty())
				statement.setNull(4, Types.NULL);
			else
				statement.setString(4, city);
			
			if(roomprice.isEmpty())
				statement.setNull(5, Types.NULL);
			else
				statement.setString(5, roomprice);

			if(roomtype.isEmpty())
				statement.setNull(6, Types.NULL);
			else
				statement.setString(6, roomtype);

			ResultSet resultSet = statement.executeQuery();
			while(resultSet.next()){
				String result = "H.hotelid = " + resultSet.getString(1) +
						" H.hotelName = " + resultSet.getString(2) +
						" H.city = " + resultSet.getString(3) +
						" R.roomno = " + resultSet.getString(4) +
						" R.price = " + resultSet.getString(5) +
						" R.roomtype = " + resultSet.getString(6);
				availableHotelNames.add(result);
			}
			
			
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		return availableHotelNames;
	}

	public void getRoomInfo()
	{
		// The booking query returns all of the rooms that are available based
		// on the information entered, and the query displays the hotelID,
		// hotelName, city, roomNo, price, and type.
	}

	//Module 3 - Booking registration
	public int makeNewBooking()
	{
		// Once an available room has been found, the agent then registers a new
		// booking by entering the hotelID, roomNo, guestID, startDate, and
		// endDate. If the booking was successful, the system returns the
		// bookingID that the agent can give to the customer as confirmation.
		// The bookingID is auto-generated, either through the API or by the
		// DBMS itself.The booking registration should also ensure that bookings
		// for the same room do not overlap
		
		return 0;
	}

	//Module 4 - Room Maintenance and Billing
	public void roomMaintenance()
	{
		// The administrative staff can use a separate interface to list all the
		// arrivals (i.e., the current date equals startDate) and all the
		// departures (i.e., the current date equals endDate) on a specific day
		// to ensure that the rooms are maintained before and after guest
		// arrivals, respectively. Finally, the administrative staff can print
		// bills for the departing guests (display on screen is sufficient),
		// based on the number of days stayed and the applicable room price.
		// Each bill printed should also be logged into the billing log for the
		// accounting purposes.
	}

}
