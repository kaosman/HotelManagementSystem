package controller;

import java.io.IOException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.DataAccessObject;

import model.Hotel;
import model.Guest;

/**A booking agent registers a new guest and enters their information,
including the name and address. The guestID is auto-generated, either
through the API or by the DBMS itself.
* 
* @author Guest
*/
@WebServlet("/RegisterNewBooking")
public class RegisterNewBooking extends HttpServlet
{
	
	private DataAccessObject dataAccessObject;
	private Guest existingGuest;
	
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
		existingGuest = this.getBookingInfo(request,response); 
		
		this.dataAccessObject.makeNewBooking(); 
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		/* Redirect to book-form. */
		getServletContext().getRequestDispatcher("/WEB-INF/RegisterNewBooking/RegisterNewBooking.jsp").forward(
				request, response);
	}
	
	// TODO make sure that all the form input names match with the getParams in the function
		private Guest getBookingInfo(HttpServletRequest request,HttpServletResponse resp)
		{	
			Guest existingGuest = new Guest();
			
			String strGuestID = request.getParameter("hotelID");
			if (!strGuestID.equals(""))
			{
				Integer guestID = Integer.parseInt(strGuestID);
				existingGuest.setGuestID(guestID.intValue());
			}	
			
			final String guestName = request.getParameter("roomNumber");
			if(!guestName.equals(""))
			{
				existingGuest.setGuestName(guestName);
			}
			
			final String guestAddress = request.getParameter("guestID");
			if(!guestAddress.equals(""))
			{
				existingGuest.setGuestAddress(guestAddress);
			}
			
			final String guestAffiliation = request.getParameter("startDate");
			if(!guestAffiliation.equals(""))
			{
				existingGuest.setGuestAffiliation(guestAffiliation);
			}
			
			if((strGuestID.equals("")) || (guestName.equals("")) || (guestAddress.equals("")) || (guestAffiliation.equals(""))) 
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
				return existingGuest;
			}
			return null;
		}			
}



