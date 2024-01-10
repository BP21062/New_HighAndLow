package com.example.new_highandlow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class WaitScreen extends JFrame{
	private JPanel back_ground_panel;
	private JLabel room_number_label, current_user_label;
	private JButton exit_room_button;
	private String user_id;
	public static String order;

	public WaitScreen(String user_id,int room_id){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(960, 540);
		setTitle("Wait Screen");
		setLocationRelativeTo(null);
		setLayout(null);

		back_ground_panel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				ImageIcon image = new ImageIcon("src/main/resources/com/example/new_highandlow/png/Wait.png");
				g.drawImage(image.getImage(), 0, 0, getWidth(), getHeight(), this);
			}
		};
		back_ground_panel.setLayout(null);
		back_ground_panel.setOpaque(false);

		room_number_label = new JLabel();
		room_number_label.setBounds(330, 180, 270, 40);
		room_number_label.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 30));
		room_number_label.setOpaque(true);
		room_number_label.setBackground(Color.orange);
		room_number_label.setForeground(Color.black);
		displayRoomNumber(room_id);

		current_user_label = new JLabel();
		current_user_label.setBounds(330, 230, 270, 40);
		current_user_label.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 30));
		current_user_label.setOpaque(true);
		current_user_label.setBackground(Color.orange);
		current_user_label.setForeground(Color.black);
		displayCurrentUser(3);

		exit_room_button = new JButton("退出");
		exit_room_button.setBounds(410, 395, 120, 50);
		exit_room_button.addActionListener(this::pushExitRoomButton);
		exit_room_button.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 25));
		exit_room_button.setBackground(Color.blue);
		exit_room_button.setForeground(Color.white);

		this.add(back_ground_panel);

		back_ground_panel.add(room_number_label);
		back_ground_panel.add(current_user_label);
		back_ground_panel.add(exit_room_button);
		setContentPane(back_ground_panel);

		this.setVisible(true);
		this.user_id = user_id;
	}

	public void pushExitRoomButton(ActionEvent event){
		if(event.getSource()==exit_room_button){
			CController cc = new CController();
			cc.logout("user_id");
			SController sc = new SController();
			sc.User_id = user_id;
			sc.changeScreen("Start");
			this.setVisible(false);
		}
	}

	public void updateUserList(){}

	public void displayRoomNumber(int number){
		String num = String.format(" 部屋番号：%d ", number);
		room_number_label.setText(num);
	}

	public void displayCurrentUser(int user){
		String usr = String.format(" 現在人数：%d / 4 ", user);
		current_user_label.setText(usr);
	}

	public void StartGame(){

		CController.waitScreen.setVisible(false);
		SController sController = new SController();
		sController.changeScreen("Game");

	}

}
