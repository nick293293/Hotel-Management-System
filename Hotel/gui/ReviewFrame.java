// This is our ReviewFrame where the customer is able to view everything they have entered in so far and submit their reservation

package gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.toedter.calendar.JDateChooser;

import controller.Controller;

public class ReviewFrame {

	private JFrame frame;
	private JTextField nameField;
	private JTextField numberField;
	private JTextField emailField;
	private JTextField cardField;
	private JTextField roomField;
	private int roomNumber;
	private int customerId;
	private Date checkIn;
	private Date checkOut;
	private Controller controller;
	private String cardNumber;
	private JTextField priceField;

	// This is our ReviewFrame constructor that takes in 5 parameters including the room number,
	// customer ID, check in date, check out date and card number
	public ReviewFrame(int roomNumber, int customerId, Date checkIn, Date checkOut, String cardNumber)
	{
		this.roomNumber = roomNumber;
		this.customerId = customerId;
		this.checkIn = checkIn;
		this.checkOut = checkOut;
		this.cardNumber = cardNumber;
		controller = new Controller();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1027, 754);
		frame.setSize(1064, 677);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Review");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 36));
		lblNewLabel.setBounds(428, 10, 233, 112);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Customer Name:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_1.setBounds(35, 145, 125, 35);
		frame.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Phone Number:");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_2.setBounds(35, 203, 132, 42);
		frame.getContentPane().add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Email:");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_3.setBounds(35, 265, 73, 42);
		frame.getContentPane().add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("Card Number:");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_4.setBounds(35, 337, 125, 25);
		frame.getContentPane().add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("Room Number:");
		lblNewLabel_5.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_5.setBounds(596, 145, 125, 25);
		frame.getContentPane().add(lblNewLabel_5);
		
		JLabel lblNewLabel_6 = new JLabel("Check In");
		lblNewLabel_6.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_6.setBounds(596, 212, 102, 25);
		frame.getContentPane().add(lblNewLabel_6);
		
		JLabel lblNewLabel_7 = new JLabel("Check Out");
		lblNewLabel_7.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_7.setBounds(596, 265, 102, 42);
		frame.getContentPane().add(lblNewLabel_7);
		
