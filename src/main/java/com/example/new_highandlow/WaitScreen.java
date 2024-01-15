package com.example.new_highandlow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class WaitScreen extends JFrame{
	private JPanel back_ground_panel;
	private JLabel room_number_label, message_label;
	private JButton exit_room_button;
	private String user_id;
	static Gson gson = new Gson();

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

		this.setVisible(true);
		this.user_id = user_id;
	}

	public void pushExitRoomButton(ActionEvent event){
		if(event.getSource()==exit_room_button){
			CController cc = new CController();
			cc.logout("user_id");
			SController sc = new SController(user_id);
			sc.User_id = user_id;
			sc.changeScreen("Start");
			this.setVisible(false);
		}
	}

	public void displayRoomNumber(int number){
		String num = String.format(" 部屋番号：%d ", number);
		room_number_label.setText(num);
	}

	public void displayMessage(String displayString){
		message_label.setText(displayString);
	}

	public void changeScreen(String screen){
		SController sc = new SController(user_id);
		sc.changeScreen(screen);
		this.setVisible(false);
	}

	public void StartGame(int room_id){
		CController.waitScreen.setVisible(false);
		SController sController = new SController(user_id);
		sController.Room_id = room_id;
		sController.User_id = user_id;
		sController.changeScreen("Game");

		System.out.println("sendMessage()");
		// 試しにSampleMessageのインスタンスを作ってみる
		Message sendMessage = new Message("1004", user_id);
		// クラスオブジェクトをString (JSON) に変換する
		sendMessage.result = true;
		sendMessage.messageContent.room_id = room_id;
		String sendMessageJson = gson.toJson(sendMessage);
		// 変換後の書式を表示してみる。JSON
		System.out.println(sendMessageJson);
		CController.app_connect.sendMessage(sendMessageJson);
	}
}