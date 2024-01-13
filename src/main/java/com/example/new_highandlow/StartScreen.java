package com.example.new_highandlow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class StartScreen extends JFrame{
	private JButton room1_button,room2_button,room3_button,room4_button,room5_button,room6_button,logout_button,score_button,rule_button;
	private JPanel back_ground_panel;
	private JLabel title_label,message_label;
	private String user_id;

	public StartScreen(String user_id){
		this.user_id=user_id;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(960, 540);
		setTitle("Start Screen");
		setLocationRelativeTo(null);
		setLayout(null);

		back_ground_panel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				ImageIcon image = new ImageIcon("src/main/resources/com/example/new_highandlow/png/Game.png");	//背景画像は仮としてGame画面の物を使用
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
		this.setVisible(true);
	}


	public void pushLogoutButton(ActionEvent event){
		if(event.getSource()== logout_button){
			CController cController=new CController();
			cController.logout(this.user_id);
			Message message=null;
			changeScreen("Lobby",message);
		}
	}

	public void pushScoreButton(ActionEvent event){
		if(event.getSource()== score_button){
			CController cController=new CController();
			cController.getScore();
			//changeScreen("Score");
		}
	}

	public void pushRuleButton(ActionEvent event){
		if(event.getSource()== rule_button){
			CController cController=new CController();
			cController.getRule();
			//changeScreen("Rule");
		}
	}

	public void pushEnterRoomButton(ActionEvent event){
		if(event.getSource()== room1_button){

			CController cController=new CController();
			cController.enter(user_id,1);
			SController sController = new SController();
			sController.User_id = user_id;
			this.setVisible(false);
			//sController.changeScreen("Wait");
		}else if(event.getSource() == room2_button){
			CController cController=new CController();
			cController.enter(user_id,2);
			SController sController = new SController();
			sController.User_id = user_id;
			this.setVisible(false);
		}else if(event.getSource() == room3_button){
			CController cController=new CController();
			cController.enter(user_id,3);
			SController sController = new SController();
			sController.User_id = user_id;
			this.setVisible(false);
		}else if(event.getSource() == room4_button){
			CController cController=new CController();
			cController.enter(user_id,4);
			SController sController = new SController();
			sController.User_id = user_id;
			this.setVisible(false);
		}else if(event.getSource() == room5_button){
			CController cController=new CController();
			cController.enter(user_id,5);
			SController sController = new SController();
			sController.User_id = user_id;
			this.setVisible(false);
		}else if(event.getSource() == room6_button){
			CController cController=new CController();
			cController.enter(user_id,6);
			SController sController = new SController();
			sController.User_id = user_id;
			this.setVisible(false);
		}
	}

	public void changeScreen(String screen,Message message){
		SController sc = new SController();
		sc.message=message;
		sc.changeScreen(screen);
		this.setVisible(false);
	}

	public void displayMessage(String displayString){
		message_label.setText(displayString);
	}
}



