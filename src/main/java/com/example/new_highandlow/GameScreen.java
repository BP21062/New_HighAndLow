package com.example.new_highandlow;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GameScreen extends JFrame{
	private JPanel back_ground_panel;
	private JLabel trump1_card_label, trump2_card_label;
	private JLabel remain_time_label, trump2_pattern_label, user_score_label ,user_name_label;
	private JButton high_button, just_button, low_button;
	private JButton hearts_button, diamonds_button, spades_button, clubs_button;
	private JLabel HLJ,PTN;
	public String HLJChoice,PTNChoice; //各選択肢の内容を保存する関数

	public GameScreen(){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(960, 540);
		setTitle("Game Screen");
		setLocationRelativeTo(null);
		setLayout(null);

		back_ground_panel = new JPanel(){
			@Override
			protected void paintComponent(Graphics g){
				super.paintComponent(g);
				ImageIcon image = new ImageIcon("src/main/resources/com/example/new_highandlow/png/Game.png");
				g.drawImage(image.getImage(), 0, 0, getWidth(), getHeight(), this);
			}
		};
		back_ground_panel.setLayout(null);
		back_ground_panel.setOpaque(false);

		// 最初は両方とも裏向きのカードを表示
		ImageIcon icon1 = new ImageIcon("src/main/resources/com/example/new_highandlow/png/trump2.png");
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

		user_name_label = new JLabel(CController.user_id);
		user_name_label.setBounds(50, 40, 220, 40);
		user_name_label.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 30));
		user_name_label.setForeground(Color.darkGray);

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
		high_button.setEnabled(false);

		just_button = new JButton("Just");
		just_button.setBounds(430, 350, 80, 40);
		just_button.addActionListener(this::pushButton);
		just_button.setActionCommand("Just");
		just_button.setFont(new Font("Arial", Font.BOLD, 20));
		just_button.setBackground(Color.blue);
		just_button.setForeground(Color.white);
		just_button.setEnabled(false);

		low_button = new JButton("Low");
		low_button.setBounds(560, 350, 80, 40);
		low_button.addActionListener(this::pushButton);
		low_button.setActionCommand("Low");
		low_button.setFont(new Font("Arial", Font.BOLD, 20));
		low_button.setBackground(Color.blue);
		low_button.setForeground(Color.white);
		low_button.setEnabled(false);

		HLJ = new JLabel();
		HLJ.setBounds(710,350,80,80);
		HLJ.setFont(new Font("Arial", Font.BOLD, 30));
		HLJ.setOpaque(true);
		HLJ.setBackground(Color.pink);
		HLJ.setForeground(Color.black);

		hearts_button = new JButton("♥");
		hearts_button.setBounds(290, 420, 50, 50);
		hearts_button.addActionListener(this::pushPatternButton);
		hearts_button.setActionCommand("heart");
		hearts_button.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 30));
		hearts_button.setBackground(Color.white);
		hearts_button.setForeground(Color.red);
		hearts_button.setEnabled(false);

		diamonds_button = new JButton("♦");
		diamonds_button.setBounds(390, 420, 50, 50);
		diamonds_button.addActionListener(this::pushPatternButton);
		diamonds_button.setActionCommand("dia");
		diamonds_button.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 30));
		diamonds_button.setBackground(Color.white);
		diamonds_button.setForeground(Color.red);
		diamonds_button.setEnabled(false);

		spades_button = new JButton("♠");
		spades_button.setBounds(490, 420, 50, 50);
		spades_button.addActionListener(this::pushPatternButton);
		spades_button.setActionCommand("spade");
		spades_button.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 30));
		spades_button.setBackground(Color.white);
		spades_button.setForeground(Color.black);
		spades_button.setEnabled(false);

		clubs_button = new JButton("♣");
		clubs_button.setBounds(590, 420, 50, 50);
		clubs_button.addActionListener(this::pushPatternButton);
		clubs_button.setActionCommand("club");
		clubs_button.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 30));
		clubs_button.setBackground(Color.white);
		clubs_button.setForeground(Color.black);
		clubs_button.setEnabled(false);

		PTN = new JLabel();
		PTN.setBounds(710,420,50,50);
		PTN.setFont(new Font("Arial", Font.BOLD, 30));
		PTN.setOpaque(true);
		PTN.setBackground(Color.pink);
		PTN.setForeground(Color.black);

		this.add(back_ground_panel);

		back_ground_panel.add(trump1_card_label);
		back_ground_panel.add(trump2_card_label);
		back_ground_panel.add(user_name_label);
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
		back_ground_panel.add(HLJ);
		back_ground_panel.add(PTN);
		setContentPane(back_ground_panel);
		setResizable(false);

	}

	public void displayRemainTime(int time){
		String num = String.format(" 残り時間：%d ", time);
		remain_time_label.setText(num);
		if(time == 0){
			remain_time_label.setText(" 待機中... ");
		}
	}

	public void displayFirstCard(String cardcode){
		
		back_ground_panel.remove(trump1_card_label);
		
		ImageIcon image;
		try (InputStream input = new ByteArrayInputStream(Base64.getDecoder().decode(cardcode.split(",")[1]))) {
			image = new ImageIcon(ImageIO.read(input));
			image = PictureBuilder.resizeIcon(image,140,200);
			trump1_card_label = new JLabel(image);
			trump1_card_label.setBounds(300, 130, 140, 200);;
		} catch (IOException e) {
			e.printStackTrace();
		}

		back_ground_panel.add(trump1_card_label);
		setContentPane(back_ground_panel);

		CController.finishMessage("displayFirst");
	}

	public void displaySecondCard(String cardcode){

		back_ground_panel.remove(trump2_card_label);
		
		ImageIcon image;
		try (InputStream input = new ByteArrayInputStream(Base64.getDecoder().decode(cardcode.split(",")[1]))) {
			image = new ImageIcon(ImageIO.read(input));
			image = PictureBuilder.resizeIcon(image,140,200);
			trump2_card_label = new JLabel(image);
			trump2_card_label.setBounds(500, 130, 140, 200);
		} catch (IOException e) {
			e.printStackTrace();
		}

		back_ground_panel.add(trump2_card_label);
		setContentPane(back_ground_panel);

		try{
			Thread.sleep(5000);
		}catch(InterruptedException e){
			e.printStackTrace();
		}
		
		// 再びカードを裏返す
		back_ground_panel.remove(trump2_card_label);
		ImageIcon icon2 = new ImageIcon("src/main/resources/com/example/new_highandlow/png/trump2.png");
		trump2_card_label = new JLabel(icon2);
		trump2_card_label.setBounds(500, 130, 140, 200);
		back_ground_panel.add(trump2_card_label);
		setContentPane(back_ground_panel);

		CController.finishMessage("displaySecond");
	}

	public void displaySecondCardInformation(List<Integer> pattern_list){
		List<Integer> patterns = new ArrayList<>();
		// たぶんこれも値をコピーしたかった？
		for(int a = 0; a < 4; a++){
			patterns.add(pattern_list.get(a));
		}
		trump2_pattern_label.setText("<html><body>&nbsp;2枚目の柄<br />" + "&nbsp;&emsp;♡×" + patterns.get(0) + "<br />" + "&nbsp;&emsp;♢×" + patterns.get(1) + "<br />" + "&nbsp;&emsp;♠×" + patterns.get(2) + "<br />" + "&nbsp;&emsp;♣×" + patterns.get(3) + "<br /></body></html>");
	}

	public void displayCurrentScore(List<Integer> score_list,List<String> user_list){
		List<Integer> scores = new ArrayList<>();

		// たぶんArrayListをコピーしたかった？
		for(int a = 0; a < score_list.size(); a++){
			scores.add(score_list.get(a));
		}

		String text = "<html><body>";

		// 人数が減った時対策
		for(int i = 0; i < user_list.size(); i++){
			text += "&nbsp;" + user_list.get(i) + ":" + scores.get(i) + "<br />";
		}

		text += "</body></html>";

		user_score_label.setText(text);

		CController.finishMessage("CurrentScore");
	}

	public void turnUP(){
		spades_button.setEnabled(true);
		diamonds_button.setEnabled(true);
		clubs_button.setEnabled(true);
		hearts_button.setEnabled(true);
	}

	public void pushButton(ActionEvent event){
		String cmd = event.getActionCommand();
		if(cmd.equals("High")){
			HLJChoice = "high";
			HLJ.setText("High");
			turnUP();
		}else if(cmd.equals("Low")){
			HLJChoice = "low";
			HLJ.setText("Low");
			turnUP();
		}else if(cmd.equals("Just")){
			HLJChoice = "just";
			HLJ.setText("Just");
			turnUP();
		}
	}

	public void pushPatternButton(ActionEvent event){
		String cmd = event.getActionCommand();
		if(cmd.equals("heart")){
			PTNChoice = "heart";
			PTN.setText("♥");
		}else if(cmd.equals("dia")){
			PTNChoice = "diamond";
			PTN.setText("♦");
		}else if(cmd.equals("club")){
			PTNChoice = "club";
			PTN.setText("♣");
		}else if(cmd.equals("spade")){
			PTNChoice = "spade";
			PTN.setText("♠");
		}
	}

	public void startGameTimer(){
		high_button.setEnabled(true);
		just_button.setEnabled(true);
		low_button.setEnabled(true);
		PTN.setText("");
		HLJ.setText("");
		PTNChoice = null;
		HLJChoice = null;

		for(int j = 0; j < 20; j++){
			displayRemainTime(20 - j);
			try{
				Thread.sleep(1000);
			}catch(InterruptedException e){
				e.printStackTrace();
			}
		}
		displayRemainTime(0);
		high_button.setEnabled(false);
		just_button.setEnabled(false);
		low_button.setEnabled(false);
		hearts_button.setEnabled(false);
		clubs_button.setEnabled(false);
		diamonds_button.setEnabled(false);
		spades_button.setEnabled(false);

		CController.finishMessage("Timer");

	}

	public void changeScreen(String screen) {
		this.setVisible(false);
		SController.changeScreen(screen);
	}

}


class PictureBuilder {
    public static ImageIcon resizeIcon(ImageIcon icon, int w, int h){
        Image CGresize = icon.getImage();
         BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = resizedImg.createGraphics();
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2.drawImage(CGresize, 0, 0, w, h, null);
            g2.dispose();
            ImageIcon resized = new ImageIcon();
            resized.setImage(resizedImg);
            return resized;
    }
}

