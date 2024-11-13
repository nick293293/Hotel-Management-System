// This is our CancelReservationFrame where it allows the customer to cancel their existing reservation with the transaction ID

package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JTextField;

import controller.Controller;

import javax.swing.JTextArea;
import javax.swing.JButton;

public class CancelReservationFrame
{

	private JFrame frame;
	private JTextField transField;
	Controller controller;
	private JLabel lblNewLabel_1;

	// This is the construction for our CancelReservationFrame
	public CancelReservationFrame() {
		controller = new Controller();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 19));
		frame.setBounds(100, 100, 1027, 754);
		frame.setSize(1064, 677);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Enter Your Transaction ID:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel.setBounds(23, 105, 250, 43);
		frame.getContentPane().add(lblNewLabel);
		
		transField = new JTextField();
		transField.setFont(new Font("Tahoma", Font.PLAIN, 15));
		transField.setBounds(23, 183, 250, 35);
		frame.getContentPane().add(transField);
		transField.setColumns(10);
		
		JButton cancelBtn = new JButton("Cancel Reservation");
		cancelBtn.setFont(new Font("Tahoma", Font.PLAIN, 15));
		cancelBtn.setBounds(23, 235, 250, 43);
		frame.getContentPane().add(cancelBtn);
		
		lblNewLabel_1 = new JLabel("Note: Your transaction ID was emailed to you after you made the reservation.");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_1.setBounds(23, 145, 471, 16);
		frame.getContentPane().add(lblNewLabel_1);
		cancelBtn.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				int transId = Integer.parseInt(transField.getText());
				int rowsAffected = controller.cancelReservation(transId);
				if(rowsAffected == 0)
					JOptionPane.showMessageDialog(frame, "No reservation with that transaction ID was found", "Error", JOptionPane.ERROR_MESSAGE);
				else
					JOptionPane.showMessageDialog(frame, "The reservation was successfully canceled", "Message", JOptionPane.INFORMATION_MESSAGE);
			}
		});
	}
	
	// This sets our frame to be visible
	public void setVisible(boolean n)
	{
		frame.setVisible(n);
	}
}
