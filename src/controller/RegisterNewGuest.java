package controller;

import java.io.IOException;
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
@WebServlet(name = "RegisterNewGuest", urlPatterns = {"/"})
//@WebServlet("/RegisterNewGuest")
public class RegisterNewGuest extends HttpServlet
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DataAccessObject dataAccessObject;
	private Guest newGuest;
	
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
		newGuest = this.newGuestInfo(request,response);
		this.dataAccessObject.registerNewGuest(newGuest); 
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		/* Redirect to book-form. */
		getServletContext().getRequestDispatcher("/WEB-INF/RegisterNewGuest/RegisterNewGuest.jsp").forward(request, response);
	}
	
	// TODO make sure that all the form input names match with the getParams in the function
		private Guest newGuestInfo(HttpServletRequest request,HttpServletResponse resp)
		{	
			Guest newGuest = new Guest();
			
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
				newGuest = new Guest(guestAddress, guestAffiliation, guestName);
				return newGuest;
			}
			return null;
		}			
}



