package newelectricityBillingSystem;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.*;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class menu extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private int customerID;
	public static String id;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					menu frame = new menu();
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public menu(String iden) {
		id = iden;
	}

	public menu() {
		setTitle("Electricity Billing System");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 640, 501);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 0, 132));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("MENU");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Georgia", Font.BOLD, 75));
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setBounds(10, 11, 606, 113);
		contentPane.add(lblNewLabel);
		
		JButton btnpaybill = new JButton("PAY BILL");
		btnpaybill.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				paybill menuFrame = new paybill(id);
				menuFrame.setLocationRelativeTo(null);
	            menuFrame.setVisible(true);
	            dispose();
			}
		});
		btnpaybill.setFont(new Font("SimSun", Font.BOLD, 40));
		btnpaybill.setBackground(new Color(255, 255, 255));
		btnpaybill.setForeground(new Color(0, 0, 0));
		btnpaybill.setBounds(64, 135, 501, 66);
		contentPane.add(btnpaybill);
		
		JButton btnGenerateReceipt = new JButton("GENERATE RECEIPT");
		btnGenerateReceipt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reciept receiptFrame = new reciept(id);
				receiptFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
				receiptFrame.setLocationRelativeTo(null);
				receiptFrame.setVisible(true);
				dispose();
			}
		});
		btnGenerateReceipt.setForeground(Color.BLACK);
		btnGenerateReceipt.setFont(new Font("SimSun", Font.BOLD, 40));
		btnGenerateReceipt.setBackground(Color.WHITE);
		btnGenerateReceipt.setBounds(64, 212, 501, 66);
		contentPane.add(btnGenerateReceipt);

		
		JButton btnViewBills = new JButton("VIEW BILLS");
		btnViewBills.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				viewbills viewFrame = new viewbills(id);
				viewFrame.setLocationRelativeTo(null);
				viewFrame.setVisible(true);
				dispose();
			}
		});
		btnViewBills.setForeground(Color.BLACK);
		btnViewBills.setFont(new Font("SimSun", Font.BOLD, 40));
		btnViewBills.setBackground(Color.WHITE);
		btnViewBills.setBounds(64, 289, 501, 66);
		contentPane.add(btnViewBills);
		
		JButton btnSignOut = new JButton("SIGN OUT");
		btnSignOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				signup menuform = new signup();
				menuform.setExtendedState(JFrame.MAXIMIZED_BOTH);
				menuform.setLocationRelativeTo(null);
                menuform.setVisible(true);
                dispose();
			}
		});
		btnSignOut.setForeground(Color.BLACK);
		btnSignOut.setFont(new Font("SimSun", Font.BOLD, 40));
		btnSignOut.setBackground(Color.WHITE);
		btnSignOut.setBounds(64, 367, 501, 66);
		contentPane.add(btnSignOut);
	}
}
