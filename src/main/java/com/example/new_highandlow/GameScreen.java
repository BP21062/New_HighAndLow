package com.example.new_highandlow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

public class GameScreen extends JFrame implements Runnable{
	private ArrayList<String> deck_information = new ArrayList<>();
	private ArrayList<String> card_information = new ArrayList<>();
	private int trump2_pattern_hearts, trump2_pattern_diamonds, trump2_pattern_spades, trump2_pattern_clubs;
	private int current_score_player1, current_score_player2, current_score_player3, current_score_player4;
	private JPanel back_ground_panel;
	private JLabel trump1_card_label, trump2_card_label;
	private JLabel remain_time_label, trump2_pattern_label, user_score_label;
	private JButton high_button, just_button, low_button;
	private JButton hearts_button, diamonds_button, spades_button, clubs_button;
	private JLabel HLJChoice,PTChoice;
	private String user_id;

	public GameScreen(String user_id){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(960, 540);
		setTitle("Game Screen");
		setLocationRelativeTo(null);
		setLayout(null);

		back_ground_panel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				ImageIcon image = new ImageIcon("src/main/resources/com/example/new_highandlow/png/Game.png");
				g.drawImage(image.getImage(), 0, 0, getWidth(), getHeight(), this);
			}
		};
		back_ground_panel.setLayout(null);
		back_ground_panel.setOpaque(false);

		ImageIcon icon1 = new ImageIcon("src/main/resources/com/example/new_highandlow/png/trump1.png");
		trump1_card_label = new JLabel(icon1);
		trump1_card_label.setBounds(300, 130, 140, 200);

		ImageIcon icon2 = new ImageIcon("src/main/resources/com/example/new_highandlow/png/trump2.png");
		trump2_card_label = new JLabel(icon2);
		trump2_card_label.setBounds(500, 130, 140, 200);

		remain_time_label = new JLabel();
		remain_time_label.setBounds(360, 40, 220, 40);
		remain_time_label.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 30));
		remain_time_label.setOpaque(true);
		remain_time_label.setBackground(Color.red);
		remain_time_label.setForeground(Color.white);
		displayRemainTime(10);

		trump2_pattern_label = new JLabel();
		trump2_pattern_label.setBounds(50, 140, 180, 180);
		trump2_pattern_label.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 30));
		trump2_pattern_label.setOpaque(true);
		trump2_pattern_label.setBackground(Color.pink);
		trump2_pattern_label.setForeground(Color.black);

		user_score_label = new JLabel();
		user_score_label.setBounds(710, 140, 190, 180);
		user_score_label.setFont(new Font("Arial", Font.BOLD, 30));
		user_score_label.setOpaque(true);
		user_score_label.setBackground(Color.pink);
		user_score_label.setForeground(Color.black);

		high_button = new JButton("High");
		high_button.setBounds(300, 350, 80, 40);
		high_button.addActionListener(this::pushButton);
		high_button.setActionCommand("High");
		high_button.setFont(new Font("Arial", Font.BOLD, 20));
		high_button.setBackground(Color.blue);
		high_button.setForeground(Color.white);

		just_button = new JButton("Just");
		just_button.setBounds(430, 350, 80, 40);
		just_button.addActionListener(this::pushButton);
		just_button.setActionCommand("Just");
		just_button.setFont(new Font("Arial", Font.BOLD, 20));
		just_button.setBackground(Color.blue);
		just_button.setForeground(Color.white);

		low_button = new JButton("Low");
		low_button.setBounds(560, 350, 80, 40);
		low_button.addActionListener(this::pushButton);
		low_button.setActionCommand("Low");
		low_button.setFont(new Font("Arial", Font.BOLD, 20));
		low_button.setBackground(Color.blue);
		low_button.setForeground(Color.white);

		hearts_button = new JButton("♥");
		hearts_button.setBounds(290, 420, 50, 50);
		hearts_button.addActionListener(this::pushPatternButton);
		hearts_button.setActionCommand("heart");
		hearts_button.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 30));
		hearts_button.setBackground(Color.white);
		hearts_button.setForeground(Color.red);

		diamonds_button = new JButton("♦");
		diamonds_button.setBounds(390, 420, 50, 50);
		diamonds_button.addActionListener(this::pushPatternButton);
		diamonds_button.setActionCommand("dia");
		diamonds_button.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 30));
		diamonds_button.setBackground(Color.white);
		diamonds_button.setForeground(Color.red);

		spades_button = new JButton("♠");
		spades_button.setBounds(490, 420, 50, 50);
		spades_button.addActionListener(this::pushPatternButton);
		spades_button.setActionCommand("spade");
		spades_button.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 30));
		spades_button.setBackground(Color.white);
		spades_button.setForeground(Color.black);

		clubs_button = new JButton("♣");
		clubs_button.setBounds(590, 420, 50, 50);
		clubs_button.addActionListener(this::pushPatternButton);
		clubs_button.setActionCommand("club");
		clubs_button.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 30));
		clubs_button.setBackground(Color.white);
		clubs_button.setForeground(Color.black);

		this.add(back_ground_panel);

		back_ground_panel.add(trump1_card_label);
		back_ground_panel.add(trump2_card_label);
		back_ground_panel.add(remain_time_label);
		back_ground_panel.add(trump2_pattern_label);
		back_ground_panel.add(user_score_label);
		back_ground_panel.add(high_button);
		back_ground_panel.add(just_button);
		back_ground_panel.add(low_button);
		back_ground_panel.add(hearts_button);
		back_ground_panel.add(diamonds_button);
		back_ground_panel.add(spades_button);
		back_ground_panel.add(clubs_button);
		setContentPane(back_ground_panel);

		this.user_id = user_id;

		Thread thread = new Thread(this);
		thread.start();
	}

	public void displayRemainTime(int time){
		String num = String.format(" 残り時間：%d ", time);
		remain_time_label.setText(num);
	}

	public void displaySecondCardInformation(List<Integer> pattern_list){
		List<Integer> patterns = new ArrayList<>();
		for(int a=0;a<4;a++){
			patterns.set(a, pattern_list.get(a));
		}
		String num = String.format("<html><body>&nbsp;2枚目の柄<br />" +
				"&nbsp;&emsp;♡×" + patterns.get(0) + "<br />" +
				"&nbsp;&emsp;♢×" + patterns.get(1) + "<br />" +
				"&nbsp;&emsp;♠×" + patterns.get(2) + "<br />" +
				"&nbsp;&emsp;♣×" + patterns.get(3) + "<br /></body></html>");
		trump2_pattern_label.setText(num);
	}

	public void displayCurrentScore(List<Integer> score_list){
		List<Integer> scores = new ArrayList<>();
		for(int a=0;a<4;a++){
			scores.set(a, score_list.get(a));
		}
		String num = String.format("<html><body>&nbsp;Player1：" + scores.get(0) + "<br />" +
				"&nbsp;Player2：" + scores.get(1) + "<br />" +
				"&nbsp;Player3：" + scores.get(2) + "<br />" +
				"&nbsp;Player4：" + scores.get(3) + "<br /></body></html>");
		user_score_label.setText(num);
	}

	/*
	public void pushHighButton(ActionEvent event){
		if(event.getSource()==high_button){}
	}

	public void pushJustButton(ActionEvent event){
		if(event.getSource()==just_button){}
	}

	public void pushLowButton(ActionEvent event){
		if(event.getSource()==low_button){}
	}

	public void pushHeartsButton(ActionEvent event){
		if(event.getSource()==hearts_button){}
	}

	public void pushDiamondsButton(ActionEvent event){
		if(event.getSource()==diamonds_button){}
	}

	public void pushSpadesButton(ActionEvent event){
		if(event.getSource()==spades_button){}
	}

	public void pushClubsButton(ActionEvent event){
		if(event.getSource()==clubs_button){}
	}
*/

	public void turnUP(){
		spades_button.setEnabled(true);
		diamonds_button.setEnabled(true);
		clubs_button.setEnabled(true);
		hearts_button.setEnabled(true);
	}

	public void pushButton(ActionEvent event){
		String cmd = event.getActionCommand();
		if(cmd.equals("High")){
			System.out.println("h");
			turnUP();
		}else if(cmd.equals("Low")){
			System.out.println("l");
			turnUP();
		}else if(cmd.equals("Just")){
			System.out.println("j");
			turnUP();
		}
	}

	public void pushPatternButton(ActionEvent event){
		String cmd = event.getActionCommand();
		if(cmd.equals("heart")){
			System.out.println("he");
		}else if(cmd.equals("dia")){
			System.out.println("d");
		}else if(cmd.equals("club")){
			System.out.println("c");
		}else if(cmd.equals("spade")){
			System.out.println("s");
		}
	}
	@Override
	public void run(){
		for(int i=0;i<5;i++){
			high_button.setEnabled(false);
			just_button.setEnabled(false);
			low_button.setEnabled(false);
			spades_button.setEnabled(false);
			diamonds_button.setEnabled(false);
			clubs_button.setEnabled(false);
			hearts_button.setEnabled(false);
			try{
				Thread.sleep(1000);
			}catch(InterruptedException e){
				e.printStackTrace();
			}
			high_button.setEnabled(true);
			just_button.setEnabled(true);
			low_button.setEnabled(true);

			for(int j=0;j<5;j++){
				displayRemainTime(5-j);
				try{
					Thread.sleep(1000);
				}catch(InterruptedException e){
					e.printStackTrace();
				}
			}
			displayRemainTime(0);
		}

		SController sController = new SController();
		sController.User_id = user_id;
		sController.changeScreen("Result");
		this.setVisible(false);
	}
}
