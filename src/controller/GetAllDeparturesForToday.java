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

public class GetAllDeparturesForToday extends HttpServlet
{
	private DataAccessObject dataAccessObject;
	
	public GetAllDeparturesForToday()
	{
		
	}
	
	public void init() throws ServletException 
	{		
			this.dataAccessObject = new DataAccessObject();
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{	
		String hotelID;
		ArrayList<String> listOfDepartures = new ArrayList<String>();
		HttpSession httpSession = request.getSession(false);
		
		if(request.getParameter("hotelID") == null)
			hotelID = "";
		else 
			hotelID = request.getParameter("hotelID");
		
		listOfDepartures = this.dataAccessObject.getDepartures(hotelID);
		httpSession.setAttribute("listOfDepartures", listOfDepartures);
		ServletContext context = getServletContext();
		RequestDispatcher requestDispatcher = context.getRequestDispatcher("/DisplayAllDeparturesForToday");
		requestDispatcher.forward(request, response);
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		/* Redirect to book-form. */
		getServletContext().getRequestDispatcher("/WEB-INF/GetAllDeparturesForToday/GetAllDeparturesForToday.jsp").forward(
				request, response);
	}
}
