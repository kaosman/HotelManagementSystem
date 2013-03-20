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

public class DisplayAllArrivalsForToday extends HttpServlet
{
	
	public DisplayAllArrivalsForToday()
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
		ArrayList<String> arrivalList;

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		
		arrivalList = (ArrayList<String>) session.getAttribute("listOfArrivals");
		out.println("The list of arrivals for today are as follows ...");
		
		for (String arrival : arrivalList){
			out.println(arrival);
		}
	}
}
