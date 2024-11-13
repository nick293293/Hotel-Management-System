// This is our ReportsFrame class that shows the reports for the manager

package gui;

import java.awt.EventQueue;
import java.awt.Font;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalDate;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import controller.Controller;

public class ReportsFrame {

	private JFrame frame;
	private JTextField customerField;
	Controller controller;
	private JLabel lblTheRoomOccupancy;
	private JTextField weekField;
	private JLabel lblTheRoomOccupancy_2;
	private JTextField monthField;

	// This is our constructor for our ReportsFrame class that takes in no parameters
	public ReportsFrame() {
		controller = new Controller();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(100, 100, 1027, 754);
		frame.setSize(1064, 677);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Currently there are about ");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel.setBounds(10, 129, 241, 28);
		frame.getContentPane().add(lblNewLabel);
		
		customerField = new JTextField();
		customerField.setFont(new Font("Tahoma", Font.PLAIN, 20));
		customerField.setBounds(243, 132, 27, 23);
		frame.getContentPane().add(customerField);
		customerField.setColumns(10);
		customerField.setEditable(false);
		try
		{
			customerField.setText(String.valueOf(controller.getNumberOfCustomers()));
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		JLabel lblCustomersRegisteredAt = new JLabel("customers registered at this hotel.");
		lblCustomersRegisteredAt.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblCustomersRegisteredAt.setBounds(279, 129, 309, 28);
		frame.getContentPane().add(lblCustomersRegisteredAt);
		
		lblTheRoomOccupancy = new JLabel("The room occupancy rate for upcoming week is ");
		lblTheRoomOccupancy.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblTheRoomOccupancy.setBounds(10, 182, 427, 28);
		frame.getContentPane().add(lblTheRoomOccupancy);
		
		weekField = new JTextField();
		weekField.setFont(new Font("Tahoma", Font.PLAIN, 20));
		weekField.setEditable(false);
		weekField.setColumns(10);
		weekField.setBounds(441, 185, 72, 23);
		frame.getContentPane().add(weekField);
		weekField.setEditable(false);
		double rate = generateWeeklyRate();
		DecimalFormat decimalFormat = new DecimalFormat("#.##");
		String rateString = decimalFormat.format(rate);
		weekField.setText(rateString + "%");
		
		lblTheRoomOccupancy_2 = new JLabel("The room occupancy rate for upcoming month is ");
		lblTheRoomOccupancy_2.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblTheRoomOccupancy_2.setBounds(10, 229, 445, 28);
		frame.getContentPane().add(lblTheRoomOccupancy_2);
		
		monthField = new JTextField();
		monthField.setText("0%");
		monthField.setFont(new Font("Tahoma", Font.PLAIN, 20));
		monthField.setEditable(false);
		monthField.setColumns(10);
		monthField.setBounds(451, 232, 72, 23);
		frame.getContentPane().add(monthField);
		monthField.setEditable(false);
		double rate2 = generateMonthlyRate();
		String rateString2 = decimalFormat.format(rate2);
		monthField.setText(rateString2 + "%");
	}

	// This method sets our frame to be visible
	public void setVisible(boolean n)
	{
		frame.setVisible(n);
	}
	
	// This method generates the monthly rate for the rooms
	public double generateMonthlyRate()
	{
		int[] rooms = new int[] {111, 112, 113, 114, 115,
								211, 212, 213, 214, 215,
								311, 312, 313, 314, 315};
		int count = 0;
		for(int i = 0; i < rooms.length; i++)
		{
			if(isReservedMonthly(rooms[i]))
				count++;
		}
		
		return (count / 15.0 * 100);
	}
	
	// This method checks to see if a room has been reserved within the month
	public boolean isReservedMonthly(int room)
	{
		try
		{
			ResultSet resultSet = controller.getReservedDates(room);
			while(resultSet.next())
			{
				LocalDate checkInDate = resultSet.getDate("check_in").toLocalDate();
				LocalDate checkOutDate = resultSet.getDate("check_out").toLocalDate();
				LocalDate today = LocalDate.now();
				LocalDate oneMonthFromNow = today.plusMonths(1);
				LocalDate date = checkInDate;
				while(!date.isAfter(checkOutDate))
				{
					if(date.equals(today) || date.equals(oneMonthFromNow)
							|| (date.isAfter(today) && date.isBefore(oneMonthFromNow)))
							return true;
					date = date.plusDays(1);
				}
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		return false;
	}
	
	// This method generates the weekly rate for the rooms
	public double generateWeeklyRate()
	{
		int[] rooms = new int[] {111, 112, 113, 114, 115,
								211, 212, 213, 214, 215,
								311, 312, 313, 314, 315};
		int count = 0;
		for(int i = 0; i < rooms.length; i++)
		{
			if(isReservedWeekly(rooms[i]))
				count++;
		}
		
		return (count / 15.0 * 100);
	}
	
	// This method checks to see if a room has been reserved within the week
	public boolean isReservedWeekly(int room)
	{
		try
		{
			ResultSet resultSet = controller.getReservedDates(room);
			while(resultSet.next())
			{
				LocalDate checkInDate = resultSet.getDate("check_in").toLocalDate();
				LocalDate checkOutDate = resultSet.getDate("check_out").toLocalDate();
				LocalDate today = LocalDate.now();
				LocalDate oneWeekFromNow = today.plusWeeks(1);
				LocalDate date = checkInDate;
				while(!date.isAfter(checkOutDate))
				{
					if(date.equals(today) || date.equals(oneWeekFromNow)
							|| (date.isAfter(today) && date.isBefore(oneWeekFromNow)))
							return true;
					date = date.plusDays(1);
				}
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		return false;
	}
}
