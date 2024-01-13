package com.example.new_highandlow;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.util.Base64;

public class RuleScreen extends JFrame {
	private JButton return_start_button;
	private JPanel back_ground_panel;

	private String user_id;

	public RuleScreen(Message message, String user_id) {
		this.user_id = user_id;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(960, 540);
		setTitle("Rule Screen");
		setLocationRelativeTo(null);
		setLayout(null);

		String base64data = message.messageContent.image_data;

		try (InputStream input = new ByteArrayInputStream(Base64.getDecoder().decode(base64data))) {
			back_ground_panel = new JPanel() {
				@Override
				protected void paintComponent(Graphics g) {
					super.paintComponent(g);
					ImageIcon image = new ImageIcon();
					System.out.println(image);
					try {
						image = new ImageIcon(ImageIO.read(input)); // base64をデコードしたものを背景画像に設定
						g.drawImage(image.getImage(), 0, 0, getWidth(), getHeight(), this);
						System.out.println(image);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			};
			back_ground_panel.setLayout(null);
			back_ground_panel.setOpaque(false);

			return_start_button = new JButton("戻る");
			return_start_button.setBounds(750, 20, 150, 40);
			return_start_button.addActionListener(this::pushReturnStartButton);
			return_start_button.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 20));
			return_start_button.setBackground(Color.blue);
			return_start_button.setForeground(Color.white);

			back_ground_panel.add(return_start_button);

			setContentPane(back_ground_panel);
			this.setVisible(true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void pushReturnStartButton(ActionEvent event){
		if(event.getSource()== return_start_button){
			changeScreen("Start");
		}
	}

	public void changeScreen(String screen){
		SController sc = new SController(user_id);
		sc.changeScreen(screen);
		this.setVisible(false);
	}
}
