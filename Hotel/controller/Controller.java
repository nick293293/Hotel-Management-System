// This Contoller class is what connects our front end to our back end

package controller;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;

import model.Database;

public class Controller
{
	// creates a Database class object
	Database db = new Database();
	
	// This is our constructor for the Controller class
	public Controller()
	{
		try
		{
			connect();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	// calls the connect method in our Database class in order to get a connection to the database
	public void connect() throws Exception
	{
		db.connect();
	}

	// calls the disconnect method in our Database class in order to disconnect from the database
	public void disconnect()
	{
		db.disconnect();
	}
	
	// calls the addReservation method in our Database class in order to add a reservation to our database
	public int addReservation(int roomNumber, int customerId, Date checkIn, Date checkOut)
	{
		return db.addReservation(roomNumber, customerId, checkIn, checkOut);
	}
	
	// calls the cancelReservation method in our Database class in order to remove a reservation from our database
	public int cancelReservation(int transId)
	{
		return db.cancelReservation(transId);
	}
	
	// calls the search method in our Database class in order to perform a search for rooms
	public ResultSet search(int occupancy, String smoking, int numberOfBeds, int startPrice, int endPrice) throws SQLException
	{
		return db.search(occupancy, smoking, numberOfBeds, startPrice, endPrice);
	}
	
	// calls the getReservedDates method in our Database class in order to get the reserved dates for a specific room
	public ResultSet getReservedDates(int roomNumber) throws SQLException
	{
		return db.getReservedDates(roomNumber);
	}
	
	// calls the isCustomer method in our Database class to identify if a customer with the specific ID exists
	public boolean isCustomer(int customerId) throws SQLException
	{
		return db.isCustomer(customerId);
	}
	
	// calls the addCustomer method in our Database class in order to add a customer to our database
	public int addCustomer(String name, String email, long number) throws SQLException
	{
		return db.addCustomer(name, email, number);
	}
	
	// calls the getName method in our Database class in order to get the name of the customer
	public String getName(int customerId) throws SQLException
	{
		return db.getName(customerId);
	}
	
	// calls the getNumber method in our Database class in order to get the number of the customer
	public long getNumber(int customerId) throws SQLException
	{
		return db.getNumber(customerId);
	}
	
	// calls the getEmail method in our Database class in order to get the email of the customer
	public String getEmail(int customerId) throws SQLException
	{
		return db.getEmail(customerId);
	}
	
	// calls the getPrice method in our Database class in order to get the price of a room
	public int getPrice(int roomNumber) throws SQLException
	{
		return db.getPrice(roomNumber);
	}
	
	// calls the getNumberOfCustomers method in our Database class in order to get the number of registered customers
	public int getNumberOfCustomers() throws SQLException
	{
		return db.getNumberOfCustomers();
	}
}
