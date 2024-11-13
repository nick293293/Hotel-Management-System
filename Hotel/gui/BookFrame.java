// This is our BookFrame class where the customer chooses the check in and checkOut date and also creates a new account if they are not an existing customer

package gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.sql.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;

import com.toedter.calendar.JDateChooser;

import controller.Controller;

public class BookFrame extends JFrame
{

	private JFrame frame;
	private int roomNumber;
	private JTextField textCustID;
	private JTextField textCustName;
	private JTextField textCustNumber;
	private JTextField textEmail;
	private Controller controller;
	
	// This is our BookFrame constructor that takes in one paramater of type int
	public BookFrame(int roomNumber)
	{
		this.roomNumber = roomNumber;
		controller = new Controller();
		try
		{
			initialize();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	// This method validates if the phone number entered in is in correct format
	public static boolean validatePhoneNumber(String phoneNumber) {
        // Define the regex pattern for a valid phone number
        String regex = "^(\\+\\d{1,3}[- ]?)?\\d{10}$";

        // Compile the pattern
        Pattern pattern = Pattern.compile(regex);

        // Match the provided phone number against the pattern
        Matcher matcher = pattern.matcher(phoneNumber);

        // Return true if the phone number matches the pattern, false otherwise
        return matcher.matches();
    }
	
	// This method validates that the customer name entered in is in correct format
	private boolean validateCustName(String name) {
        return Pattern.matches("[a-zA-Z]+", name);
    }
	
	// This method validates that the email entered in is in correct format
	 public static boolean validateEmailAddress(String email) {
	        // Define the regex pattern for a valid email address
	        String regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

	        // Compile the pattern
	        Pattern pattern = Pattern.compile(regex);

	        // Match the provided email against the pattern
	        Matcher matcher = pattern.matcher(email);

	        // Return true if the email matches the pattern, false otherwise
	        return matcher.matches();
	    }
	 
	 // This method validates that the id number entered in is in correct format
	 public static boolean isAllDigits(String idNumber) {
	        // Check each character in the ID number
	        for (char c : idNumber.toCharArray()) {
	            if (!Character.isDigit(c)) {
	                return false; // If any character is not a digit, return false
	            }
	        }
	        return true; // All characters are digits
	    }
	 
	 // This method gives the overall validation in case of the registration of a new customer
	 private boolean validateInfo() {
		 String name = textCustName.getText();
		 String phoneNumber = textCustNumber.getText();
		 String email = textEmail.getText();
		 
		 if (!validateCustName(name)) {
			 JOptionPane.showMessageDialog(frame, "Invalid customer name");
				return false;
			 
		 }
		 
		 if (!validatePhoneNumber(phoneNumber)) {
			 JOptionPane.showMessageDialog(frame, "Invalid phone number");
				return false;
			 
		 }
		 
		 if (!validateEmailAddress(email)) {
			 JOptionPane.showMessageDialog(frame, "Invalid email address");
				return false;
			 
		 }
		 
		 return true;
	 }

	 /**
		 * Initialize the contents of the frame.
		 */
	private void initialize() throws SQLException {
		frame = new JFrame();
		frame.setBounds(100, 100, 693, 426);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setSize(1064, 677);
		
		JLabel lblNewLabel = new JLabel("Check In");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel.setBounds(10, 32, 104, 40);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Check Out");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_1.setBounds(10, 95, 90, 40);
		frame.getContentPane().add(lblNewLabel_1);
		
		JDateChooser checkIn = new JDateChooser();
		//checkIn = disableReservedDates(checkIn);
		checkIn.getDateEditor().getUiComponent().setEnabled(false);
		checkIn.setBounds(107, 41, 104, 31);
		checkIn.setMinSelectableDate(new java.util.Date());
		frame.getContentPane().add(checkIn);
		
		JDateChooser checkOut = new JDateChooser();
		checkOut.getDateEditor().getUiComponent().setEnabled(false);
		checkOut.setBounds(107, 96, 104, 28);
		frame.getContentPane().add(checkOut);
		checkOut.setEnabled(false);
		
		JCheckBox chckbxNewCheckBox = new JCheckBox("  Are you an existing customer?");
		chckbxNewCheckBox.setFont(new Font("Tahoma", Font.BOLD, 16));
		chckbxNewCheckBox.setBounds(337, 131, 356, 56);
		frame.getContentPane().add(chckbxNewCheckBox);
		
		JLabel lblCustID = new JLabel("Please enter your ID");
		lblCustID.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblCustID.setBounds(10, 274, 162, 36);
		frame.getContentPane().add(lblCustID);
		lblCustID.setVisible(false);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(128, 251, 614, 13);
		frame.getContentPane().add(separator);
		
		JLabel lblCustName = new JLabel("Enter your name");
		lblCustName.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblCustName.setBounds(10, 334, 135, 44);
		frame.getContentPane().add(lblCustName);
		
		JLabel lblCustNumber = new JLabel("Enter your phone number ");
		lblCustNumber.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblCustNumber.setBounds(11, 403, 187, 36);
		frame.getContentPane().add(lblCustNumber);
		
		JLabel lblCustEmail = new JLabel("Enter Your Email");
		lblCustEmail.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblCustEmail.setBounds(10, 462, 148, 36);
		frame.getContentPane().add(lblCustEmail);
		
	
		JButton paymentBtn = new JButton("Payment");
		paymentBtn.setFont(new Font("Tahoma", Font.BOLD, 18));
		paymentBtn.setBounds(311, 563, 234, 56);
		frame.getContentPane().add(paymentBtn);

		paymentBtn.addActionListener(new ActionListener()
		{
		    public void actionPerformed(ActionEvent e)
		    {
		        if (chckbxNewCheckBox.isSelected())
		        {
		            int customerId =  Integer.parseInt(textCustID.getText());
		            try
		            {
						if(controller.isCustomer(customerId))
						{
							 if(checkIn.getDate() == null || checkOut.getDate() == null)
					        		JOptionPane.showMessageDialog(frame, "Check in or check out date not selected", "Error", JOptionPane.ERROR_MESSAGE);
					         else
					         {
					        	 java.sql.Date javaCheckIn = (java.sql.Date) convertUtilDateToSqlDate(checkIn.getDate());
					        	 java.sql.Date javaCheckOut = (java.sql.Date) convertUtilDateToSqlDate(checkOut.getDate());
						         PaymentFrame paymentFrame = new PaymentFrame(roomNumber, customerId, javaCheckIn, javaCheckOut);
						         paymentFrame.setVisible(true);
						         frame.setVisible(false);
					         }
						}
						else
							JOptionPane.showMessageDialog(frame, "A customer with that ID does not exist", "Error", JOptionPane.ERROR_MESSAGE);
					}
		            catch (SQLException e1)
		            {
						e1.printStackTrace();
					}
		        }
		        else
		        {
		            if (validateInfo())
		            {
		            String name = textCustName.getText();
		            long phoneNumber = Long.parseLong(textCustNumber.getText());
		            String email = textEmail.getText();
		            
		            int id = 0;
		            
		            try
		            {
						id = controller.addCustomer(name, email, phoneNumber);
					}
		            catch (SQLException e1)
		            {
						e1.printStackTrace();
					}
		            
		            if(checkIn.getDate() == null || checkOut.getDate() == null)
		        		JOptionPane.showMessageDialog(frame, "Check in or check out date not selected", "Error", JOptionPane.ERROR_MESSAGE);
		            else if(id != 0)
		            {
		            	java.sql.Date javaCheckIn = (java.sql.Date) convertUtilDateToSqlDate(checkIn.getDate());
						java.sql.Date javaCheckOut = (java.sql.Date) convertUtilDateToSqlDate(checkOut.getDate());
			            PaymentFrame paymentFrame = new PaymentFrame(roomNumber, id, javaCheckIn, javaCheckOut);
			            paymentFrame.setVisible(true);
			            frame.setVisible(false);
		            }
		            }
		        }
		      
		        
		    }
		});

		
		JButton cancelBtn = new JButton("Cancel");
		cancelBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				MainFrame main = new MainFrame();
				main.setVisible(true);
				frame.setVisible(false);
			}
		});
		cancelBtn.setFont(new Font("Tahoma", Font.BOLD, 18));
		cancelBtn.setBounds(35, 563, 234, 56);
		frame.getContentPane().add(cancelBtn);
		
