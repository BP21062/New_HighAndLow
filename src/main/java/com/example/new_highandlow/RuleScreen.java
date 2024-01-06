package com.example.new_highandlow;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.util.Base64;

public class RuleScreen extends JFrame{
	private JButton return_start_button;
	private JPanel back_ground_panel;

	public RuleScreen(){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(960, 540);
		setTitle("Start Screen");
		setLocationRelativeTo(null);
		setLayout(null);

		//画像ファイルを読み込む
		byte[] binary = getFileBinary("src/main/resources/com/example/new_highandlow/png/img.png");

		// base64のライブラリからencodeToStringを利用してbinaryタイプ(byte[])をbase64(Stringタイプ)に変換する。
		String base64data = Base64.getEncoder().encodeToString(binary); //画像ファイルをbase64にエンコード

		InputStream input = new ByteArrayInputStream(Base64.getDecoder().decode(base64data));	//base64のデコード


		back_ground_panel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				ImageIcon image = null;
				try{
					image = new ImageIcon(ImageIO.read(input));	//base64をデコードしたものを背景画像に設定
				}catch(IOException e){
					e.printStackTrace();
				}
				g.drawImage(image.getImage(), 0, 0, getWidth(), getHeight(), this);
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

		this.add(back_ground_panel);

		back_ground_panel.add(return_start_button);

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

	private static byte[] getFileBinary(String filepath) {	//base64をエンコードするためだけのメソッド
															//本番環境では削除します
		// Fileクラスを割当てする。
		File file = new File(filepath);
		// ファイルサイズでbyteバッファを割り当てする。
		byte[] data = new byte[(int) file.length()];
		// IOのストリームを取得する。
		try (FileInputStream stream = new FileInputStream(file)) {
			// ファイルを読み込む。
			stream.read(data, 0, data.length);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		// binaryを返却。
		return data;
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			RuleScreen ruleScreen=new RuleScreen();
			ruleScreen.setVisible(true);
		});
	}
}