		JButton exitBtn = new JButton("Exit");
		exitBtn.setFont(new Font("Tahoma", Font.BOLD, 18));
		exitBtn.setBounds(35, 536, 300, 72);
		frame.getContentPane().add(exitBtn);
		exitBtn.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				System.exit(0);
			}
		});
		
		JButton editPaymentBtn = new JButton("Edit Payment");
		editPaymentBtn.setFont(new Font("Tahoma", Font.BOLD, 18));
		editPaymentBtn.setBounds(742, 356, 300, 72);
		frame.getContentPane().add(editPaymentBtn);
		editPaymentBtn.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				PaymentFrame paymentFrame = new PaymentFrame(roomNumber, customerId, checkIn, checkOut);
				paymentFrame.setVisible(true);
				frame.setVisible(false);
			}
		});
		
		JButton startOverBtn = new JButton("Start Over");
		startOverBtn.setFont(new Font("Tahoma", Font.BOLD, 18));
		startOverBtn.setBounds(399, 536, 300, 72);
		frame.getContentPane().add(startOverBtn);
		startOverBtn.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				MainFrame mainFrame = new MainFrame();
				mainFrame.setVisible(true);
				frame.setVisible(false);
			}
		});
		
		JButton submitBtn = new JButton("Submit");
		submitBtn.setFont(new Font("Tahoma", Font.BOLD, 18));
		submitBtn.setBounds(742, 536, 300, 72);
		frame.getContentPane().add(submitBtn);
		submitBtn.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				int transId = controller.addReservation(roomNumber, customerId, checkIn, checkOut);
				frame.setVisible(false);
				JOptionPane.showMessageDialog(frame, "Your reservation was successfully made! Check your email!", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
				sendEmail(transId);
			}
		});
		
		nameField = new JTextField();
		nameField.setBounds(193, 149, 283, 31);
		frame.getContentPane().add(nameField);
		nameField.setColumns(10);
		nameField.setEditable(false);
		try
		{
			nameField.setText(controller.getName(customerId));
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		numberField = new JTextField();
		numberField.setBounds(193, 211, 283, 31);
		frame.getContentPane().add(numberField);
		numberField.setColumns(10);
		numberField.setEditable(false);
		try
		{
			numberField.setText(String.valueOf(controller.getNumber(customerId)));
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		emailField = new JTextField();
		emailField.setBounds(193, 273, 283, 31);
		frame.getContentPane().add(emailField);
		emailField.setColumns(10);
		emailField.setEditable(false);
		try
		{
			emailField.setText(controller.getEmail(customerId));
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		cardField = new JTextField();
		cardField.setBounds(193, 336, 283, 31);
		frame.getContentPane().add(cardField);
		cardField.setColumns(10);
		cardField.setEditable(false);
		cardField.setText(cardNumber);
		
		roomField = new JTextField();
		roomField.setBounds(734, 149, 283, 31);
		frame.getContentPane().add(roomField);
		roomField.setColumns(10);
		roomField.setEditable(false);
		roomField.setText(String.valueOf(roomNumber));
		
		JDateChooser checkInDate = new JDateChooser();
		checkInDate.setBounds(734, 208, 147, 37);
		frame.getContentPane().add(checkInDate);
		checkInDate.setDate(checkIn);
		checkInDate.getDateEditor().setEnabled(false);
		checkInDate.setMinSelectableDate(checkIn);
		checkInDate.setMaxSelectableDate(checkIn);
		
		JDateChooser checkOutDate = new JDateChooser();
		checkOutDate.setBounds(734, 270, 147, 37);
		frame.getContentPane().add(checkOutDate);
		checkOutDate.setDate(checkOut);
		checkOutDate.getDateEditor().setEnabled(false);
		checkOutDate.setMinSelectableDate(checkOut);
		checkOutDate.setMaxSelectableDate(checkOut);
		
		JLabel lblNewLabel_4_1 = new JLabel("Total Price:");
		lblNewLabel_4_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_4_1.setBounds(35, 398, 125, 25);
		frame.getContentPane().add(lblNewLabel_4_1);
		
		priceField = new JTextField();
		priceField.setText("<dynamic>");
		priceField.setEditable(false);
		priceField.setColumns(10);
		priceField.setBounds(193, 397, 283, 31);
		frame.getContentPane().add(priceField);
		try {
			int price = controller.getPrice(roomNumber);
			int days = getNumberOfDays();
			int total = price * days;
			priceField.setText("$" + String.valueOf(total));
		}
		catch (SQLException e1)
		{
			e1.printStackTrace();
		}
	}
	
	// This method calculates the number of days that the customer wants to reserve the room for
	public int getNumberOfDays()
	{
		Date in = checkIn;
		Date out = checkOut;
		long millisecondsIn = in.getTime();
		long millisecondsOut = out.getTime();
		long secondsIn = millisecondsIn / 1000;
		long secondsOut = millisecondsOut / 1000;
		int resultIn = ((int)secondsIn) / (24 * 60 * 60);
		int resultOut = ((int)secondsOut) / (24 * 60 * 60);
		return (resultOut - resultIn) + 1;
	}
	
	// This method sets our frame to be visible
	public void setVisible(boolean n)
	{
		frame.setVisible(n);
	}
	
	// This method sends a confirmation email to the customer's email address
	public void sendEmail(int transId)
	{
		 String senderEmail = "karenartyom2016@gmail.com";
	     String password = "fnby upvs tpmd cbax";

	        // Recipient's email address
	        String recipientEmail = "";
			try {
				recipientEmail = controller.getEmail(customerId);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	        // Set up mail server properties
	        
	        Properties properties = new Properties();
	        properties.put("mail.smtp.auth", "true");
	        properties.put("mail.smtp.starttls.enable", "true");
	        properties.put("mail.smtp.host", "smtp.gmail.com"); // Replace with your SMTP server host
	        properties.put("mail.smtp.port", "587"); // Replace with your SMTP server port

	        // Create a session with an authenticator
	        Session session = Session.getInstance(properties, new Authenticator() {
	            @Override
	            protected PasswordAuthentication getPasswordAuthentication()
	            {
	                return new PasswordAuthentication(senderEmail, password);
	            }
	        });

	        try
	        {
	            // Create a message
	            Message message = new MimeMessage(session);
	            message.setFrom(new InternetAddress(senderEmail));
	            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmail));
	            message.setSubject("Reservation");
	            message.setText("Thanks for making a reservation with our hotel!"
	            		+ "\nYour Reservation was successfully made from " + checkIn + " to " + checkOut
	            		+ ".\nYour transaction ID is " + transId + "."
	            		+ "\nYour customer ID is " + customerId + "."
	            		+ "\nSave this transaction ID in case you need to cancel your reservation!");

	            // Send the message
	            Transport.send(message);

	            System.out.println("Email sent successfully!");

	        } catch (MessagingException e) {
	            e.printStackTrace();
	        }
	}
}
