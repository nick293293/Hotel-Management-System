// This is our PaymentFrame class where the customer enters their card information to make a payment

package gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.regex.Pattern;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import controller.Controller;

public class PaymentFrame {

	private JFrame frame;
	private JTextField textCardName;
	private JTextField textCardNumber;
	private JTextField textDate;
	private JTextField textCVC;
	private JTextField textZipCode;
	private Date checkIn;
	private Date checkOut;
	
    private int customerId;
    private int roomNumber;
	private Controller controller;
	
	// This is our constructor for our PaymentFrame that has the parameters room number, customer id, check in and check out date
	public PaymentFrame(int roomNumber, int customerId, Date checkIn, Date checkOut)
	{
		this.roomNumber = roomNumber;
		this.customerId = customerId;
		this.checkIn = checkIn;
		this.checkOut = checkOut;
		controller = new Controller();
		initialize();
	}
	
	// This method validates that the cardholder's name entered in is valid
	private boolean validateCardholderName(String name) {
        return Pattern.matches("[a-zA-Z]+", name);
    }
	
	// This method validates that the card number entered in is valid
	private boolean validateCardNumber(String cardNumber) {
        return Pattern.matches("\\d{16}", cardNumber);
    }
	
	// This method validates that the expiration date entered in is valid
	private boolean validateExpirationDate(String date) {
	    try {
	        // Split the input date into month and year
	        String[] dateParts = date.split("/");
	        if (dateParts.length != 2) {
	            return false; // Invalid format
	        }

	        int month = Integer.parseInt(dateParts[0]);
	        int year = Integer.parseInt(dateParts[1]);

	        // Ensure the month is between 1 and 12 and the year is a valid 4-digit number
	        if (month < 1 || month > 12 || year < 1000 || year > 9999) {
	            return false;
	        }

	        // Validate the date using a simple check (e.g., considering all dates valid)
	        return true;
	    } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
	        return false; // Exception occurred, invalid format
	    }
	}

	// This method validates that the cvv entered in is valid
	private boolean validateCVV(String cvv) {
        return Pattern.matches("\\d{3}", cvv);
    }
	
	// This method validates that the zip code entered in is valid
	private boolean validateZipCode(String zipCode) {
        return Pattern.matches("\\d{5}", zipCode);
    }
	
	// This method does the overall validation of the payment and displays a message if something is incorrect
	private boolean validatePayment() {
		String cardholderName = textCardName.getText();
		String cardNumber = textCardNumber.getText();
		String expirationDate = textDate.getText();
		String cvv = textCVC.getText();
		String zipCode = textZipCode.getText();
		
		if( !validateCardholderName(cardholderName)) {
			JOptionPane.showMessageDialog(frame, "Invalid Cardholder's name");
			return false;
		}
		
		if( !validateCardNumber(cardNumber)) {
			JOptionPane.showMessageDialog(frame, "Invalid Card Number");
			return false;
		}
		
		  if (!validateExpirationDate(expirationDate)) {
	            JOptionPane.showMessageDialog(frame, "Invalid Expiration Date");
	            return false;
	        }
		
		  if (!validateCVV(cvv)) {
	            JOptionPane.showMessageDialog(frame, "Invalid CVV/CVC");
	            return false;
	        }

	        if (!validateZipCode(zipCode)) {
	            JOptionPane.showMessageDialog(frame, "Invalid Zip Code");
	            return false;
	        }
	        return true;
		
	  
		
		
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		 
		frame = new JFrame();
		frame.setBounds(100, 100, 983, 668);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblCardName = new JLabel("Cardholder's Name");
		lblCardName.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblCardName.setBounds(20, 156, 166, 32);
		frame.getContentPane().add(lblCardName);
		
		textCardName = new JTextField();
		textCardName.setBounds(292, 159, 283, 31);
		frame.getContentPane().add(textCardName);
		textCardName.setColumns(10);
		
		JLabel lblCardNumber = new JLabel("Card Number");
		lblCardNumber.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblCardNumber.setBounds(20, 219, 141, 32);
		frame.getContentPane().add(lblCardNumber);
		
		textCardNumber = new JTextField();
		textCardNumber.setBounds(292, 222, 283, 31);
		frame.getContentPane().add(textCardNumber);
		textCardNumber.setColumns(10);
		
		JLabel lblCardType = new JLabel("Card Type");
		lblCardType.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblCardType.setBounds(20, 63, 103, 32);
		frame.getContentPane().add(lblCardType);
		
		JComboBox comboBoxCards = new JComboBox();
		comboBoxCards.setFont(new Font("Tahoma", Font.PLAIN, 16));
		comboBoxCards.setModel(new DefaultComboBoxModel(new String[] {"Visa", "Master Card"}));
		comboBoxCards.setBounds(196, 55, 103, 48);
		frame.getContentPane().add(comboBoxCards);

		JLabel lblDate = new JLabel("Expiration Date (MM/YYYY)");
		lblDate.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblDate.setBounds(20, 288, 194, 32);
		frame.getContentPane().add(lblDate);
		
		textDate = new JTextField();
		textDate.setBounds(292, 291, 283, 31);
		frame.getContentPane().add(textDate);
		textDate.setColumns(10);
		
		JLabel lblCVC = new JLabel("CVV/CVC");
		lblCVC.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblCVC.setBounds(20, 368, 84, 31);
		frame.getContentPane().add(lblCVC);
		
		textCVC = new JTextField();
		textCVC.setBounds(292, 368, 283, 31);
		frame.getContentPane().add(textCVC);
		textCVC.setColumns(10);
		
		JLabel lblZipCode = new JLabel("Billing Zip Code");
		lblZipCode.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblZipCode.setBounds(20, 432, 124, 48);
		frame.getContentPane().add(lblZipCode);
		
		textZipCode = new JTextField();
		textZipCode.setBounds(292, 443, 283, 31);
		frame.getContentPane().add(textZipCode);
		textZipCode.setColumns(10);
		
		JButton cancelBtn = new JButton("Cancel");
		cancelBtn.setFont(new Font("Tahoma", Font.BOLD, 18));
		cancelBtn.setBounds(83, 545, 300, 72);
		frame.getContentPane().add(cancelBtn);
		cancelBtn.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				MainFrame main = new MainFrame();
				main.setVisible(true);
				frame.setVisible(false);
			}
		});
		
		JButton btnProceedPayment = new JButton("Proceed to Checkout");
		btnProceedPayment.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnProceedPayment.setBounds(565, 545, 300, 72);
		frame.getContentPane().add(btnProceedPayment);
		btnProceedPayment.addActionListener(e->{
			if (validatePayment())
			{
				ReviewFrame reviewFrame = new ReviewFrame(roomNumber, customerId, checkIn, checkOut, textCardNumber.getText());
				reviewFrame.setVisible(true);
				frame.setVisible(false);
			}
	});
	
	
	
	
	}

	// This method sets our frame to be visible
	 public void setVisible(boolean n)
	 {
		frame.setVisible(n);
	 }
}
