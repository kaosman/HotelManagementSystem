package test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.dbunit.DBTestCase;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.database.QueryDataSet;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;

public class DBUnitTest extends DBTestCase
{
	
	String TEST_DB_FLAT_FILE = "C:\\Users\\kehindocha\\git\\HotelManagementSystem2\\src\\test\\SampleData\\test-dataset_temp.xml";
	
	/**
	 * Constructor
	 * @param name
	 */
	public DBUnitTest(String name)
	{
		super( name );
		System.setProperty( PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS, "org.postgresql.Driver" );
		System.setProperty( PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL, "jdbc:postgresql://localhost:5432/HotelManagementSystem" );
		System.setProperty( PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME, "postgres" );
		System.setProperty( PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD, "root" );
		// System.setProperty( PropertiesBasedJdbcDatabaseTester.DBUNIT_SCHEMA, "" );
	}

    
    @Override
    protected IDataSet getDataSet() throws Exception {
        return new FlatXmlDataSetBuilder().build(new FileInputStream(TEST_DB_FLAT_FILE));
    }
    
    @Override
    protected DatabaseOperation getSetUpOperation() throws Exception {
        return DatabaseOperation.CLEAN_INSERT;
    }

    @Override
    protected DatabaseOperation getTearDownOperation() throws Exception {
        return DatabaseOperation.NONE;
    }


