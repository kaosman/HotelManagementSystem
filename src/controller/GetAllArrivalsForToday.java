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

import dao.DataAccessObject;

public class GetAllArrivalsForToday extends HttpServlet
{
	private DataAccessObject dataAccessObject;
	
	public GetAllArrivalsForToday()
	{
		
	}
	
	public void init() throws ServletException 
	{		
			this.dataAccessObject = new DataAccessObject();
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{	
		String hotelID;
		ArrayList<String> listOfArrivals = new ArrayList<String>();
		HttpSession httpSession = request.getSession(false);
		
		if(request.getParameter("hotelID") == null)
			hotelID = "";
		else 
			hotelID = request.getParameter("hotelID");
		
		listOfArrivals = this.dataAccessObject.getArrivals(hotelID);
		httpSession.setAttribute("listOfArrivals", listOfArrivals);
		ServletContext context = getServletContext();
		RequestDispatcher requestDispatcher = context.getRequestDispatcher("/DisplayAllArrivalsForToday");
		requestDispatcher.forward(request, response);
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		/* Redirect to book-form. */
		getServletContext().getRequestDispatcher("/WEB-INF/GetAllArrivalsForToday/GetAllArrivalsForToday.jsp").forward(
				request, response);
	}
}
