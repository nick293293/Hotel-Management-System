package model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class Database
{
	// creates a connection object
	private Connection con;
	
	// gets a connection to the database
	public void connect() throws Exception
	{
		if(con != null) return;
		
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
		}
		catch (ClassNotFoundException e)
		{
			throw new Exception("Driver not found");
		}
		
		String url = "jdbc:mysql://localhost:3306/hotelreservation";
		
		con = DriverManager.getConnection(url, "root", "Andranik90");
		
		System.out.println("Connected: " + con);
	}
	
	// disconnects from database
	public void disconnect()
	{
		if(con != null)
			try
			{
				con.close();
			}
			catch (SQLException e)
			{
				System.out.println("Can't close connection");
			}
	}

	// adds a reservation to the database
	public int addReservation(int roomNumber, int customerId, Date checkIn, Date checkOut)
	{
		int transactionId = generateId();
		try
		 {
			String query = "INSERT INTO reservation (room_number, customer_id, check_in, check_out, transaction_id) VALUES(?, ?, ?, ?, ?)";
			PreparedStatement stat = con.prepareStatement(query);
			stat.setInt(1, roomNumber);
			stat.setInt(2,  customerId);
			stat.setDate(3, checkIn);
			stat.setDate(4, checkOut);
			stat.setInt(5, transactionId);
			
			stat.executeUpdate();

	        return transactionId;
		 }
		 catch (SQLException e)
		 {
			 System.err.println("Error: " + e.getMessage());
			 return 0;
		 }  
	}
	
	// removes a reservation from the database
	public int cancelReservation(int transId)
	{
		 try
		 {
			PreparedStatement stat = con.prepareStatement("DELETE FROM reservation WHERE transaction_id = ?");
			stat.setInt(1, transId);
			
			int rowsAffected = stat.executeUpdate();

	        return rowsAffected;
		 }
		 catch (SQLException e)
		 {
			 System.err.println("Error: " + e.getMessage());
			 return 0;
		 }  
	}
	
	// adds a customer to the database
	public int addCustomer(String name, String email, long number) throws SQLException
	{
		String query = "INSERT INTO customer (name, email, number) VALUES (?, ?, ?)";
		PreparedStatement stat = con.prepareStatement(query);
		stat.setString(1, name);
		stat.setString(2, email);
		stat.setLong(3, number);
		
		stat.executeUpdate();
		
		String query2 = "SELECT * FROM customer where name = ? AND email = ? AND number = ?";
		PreparedStatement stat2 = con.prepareStatement(query2);
		stat2.setString(1, name);
		stat2.setString(2, email);
		stat2.setLong(3, number);
		
		ResultSet resultSet = stat2.executeQuery();
		
		int id = 0;
		while(resultSet.next())
		{
			id = resultSet.getInt("customer_id");
		}
		
		return id;
	}
	
	// checks through the customer ID to see if the customer exists
	public boolean isCustomer(int customerId) throws SQLException
	{
		String query = "SELECT * FROM customer where customer_id = ?";
		PreparedStatement stat = con.prepareStatement(query);
		stat.setInt(1, customerId);
		
		ResultSet resultSet = stat.executeQuery();
		if (resultSet.next())
		{
            return true;
        } else
        {
            return false;
        }
	}
	
	// performs the search through the database to look for rooms with specific filters
	public ResultSet search(int occupancy, String smoking, int numberOfBeds, int startPrice, int endPrice) throws SQLException
	{
		String query = "SELECT * FROM rooms WHERE occupancy = ? AND smoking = ? AND beds = ? AND price >= ? AND price <= ?";
		
		PreparedStatement stat = con.prepareStatement(query);
		stat.setInt(1, occupancy);
		stat.setString(2, smoking);
		stat.setInt(3, numberOfBeds);
		stat.setInt(4, startPrice);
		stat.setInt(5, endPrice);
		
		ResultSet resultSet = stat.executeQuery();
		return resultSet;
	}
	
	// generates a random ID
	public int generateId()
	{
		UUID uuid = UUID.randomUUID();
		return Math.abs(uuid.toString().hashCode());
	}
	
	// gets the reservation dates for a specific room
	public ResultSet getReservedDates(int roomNumber) throws SQLException
	{
		String query = "SELECT check_in, check_out FROM reservation WHERE room_number = ?";
		PreparedStatement stat = con.prepareStatement(query);
		stat.setInt(1, roomNumber);
		return stat.executeQuery();
	}
	
	// gets the name of the customer
	public String getName(int customerId) throws SQLException
	{
		String query = "SELECT name FROM customer WHERE customer_id = ?";
		PreparedStatement stat = con.prepareStatement(query);
		stat.setInt(1, customerId);
		ResultSet result = stat.executeQuery();
		result.next();
		return result.getString("name");
	}
	
	// gets the number of the customer
	public long getNumber(int customerId) throws SQLException
	{
		String query = "SELECT number FROM customer WHERE customer_id = ?";
		PreparedStatement stat = con.prepareStatement(query);
		stat.setInt(1, customerId);
		ResultSet result = stat.executeQuery();
		result.next();
		return result.getLong("number");
	}
	
	// gets the email of the customer
	public String getEmail(int customerId) throws SQLException
	{
		String query = "SELECT email FROM customer WHERE customer_id = ?";
		PreparedStatement stat = con.prepareStatement(query);
		stat.setInt(1, customerId);
		ResultSet result = stat.executeQuery();
		result.next();
		return result.getString("email");
	}
	
	// gets the price of a room
	public int getPrice(int roomNumber) throws SQLException
	{
		String query = "SELECT price FROM rooms WHERE room_number = ?";
		PreparedStatement stat = con.prepareStatement(query);
		stat.setInt(1, roomNumber);
		ResultSet result = stat.executeQuery();
		result.next();
		return result.getInt("price");
	}
	
	// calculates the number of customers
	public int getNumberOfCustomers() throws SQLException
	{
		String query = "SELECT * FROM customer";
		PreparedStatement stat = con.prepareStatement(query);
		ResultSet result = stat.executeQuery();
		int count = 0;
		while(result.next())
		{
			count++;
		}
		return count;
	}
}