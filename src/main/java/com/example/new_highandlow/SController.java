package com.example.new_highandlow;

import javax.swing.*;

public class SController{

	static String user_id;
	int room_id;
	Message message;

	SController(String user_id){
		this.user_id = user_id;
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
					StartScreen startScreen = new StartScreen(user_id);
					newScreen = startScreen;
					CController.startScreen = startScreen;
					break;
				case "Score":
					ScoreScreen scoreScreen = new ScoreScreen(message,user_id);
					newScreen = scoreScreen;
					break;
				case "Rule":
					newScreen = new RuleScreen(message,user_id);
					break;
				case "Wait":
					newScreen = new WaitScreen(user_id,room_id);
					break;
				case "Game":
					newScreen = new GameScreen(user_id);
					break;
				case "Result":
					newScreen = new ResultScreen(user_id);
					break;
			}

			if (newScreen != null) {
				newScreen.setVisible(true);
			}
	}
}
