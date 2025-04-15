package newelectricityBillingSystem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.UUID;

public class paybill extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTable table;
    private DefaultTableModel model;
    private String userId;

    public paybill(String id) {
        this.userId = id;

        setTitle("Electricity Billing System ");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 900, 500);

        contentPane = new JPanel(new BorderLayout());
        contentPane.setBackground(new Color(0, 0, 102));
        contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setContentPane(contentPane);

        JLabel lblTitle = new JLabel("UNPAID BILLS", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Georgia", Font.BOLD, 35));
        lblTitle.setForeground(Color.WHITE);
        contentPane.add(lblTitle, BorderLayout.NORTH);

        model = new DefaultTableModel(new Object[]{"Bill ID", "Units", "Price/Unit", "Total", "Pay"}, 0);
        table = new JTable(model) {
            public boolean isCellEditable(int row, int column) {
                return column == 4;
            }
        };

        table.setRowHeight(35);
        table.getColumn("Pay").setCellRenderer(new ButtonRenderer());
        table.getColumn("Pay").setCellEditor(new ButtonEditor(new JCheckBox(), this));

        JScrollPane scrollPane = new JScrollPane(table);
        contentPane.add(scrollPane, BorderLayout.CENTER);

        // Back Button Panel
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(new Color(0, 0, 102));
        
        JButton menubtn = new JButton("MENU");
        menubtn.setForeground(new Color(0, 0, 0));
        menubtn.setFont(new Font("SimSun", Font.BOLD, 20));
        menubtn.setBackground(new Color(255, 255, 255));
        menubtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false); 
                menu menuFrame = new menu();
                menuFrame.setLocationRelativeTo(null);
                menuFrame.setVisible(true);
            }
        });
        
        bottomPanel.add(menubtn);
        contentPane.add(bottomPanel, BorderLayout.SOUTH);

        loadUnpaidBills();
    }

    private void loadUnpaidBills() {
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/electricity", "root", "5306");
            String query = "SELECT * FROM bills WHERE customer_id = ? AND is_paid = '0'";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int billId = rs.getInt("bill_id");
                int units = rs.getInt("units_used");
                double price = rs.getDouble("price_per_unit");
                double total = rs.getDouble("total_amount");

                model.addRow(new Object[]{billId, units, price, total, "Pay Now"});
            }

            rs.close();
            ps.close();
            con.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading bills: " + e.getMessage());
        }
    }

    public void processPayment(int row) {
        int billId = (int) model.getValueAt(row, 0);
        String transactionId = UUID.randomUUID().toString().substring(0, 10);
        java.sql.Date paymentDate = new java.sql.Date(System.currentTimeMillis());

        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/electricity", "root", "5306");
            con.setAutoCommit(false);

            PreparedStatement ps1 = con.prepareStatement("UPDATE bills SET is_paid=1 WHERE bill_id=?");
            ps1.setInt(1, billId);
            ps1.executeUpdate();

            PreparedStatement ps2 = con.prepareStatement("INSERT INTO payments (bill_id, transaction_id, payment_date) VALUES (?, ?, ?)");
            ps2.setInt(1, billId);
            ps2.setString(2, transactionId);
            ps2.setDate(3, paymentDate);
            ps2.executeUpdate();

            con.commit();
            ps1.close();
            ps2.close();
            con.close();

            JOptionPane.showMessageDialog(this, "Payment Successful!\nTransaction ID: " + transactionId);
            model.removeRow(row);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Payment Failed: " + e.getMessage());
        }
    }
}

