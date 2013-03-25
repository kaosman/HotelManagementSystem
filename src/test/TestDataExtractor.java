package test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
 
import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.database.QueryDataSet;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.xml.FlatXmlDataSet;
 
public class TestDataExtractor {
 
public static void main(String[] args) throws ClassNotFoundException, SQLException, FileNotFoundException, IOException, DatabaseUnitException {
Class.forName("org.postgresql.Driver");
 
Connection jdbcConnection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/HotelManagementSystem", "postgres", "root");
IDatabaseConnection connection = new DatabaseConnection(jdbcConnection);
 
QueryDataSet partialDataSet = new QueryDataSet(connection);
 
// Mention all the tables here for which you want data to be extracted
// take note of the order to prevent FK constraint violation when re-inserting
partialDataSet.addTable("Hotel");
partialDataSet.addTable("Room");
partialDataSet.addTable("Booking");
partialDataSet.addTable("Guest");
partialDataSet.addTable("BillingLog");
 
// XML file into which data needs to be extracted
FlatXmlDataSet.write(partialDataSet, new FileOutputStream("/Users/kaosman/Desktop/DB/test-dataset_temp.xml"));
System.out.println("Dataset written");
}
}