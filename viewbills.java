package newelectricityBillingSystem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class viewbills extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTable table;
    private String userId;

    public viewbills(String id) {
        this.userId = id;
        setTitle("Electricity Billing System ");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 800, 500);

        contentPane = new JPanel();
        contentPane.setBackground(new Color(0, 0, 100));
        contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("BILL DETAILS", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Georgia", Font.BOLD, 35));
        titleLabel.setForeground(Color.WHITE);
        contentPane.add(titleLabel, BorderLayout.NORTH);

        table = new JTable();
        table.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        table.setRowHeight(30);

        JScrollPane scrollPane = new JScrollPane(table);
        contentPane.add(scrollPane, BorderLayout.CENTER);

        // Panel for the Back Button
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(0, 0, 100));

        JButton backButton = new JButton("MENU");
        backButton.setForeground(new Color(0, 0, 0));
        backButton.setFont(new Font("SimSun", Font.BOLD, 24));
        backButton.setBackground(new Color(255, 255, 255));

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false); // Close viewbills window
                menu menuFrame = new menu();
                menuFrame.setLocationRelativeTo(null);
                menuFrame.setVisible(true);
            }
        });

        buttonPanel.add(backButton);
        contentPane.add(buttonPanel, BorderLayout.SOUTH);

        loadBillData();
    }

    private void loadBillData() {
        DefaultTableModel model = new DefaultTableModel(
            new Object[]{"Units Used", "Price per Unit", "Total Amount", "Status"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make all cells non-editable
            }
        };

        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/electricity", "root", "5306");
            String query = "SELECT units_used, price_per_unit, total_amount, is_paid FROM bills WHERE customer_id = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int units = rs.getInt("units_used");
                double price = rs.getDouble("price_per_unit");
                double total = rs.getDouble("total_amount");
                int paidFlag = rs.getInt("is_paid");

                String status = (paidFlag == 1) ? "Paid" : "Unpaid";
                model.addRow(new Object[]{units, price, total, status});
            }

            table.setModel(model);

            rs.close();
            ps.close();
            con.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error fetching bill data: " + e.getMessage());
        }
    }
}
