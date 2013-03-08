package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/** This class connects to the Postgresql Database
 * 
 * 
 * 
 * */
public class ServiceProvider
{
	
	//Class variables needed to establish connection
	private Connection connectionHotel= null;
	private final String username = "postgres";
	private final String password = "root";
	private final String databaseurl = "jdbc:postgresql://localhost:5432/Hotel_Database";
	
	//Object of class service provider
	private static ServiceProvider serviceProvider = new ServiceProvider();

	public ServiceProvider() 
	{
		try
		{
			try {
				Class.forName("org.postgresql.Driver").newInstance();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.connectionHotel = DriverManager.getConnection(databaseurl,username, password);
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (ClassNotFoundException e) 
		{
			e.printStackTrace();
		}
	}
	
	public static ServiceProvider getInstance()
	{
		return serviceProvider;
	}
	
	public Connection getConnection()
	{
		return this.connectionHotel;
	}
}
