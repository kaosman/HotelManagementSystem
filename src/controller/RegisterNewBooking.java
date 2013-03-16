package controller;

import java.io.IOException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.DataAccessObject;

import model.Booking;
import model.Hotel;
import model.Guest;

/**A booking agent registers a new guest and enters their information,
including the name and address. The guestID is auto-generated, either
through the API or by the DBMS itself.
* 
* @author Guest
*/
public class RegisterNewBooking extends HttpServlet
{
	
	private DataAccessObject dataAccessObject;
	
	public RegisterNewBooking() 
	{
        super();
    }
	
	public void init() throws ServletException 
	{		
			this.dataAccessObject = new DataAccessObject();
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
		HttpSession httpSession = request.getSession(false);
		Booking newBooking = this.getBookingInfo(request,response); 
		int bookingConfirmation = this.dataAccessObject.registerNewBooking(newBooking); 
		httpSession.setAttribute("bookingID", bookingConfirmation);
		ServletContext context = getServletContext();
		RequestDispatcher requestDispatcher = context.getRequestDispatcher("/DisplayBookingConfirmation");
		requestDispatcher.forward(request, response);
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		/* Redirect to book-form. */
		getServletContext().getRequestDispatcher("/WEB-INF/RegisterNewBooking/RegisterNewBooking.jsp").forward(
				request, response);
	}
	
	// TODO make sure that all the form input names match with the getParams in the function
		private Booking getBookingInfo(HttpServletRequest request,HttpServletResponse resp)
		{	
			Booking booking = new Booking();
			
			String hotelID = request.getParameter("hotelID");
			if (!hotelID.equals(""))
			{
				booking.setHotelID(hotelID);
			}	
			
			final String roomNumber = request.getParameter("roomNumber");
			if(!roomNumber.equals(""))
			{
				booking.setRoomNo(roomNumber);
			}
			
			final String guestID = request.getParameter("guestID");
			if (!guestID.equals(""))
			{
				Integer intGuestID = Integer.parseInt(guestID);
				booking.setGuestID(intGuestID.intValue());
			}	
			
			final String startDate = request.getParameter("startDate");
			if(!startDate.equals(""))
			{
				booking.setStartdate(startDate);
			}
			
			
			final String endDate = request.getParameter("endDate");
			if(!endDate.equals(""))
			{
				booking.setEnddate(endDate);
			}
			
			if((hotelID.equals("")) || (roomNumber.equals("")) || (guestID.equals("")) 
					|| (startDate.equals("")) || (endDate.equals("")) ) 
			{	
				try {
					resp.sendRedirect("Error.jsp");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else
			{
				return booking;
			}
			return null;
		}			
}