	protected void loadCurrentDatabase() throws Exception {
		String driverName = System.getProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS);
		String conName = System.getProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL);
		String user = System.getProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME);
		String pass = System.getProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD);
		Class.forName(driverName);
		Connection jdbcConnection = DriverManager.getConnection(conName, user, pass);
		IDatabaseConnection connection = new DatabaseConnection(jdbcConnection);

		// Add partial data set.
		// IDataSet fullDataSet = connection.createDataSet();
		QueryDataSet partialDataSet = new QueryDataSet(connection);
		String schema = "public";
		String[] dataTypes = new String[] { "TABLE" };
		ResultSet publicTables = jdbcConnection.getMetaData().getTables(null, schema, null, dataTypes);
		while (publicTables.next()) {
			String table = publicTables.getString(3);
			partialDataSet.addTable(table);
			System.out.println(table);
		}
		FlatXmlDataSet.write(partialDataSet, new FileOutputStream("docs/" + TEST_DB_FLAT_FILE));
	}
	
    public void testRegisterNewGuest() throws Exception {
    	// Setup connection
    	Connection connection = getConnection().getConnection();
    	
    	// Test Query goes here
		final String sql = "INSERT INTO Guest(guestname,guestaddress,guestaffiliation) VALUES (?,?,?)";

		try{

			PreparedStatement statement = connection.prepareStatement(sql);

			statement.setString(1, "John");
			statement.setString(2, "10 Downing Street");
			statement.setString(3, "CAA");
			ResultSet resultSet = statement.executeQuery();
			
	        int count = -1;
	        if (resultSet.next()) {
	            count = resultSet.getInt(1);
	        }
	        resultSet.close();
	        statement.close();
	        assertTrue("Must be 10 units, are " + count, 10 == count);

		}catch(SQLException e){
			e.printStackTrace();
		}
    }
    
    public void testUpdateExistingGuest() throws Exception {
    	// Setup connection
    	Connection connection = getConnection().getConnection();
    	
    	// Test Query goes here
		final String sql = "INSERT INTO Guest(guestname,guestaddress,guestaffiliation) VALUES (?,?,?)";

		try{

			PreparedStatement statement = connection.prepareStatement(sql);

			statement.setString(1, "John");
			statement.setString(2, "10 Downing Street");
			statement.setString(3, "CAA");
			ResultSet resultSet = statement.executeQuery();
			
	        int count = -1;
	        if (resultSet.next()) {
	            count = resultSet.getInt(1);
	        }
	        resultSet.close();
	        statement.close();
	        assertTrue("Must be 10 units, are " + count, 10 == count);

		}catch(SQLException e){
			e.printStackTrace();
		}
    	
    }
    
    public void testDeleteExistingGuest() throws Exception {
    	// Setup connection
    	Connection connection = getConnection().getConnection();
    	
    	// Test Query goes here
		final String sql = "INSERT INTO Guest(guestname,guestaddress,guestaffiliation) VALUES (?,?,?)";

		try{

			PreparedStatement statement = connection.prepareStatement(sql);

			statement.setString(1, "John");
			statement.setString(2, "10 Downing Street");
			statement.setString(3, "CAA");
			ResultSet resultSet = statement.executeQuery();
			
	        int count = -1;
	        if (resultSet.next()) {
	            count = resultSet.getInt(1);
	        }
	        resultSet.close();
	        statement.close();
	        assertTrue("Must be 10 units, are " + count, 10 == count);

		}catch(SQLException e){
			e.printStackTrace();
		}
    	
    }
    
    public void testGetAvailableHotels() throws Exception {
    	// Setup connection
    	Connection connection = getConnection().getConnection();
    	
    	// Test Query goes here
		final String sql = "INSERT INTO Guest(guestname,guestaddress,guestaffiliation) VALUES (?,?,?)";

		try{

			PreparedStatement statement = connection.prepareStatement(sql);

			statement.setString(1, "John");
			statement.setString(2, "10 Downing Street");
			statement.setString(3, "CAA");
			ResultSet resultSet = statement.executeQuery();
			
	        int count = -1;
	        if (resultSet.next()) {
	            count = resultSet.getInt(1);
	        }
	        resultSet.close();
	        statement.close();
	        assertTrue("Must be 10 units, are " + count, 10 == count);

		}catch(SQLException e){
			e.printStackTrace();
		}
    	
    }
    
    public void testRegisterNewBooking() throws Exception {
    	// Setup connection
    	Connection connection = getConnection().getConnection();
    	
    	// Test Query goes here
		final String sql = "INSERT INTO Guest(guestname,guestaddress,guestaffiliation) VALUES (?,?,?)";

		try{

			PreparedStatement statement = connection.prepareStatement(sql);

			statement.setString(1, "John");
			statement.setString(2, "10 Downing Street");
			statement.setString(3, "CAA");
			ResultSet resultSet = statement.executeQuery();
			
	        int count = -1;
	        if (resultSet.next()) {
	            count = resultSet.getInt(1);
	        }
	        resultSet.close();
	        statement.close();
	        assertTrue("Must be 10 units, are " + count, 10 == count);

		}catch(SQLException e){
			e.printStackTrace();
		}
    	
    }
    
    public void testGetArrivals() throws Exception {
    	// Setup connection
    	Connection connection = getConnection().getConnection();
    	
    	// Test Query goes here
		final String sql = "INSERT INTO Guest(guestname,guestaddress,guestaffiliation) VALUES (?,?,?)";

		try{

			PreparedStatement statement = connection.prepareStatement(sql);

			statement.setString(1, "John");
			statement.setString(2, "10 Downing Street");
			statement.setString(3, "CAA");
			ResultSet resultSet = statement.executeQuery();
			
	        int count = -1;
	        if (resultSet.next()) {
	            count = resultSet.getInt(1);
	        }
	        resultSet.close();
	        statement.close();
	        assertTrue("Must be 10 units, are " + count, 10 == count);

		}catch(SQLException e){
			e.printStackTrace();
		}
    	
    }
    
    public void getDepartures() throws Exception {
    	// Setup connection
    	Connection connection = getConnection().getConnection();
    	
    	// Test Query goes here
		final String sql = "INSERT INTO Guest(guestname,guestaddress,guestaffiliation) VALUES (?,?,?)";

		try{

			PreparedStatement statement = connection.prepareStatement(sql);

			statement.setString(1, "John");
			statement.setString(2, "10 Downing Street");
			statement.setString(3, "CAA");
			ResultSet resultSet = statement.executeQuery();
			
	        int count = -1;
	        if (resultSet.next()) {
	            count = resultSet.getInt(1);
	        }
	        resultSet.close();
	        statement.close();
	        assertTrue("Must be 10 units, are " + count, 10 == count);

		}catch(SQLException e){
			e.printStackTrace();
		}
    	
    }
}