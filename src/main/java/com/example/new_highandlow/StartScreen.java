package com.example.new_highandlow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class StartScreen extends JFrame {
	private JButton room1_button, room2_button, room3_button, room4_button, room5_button, room6_button, logout_button,
			score_button, rule_button;
	private JPanel back_ground_panel;
	private JLabel title_label, message_label;
	private ArrayList<JButton> enter_room_button_list; // 不要っぽそう
	private ArrayList<String> numberofpeople_list; // 各部屋に入っている人数を格納するリスト
	// 要求仕様書のように部屋に入っている人数を表示するなら、画面遷移の時にアプリケーションサーバから引っ張ってくる必要がある
	private String user_id; // pushLogoutButton()で必要だが、現時点で未定義
	// 「addUserid(user_id)」のようなメソッドを定義して、LobbyScreenから遷移する時に引っ張ってくるのが簡単そう
	public static Boolean room_state_flag; // 部屋の入室可否変数

	public StartScreen() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(960, 540);
		setTitle("Start Screen");
		setLocationRelativeTo(null);
		setLayout(null);

		back_ground_panel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				ImageIcon image = new ImageIcon("src/main/resources/com/example/new_highandlow/png/Game.png"); // 背景画像は仮としてGame画面の物を使用
				g.drawImage(image.getImage(), 0, 0, getWidth(), getHeight(), this);
			}
		};
		back_ground_panel.setLayout(null);
		back_ground_panel.setOpaque(false);

		title_label = new JLabel("High&Low&Just");
		title_label.setBounds(360, 40, 300, 40);
		title_label.setFont(new Font("Arial", Font.BOLD, 30));

		logout_button = new JButton("ログアウト");
		logout_button.setBounds(750, 40, 150, 40);
		logout_button.addActionListener(this::pushLogoutButton);
		logout_button.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 20));
		logout_button.setBackground(Color.red);
		logout_button.setForeground(Color.white);

		room1_button = new JButton("部屋1");
		room1_button.setBounds(250, 150, 180, 40);
		room1_button.addActionListener(this::pushEnterRoomButton);
		room1_button.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 25));
		room1_button.setBackground(Color.orange);
		room1_button.setForeground(Color.black);

		room2_button = new JButton("部屋2");
		room2_button.setBounds(500, 150, 180, 40);
		room2_button.addActionListener(this::pushEnterRoomButton);
		room2_button.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 25));
		room2_button.setBackground(Color.orange);
		room2_button.setForeground(Color.black);

		room3_button = new JButton("部屋3");
		room3_button.setBounds(250, 230, 180, 40);
		room3_button.addActionListener(this::pushEnterRoomButton);
		room3_button.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 25));
		room3_button.setBackground(Color.orange);
		room3_button.setForeground(Color.black);

		room4_button = new JButton("部屋4");
		room4_button.setBounds(500, 230, 180, 40);
		room4_button.addActionListener(this::pushEnterRoomButton);
		room4_button.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 25));
		room4_button.setBackground(Color.orange);
		room4_button.setForeground(Color.black);

		room5_button = new JButton("部屋5");
		room5_button.setBounds(250, 310, 180, 40);
		room5_button.addActionListener(this::pushEnterRoomButton);
		room5_button.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 25));
		room5_button.setBackground(Color.orange);
		room5_button.setForeground(Color.black);

		room6_button = new JButton("部屋6");
		room6_button.setBounds(500, 310, 180, 40);
		room6_button.addActionListener(this::pushEnterRoomButton);
		room6_button.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 25));
		room6_button.setBackground(Color.orange);
		room6_button.setForeground(Color.black);

		score_button = new JButton("戦績");
		score_button.setBounds(300, 410, 120, 40);
		score_button.addActionListener(this::pushScoreButton);
		score_button.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 20));
		score_button.setBackground(Color.yellow);
		score_button.setForeground(Color.black);

		rule_button = new JButton("ルール");
		rule_button.setBounds(500, 410, 120, 40);
		rule_button.addActionListener(this::pushRuleButton);
		rule_button.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 20));
		rule_button.setBackground(Color.yellow);
		rule_button.setForeground(Color.black);

		message_label = new JLabel();
		message_label.setBounds(0, 500, 450, 40);
		message_label.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 15));

		this.add(back_ground_panel);

		back_ground_panel.add(title_label);
		back_ground_panel.add(logout_button);
		back_ground_panel.add(room1_button);
		back_ground_panel.add(room2_button);
		back_ground_panel.add(room3_button);
		back_ground_panel.add(room4_button);
		back_ground_panel.add(room5_button);
		back_ground_panel.add(room6_button);
		back_ground_panel.add(score_button);
		back_ground_panel.add(rule_button);

		setContentPane(back_ground_panel);

		setResizable(false);
	}

	public void pushLogoutButton(ActionEvent event) {
		if (event.getSource() == logout_button) {
			CController.logout();
			changeScreen("Lobby");
		}
	}

	public void pushScoreButton(ActionEvent event) {
		if (event.getSource() == score_button) {
			CController.getScore();
			// changeScreen("Score");
		}
	}

	public void pushRuleButton(ActionEvent event) {
		if (event.getSource() == rule_button) {
			if (CController.ruleScreen == null) {
				CController.getRule();
			}
			
			changeScreen("Rule");
		}
	}

	public void pushEnterRoomButton(ActionEvent event) {

		int room_id = 0;

		if (event.getSource() == room1_button) {
			room_id = 1;
		} else if (event.getSource() == room2_button) {
			room_id = 2;
		} else if (event.getSource() == room3_button) {
			room_id = 3;
		} else if (event.getSource() == room4_button) {
			room_id = 4;
		} else if (event.getSource() == room5_button) {
			room_id = 5;
		} else if (event.getSource() == room6_button) {
			room_id = 6;
		} else {
			System.out.println("[client] Error: pushEnterRoomButton()");
		}

		// まずnullを入れる
		room_state_flag = null;

		// これは接続できたか否か
		if (CController.checkRoomState(user_id, room_id)) {
			/*
			 * checkRoomStateメソッドの処理終了(=入室可否が返信される)を待たずに
			 * 次の処理を開始してしまう非同期処理になる?
			 * ⇒便宜的にウェイトを置いて対処
			 */


			// 書き換えが来るとnullではなくなる
			while (room_state_flag == null){
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			if (room_state_flag) {
				CController.enter(room_id);
				SController.room_id = room_id;

				this.setVisible(false);
				changeScreen("Wait");
			}
		}

	}

	public void changeScreen(String screen) {
		this.setVisible(false);
		SController.changeScreen(screen);
		
	}
	public void changeScreen(String screen, Message message) {
		this.setVisible(false);
		SController.changeScreen(screen, message);
	}

	public void displayMessage(String displayString) {
		message_label.setText(displayString);
	}
}
