package com.example.new_highandlow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ScoreScreen extends JFrame{
	private JButton return_start_button;
	private JPanel back_ground_panel;
	private JLabel title_label;
	private JTextArea score_area;

	public ScoreScreen(){
		String play_count="50";		//プレイ数、勝利数、ヒット数を仮に定数で置いています
		String win_count="25";
		String hit_count="30";
		double win_rate=Double.parseDouble(win_count)/Double.parseDouble(play_count);
		double hit_rate=Double.parseDouble(hit_count)/(Double.parseDouble(play_count)*5);
		String scoreString="プレイ回数： "+play_count+"\n\n勝利数: "+win_count+"\n\nヒット数: "+hit_count+"\n\n勝率:"+win_rate+"\n\nヒット率:"+hit_rate;

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

		title_label = new JLabel("戦績");
		title_label.setBounds(400, 40, 300, 40);
		title_label.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 30));

		return_start_button = new JButton("戻る");
		return_start_button.setBounds(750, 40, 150, 40);
		return_start_button.addActionListener(this::pushReturnStartButton);
		return_start_button.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 20));
		return_start_button.setBackground(Color.blue);
		return_start_button.setForeground(Color.white);

		score_area =new JTextArea(scoreString);
		score_area.setBounds(80,100,800,380);
		score_area.setFont(new Font("ＭＳ ゴシック",Font.BOLD,35));
		score_area.setForeground(Color.black);
		score_area.setBackground(Color.orange);
		score_area.setEditable(false);

		this.add(back_ground_panel);

		back_ground_panel.add(title_label);
		back_ground_panel.add(return_start_button);
		back_ground_panel.add(score_area);

		setContentPane(back_ground_panel);
		this.setVisible(true);
	}

	private void pushReturnStartButton(ActionEvent event){
		if(event.getSource()== return_start_button){
			CController cController=new CController();
			changeScreen("Start");
		}
	}

	public void changeScreen(String screen){
		SController sc = new SController();
		sc.changeScreen(screen);
		this.setVisible(false);
	}
}
