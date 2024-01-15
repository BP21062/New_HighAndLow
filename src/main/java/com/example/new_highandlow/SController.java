package com.example.new_highandlow;

import javax.swing.*;

public class SController {

	// staticにすることで共通化
	static int room_id;
	static Message message;

	// GameScreenは毎回作り直す
	// 他は使いまわす

	// オーバーロードする
	public static void changeScreen(String screen_id, Message message) {
		if (screen_id.equals("Wait") || screen_id.equals("Score") || screen_id.equals("Result") || screen_id.equals("Rule")) {
			SController.message = message;
		} else {
			System.out.println("[client] Error: changeScreen()");
		}

		// websocketから来た画像
		if (screen_id.equals("Rule")) {
			CController.ruleScreen = new RuleScreen(message);
		}

		changeScreen(screen_id);
	}

	// 画面遷移用
	public static void changeScreen(String screen_id) {
		JFrame newScreen = null;

		// 使いまわせるなら使いまわした方がいい
		switch (screen_id) {
			case "Lobby":
				// これはCControllerの起動時に作成される
				newScreen = CController.lobbyScreen;
				break;
			case "Start":
				// これはCControllerの起動時に作成される
				newScreen = CController.startScreen;
				break;
			case "Score":
				// これはCControllerの起動時に作成される
				// 再利用
				CController.scoreScreen.reload(message);
				newScreen = CController.scoreScreen;
				break;
			case "Rule":
				// websocketから来たら作成される
				newScreen = CController.ruleScreen;
				break;
			case "Wait":
				// これはCControllerの起動時に作成される
				// 再利用
				CController.waitScreen.reload(SController.room_id);
				newScreen = CController.waitScreen;
				break;
			case "Game":
				// websocketから来たら作成される
				newScreen = CController.gameScreen;
				break;
			case "Result":
				// これはCControllerの起動時に作成される
				// 再利用
				newScreen = CController.resultScreen;
				break;
		}

		if (newScreen != null) {
			newScreen.setVisible(true);
		}
	}
}
