package controller;

import java.io.IOException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.DataAccessObject;

import model.Hotel;
import model.Guest;
import model.Booking;
import model.Room;

/**Once the guest information has been entered, the agent can then query
hotels for available rooms on specified dates. That is, the agent
enters one or all of the following: startDate, endDate, hotelName,
city, room price, and room type. For any entry that is left blank,
the corresponding condition is not applied (e.g., if city is left
blank, all cities are considered).
* 
* @author Agent
*/
@WebServlet("/GetAvailableHotels")

public class GetAvailableHotels extends HttpServlet
{
	private DataAccessObject dataAccessObject;
	private Hotel hotel;
	private Booking booking;
	private Room room;
	private Guest guest;
	
	public GetAvailableHotels()
	{
		
	}
	
	public void init() throws ServletException 
	{		
			this.dataAccessObject = new DataAccessObject();
	}
	
	//TODO convert string date input to SQL date format
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
		HttpSession httpSession = request.getSession(false);
		
		String startDate = request.getParameter("StartDate");
		String endDate = request.getParameter("EndDate");
		String hotelName = request.getParameter("HotelName");
		String city = request.getParameter("City");
		String roomPrice = request.getParameter("RoomPrice");
		String roomType = request.getParameter("RoomType");
				
		this.dataAccessObject.getAvailableHotels(startDate, endDate, hotelName, city, roomPrice, roomType); //newGuest object data is sent as a parameter to the DAO method to insert guest info into guest table
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		/* Redirect to book-form. */
		getServletContext().getRequestDispatcher("/WEB-INF/HotelDisplay/HotelDisplay.jsp").forward(
				request, response);
	}

	/*
	 * Returns true if input date is before current date.
	 */
	private boolean checkBeforeDate(String date) {

		try {
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date inputDate;
			inputDate = (Date) dateFormat.parse(date);
			Date currentDate = new Date(0, 0, 0);

			Calendar current = Calendar.getInstance();
			current.setTime(currentDate);

			Calendar input = Calendar.getInstance();
			input.setTime(inputDate);

			return input.before(current);

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
}
