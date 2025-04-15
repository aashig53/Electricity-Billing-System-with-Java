package newelectricityBillingSystem;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class DeleteCustomer extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField emailField, nameField, addressField, contactField;

	public DeleteCustomer() {
		setTitle("Electricity Billing System ");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 640, 600);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 0, 132));
		contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel titleLabel = new JLabel("DELETE CUSTOMER");
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setForeground(Color.WHITE);
		titleLabel.setFont(new Font("Georgia", Font.BOLD, 40));
		titleLabel.setBounds(10, 10, 606, 60);
		contentPane.add(titleLabel);

		JLabel emailLabel = new JLabel("Email:");
		emailLabel.setForeground(Color.WHITE);
		emailLabel.setFont(new Font("SimSun", Font.BOLD, 20));
		emailLabel.setBounds(50, 90, 120, 30);
		contentPane.add(emailLabel);

		emailField = new JTextField();
		emailField.setFont(new Font("SimSun", Font.PLAIN, 18));
		emailField.setBounds(200, 90, 360, 30);
		contentPane.add(emailField);

		JButton btnSearch = new JButton("Search");
		btnSearch.setFont(new Font("SimSun", Font.BOLD, 18));
		btnSearch.setBackground(Color.WHITE);
		btnSearch.setBounds(200, 130, 120, 30);
		btnSearch.addActionListener(e -> searchCustomer());
		contentPane.add(btnSearch);

		JLabel nameLabel = new JLabel("Name:");
		nameLabel.setForeground(Color.WHITE);
		nameLabel.setFont(new Font("SimSun", Font.BOLD, 20));
		nameLabel.setBounds(50, 190, 120, 30);
		contentPane.add(nameLabel);

		nameField = new JTextField();
		nameField.setFont(new Font("SimSun", Font.PLAIN, 18));
		nameField.setEditable(false);
		nameField.setBounds(200, 190, 360, 30);
		contentPane.add(nameField);

		JLabel addressLabel = new JLabel("Address:");
		addressLabel.setForeground(Color.WHITE);
		addressLabel.setFont(new Font("SimSun", Font.BOLD, 20));
		addressLabel.setBounds(50, 240, 120, 30);
		contentPane.add(addressLabel);

		addressField = new JTextField();
		addressField.setFont(new Font("SimSun", Font.PLAIN, 18));
		addressField.setEditable(false);
		addressField.setBounds(200, 240, 360, 30);
		contentPane.add(addressField);

		JLabel contactLabel = new JLabel("Contact:");
		contactLabel.setForeground(Color.WHITE);
		contactLabel.setFont(new Font("SimSun", Font.BOLD, 20));
		contactLabel.setBounds(50, 290, 120, 30);
		contentPane.add(contactLabel);

		contactField = new JTextField();
		contactField.setFont(new Font("SimSun", Font.PLAIN, 18));
		contactField.setEditable(false);
		contactField.setBounds(200, 290, 360, 30);
		contentPane.add(contactField);

		JButton btnDelete = new JButton("DELETE CUSTOMER");
		btnDelete.setFont(new Font("SimSun", Font.BOLD, 22));
		btnDelete.setBackground(Color.WHITE);
		btnDelete.setBounds(180, 360, 280, 50);
		btnDelete.addActionListener(e -> deleteCustomer());
		contentPane.add(btnDelete);

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
		String email = emailField.getText().trim();

		if (email.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Enter email to search.");
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
			} else {
				JOptionPane.showMessageDialog(this, "Customer not found.");
				nameField.setText("");
				addressField.setText("");
				contactField.setText("");
			}
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
		}
	}

	private void deleteCustomer() {
	    String email = emailField.getText().trim();

	    if (email.isEmpty()) {
	        JOptionPane.showMessageDialog(this, "Enter email to delete.");
	        return;
	    }

	    int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this customer?", "Confirm", JOptionPane.YES_NO_OPTION);
	    if (confirm != JOptionPane.YES_OPTION) return;

	    try {
	        Class.forName("com.mysql.cj.jdbc.Driver");
	        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/electricity", "root", "5306");

	        conn.setAutoCommit(false);  // Start transaction

	        String getIdQuery = "SELECT customer_id FROM customers WHERE email=?";
	        PreparedStatement getIdStmt = conn.prepareStatement(getIdQuery);
	        getIdStmt.setString(1, email);
	        ResultSet rs = getIdStmt.executeQuery();

	        if (rs.next()) {
	            int customerId = rs.getInt("customer_id");

	            // 1. Get all bill_ids for this customer
	            PreparedStatement getBills = conn.prepareStatement("SELECT bill_id FROM bills WHERE customer_id=?");
	            getBills.setInt(1, customerId);
	            ResultSet billRs = getBills.executeQuery();

	            while (billRs.next()) {
	                int billId = billRs.getInt("bill_id");

	                // 2. Delete from payments where bill_id = ?
	                PreparedStatement deletePayments = conn.prepareStatement("DELETE FROM payments WHERE bill_id=?");
	                deletePayments.setInt(1, billId);
	                deletePayments.executeUpdate();
	            }

	            // 3. Delete from bills
	            PreparedStatement deleteBills = conn.prepareStatement("DELETE FROM bills WHERE customer_id=?");
	            deleteBills.setInt(1, customerId);
	            deleteBills.executeUpdate();

	            // 4. Delete from customers
	            PreparedStatement deleteCustomer = conn.prepareStatement("DELETE FROM customers WHERE customer_id=?");
	            deleteCustomer.setInt(1, customerId);
	            int deleted = deleteCustomer.executeUpdate();

	            if (deleted > 0) {
	                conn.commit();
	                JOptionPane.showMessageDialog(this, "Customer and related data deleted successfully.");
	                nameField.setText("");
	                addressField.setText("");
	                contactField.setText("");
	                emailField.setText("");
	            } else {
	                conn.rollback();
	                JOptionPane.showMessageDialog(this, "Customer deletion failed.");
	            }
	        } else {
	            JOptionPane.showMessageDialog(this, "Customer not found.");
	        }

	        conn.setAutoCommit(true);
	        conn.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
	    }
	}



}
