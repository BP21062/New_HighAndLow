package com.example.new_highandlow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class ResultScreen extends JFrame{
	private JPanel back_ground_panel;
	private JLabel result_label;
	private JButton return_start_button;
	private String user_id;

	public ResultScreen(String user_Id){
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

		this.user_id = user_Id;
		this.setVisible(true);
	}

	public void displayResult(List<Integer> score_list, List<String> user_list){
		int score=0, rank=1;
		for (int i = 0; i < user_list.size(); i++) {
			if (user_list.get(i).equals(user_id)) {
				score = score_list.get(i);
				break;
			}
		}
		for (int temp : score_list) {
			if (temp > score) {
				rank++;
			}
		}
		String result = Integer.toString(rank);
		result_label.setText(result);
		result_label.setForeground(Color.orange);
	}

	public void pushReturnStartButton(ActionEvent event){
		if(event.getSource()==return_start_button){
			SController sc = new SController();
			sc.changeScreen("Start");
			this.setVisible(false);
		}
	}
}
