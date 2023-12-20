package com.example.new_highandlow;

import javax.swing.*;

public class SController{

	public void changeScreen(String screen_id){
			JFrame newScreen = null;

			switch(screen_id){
				case "Start":
					newScreen = new StartScreen();
					break;
				case "Score":
					newScreen = new ScoreScreen();
					break;
				case "Rule":
					newScreen = new RuleScreen();
					break;
				case "Wait":
					newScreen = new WaitScreen();
					break;
				case "Game":
					newScreen = new GameScreen();
					break;
				case "Result":
					newScreen = new ResultScreen();
					break;
			}

			if (newScreen != null) {
				newScreen.setVisible(true);
			}
	}
}
