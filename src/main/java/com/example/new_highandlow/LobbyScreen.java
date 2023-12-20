package com.example.new_highandlow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class LobbyScreen extends JFrame{
	private JPanel back_ground_panel;
	private JTextField user_Id_text;
	private JPasswordField password_text;
	private JButton login_button, create_account_button;
	private JLabel user_Id_label, password_label, title_label, message_label;
	private JCheckBox showPassword;

	public LobbyScreen(){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800, 600);
		setTitle("Lobby Screen");
		setLocationRelativeTo(null);
		setLayout(null);

		back_ground_panel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				ImageIcon image = new ImageIcon("src/main/java/com/example/new_highandlow/cccc.png");
				g.drawImage(image.getImage(), 0, 0, getWidth(), getHeight(), this);
			}
		};
		back_ground_panel.setLayout(null);
		back_ground_panel.setOpaque(false);

		title_label = new JLabel("New High and Low");
		title_label.setBounds(250, 50, 400, 40);
		title_label.setFont(new Font("Arial", Font.BOLD, 35));

		user_Id_label = new JLabel("User ID");
		user_Id_label.setBounds(180, 150, 100, 40);
		user_Id_label.setFont(new Font("Arial", Font.BOLD, 20));

		password_label = new JLabel("PassWord");
		password_label.setBounds(180, 200, 200, 40);
		password_label.setFont(new Font("Arial", Font.BOLD, 20));

		message_label = new JLabel();
		message_label.setBounds(300, 360, 300, 40);
		message_label.setFont(new Font("Arial", Font.BOLD, 20));
		message_label.setForeground(Color.red);

		user_Id_text = new JTextField();
		user_Id_text.setBounds(300, 150, 300, 40);
		user_Id_text.setFont(new Font("Arial", Font.BOLD, 20));
		user_Id_text.setForeground(Color.black);

		password_text = new JPasswordField();
		password_text.setBounds(300, 200, 300, 40);
		password_text.setFont(new Font("Arial", Font.BOLD, 20));
		password_text.setForeground(Color.black);

		showPassword = new JCheckBox("show password");
		showPassword.setBounds(300, 250, 300, 40);
		showPassword.setFont(new Font("Arial", Font.ITALIC, 20));
		showPassword.setForeground(Color.black);
		showPassword.addActionListener(this::showPassword);

		login_button = new JButton("LOGIN");
		login_button.setBounds(250, 300, 100, 40);
		login_button.addActionListener(this::pushLoginButton);
		login_button.setFont(new Font("Arial", Font.BOLD, 16));
		login_button.setBackground(Color.blue);
		login_button.setForeground(Color.white);

		create_account_button = new JButton("CREATE ACCOUNT");
		create_account_button.setBounds(450, 300, 190, 40);
		create_account_button.addActionListener(this::pushCreateAccountButton);
		create_account_button.setFont(new Font("Arial", Font.BOLD, 16));
		create_account_button.setBackground(Color.red);
		create_account_button.setForeground(Color.white);

		this.add(back_ground_panel);

		back_ground_panel.add(title_label);
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
	}

	public void pushLoginButton(ActionEvent event){
		if(event.getSource()==login_button){
			String user = user_Id_text.getText();
			String pwd = new String(password_text.getPassword());

			if(user.equalsIgnoreCase("admin") && pwd.equalsIgnoreCase("pass")){
				SController sc = new SController();
				sc.changeScreen("Start");
				this.setVisible(false);
			}
			else{
				displayMessage("Invalid Username and Password !");
			}
		}
	}

	public void pushCreateAccountButton(ActionEvent event){
		if(event.getSource()==create_account_button){
			CController cc = new CController();
			char[] password = password_text.getPassword();
			String passwordstr = new String(password);
			cc.checkPasswordStrength(passwordstr);
		}
	}

	public void showPassword(ActionEvent event){
		if(event.getSource()==showPassword){
			if(showPassword.isSelected()){
				password_text.setEchoChar((char) 0);
			}
			else{
				password_text.setEchoChar('●');
			}
		}
	}

	public void displayMessage(String displayString){
		message_label.setText(displayString);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			LobbyScreen lobbyScreen = new LobbyScreen();
			lobbyScreen.setVisible(true);
		});
	}
}