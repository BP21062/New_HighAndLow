package com.example.new_highandlow;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.util.Base64;

public class RuleScreen extends JFrame {

	private JPanel back_ground_panel;
	private JButton return_start_button;

	// 画像が必要なので、Messageクラスを引数にする
	public RuleScreen(Message message) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(960, 540);
		setTitle("Rule Screen");
		setLocationRelativeTo(null);
		setLayout(null);

		String base64data = message.messageContent.image_data;

		back_ground_panel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				ImageIcon image = new ImageIcon();
				try (InputStream input = new ByteArrayInputStream(Base64.getDecoder().decode(base64data))) {
					image = new ImageIcon(ImageIO.read(input));
					g.drawImage(image.getImage(), 0, 0, getWidth(), getHeight(), this);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};
		back_ground_panel.setLayout(null);
		back_ground_panel.setOpaque(false);

		return_start_button = new JButton("戻る");
		return_start_button.setBounds(750, 20, 150, 40);
		return_start_button.addActionListener(e -> pushReturnStartButton(e));
		return_start_button.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 20));
		return_start_button.setBackground(Color.BLUE);
		return_start_button.setForeground(Color.WHITE);

		back_ground_panel.add(return_start_button);

		setContentPane(back_ground_panel);

		setResizable(false);
	}

	private void pushReturnStartButton(ActionEvent event){
		if(event.getSource()== return_start_button){
			changeScreen("Start");
		}
	}

	public void changeScreen(String screen){
		this.setVisible(false);
		SController.changeScreen(screen);
	}
}
