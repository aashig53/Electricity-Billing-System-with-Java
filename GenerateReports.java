package newelectricityBillingSystem;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class GenerateReports extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextArea reportArea;

	public GenerateReports() {
		setTitle("Electricity Billing System ");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 0, 132));
		contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblTitle = new JLabel("GENERATE REPORTS");
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setForeground(Color.WHITE);
		lblTitle.setFont(new Font("Georgia", Font.BOLD, 40));
		lblTitle.setBounds(10, 10, 764, 50);
		contentPane.add(lblTitle);

		JButton btnCustomerReport = new JButton("CUSTOMER REPORT");
		btnCustomerReport.setFont(new Font("SimSun", Font.BOLD, 20));
		btnCustomerReport.setBounds(30, 80, 250, 40);
		btnCustomerReport.setBackground(Color.WHITE);
		btnCustomerReport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				generateCustomerReport();
			}
		});
		contentPane.add(btnCustomerReport);

		// Placeholder for a future bill report feature
		JButton btnBillingReport = new JButton("BILLING REPORT");
		btnBillingReport.setFont(new Font("SimSun", Font.BOLD, 20));
		btnBillingReport.setBounds(300, 80, 250, 40);
		btnBillingReport.setBackground(Color.WHITE);
		btnBillingReport.addActionListener(e -> {
		    try {
		        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/electricity", "root", "5306");
		        Statement stmt = conn.createStatement();
		        ResultSet rs = stmt.executeQuery(
		            "SELECT COUNT(*) AS total_bills, " +
		            "SUM(total_amount) AS total_revenue, " +
		            "SUM(CASE WHEN is_paid = 0 THEN total_amount ELSE 0 END) AS unpaid_amount, " +
		            "SUM(CASE WHEN is_paid = 1 THEN total_amount ELSE 0 END) AS paid_amount " +
		            "FROM bills"
		        );

		        if (rs.next()) {
		            StringBuilder report = new StringBuilder();
		            report.append("=== Billing Report ===\n\n");
		            report.append("Total Bills: " + rs.getInt("total_bills") + "\n");
		            report.append("Total Revenue: ₹" + rs.getDouble("total_revenue") + "\n");
		            report.append("Paid Amount: ₹" + rs.getDouble("paid_amount") + "\n");
		            report.append("Unpaid Amount: ₹" + rs.getDouble("unpaid_amount") + "\n");
		            reportArea.setText(report.toString());
		        }

		        rs.close();
		        stmt.close();
		        conn.close();
		    } catch (Exception ex) {
		        ex.printStackTrace();
		        reportArea.setText("Error fetching billing report.");
		    }
		});
		contentPane.add(btnBillingReport);


		JButton btnBack = new JButton("MENU");
		btnBack.setFont(new Font("SimSun", Font.BOLD, 20));
		btnBack.setBounds(590, 80, 120, 40);
		btnBack.setBackground(Color.WHITE);
		btnBack.addActionListener(e -> {
			AdminMenu adminMenu = new AdminMenu();
			adminMenu.setLocationRelativeTo(null);
			adminMenu.setVisible(true);
			dispose();
		});
		contentPane.add(btnBack);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(30, 140, 720, 390);
		contentPane.add(scrollPane);

		reportArea = new JTextArea();
		reportArea.setFont(new Font("SimSun", Font.PLAIN, 16));
		reportArea.setLineWrap(true);
		reportArea.setWrapStyleWord(true);
		scrollPane.setViewportView(reportArea);
	}

	private void generateCustomerReport() {
	    StringBuilder report = new StringBuilder();
	    report.append("CUSTOMER REPORT\n\n");

	    int totalUnitsConsumed = 0;

	    try {
	        Class.forName("com.mysql.cj.jdbc.Driver");
	        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/electricity", "root", "5306");

	        String query = "SELECT c.customer_id, c.name, c.email, c.contact_details, " +
	                       "COALESCE(SUM(b.units_used), 0) AS total_units " +
	                       "FROM customers c " +
	                       "LEFT JOIN bills b ON c.customer_id = b.customer_id " +
	                       "GROUP BY c.customer_id, c.name, c.email, c.contact_details";

	        Statement stmt = conn.createStatement();
	        ResultSet rs = stmt.executeQuery(query);

	        while (rs.next()) {
	            report.append("ID: ").append(rs.getInt("customer_id")).append("\n");
	            report.append("Name: ").append(rs.getString("name")).append("\n");
	            report.append("Email: ").append(rs.getString("email")).append("\n");
	            report.append("Contact: ").append(rs.getString("contact_details")).append("\n");
	            report.append("Total Units Consumed: ").append(rs.getInt("total_units")).append("\n");
	            report.append("--------------------------------------------------\n");
	            totalUnitsConsumed += rs.getInt("total_units");
	        }

	        report.append("\nTOTAL UNITS CONSUMED BY ALL CUSTOMERS: ").append(totalUnitsConsumed).append(" units");

	        reportArea.setText(report.toString());
	        rs.close();
	        stmt.close();
	        conn.close();

	    } catch (Exception e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(this, "Error generating report: " + e.getMessage());
	    }
	}

}
