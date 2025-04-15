package newelectricityBillingSystem;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class EditCustomer extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField emailSearchField;
	private JTextField nameField, addressField, contactField;
	private JPasswordField passwordField;

	public EditCustomer() {
		setTitle("Electricity Billing System ");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 640, 600);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 0, 132));
		contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel titleLabel = new JLabel("EDIT CUSTOMER");
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setForeground(Color.WHITE);
		titleLabel.setFont(new Font("Georgia", Font.BOLD, 40));
		titleLabel.setBounds(10, 10, 606, 60);
		contentPane.add(titleLabel);

		JLabel emailSearchLabel = new JLabel("Search by Email:");
		emailSearchLabel.setForeground(Color.WHITE);
		emailSearchLabel.setFont(new Font("SimSun", Font.BOLD, 20));
		emailSearchLabel.setBounds(50, 90, 180, 30);
		contentPane.add(emailSearchLabel);

		emailSearchField = new JTextField();
		emailSearchField.setFont(new Font("SimSun", Font.PLAIN, 18));
		emailSearchField.setBounds(230, 90, 280, 30);
		contentPane.add(emailSearchField);

		JButton btnSearch = new JButton("Search");
		btnSearch.setFont(new Font("SimSun", Font.BOLD, 18));
		btnSearch.setBackground(Color.WHITE);
		btnSearch.setBounds(230, 130, 120, 30);
		btnSearch.addActionListener(e -> searchCustomer());
		contentPane.add(btnSearch);

		JLabel nameLabel = new JLabel("Name:");
		nameLabel.setForeground(Color.WHITE);
		nameLabel.setFont(new Font("SimSun", Font.BOLD, 20));
		nameLabel.setBounds(50, 190, 120, 30);
		contentPane.add(nameLabel);

		nameField = new JTextField();
		nameField.setFont(new Font("SimSun", Font.PLAIN, 18));
		nameField.setBounds(200, 190, 360, 30);
		contentPane.add(nameField);

		JLabel addressLabel = new JLabel("Address:");
		addressLabel.setForeground(Color.WHITE);
		addressLabel.setFont(new Font("SimSun", Font.BOLD, 20));
		addressLabel.setBounds(50, 240, 120, 30);
		contentPane.add(addressLabel);

		addressField = new JTextField();
		addressField.setFont(new Font("SimSun", Font.PLAIN, 18));
		addressField.setBounds(200, 240, 360, 30);
		contentPane.add(addressField);

		JLabel contactLabel = new JLabel("Contact:");
		contactLabel.setForeground(Color.WHITE);
		contactLabel.setFont(new Font("SimSun", Font.BOLD, 20));
		contactLabel.setBounds(50, 290, 120, 30);
		contentPane.add(contactLabel);

		contactField = new JTextField();
		contactField.setFont(new Font("SimSun", Font.PLAIN, 18));
		contactField.setBounds(200, 290, 360, 30);
		contentPane.add(contactField);

		JLabel passwordLabel = new JLabel("Password:");
		passwordLabel.setForeground(Color.WHITE);
		passwordLabel.setFont(new Font("SimSun", Font.BOLD, 20));
		passwordLabel.setBounds(50, 340, 120, 30);
		contentPane.add(passwordLabel);

		passwordField = new JPasswordField();
		passwordField.setFont(new Font("SimSun", Font.PLAIN, 18));
		passwordField.setBounds(200, 340, 360, 30);
		contentPane.add(passwordField);

		JButton btnUpdate = new JButton("UPDATE CUSTOMER");
		btnUpdate.setFont(new Font("SimSun", Font.BOLD, 22));
		btnUpdate.setBackground(Color.WHITE);
		btnUpdate.setBounds(180, 410, 280, 50);
		btnUpdate.addActionListener(e -> updateCustomer());
		contentPane.add(btnUpdate);

		JButton btnBack = new JButton("BACK");
		btnBack.setFont(new Font("SimSun", Font.BOLD, 20));
		btnBack.setBackground(Color.WHITE);
		btnBack.setBounds(20, 480, 100, 40);
		btnBack.addActionListener(e -> {
			ManageAccounts back = new ManageAccounts();
			back.setLocationRelativeTo(null);
			back.setVisible(true);
			dispose();
		});
		contentPane.add(btnBack);
	}

	private void searchCustomer() {
		String email = emailSearchField.getText().trim();

		if (email.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Enter an email to search.");
			return;
		}

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/electricity", "root", "5306");

			String query = "SELECT * FROM customers WHERE email=?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, email);

			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				nameField.setText(rs.getString("name"));
				addressField.setText(rs.getString("address"));
				contactField.setText(rs.getString("contact_details"));
				passwordField.setText(""); // Do not fetch or display password
			} else {
				JOptionPane.showMessageDialog(this, "Customer not found.");
			}

			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
		}
	}

	private void updateCustomer() {
		String email = emailSearchField.getText().trim();
		String name = nameField.getText().trim();
		String address = addressField.getText().trim();
		String contact = contactField.getText().trim();
		String password = new String(passwordField.getPassword()).trim();

		if (email.isEmpty() || name.isEmpty() || address.isEmpty() || contact.isEmpty()) {
			JOptionPane.showMessageDialog(this, "All fields except password must be filled.");
			return;
		}

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/electricity", "root", "5306");

			String query;
			PreparedStatement stmt;

			if (!password.isEmpty()) {
				query = "UPDATE customers SET name=?, address=?, contact_details=?, password_hash=? WHERE email=?";
				stmt = conn.prepareStatement(query);
				stmt.setString(1, name);
				stmt.setString(2, address);
				stmt.setString(3, contact);
				stmt.setString(4, password); // plain password (not hashed)
				stmt.setString(5, email);
			} else {
				query = "UPDATE customers SET name=?, address=?, contact_details=? WHERE email=?";
				stmt = conn.prepareStatement(query);
				stmt.setString(1, name);
				stmt.setString(2, address);
				stmt.setString(3, contact);
				stmt.setString(4, email);
			}

			int updated = stmt.executeUpdate();

			if (updated > 0) {
				JOptionPane.showMessageDialog(this, "Customer updated successfully.");
				emailSearchField.setText("");
				nameField.setText("");
				addressField.setText("");
				contactField.setText("");
				passwordField.setText("");
			} else {
				JOptionPane.showMessageDialog(this, "Failed to update customer.");
				emailSearchField.setText("");
				nameField.setText("");
				addressField.setText("");
				contactField.setText("");
				passwordField.setText("");
			}

			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
		}
	}
}
