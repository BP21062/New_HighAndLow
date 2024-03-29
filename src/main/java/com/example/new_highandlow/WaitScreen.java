package com.example.new_highandlow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class WaitScreen extends JFrame {
	private JPanel back_ground_panel;
	private JLabel room_number_label, message_label;
	private JButton exit_room_button;

	public WaitScreen(int room_id) {
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

		message_label = new JLabel();
		message_label.setBounds(330, 230, 270, 40);
		message_label.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 30));
		message_label.setOpaque(true);
		message_label.setBackground(Color.blue);
		message_label.setForeground(Color.black);

		exit_room_button = new JButton("退出");
		exit_room_button.setBounds(410, 395, 120, 50);
		exit_room_button.addActionListener(this::pushExitRoomButton);
		exit_room_button.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 25));
		exit_room_button.setBackground(Color.blue);
		exit_room_button.setForeground(Color.white);

		this.add(back_ground_panel);

		back_ground_panel.add(room_number_label);
		back_ground_panel.add(message_label);
		back_ground_panel.add(exit_room_button);
		setContentPane(back_ground_panel);

		setResizable(false);
	}

	public void pushExitRoomButton(ActionEvent event) {
		if (event.getSource() == exit_room_button) {
			CController.logout();
			changeScreen("Lobby");
		}
	}

	public void displayRoomNumber(int number) {
		String num = String.format(" 部屋番号：%d ", number);
		room_number_label.setText(num);
	}

	public void displayMessage(String displayString) {
		message_label.setText(displayString);
	}

	public void changeScreen(String screen) {
		this.setVisible(false);
		SController.changeScreen(screen);
	}

	public void reload(int room_id) {
		displayRoomNumber(room_id);
	}

}