		textCustID = new JTextField();
		textCustID.setBounds(224, 279, 283, 31);
		frame.getContentPane().add(textCustID);
		textCustID.setColumns(10);
		textCustID.setVisible(false);
		
		textCustName = new JTextField();
		textCustName.setBounds(224, 343, 283, 31);
		frame.getContentPane().add(textCustName);
		textCustName.setColumns(10);
		
		textCustNumber = new JTextField();
		textCustNumber.setBounds(224, 408, 283, 31);
		frame.getContentPane().add(textCustNumber);
		textCustNumber.setColumns(10);
		
		textEmail = new JTextField();
		textEmail.setBounds(224, 467, 283, 31);
		frame.getContentPane().add(textEmail);
		textEmail.setColumns(10);
		
		chckbxNewCheckBox.addItemListener(new ItemListener(){
			
			public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                	lblCustID.setVisible(true);
                	textCustID.setVisible(true);
                	lblCustName.setVisible(false);
                	textCustName.setVisible(false);
                	lblCustNumber.setVisible(false);
                	textCustNumber.setVisible(false);
                	lblCustEmail.setVisible(false);
                	textEmail.setVisible(false);
                	
                } else {
                	lblCustID.setVisible(false);
                	textCustID.setVisible(false);
                	lblCustName.setVisible(true);
                	textCustName.setVisible(true);
                	lblCustNumber.setVisible(true);
                	textCustNumber.setVisible(true);
                	lblCustEmail.setVisible(true);
                	textEmail.setVisible(true);
                	
                }
			} 
			});
		
		   try {
		        getReservedDates(checkIn, checkOut);
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		
		
		
	}
	
	// This method gets the Reserved dates and validates that the selected check in date is not reserved
	private void getReservedDates(JDateChooser checkIn, JDateChooser checkOut) throws SQLException
	{
	    ResultSet resultSet = controller.getReservedDates(roomNumber);

	    List<LocalDate> restrictedDates = new ArrayList<>();

	    while (resultSet.next()) {
	        LocalDate checkInDate = resultSet.getDate("check_in").toLocalDate();
	        LocalDate checkOutDate = resultSet.getDate("check_out").toLocalDate();

	        LocalDate date = checkInDate;
	        while (!date.isAfter(checkOutDate)) {
	            restrictedDates.add(date);
	            date = date.plusDays(1);
	        }
	    }
	    
	    checkIn.getDateEditor().addPropertyChangeListener("date", evt ->
	    {
	    	java.util.Date d1 = checkIn.getDate();
	        LocalDate selectedDate = null;
	        if(d1 != null)
	        {
	        	selectedDate = checkIn.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	        }
	        if (restrictedDates.contains(selectedDate)) {
	            // Show a warning or prevent further processing
	            JOptionPane.showMessageDialog(null, "This date is not available for selection!");
	            checkIn.setDate(null); // Clear the selected date
	            checkOut.setEnabled(false);
	        }
	        else if(!restrictedDates.contains(selectedDate) && checkIn.getDate() != null)
	        {
	        	checkOut.setEnabled(true);
	        	checkOut.setMinSelectableDate(checkIn.getDate());
	        }
	        
	        if(checkIn.getDate() != null)
	        {
		        LocalDate d = checkIn.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		        int count = 1;
		        while(count < 5 && !restrictedDates.contains(d))
		        {
		        	d = d.plusDays(1);
		        	count++;
		        }
		        d = d.minusDays(1);
		        checkOut.setMaxSelectableDate(Date.from(d.atStartOfDay(ZoneId.systemDefault()).toInstant()));
		   }
	    });
	}

	// This method sets our frame to be visible
    public void setVisible(boolean n)
    {
    		frame.setVisible(n);
    }
    
    // This method converts our java Date object to SQL Date object
    private static Date convertUtilDateToSqlDate(java.util.Date utilDate)
	{
        // Get the time in milliseconds from the java.util.Date
        long timeInMillis = utilDate.getTime();

        // Create a java.sql.Date using the time in milliseconds
        return new Date(timeInMillis);
    }
}
