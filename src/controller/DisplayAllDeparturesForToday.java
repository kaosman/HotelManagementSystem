package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
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

public class DisplayAllDeparturesForToday extends HttpServlet
{
	
	public DisplayAllDeparturesForToday()
	{
		
	}
	
	public void init() throws ServletException 
	{		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{	
		doGet(request, response);
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		ArrayList<String> departureList;

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		
		departureList = (ArrayList<String>) session.getAttribute("listOfDepartures");
		out.println("The list of departures for today are as follows ...");
		
		for (String departure : departureList){
			out.println(departure);
		}
	}
}
