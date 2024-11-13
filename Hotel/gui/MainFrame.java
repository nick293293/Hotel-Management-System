// This is our MainFrame where we can filter and search for a room and also where we run our main code

package gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import controller.Controller;

public class MainFrame
{

	private JFrame frame;
	private JTable displayTable;
    private DefaultTableModel model;
    Controller controller;
    
    // This contains the main method for our class
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try
				{
					MainFrame window = new MainFrame();
					window.frame.setVisible(true);
				}
				catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainFrame() {
		controller = new Controller();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setAlwaysOnTop(true);
		frame.setBounds(100, 100, 1027, 754);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(true);
		frame.getContentPane().setLayout(null);
		frame.setSize(1064, 677);
		
		JLabel lblMain = new JLabel("Hotel Reservation System");
		lblMain.setBounds(288, 22, 489, 77);
		lblMain.setBackground(new Color(102, 51, 102));
		lblMain.setVerticalAlignment(SwingConstants.TOP);
		lblMain.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 36));
		frame.getContentPane().add(lblMain);
		
		JLabel smokingLabel = new JLabel("Smoking");
		smokingLabel.setBounds(44, 171, 129, 67);
		smokingLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		frame.getContentPane().add(smokingLabel);
		
		JComboBox smoking = new JComboBox();
		smoking.setBounds(170, 178, 178, 57);
		smoking.setModel(new DefaultComboBoxModel(new String[] {"Forbidden", "Allowed"}));
		frame.getContentPane().add(smoking);
		
		JLabel label_2 = new JLabel("");
		label_2.setBounds(940, 120, 470, 120);
		frame.getContentPane().add(label_2);
		
		JLabel numberOfBedsLabel = new JLabel("Number of beds");
		numberOfBedsLabel.setBounds(44, 241, 122, 67);
		numberOfBedsLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		frame.getContentPane().add(numberOfBedsLabel);
		
		JComboBox numberOfBeds = new JComboBox();
		numberOfBeds.setBounds(170, 246, 178, 57);
		numberOfBeds.setFont(new Font("Tahoma", Font.PLAIN, 16));
		numberOfBeds.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3"}));
		frame.getContentPane().add(numberOfBeds);
		
		JLabel label_3 = new JLabel("");
		label_3.setBounds(940, 240, 470, 120);
		frame.getContentPane().add(label_3);
		
		JLabel label_4 = new JLabel("");
		label_4.setBounds(940, 360, 470, 120);
		frame.getContentPane().add(label_4);
		
		JLabel label_5 = new JLabel("");
		label_5.setBounds(940, 480, 470, 120);
		frame.getContentPane().add(label_5);
		
		JComboBox occupancy = new JComboBox();
		occupancy.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4"}));
		occupancy.setBounds(170, 110, 178, 57);
		frame.getContentPane().add(occupancy);
		
		JComboBox startPrice = new JComboBox();
		startPrice.setModel(new DefaultComboBoxModel(new String[] {"25", "50", "75", "100"}));
		startPrice.setFont(new Font("Tahoma", Font.PLAIN, 16));
		startPrice.setBounds(170, 314, 178, 57);
		frame.getContentPane().add(startPrice);
		
		JComboBox endPrice = new JComboBox();
		endPrice.setModel(new DefaultComboBoxModel(new String[] {"100", "125", "150", "175", "200"}));
		endPrice.setFont(new Font("Tahoma", Font.PLAIN, 16));
		endPrice.setBounds(170, 382, 178, 57);
		frame.getContentPane().add(endPrice);
		
		model = new DefaultTableModel();
		model.addColumn("Room Number");
		model.addColumn("Occupancy");
		model.addColumn("Smoking");
		model.addColumn("Number of Beds");
		model.addColumn("Price");
		displayTable = new JTable(model);
	
		displayTable = new JTable(model);
		JScrollPane scrollPane = new JScrollPane(displayTable);
		scrollPane.setBounds(439, 110, 580, 369);
		frame.getContentPane().add(scrollPane);
		
		JButton searchBtn = new JButton("Search");
		searchBtn.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				int occ = Integer.parseInt(occupancy.getSelectedItem().toString());
				int bed = Integer.parseInt(numberOfBeds.getSelectedItem().toString());
				int start = Integer.parseInt(startPrice.getSelectedItem().toString());
				int end = Integer.parseInt(endPrice.getSelectedItem().toString());
				String smoke = smoking.getSelectedItem().toString();
				
				model.setRowCount(0);
				
				try
				{
					ResultSet result = controller.search(occ, smoke, bed, start, end);
					while(result.next())
					{
						model.addRow(new Object[]
								{
										result.getInt("room_number"),
										result.getInt("occupancy"),
										result.getString("smoking"),
										result.getInt("beds"),
										result.getInt("price")
								});
					}

					model.fireTableDataChanged();
				} 
				catch (SQLException e1)
				{
					e1.printStackTrace();
				}
			}
		});
		searchBtn.setBounds(439, 497, 277, 67);
		searchBtn.setFont(new Font("Tahoma", Font.BOLD, 18));
		frame.getContentPane().add(searchBtn);
		
		JLabel occupancyLabel = new JLabel("Occupancy");
		occupancyLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		occupancyLabel.setBounds(44, 120, 104, 40);
		frame.getContentPane().add(occupancyLabel);
		
		JButton bookBtn = new JButton("Book");
		bookBtn.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				int selectedRow = displayTable.getSelectedRow();
				if(selectedRow != -1)
				{
					int roomNumber = (int)model.getValueAt(selectedRow, 0);
					BookFrame bookFrame = new BookFrame(roomNumber);
					bookFrame.setVisible(true);
					frame.setVisible(false);
				}
				else
				{
					JOptionPane.showMessageDialog(frame, "No room was selected.", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		bookBtn.setFont(new Font("Tahoma", Font.BOLD, 18));
		bookBtn.setBounds(753, 497, 277, 67);
		frame.getContentPane().add(bookBtn);
		
		JButton cancelResBtn = new JButton("Cancel Existing Reservation");
		cancelResBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				CancelReservationFrame cancelReservationFrame = new CancelReservationFrame();
				cancelReservationFrame.setVisible(true);
				frame.setVisible(false);
			}
		});
		cancelResBtn.setFont(new Font("Tahoma", Font.BOLD, 18));
		cancelResBtn.setBounds(546, 575, 384, 67);
		frame.getContentPane().add(cancelResBtn);
		
		JLabel startPriceLabel = new JLabel("Starting Price    $");
		startPriceLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		startPriceLabel.setBounds(44, 309, 129, 67);
		frame.getContentPane().add(startPriceLabel);
		
		JLabel endPriceLabel = new JLabel("Ending Price     $");
		endPriceLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		endPriceLabel.setBounds(44, 377, 122, 67);
		frame.getContentPane().add(endPriceLabel);
		
		JButton managerBtn = new JButton("Manager Log in");
		managerBtn.setFont(new Font("Tahoma", Font.PLAIN, 15));
		managerBtn.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				LoginFrame loginFrame = new LoginFrame();
				loginFrame.setVisible(true);
				frame.setVisible(false);
			}
		});
		managerBtn.setBounds(10, 591, 138, 40);
		frame.getContentPane().add(managerBtn);
	}
	
	// This sets our frame to be visible
	public void setVisible(boolean n)
	{
		frame.setVisible(n);
	}
}
