package newelectricityBillingSystem;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ManageAccounts extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	public ManageAccounts() {
		setTitle("Electricity Billing System ");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 640, 501);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 0, 132));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblTitle = new JLabel("CUSTOMER MANAGEMENT");
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setForeground(Color.WHITE);
		lblTitle.setFont(new Font("Georgia", Font.BOLD, 40));
		lblTitle.setBounds(10, 20, 606, 60);
		contentPane.add(lblTitle);

		JButton btnAddCustomer = new JButton("ADD CUSTOMER");
		btnAddCustomer.setFont(new Font("SimSun", Font.BOLD, 35));
		btnAddCustomer.setBackground(Color.WHITE);
		btnAddCustomer.setBounds(64, 100, 501, 60);
		btnAddCustomer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Replace with actual AddCustomer page
				AddCustomer add = new AddCustomer();
				add.setLocationRelativeTo(null);
				add.setVisible(true);
				dispose();
			}
		});
		contentPane.add(btnAddCustomer);

		JButton btnEditCustomer = new JButton("EDIT CUSTOMER");
		btnEditCustomer.setFont(new Font("SimSun", Font.BOLD, 35));
		btnEditCustomer.setBackground(Color.WHITE);
		btnEditCustomer.setBounds(64, 170, 501, 60);
		btnEditCustomer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Replace with actual EditCustomer page
				EditCustomer edit = new EditCustomer();
				edit.setLocationRelativeTo(null);
				edit.setVisible(true);
				dispose();
			}
		});
		contentPane.add(btnEditCustomer);

		JButton btnDeleteCustomer = new JButton("DELETE CUSTOMER");
		btnDeleteCustomer.setFont(new Font("SimSun", Font.BOLD, 35));
		btnDeleteCustomer.setBackground(Color.WHITE);
		btnDeleteCustomer.setBounds(64, 240, 501, 60);
		btnDeleteCustomer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Replace with actual DeleteCustomer page
				DeleteCustomer delete = new DeleteCustomer();
				delete.setLocationRelativeTo(null);
				delete.setVisible(true);
				dispose();
			}
		});
		contentPane.add(btnDeleteCustomer);

		JButton btnViewCustomers = new JButton("VIEW ALL CUSTOMERS");
		btnViewCustomers.setFont(new Font("SimSun", Font.BOLD, 35));
		btnViewCustomers.setBackground(Color.WHITE);
		btnViewCustomers.setBounds(64, 310, 501, 60);
		btnViewCustomers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Replace with actual ViewCustomers page
				ViewCustomers view = new ViewCustomers();
				view.setLocationRelativeTo(null);
				view.setVisible(true);
				dispose();
			}
		});
		contentPane.add(btnViewCustomers);

		JButton btnBack = new JButton("MENU");
		btnBack.setFont(new Font("SimSun", Font.BOLD, 35));
		btnBack.setBackground(Color.WHITE);
		btnBack.setBounds(64, 380, 501, 50);
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdminMenu back = new AdminMenu();
				back.setLocationRelativeTo(null);
				back.setVisible(true);
				dispose();
			}
		});
		contentPane.add(btnBack);
	}
}
