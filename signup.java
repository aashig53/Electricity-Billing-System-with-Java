package newelectricityBillingSystem;

import java.awt.EventQueue;
import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;
import librarayManagement.SignIn;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class signup extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField signinname;
	private JLabel lblSignUp;
	private JLabel lblElectricity;
	private JLabel lblBilling;
	private JLabel lblSystem;
	private JLabel lblNewLabel_1;
	private JSeparator separator;
	private JSeparator separator_1;
	private JSeparator separator_2;
	private JSeparator separator_3;
	private JSeparator separator_4;
	private JLabel lblsigninname;
	private JLabel lblsigninpass;
	private JPasswordField signinpass;
	private JTextField textField;
	private JPasswordField passwordField;
	private JButton buttonLogin;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					signup frame = new signup();
					frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public signup() {
		setTitle("Electricity Billing System");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1297, 810);
		contentPane = new JPanel();
		contentPane.setForeground(new Color(255, 255, 255));
		contentPane.setBackground(new Color(0, 0, 132));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("     Sign In as Customer");
		lblNewLabel.setBackground(new Color(0, 0, 255));
		lblNewLabel.setFont(new Font("Georgia", Font.BOLD, 30));
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setBounds(0, 23, 634, 88);
		contentPane.add(lblNewLabel);
		
		signinname = new JTextField();
		signinname.setFont(new Font("SimSun", Font.BOLD, 16));
		signinname.setBounds(652, 53, 249, 26);
		contentPane.add(signinname);
		signinname.setColumns(10);
		
		JButton btnsignin = new JButton("Sign In");
		btnsignin.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	            String username = signinname.getText().trim();
	            String password = new String(signinpass.getPassword()).trim();

	            try {
	                Class.forName("com.mysql.cj.jdbc.Driver");
	                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/electricity", "root", "5306");

	                String query = "SELECT * FROM customers WHERE name = '"+username+"' AND password_hash = '"+password+"'";
	                Statement s = con.createStatement();
	                
	                ResultSet rs = s.executeQuery(query);

	                if (rs.next()) {
	                	menu menuform1 = new menu(rs.getInt(1)+"");
	                	menu menuform = new menu();
	                    menuform.setLocationRelativeTo(null);
	                    menuform.setVisible(true);
	                    dispose();
	                } else {
	                    JOptionPane.showMessageDialog(null, "Invalid username or password");
	                }

	                s.close();
	                con.close();
	            } catch (Exception ex) {
	                ex.printStackTrace();
	            }
	        }
	    });

		btnsignin.setFont(new Font("Georgia", Font.BOLD, 14));
		btnsignin.setBackground(new Color(0, 236, 0));
		btnsignin.setForeground(new Color(255, 255, 255));
		btnsignin.setBounds(1169, 52, 89, 26);
		contentPane.add(btnsignin);
		
		lblSignUp = new JLabel("Sign In as Admin");
		lblSignUp.setHorizontalAlignment(SwingConstants.CENTER);
		lblSignUp.setForeground(Color.WHITE);
		lblSignUp.setFont(new Font("Georgia", Font.BOLD, 65));
		lblSignUp.setBackground(Color.BLUE);
		lblSignUp.setBounds(506, 187, 645, 75);
		contentPane.add(lblSignUp);
		
		lblElectricity = new JLabel("Electricity ");
		lblElectricity.setHorizontalAlignment(SwingConstants.CENTER);
		lblElectricity.setForeground(Color.WHITE);
		lblElectricity.setFont(new Font("Georgia", Font.BOLD, 70));
		lblElectricity.setBackground(Color.BLUE);
		lblElectricity.setBounds(9, 381, 474, 81);
		contentPane.add(lblElectricity);
		
		lblBilling = new JLabel("Billing ");
		lblBilling.setHorizontalAlignment(SwingConstants.CENTER);
		lblBilling.setForeground(Color.WHITE);
		lblBilling.setFont(new Font("Georgia", Font.BOLD, 70));
		lblBilling.setBackground(new Color(0, 255, 255));
		lblBilling.setBounds(19, 449, 464, 81);
		contentPane.add(lblBilling);
		
		lblSystem = new JLabel("System");
		lblSystem.setHorizontalAlignment(SwingConstants.CENTER);
		lblSystem.setForeground(Color.WHITE);
		lblSystem.setFont(new Font("Georgia", Font.BOLD, 70));
		lblSystem.setBackground(Color.BLUE);
		lblSystem.setBounds(9, 509, 474, 88);
		contentPane.add(lblSystem);
		
		lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon("C:\\Users\\aashi\\Downloads\\7866488 (1).png"));
		lblNewLabel_1.setBounds(134, 187, 192, 187);
		contentPane.add(lblNewLabel_1);
		
		separator = new JSeparator();
		separator.setForeground(Color.WHITE);
		separator.setBackground(Color.WHITE);
		separator.setBounds(493, 329, 695, 2);
		contentPane.add(separator);
		
		separator_1 = new JSeparator();
		separator_1.setForeground(Color.WHITE);
		separator_1.setBackground(Color.WHITE);
		separator_1.setBounds(493, 661, 695, 2);
		contentPane.add(separator_1);
		
		separator_2 = new JSeparator();
		separator_2.setOrientation(SwingConstants.VERTICAL);
		separator_2.setForeground(Color.WHITE);
		separator_2.setBackground(Color.WHITE);
		separator_2.setBounds(494, 329, 2, 334);
		contentPane.add(separator_2);
		
		separator_3 = new JSeparator();
		separator_3.setOrientation(SwingConstants.VERTICAL);
		separator_3.setForeground(Color.WHITE);
		separator_3.setBackground(Color.WHITE);
		separator_3.setBounds(1186, 329, 2, 334);
		contentPane.add(separator_3);
		
		separator_4 = new JSeparator();
		separator_4.setForeground(Color.WHITE);
		separator_4.setBackground(Color.WHITE);
		separator_4.setBounds(20, 99, 1253, 2);
		contentPane.add(separator_4);
		
		lblsigninname = new JLabel("Username");
		lblsigninname.setFont(new Font("SimSun", Font.BOLD, 20));
		lblsigninname.setForeground(new Color(255, 255, 255));
		lblsigninname.setBounds(652, 11, 138, 31);
		contentPane.add(lblsigninname);
		
		lblsigninpass = new JLabel("Password");
		lblsigninpass.setForeground(Color.WHITE);
		lblsigninpass.setFont(new Font("SimSun", Font.BOLD, 20));
		lblsigninpass.setBounds(911, 11, 138, 31);
		contentPane.add(lblsigninpass);
		
		signinpass = new JPasswordField();
		signinpass.setBounds(911, 53, 242, 26);
		contentPane.add(signinpass);
		
		JLabel lblUsername_1 = new JLabel("Username");
		lblUsername_1.setForeground(Color.WHITE);
		lblUsername_1.setFont(new Font("Georgia", Font.PLAIN, 20));
		lblUsername_1.setBounds(591, 400, 200, 40);
		contentPane.add(lblUsername_1);
		
		textField = new JTextField();
		textField.setFont(new Font("Georgia", Font.PLAIN, 18));
		textField.setColumns(10);
		textField.setBounds(791, 400, 300, 40);
		contentPane.add(textField);
		
		passwordField = new JPasswordField();
		passwordField.setFont(new Font("Georgia", Font.PLAIN, 18));
		passwordField.setBounds(791, 470, 300, 40);
		contentPane.add(passwordField);
		
		JLabel lblPassword_1 = new JLabel("Password");
		lblPassword_1.setForeground(Color.WHITE);
		lblPassword_1.setFont(new Font("Georgia", Font.PLAIN, 20));
		lblPassword_1.setBounds(591, 470, 200, 40);
		contentPane.add(lblPassword_1);
		
		buttonLogin = new JButton("Sign In");
		buttonLogin.setForeground(Color.WHITE);
		buttonLogin.setFont(new Font("Georgia", Font.BOLD, 20));
		buttonLogin.setBackground(new Color(0, 255, 0));
		buttonLogin.setBounds(792, 562, 150, 40);
		contentPane.add(buttonLogin);
		
		buttonLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String username = textField.getText().trim();
				String password = new String(passwordField.getPassword()).trim();

				if (username.isEmpty() || password.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please enter both username and password.");
					return;
				}

				try {
					Class.forName("com.mysql.cj.jdbc.Driver");
					Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/electricity", "root", "5306");

					String query = "SELECT * FROM admins WHERE username = ? AND password_hash = ?";
					PreparedStatement ps = con.prepareStatement(query);
					ps.setString(1, username);
					ps.setString(2, password);

					ResultSet rs = ps.executeQuery();

					if (rs.next()) {
						AdminMenu adminMenu = new AdminMenu();
						adminMenu.setLocationRelativeTo(null);
						adminMenu.setVisible(true);
						dispose();
					} else {
						JOptionPane.showMessageDialog(null, "Invalid credentials");
					}

					rs.close();
					ps.close();
					con.close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		
	}
}
