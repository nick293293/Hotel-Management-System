// This is our LoginFrame for the manager to login in order to view reports

package gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginFrame {

	private JFrame frame;
	private JTextField loginField;
	private JPasswordField passwordField;
	
	/**
	 * Create the application.
	 */
	public LoginFrame() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 693, 426);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1064, 677);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Login:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel.setBounds(40, 129, 65, 50);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblPassword.setBounds(10, 190, 118, 50);
		frame.getContentPane().add(lblPassword);
		
		loginField = new JTextField();
		loginField.setBounds(115, 143, 142, 31);
		frame.getContentPane().add(loginField);
		loginField.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setColumns(10);
		passwordField.setBounds(115, 198, 142, 31);
		frame.getContentPane().add(passwordField);
		
		JLabel lblNewLabel_1 = new JLabel("Note: Your login and password was provided to you at the time of employment.");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_1.setBounds(10, 282, 474, 16);
		frame.getContentPane().add(lblNewLabel_1);
		
		JButton loginBtn = new JButton("Login");
		loginBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				if(loginField.getText().equals("hotel") && passwordField.getText().equals("reservation"))
				{
					ReportsFrame reportsFrame = new ReportsFrame();
					reportsFrame.setVisible(true);
					frame.setVisible(false);
				}
				else
					JOptionPane.showMessageDialog(frame, "Incorrect Login or Password", "Error", JOptionPane.ERROR_MESSAGE);
			}
		});
		loginBtn.setFont(new Font("Tahoma", Font.PLAIN, 16));
		loginBtn.setBounds(136, 240, 100, 31);
		frame.getContentPane().add(loginBtn);
		
		JButton btnNewButton = new JButton("Show Password");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				char[] password = passwordField.getPassword();
				JOptionPane.showMessageDialog(frame, "Password: " + new String(password));
			}
		});
		btnNewButton.setBounds(272, 202, 134, 23);
		frame.getContentPane().add(btnNewButton);
		
		JButton goBackBtn = new JButton("Go Back");
		goBackBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainFrame mainFrame = new MainFrame();
				mainFrame.setVisible(true);
				frame.setVisible(false);
			}
		});
		goBackBtn.setFont(new Font("Tahoma", Font.PLAIN, 16));
		goBackBtn.setBounds(10, 544, 134, 60);
		frame.getContentPane().add(goBackBtn);
		
		JButton exitBtn = new JButton("Exit");
		exitBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		exitBtn.setFont(new Font("Tahoma", Font.PLAIN, 16));
		exitBtn.setBounds(172, 544, 134, 60);
		frame.getContentPane().add(exitBtn);
	}
	
	// This sets our frame to be visible
	public void setVisible(boolean n)
	{
		frame.setVisible(n);
	}
}
