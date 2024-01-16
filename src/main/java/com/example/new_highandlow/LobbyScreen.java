package com.example.new_highandlow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class LobbyScreen extends JFrame {
	private JPanel back_ground_panel;
	private JTextField user_Id_text;
	private JPasswordField password_text;
	private JButton login_button, create_account_button;
	private JLabel user_Id_label, password_label, message_label;
	private JCheckBox showPassword;

	public LobbyScreen() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(960, 540);
		setTitle("Lobby Screen");
		setLocationRelativeTo(null);
		setLayout(null);

		back_ground_panel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				ImageIcon image = new ImageIcon("src/main/resources/com/example/new_highandlow/png/Lobby.png");
				g.drawImage(image.getImage(), 0, 0, getWidth(), getHeight(), this);
			}
		};
		back_ground_panel.setLayout(null);
		back_ground_panel.setOpaque(false);

		user_Id_label = new JLabel("User ID");
		user_Id_label.setBounds(340, 160, 200, 40);
		user_Id_label.setFont(new Font("Arial", Font.BOLD, 15));

		password_label = new JLabel("Password");
		password_label.setBounds(340, 225, 200, 40);
		password_label.setFont(new Font("Arial", Font.BOLD, 15));

		message_label = new JLabel();
		message_label.setBounds(400, 160, 500, 40);
		message_label.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 15));

		user_Id_text = new JTextField();
		user_Id_text.setBounds(345, 195, 255, 30);
		user_Id_text.setFont(new Font("Arial", Font.BOLD, 20));
		user_Id_text.setForeground(Color.black);

		password_text = new JPasswordField();
		password_text.setBounds(345, 260, 255, 30);
		password_text.setFont(new Font("Arial", Font.BOLD, 20));
		password_text.setForeground(Color.black);

		showPassword = new JCheckBox("show password");
		showPassword.setBounds(350, 285, 300, 40);
		showPassword.setFont(new Font("Arial", Font.ITALIC, 15));
		showPassword.setForeground(Color.black);
		showPassword.setOpaque(false);
		showPassword.addActionListener(this::showPassword);

		login_button = new JButton("Login");
		login_button.setBounds(425, 325, 100, 40);
		login_button.addActionListener(this::pushLoginButton);
		login_button.setFont(new Font("Arial", Font.BOLD, 16));
		login_button.setBackground(Color.blue);
		login_button.setForeground(Color.white);

		create_account_button = new JButton("Create Account");
		create_account_button.setBounds(390, 380, 170, 40);
		create_account_button.addActionListener(this::pushCreateAccountButton);
		create_account_button.setFont(new Font("Arial", Font.BOLD, 16));
		create_account_button.setBackground(Color.blue);
		create_account_button.setForeground(Color.white);

		this.add(back_ground_panel);

		back_ground_panel.add(user_Id_label);
		back_ground_panel.add(user_Id_text);
		back_ground_panel.add(password_label);
		back_ground_panel.add(password_text);
		back_ground_panel.add(login_button);
		back_ground_panel.add(create_account_button);
		back_ground_panel.add(showPassword);
		back_ground_panel.add(message_label);
		setContentPane(back_ground_panel);

		this.setVisible(true);
		setResizable(false);
	}

	public void pushLoginButton(ActionEvent event) {
		if (event.getSource() == login_button) {
			String user = user_Id_text.getText().trim();
			String pwd = new String(password_text.getPassword()).trim();

			if (user.isEmpty() && pwd.isEmpty()) {
				message_label.setForeground(Color.orange);
				displayMessage("※ユーザIDとパスワードを入力してください");
			} else if (user.isEmpty()) {
				message_label.setForeground(Color.orange);
				displayMessage("※ユーザIDを入力してください");
			} else if (pwd.isEmpty()) {
				message_label.setForeground(Color.orange);
				displayMessage("※パスワードを入力してください");
			} else {
				char[] password = password_text.getPassword();
				String passwordstr = new String(password);

				CController.user_id = user_Id_text.getText();
				CController.passwd = passwordstr;
				
				CController.login();
			}
		}
	}

	public void pushCreateAccountButton(ActionEvent event) {
		if (event.getSource() == create_account_button) {
			char[] password = password_text.getPassword();
			String passwordstr = new String(password);
			CController.user_id = user_Id_text.getText();
			CController.passwd = passwordstr;
			if (CController.checkPasswordStrength())
				CController.registerUser();
			else {
				message_label.setForeground(Color.red);
				displayMessage("※パスワードは英数字8~12文字で大文字を1文字以上含んでください");
			}
		}
	}

	public void showPassword(ActionEvent event) {
		if (event.getSource() == showPassword) {
			if (showPassword.isSelected()) {
				password_text.setEchoChar((char) 0);
			} else {
				password_text.setEchoChar('●');
			}
		}
	}

	public void displayMessage(String displayString) {
		message_label.setText(displayString);
	}

	public void changeScreen(String screen) {
		this.setVisible(false);
		SController.changeScreen(screen);
		
	}

}