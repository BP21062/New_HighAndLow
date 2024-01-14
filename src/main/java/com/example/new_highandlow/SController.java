package com.example.new_highandlow;

import javax.swing.*;

public class SController{

	String User_id;
	String passWard;
	int Room_id;
	Message message;

	SController(String user_id){
		this.User_id = user_id;
	}

	public void changeScreen(String screen_id){
			JFrame newScreen = null;

			switch(screen_id){
				case "Lobby":
					LobbyScreen lobbyScreen = new LobbyScreen();
					newScreen = lobbyScreen;
					CController.lobbyScreen = lobbyScreen;
					break;
				case "Start":
					StartScreen startScreen = new StartScreen(User_id);
					newScreen = startScreen;
					CController.startScreen = startScreen;
					break;
				case "Score":
					ScoreScreen scoreScreen = new ScoreScreen(message,User_id);
					newScreen = scoreScreen;
					break;
				case "Rule":
					newScreen = new RuleScreen(message,User_id);
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
