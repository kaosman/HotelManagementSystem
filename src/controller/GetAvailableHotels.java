package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Booking;
import model.Guest;
import model.Hotel;
import model.Room;
import dao.DataAccessObject;

/**Once the guest information has been entered, the agent can then query
hotels for available rooms on specified dates. That is, the agent
enters one or all of the following: startDate, endDate, hotelName,
city, room price, and room type. For any entry that is left blank,
the corresponding condition is not applied (e.g., if city is left
blank, all cities are considered).
* 
* @author Agent
*/
public class GetAvailableHotels extends HttpServlet
{
	private DataAccessObject dataAccessObject;
	
	public GetAvailableHotels()
	{
		
	}
	
	public void init() throws ServletException 
	{		
			this.dataAccessObject = new DataAccessObject();
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{	
		String startDate;
		String endDate;
		String hotelName;
		String city;
		String roomPrice;
		String roomType;
		ArrayList<String> hotelnamelist;
		HttpSession httpSession = request.getSession(false);
		
		if(request.getParameter("StartDate") == null)
			startDate = "";
		else 
			startDate = request.getParameter("StartDate");
		
		if(request.getParameter("EndDate") == null)
			endDate = "";
		else 
			endDate = request.getParameter("EndDate");

		if(request.getParameter("HotelName") == null)
			hotelName = "";
		else 
			hotelName = request.getParameter("HotelName");

		
		if(request.getParameter("City") == null)
			city = "";
		else 
			city = request.getParameter("City");
		
		if(request.getParameter("RoomPrice") == null)
			roomPrice = "";
		else 
			roomPrice = request.getParameter("RoomPrice");
		
		if(request.getParameter("RoomType") == null)
			roomType = "";
		else 
			roomType = request.getParameter("RoomType");

		
		hotelnamelist=this.dataAccessObject.getAvailableHotels(startDate, endDate, hotelName, city, roomPrice, roomType);
		
		httpSession.setAttribute("hotelnamelist", hotelnamelist);
		ServletContext context = getServletContext();
		RequestDispatcher requestDispatcher = context.getRequestDispatcher("/DisplayHotelNames");
		requestDispatcher.forward(request, response);
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		/* Redirect to book-form. */
		getServletContext().getRequestDispatcher("/WEB-INF/GetAvailableHotels/GetAvailableHotels.jsp").forward(
				request, response);
	}
}
