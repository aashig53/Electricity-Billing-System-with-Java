package newelectricityBillingSystem;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.sql.*;
import java.awt.event.*;
import java.util.HashMap;

public class BillManagement extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtUnitsUsed, txtPricePerUnit, txtTotalAmount;
	private JComboBox<String> customerDropdown;
	private HashMap<String, Integer> customerMap = new HashMap<>();

	public BillManagement() {
		setTitle("Electricity Billing System ");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 700, 500);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 0, 132));
		contentPane.setBorder(new EmptyBorder(20, 20, 20, 20));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblTitle = new JLabel("BILL MANAGEMENT");
		lblTitle.setForeground(Color.WHITE);
		lblTitle.setFont(new Font("Georgia", Font.BOLD, 45));
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setBounds(10, 10, 660, 60);
		contentPane.add(lblTitle);

		JLabel lblCustomer = new JLabel("Select Customer:");
		lblCustomer.setForeground(Color.WHITE);
		lblCustomer.setFont(new Font("SimSun", Font.BOLD, 25));
		lblCustomer.setBounds(60, 100, 220, 30);
		contentPane.add(lblCustomer);

		customerDropdown = new JComboBox<>();
		customerDropdown.setFont(new Font("SimSun", Font.PLAIN, 20));
		customerDropdown.setBounds(300, 100, 300, 30);
		contentPane.add(customerDropdown);
		loadCustomers();

		JLabel lblUnitsUsed = new JLabel("Units Used:");
		lblUnitsUsed.setForeground(Color.WHITE);
		lblUnitsUsed.setFont(new Font("SimSun", Font.BOLD, 25));
		lblUnitsUsed.setBounds(60, 150, 220, 30);
		contentPane.add(lblUnitsUsed);

		txtUnitsUsed = new JTextField();
		txtUnitsUsed.setFont(new Font("SimSun", Font.PLAIN, 20));
		txtUnitsUsed.setBounds(300, 150, 300, 30);
		contentPane.add(txtUnitsUsed);

		JLabel lblPricePerUnit = new JLabel("Price/Unit:");
		lblPricePerUnit.setForeground(Color.WHITE);
		lblPricePerUnit.setFont(new Font("SimSun", Font.BOLD, 25));
		lblPricePerUnit.setBounds(60, 200, 220, 30);
		contentPane.add(lblPricePerUnit);

		txtPricePerUnit = new JTextField("8.50");
		txtPricePerUnit.setFont(new Font("SimSun", Font.PLAIN, 20));
		txtPricePerUnit.setBounds(300, 200, 300, 30);
		txtPricePerUnit.setEditable(false); // Fixed and non-editable
		contentPane.add(txtPricePerUnit);

		JLabel lblTotal = new JLabel("Total Amount:");
		lblTotal.setForeground(Color.WHITE);
		lblTotal.setFont(new Font("SimSun", Font.BOLD, 25));
		lblTotal.setBounds(60, 250, 220, 30);
		contentPane.add(lblTotal);

		txtTotalAmount = new JTextField();
		txtTotalAmount.setFont(new Font("SimSun", Font.PLAIN, 20));
		txtTotalAmount.setBounds(300, 250, 300, 30);
		txtTotalAmount.setEditable(false);
		contentPane.add(txtTotalAmount);

		JButton btnCalculate = new JButton("CALCULATE");
		btnCalculate.setFont(new Font("SimSun", Font.BOLD, 25));
		btnCalculate.setBounds(60, 310, 220, 40);
		btnCalculate.setBackground(Color.WHITE);
		contentPane.add(btnCalculate);

		JButton btnSave = new JButton("SAVE BILL");
		btnSave.setFont(new Font("SimSun", Font.BOLD, 25));
		btnSave.setBounds(300, 310, 220, 40);
		btnSave.setBackground(Color.WHITE);
		contentPane.add(btnSave);

		JButton btnBack = new JButton("MENU");
		btnBack.setFont(new Font("SimSun", Font.BOLD, 20));
		btnBack.setBounds(10, 400, 100, 30);
		btnBack.setBackground(Color.WHITE);
		contentPane.add(btnBack);

		btnCalculate.addActionListener(e -> {
			try {
				double units = Double.parseDouble(txtUnitsUsed.getText());
				double price = 8.50; // Fixed price
				double total = units * price;
				txtTotalAmount.setText(String.format("%.2f", total));
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(null, "Enter valid numbers for units.");
			}
		});

		btnSave.addActionListener(e -> saveBill());

		btnBack.addActionListener(e -> {
			AdminMenu menu = new AdminMenu();
			menu.setLocationRelativeTo(null);
			menu.setVisible(true);
			dispose();
		});
	}

	private void loadCustomers() {
		try {
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/electricity", "root", "5306");
			String sql = "SELECT customer_id, name FROM customers";
			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("customer_id");
				String name = rs.getString("name");
				customerMap.put(name, id);
				customerDropdown.addItem(name);
			}
			rs.close();
			stmt.close();
			conn.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error loading customers: " + e.getMessage());
		}
	}

	private void saveBill() {
		String selectedCustomer = (String) customerDropdown.getSelectedItem();
		if (selectedCustomer == null) {
			JOptionPane.showMessageDialog(null, "Select a customer.");
			return;
		}
		try {
			int customerId = customerMap.get(selectedCustomer);
			int units = Integer.parseInt(txtUnitsUsed.getText());
			double price = 8.50; // Fixed price
			double total = units * price;

			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/electricity", "root", "5306");

			String insertQuery = "INSERT INTO bills (customer_id, bill_no, units_used, price_per_unit, total_amount, is_paid) VALUES (?, ?, ?, ?, ?, ?)";
			PreparedStatement stmt = conn.prepareStatement(insertQuery);

			String billNo = "BILL" + System.currentTimeMillis();

			stmt.setInt(1, customerId);
			stmt.setString(2, billNo);
			stmt.setInt(3, units);
			stmt.setDouble(4, price);
			stmt.setDouble(5, total);
			stmt.setBoolean(6, false);

			stmt.executeUpdate();
			stmt.close();
			conn.close();

			JOptionPane.showMessageDialog(null, "Bill saved successfully!");
			txtUnitsUsed.setText("");
			txtTotalAmount.setText("");

		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(null, "Please enter valid numeric values.");
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Database error: " + e.getMessage());
		}
	}
}
