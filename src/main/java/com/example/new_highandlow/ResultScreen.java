package com.example.new_highandlow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class ResultScreen extends JFrame{
	private JPanel back_ground_panel;
	private JLabel result_label;
	private JButton return_start_button;

	public ResultScreen(){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(960, 540);
		setTitle("Result Screen");
		setLocationRelativeTo(null);
		setLayout(null);

		back_ground_panel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				ImageIcon image = new ImageIcon("src/main/resources/com/example/new_highandlow/png/Result.png");
				g.drawImage(image.getImage(), 0, 0, getWidth(), getHeight(), this);
			}
		};
		back_ground_panel.setLayout(null);
		back_ground_panel.setOpaque(false);

		result_label = new JLabel();
		result_label.setBounds(400, 220, 450, 100);
		result_label.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 80));
		//result_label.setOpaque(true);

		return_start_button = new JButton("Return");
		return_start_button.setBounds(400, 400, 150, 50);
		return_start_button.addActionListener(this::pushReturnStartButton);
		return_start_button.setFont(new Font("Arial", Font.BOLD, 20));
		return_start_button.setBackground(Color.blue);
		return_start_button.setForeground(Color.white);

		this.add(back_ground_panel);

		back_ground_panel.add(result_label);
		back_ground_panel.add(return_start_button);
		setContentPane(back_ground_panel);

		setResizable(false);
	}

	public void displayResult(List<Integer> score_list, List<String> user_list){
		String targetUser = CController.user_id;
		System.out.println(CController.user_id);
		System.out.println(score_list);
		int targetScore = -1;
		for(String user : user_list){
			if(user.equals(targetUser)){
				targetScore = score_list.get(user_list.indexOf(user));
				break;
			}
		}
		System.out.println(targetScore);
		// ターゲットユーザーのスコアより高いユーザーの数を数える
		int targetUserRank = 1;
		for(Integer score : score_list){
			if(targetScore < score){
				targetUserRank++;
			}
		}
		System.out.println(targetUserRank);

		if(targetUserRank == 1){
			result_label.setText("1位!");
			result_label.setForeground(Color.orange);
		}else if(targetUserRank == 2){
			result_label.setText("2位!");
			result_label.setForeground(Color.cyan);
		}else if(targetUserRank == 3){
			result_label.setText("3位!");
			result_label.setForeground(Color.pink);
		}else if(targetUserRank == 4){
			result_label.setText("4位...");
			result_label.setForeground(Color.blue);
		}else{
			result_label.setText("Error");
			result_label.setForeground(Color.black);
		}
	}

	public void pushReturnStartButton(ActionEvent event){
		// 切断してロビーに戻る
		if(event.getSource()==return_start_button){
			CController.logout();
			changeScreen("Lobby");
		}
	}

	public void changeScreen(String screen) {
		this.setVisible(false);
		SController.changeScreen(screen);
		
	}
}
