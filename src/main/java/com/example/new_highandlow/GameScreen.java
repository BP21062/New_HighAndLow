package com.example.new_highandlow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class GameScreen extends JFrame{
	private ArrayList<String> deck_information = new ArrayList<>();
	private ArrayList<String> card_information = new ArrayList<>();
	private int trump2_pattern_hearts, trump2_pattern_diamonds, trump2_pattern_spades, trump2_pattern_clubs;
	private int current_score_player1, current_score_player2, current_score_player3, current_score_player4;
	private JPanel back_ground_panel;
	private JLabel trump1_card_label, trump2_card_label;
	private JLabel remain_time_label, trump2_pattern_label, user_score_label;
	private JButton high_button, just_button, low_button;
	private JButton hearts_button, diamonds_button, spades_button, clubs_button;

	public GameScreen(){
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

		int [] patterns = displaySecondCardInformation();
		trump2_pattern_hearts = patterns[0];
		trump2_pattern_diamonds = patterns[1];
		trump2_pattern_spades = patterns[2];
		trump2_pattern_clubs = patterns[3];
		trump2_pattern_label = new JLabel("<html><body>&nbsp;2枚目の柄<br />" +
				"&nbsp;&emsp;♡×" + trump2_pattern_hearts + "<br />" +
				"&nbsp;&emsp;♢×" + trump2_pattern_diamonds + "<br />" +
				"&nbsp;&emsp;♠×" + trump2_pattern_spades + "<br />" +
				"&nbsp;&emsp;♣×" + trump2_pattern_clubs + "<br /></body></html>");
		trump2_pattern_label.setBounds(50, 140, 180, 180);
		trump2_pattern_label.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 30));
		trump2_pattern_label.setOpaque(true);
		trump2_pattern_label.setBackground(Color.pink);
		trump2_pattern_label.setForeground(Color.black);

		int[] scores = displayCurrentScore();
		current_score_player1 = scores[0];
		current_score_player2 = scores[1];
		current_score_player3 = scores[2];
		current_score_player4 = scores[3];
		user_score_label = new JLabel("<html><body>&nbsp;Player1：" + current_score_player1 + "<br />" +
				"&nbsp;Player2：" + current_score_player2 + "<br />" +
				"&nbsp;Player3：" + current_score_player3 + "<br />" +
				"&nbsp;Player4：" + current_score_player4 + "<br /></body></html>");
		user_score_label.setBounds(710, 140, 190, 180);
		user_score_label.setFont(new Font("Arial", Font.BOLD, 30));
		user_score_label.setOpaque(true);
		user_score_label.setBackground(Color.pink);
		user_score_label.setForeground(Color.black);

		high_button = new JButton("High");
		high_button.setBounds(300, 350, 80, 40);
		high_button.addActionListener(this::pushHighButton);
		high_button.setFont(new Font("Arial", Font.BOLD, 20));
		high_button.setBackground(Color.blue);
		high_button.setForeground(Color.white);

		just_button = new JButton("Just");
		just_button.setBounds(430, 350, 80, 40);
		just_button.addActionListener(this::pushJustButton);
		just_button.setFont(new Font("Arial", Font.BOLD, 20));
		just_button.setBackground(Color.blue);
		just_button.setForeground(Color.white);

		low_button = new JButton("Low");
		low_button.setBounds(560, 350, 80, 40);
		low_button.addActionListener(this::pushLowButton);
		low_button.setFont(new Font("Arial", Font.BOLD, 20));
		low_button.setBackground(Color.blue);
		low_button.setForeground(Color.white);

		hearts_button = new JButton("♥");
		hearts_button.setBounds(290, 420, 50, 50);
		hearts_button.addActionListener(this::pushHeartsButton);
		hearts_button.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 30));
		hearts_button.setBackground(Color.white);
		hearts_button.setForeground(Color.red);

		diamonds_button = new JButton("♦");
		diamonds_button.setBounds(390, 420, 50, 50);
		diamonds_button.addActionListener(this::pushDiamondsButton);
		diamonds_button.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 30));
		diamonds_button.setBackground(Color.white);
		diamonds_button.setForeground(Color.red);

		spades_button = new JButton("♠");
		spades_button.setBounds(490, 420, 50, 50);
		spades_button.addActionListener(this::pushSpadesButton);
		spades_button.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 30));
		spades_button.setBackground(Color.white);
		spades_button.setForeground(Color.black);

		clubs_button = new JButton("♣");
		clubs_button.setBounds(590, 420, 50, 50);
		clubs_button.addActionListener(this::pushClubsButton);
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

		this.setVisible(true);
	}

	public void displayRemainTime(int time){
		String num = String.format(" 残り時間：%d ", time);
		remain_time_label.setText(num);
	}

	public static int[] displaySecondCardInformation(){
		int [] patterns = new int[4];
		patterns[0] = 3;
		patterns[1] = 7;
		patterns[2] = 2;
		patterns[3] = 8;
		return patterns;
	}

	public static int[] displayCurrentScore(){
		int [] scores = new int[4];
		scores[0] = 10;
		scores[1] = 5;
		scores[2] = 0;
		scores[3] = 2;
		return scores;
	}

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
}
