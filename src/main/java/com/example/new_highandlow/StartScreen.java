package com.example.new_highandlow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartScreen extends JFrame implements ActionListener{
	private JButton btn;

	public StartScreen(){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800, 600);
		setTitle("Start Screen");
		setLocationRelativeTo(null);
		setLayout(null);

		btn = new JButton("Start");
		btn.setBounds(250, 300, 100, 40);
		btn.addActionListener(this);
		btn.setFont(new Font("Arial", Font.BOLD, 16));
		btn.setBackground(Color.blue);
		btn.setForeground(Color.white);
		this.add(btn);
		this.setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent event){
		if(event.getSource()==btn){

		}
	}

}
