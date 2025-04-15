package newelectricityBillingSystem;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class ViewCustomers extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;

	public ViewCustomers() {
		setTitle("Electricity Billing System ");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 0, 132));
		contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel titleLabel = new JLabel("ALL CUSTOMERS");
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setForeground(Color.WHITE);
		titleLabel.setFont(new Font("Georgia", Font.BOLD, 40));
		titleLabel.setBounds(10, 10, 764, 50);
		contentPane.add(titleLabel);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 80, 740, 400);
		contentPane.add(scrollPane);

		table = new JTable();
		table.setFont(new Font("SimSun", Font.PLAIN, 16));
		table.setRowHeight(30);
		table.setModel(new DefaultTableModel(
			    new Object[][] {},
			    new String[] {
			        "Customer ID", "Name", "Address", "Contact", "Email"
			    }
			) {
			    private static final long serialVersionUID = 1L;

			    @Override
			    public boolean isCellEditable(int row, int column) {
			        return false; 
			    }
			});

		scrollPane.setViewportView(table);

		JButton btnLoad = new JButton("LOAD CUSTOMERS");
		btnLoad.setFont(new Font("SimSun", Font.BOLD, 20));
		btnLoad.setBackground(Color.WHITE);
		btnLoad.setBounds(280, 500, 220, 40);
		btnLoad.addActionListener(e -> loadCustomers());
		contentPane.add(btnLoad);

		JButton btnBack = new JButton("BACK");
		btnBack.setFont(new Font("SimSun", Font.BOLD, 20));
		btnBack.setBackground(Color.WHITE);
		btnBack.setBounds(20, 500, 100, 40);
		btnBack.addActionListener(e -> {
			ManageAccounts back = new ManageAccounts();
			back.setLocationRelativeTo(null);
			back.setVisible(true);
			dispose();
		});
		contentPane.add(btnBack);
	}

	private void loadCustomers() {
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		model.setRowCount(0);

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/electricity", "root", "5306");

			String query = "SELECT customer_id, name, address, contact_details, email FROM customers";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			while (rs.next()) {
				model.addRow(new Object[]{
					rs.getInt("customer_id"),
					rs.getString("name"),
					rs.getString("address"),
					rs.getString("contact_details"),
					rs.getString("email")
				});
			}
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "Error loading customers: " + e.getMessage());
		}
	}
}
