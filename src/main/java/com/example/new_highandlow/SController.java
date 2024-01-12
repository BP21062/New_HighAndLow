package com.example.new_highandlow;

import javax.swing.*;

public class SController{

	String User_id;
	int Room_id;

	public void changeScreen(String screen_id){
			JFrame newScreen = null;

			switch(screen_id){
				case "Lobby":
					newScreen = new LobbyScreen();
					break;
				case "Start":
					newScreen = new StartScreen(User_id);
					break;
				case "Score":
					newScreen = new ScoreScreen();
					break;
				case "Rule":
					newScreen = new RuleScreen();
					break;
				case "Wait":
					newScreen = new WaitScreen(User_id,Room_id);
					break;
				case "Game":
					newScreen = new GameScreen(User_id);
					break;
				case "Result":
					newScreen = new ResultScreen(User_id);
					break;
			}

			if (newScreen != null) {
				newScreen.setVisible(true);
			}
	}
}
