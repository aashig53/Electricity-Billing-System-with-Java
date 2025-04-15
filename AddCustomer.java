package newelectricityBillingSystem;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class AddCustomer extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField nameField;
	private JTextField addressField;
	private JTextField contactField;
	private JTextField emailField;
	private JPasswordField passwordField;

	public AddCustomer() {
		setTitle("Electricity Billing System ");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 640, 550);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 0, 132));
		contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel titleLabel = new JLabel("ADD CUSTOMER");
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setForeground(Color.WHITE);
		titleLabel.setFont(new Font("Georgia", Font.BOLD, 40));
		titleLabel.setBounds(10, 10, 606, 60);
		contentPane.add(titleLabel);

		JLabel nameLabel = new JLabel("Name:");
		nameLabel.setForeground(Color.WHITE);
		nameLabel.setFont(new Font("SimSun", Font.BOLD, 20));
		nameLabel.setBounds(50, 90, 120, 30);
		contentPane.add(nameLabel);

		nameField = new JTextField();
		nameField.setFont(new Font("SimSun", Font.PLAIN, 18));
		nameField.setBounds(200, 90, 360, 30);
		contentPane.add(nameField);

		JLabel addressLabel = new JLabel("Address:");
		addressLabel.setForeground(Color.WHITE);
		addressLabel.setFont(new Font("SimSun", Font.BOLD, 20));
		addressLabel.setBounds(50, 140, 120, 30);
		contentPane.add(addressLabel);

		addressField = new JTextField();
		addressField.setFont(new Font("SimSun", Font.PLAIN, 18));
		addressField.setBounds(200, 140, 360, 30);
		contentPane.add(addressField);

		JLabel contactLabel = new JLabel("Contact:");
		contactLabel.setForeground(Color.WHITE);
		contactLabel.setFont(new Font("SimSun", Font.BOLD, 20));
		contactLabel.setBounds(50, 190, 120, 30);
		contentPane.add(contactLabel);

		contactField = new JTextField();
		contactField.setFont(new Font("SimSun", Font.PLAIN, 18));
		contactField.setBounds(200, 190, 360, 30);
		contentPane.add(contactField);

		JLabel emailLabel = new JLabel("Email:");
		emailLabel.setForeground(Color.WHITE);
		emailLabel.setFont(new Font("SimSun", Font.BOLD, 20));
		emailLabel.setBounds(50, 240, 120, 30);
		contentPane.add(emailLabel);

		emailField = new JTextField();
		emailField.setFont(new Font("SimSun", Font.PLAIN, 18));
		emailField.setBounds(200, 240, 360, 30);
		contentPane.add(emailField);

		JLabel passwordLabel = new JLabel("Password:");
		passwordLabel.setForeground(Color.WHITE);
		passwordLabel.setFont(new Font("SimSun", Font.BOLD, 20));
		passwordLabel.setBounds(50, 290, 120, 30);
		contentPane.add(passwordLabel);

		passwordField = new JPasswordField();
		passwordField.setFont(new Font("SimSun", Font.PLAIN, 18));
		passwordField.setBounds(200, 290, 360, 30);
		contentPane.add(passwordField);

		JButton btnAdd = new JButton("ADD CUSTOMER");
		btnAdd.setFont(new Font("SimSun", Font.BOLD, 25));
		btnAdd.setBackground(Color.WHITE);
		btnAdd.setBounds(180, 360, 270, 50);
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addCustomerToDatabase();
			}
		});
		contentPane.add(btnAdd);

		JButton btnBack = new JButton("BACK");
		btnBack.setFont(new Font("SimSun", Font.BOLD, 20));
		btnBack.setBackground(Color.WHITE);
		btnBack.setBounds(20, 430, 100, 40);
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ManageAccounts back = new ManageAccounts();
				back.setLocationRelativeTo(null);
				back.setVisible(true);
				dispose();
			}
		});
		contentPane.add(btnBack);
	}

	private void addCustomerToDatabase() {
		String name = nameField.getText();
		String address = addressField.getText();
		String contact = contactField.getText();
		String email = emailField.getText();
		String password = new String(passwordField.getPassword());

		if (name.isEmpty() || address.isEmpty() || contact.isEmpty() || email.isEmpty() || password.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Please fill all fields.");
			return;
		}

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/electricity", "root", "5306");

			String query = "INSERT INTO customers (name, address, contact_details, email, password_hash) VALUES (?, ?, ?, ?, ?)";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, name);
			stmt.setString(2, address);
			stmt.setString(3, contact);
			stmt.setString(4, email);
			stmt.setString(5, password); // saving password as plain text

			int rowsInserted = stmt.executeUpdate();

			if (rowsInserted > 0) {
				JOptionPane.showMessageDialog(this, "Customer added successfully.");
				clearFields();
			} else {
				JOptionPane.showMessageDialog(this, "Failed to add customer.");
			}

			conn.close();

		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
		}
	}

	private void clearFields() {
		nameField.setText("");
		addressField.setText("");
		contactField.setText("");
		emailField.setText("");
		passwordField.setText("");
	}
}
