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

import model.Guest;

/**A booking agent registers a new guest and enters their information,
including the name and address. The guestID is auto-generated, either
through the API or by the DBMS itself.
* 
* @author Guest
*/
//@WebServlet(name = "RegisterNewGuest", urlPatterns = {"/RegisterNewGuest/RegisterNewGuest.jsp"})
@WebServlet("/RegisterNewGuest")
public class RegisterNewGuest extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	private Guest newGuest;
	private DataAccessObject dataAccessObject;
	
	public RegisterNewGuest() 
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
		newGuest = this.newGuestInfo(request,response); //newPatientRecord object will store the new data
		
		this.dataAccessObject.registerNewGuest(newGuest); //newPatientRecord object data is sent as a parameter to the DAO method update patient's personal record
	}
	
//	protected void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
//	{
//		doPost(request,response);
//	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		/* Redirect to book-form. */
		getServletContext().getRequestDispatcher("/WEB-INF/RegisterNewGuest/RegisterNewGuest.jsp").forward(
				request, response);
	}
	
	// TODO make sure that all the form input names match with the getParams in the function
		private Guest newGuestInfo(HttpServletRequest request,HttpServletResponse resp)
		{	
			Guest newGuest = new Guest();
			final int guestID = 2015;
			final String guestName = request.getParameter("GuestName");
			if(!guestName.equals(""))
			{
				newGuest.setGuestName(guestName);
			}
			
			final String guestAddress = request.getParameter("GuestAddress");
			if(!guestAddress.equals(""))
			{
				newGuest.setGuestAddress(guestAddress);
			}
			
			final String guestAffiliation = request.getParameter("GuestAffiliation");
			if(!guestAffiliation.equals(""))
			{
				newGuest.setGuestAffiliation(guestAffiliation);
			}
			
			if((guestName.equals("")) || (guestAddress.equals("")) || (guestAffiliation.equals(""))) 
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
				newGuest = new Guest(guestID, guestAddress, guestAffiliation, guestName);
				return newGuest;
			}
			return null;
		}			
}