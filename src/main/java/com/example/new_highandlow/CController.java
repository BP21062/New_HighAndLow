package com.example.new_highandlow;

import javax.swing.SwingUtilities;

import com.google.gson.Gson;

//WebSocketClientSample.java
public class CController {
	public static String user_id;
	public static String passwd;

	private static CServerConnector lobby_connect;
	private static CServerConnector app_connect;

	// 基本的にこれをあちこちで使いまわす
	// 例外はWaitScreen, ScoreScreen, GameScreen, ResultScreen
	static LobbyScreen lobbyScreen;
	static StartScreen startScreen;
	static WaitScreen waitScreen;
	static GameScreen gameScreen;
	static ResultScreen resultScreen;
	static RuleScreen ruleScreen;
	static ScoreScreen scoreScreen;
	/*
	 * サーバ側のエンドポイントと合わせる．2箇所確認する．
	 * 1. mainメソッド内でserverインスタンスを生成する際のContextRoot
	 * サンプル例）static String contextRoot = "/app";
	 * 2. エンドポイントクラスのアノテーション（@ServerEndpoint("エンドポイント名")）
	 * サンプル例）@ServerEndpoint("/sample")
	 * この場合指定すべきエンドポイントは上記2つとサーバのアドレス、ポート番号、プロトコルから決定され、
	 * 例えば、"ws://localhost:8080/app/sample"といった形になる。
	 * この例ではプロトコルはWebSocket（ws），ポートは8080，サーバアドレスはlocalhost
	 * 使い分けるときは適宜Stringとして分割して定義し結合すれば良い．
	 */
	static String serverAppEndpoint = "ws://localhost:8082/app/playgame"; // appサーバー用
	static String serverLobbyEndpoint = "ws://localhost:8080/lobby/";

	static int id = 0;
	static String password = "password";
	static Gson gson = new Gson();

	public static boolean checkPasswordStrength() {
		return passwd.matches("(?=.*[A-Z]).{8,12}"); // パスワードが条件を満たしていればtrue それ以外はfalse
	}

	public static void registerUser() {

		Message sendMessage = new Message("2", user_id);

		sendMessage.messageContent.password = passwd;
		String sendMessageJson = gson.toJson(sendMessage);

		// ログ
		System.out.println("[client]: registerUser(): " + sendMessageJson + "\n");

		lobby_connect = new CServerConnector(serverLobbyEndpoint);
		if (lobby_connect.connect()) {
			lobby_connect.sendMessage(sendMessageJson);
		} else {
			lobbyScreen.displayMessage("※接続に失敗しました");
		}
	}

	public static void login() {

		Message sendMessage = new Message("4", user_id);

		sendMessage.messageContent.password = passwd;
		String sendMessageJson = gson.toJson(sendMessage);

		// ログ
		System.out.println("[client]: login(): " + sendMessageJson + "\n");

		lobby_connect = new CServerConnector(serverLobbyEndpoint);
		if (lobby_connect.connect()) {
			lobby_connect.sendMessage(sendMessageJson);
		} else {
			lobbyScreen.displayMessage("※接続に失敗しました");
		}
	}

	public static void logout() {

		// 結局メッセージは送らずdisconnect()だけ

		lobby_connect.disconnect();
		if (app_connect != null) {
			app_connect.disconnect();
		}

		// ログ
		System.out.println("[client]: logout(): Done");

	}

	public static void getScore() {

		Message sendMessage = new Message("8", user_id);

		String sendMessageJson = gson.toJson(sendMessage);

		// ログ
		System.out.println("[client]: getScore(): " + sendMessageJson + "\n");

		lobby_connect.sendMessage(sendMessageJson);
	}

	public static void getRule() {

		Message sendMessage = new Message("9", user_id);

		String sendMessageJson = gson.toJson(sendMessage);

		// ログ
		System.out.println("[client]: getRule(): " + sendMessageJson + "\n");

		lobby_connect.sendMessage(sendMessageJson);
	}

	public static void enter(int room_id) {
		SController.room_id = room_id;

		Message sendMessage = new Message("1003", user_id);
		sendMessage.messageContent.room_id = room_id;

		String sendMessageJson = gson.toJson(sendMessage);

		// ログ
		System.out.println("[client]: enter(): " + sendMessageJson + "\n");

		// Appサーバーへの接続の確率
		app_connect = new CServerConnector(serverAppEndpoint);
		if (app_connect.connect()) {
			app_connect.sendMessage(sendMessageJson);
			startScreen.setVisible(false);
			waitScreen = new WaitScreen(room_id);
			waitScreen.setVisible(true);
		} else {
			startScreen.displayMessage("※接続に失敗しました");
		}
	}

	public static boolean checkRoomState(String user_id, int room_id) {
		SController.room_id = room_id;

		Message sendMessage = new Message("6", user_id);

		sendMessage.messageContent.room_id = room_id;
		String sendMessageJson = gson.toJson(sendMessage);

		// ログ
		System.out.println("[client]: checkRoomState(): " + sendMessageJson + "\n");

		if (lobby_connect.isConnected()) {
			lobby_connect.sendMessage(sendMessageJson);
			return true;
		} else
			startScreen.displayMessage("※接続に失敗しました");
		return false;
	}

	public static void finishMessage(String finishTask){
		if(finishTask.equals("CurrentScore")){
			Message message = new Message("1005",user_id);
			message.result = true;
			message.messageContent.room_id = SController.room_id;
			String sendMessageJson = gson.toJson(message);
			app_connect.sendMessage(sendMessageJson);
		}else if(finishTask.equals("displayFirst")){
			Message message = new Message("1006",user_id);
			message.result = true;
			message.messageContent.room_id = SController.room_id;
			String sendMessageJson = gson.toJson(message);
			app_connect.sendMessage(sendMessageJson);
			gameScreen.startGameTimer();
		}else if(finishTask.equals("Timer")){
			Message message = new Message("1007",user_id);
			message.messageContent.choice = gameScreen.HLJChoice;
			message.messageContent.pattern = gameScreen.PTNChoice;
			message.messageContent.room_id = SController.room_id;
			String sendMessageJson = gson.toJson(message);
			app_connect.sendMessage(sendMessageJson);
		}else if(finishTask.equals("displaySecond")){
			Message message = new Message("1008",user_id);
			message.result = true;
			message.messageContent.room_id = SController.room_id;
			String sendMessageJson = gson.toJson(message);
			app_connect.sendMessage(sendMessageJson);
		}


	}

	public static void main(String[] args) {

		SwingUtilities.invokeLater(() -> {

			// ロビー画面の作成
			LobbyScreen lobbyScreen = new LobbyScreen();
			lobbyScreen.setVisible(true);
			CController.lobbyScreen = lobbyScreen;

			CController.startScreen = new StartScreen();
			// CController.ruleScreen = new RuleScreen();	// 画像があるため作成しない
			CController.scoreScreen = new ScoreScreen(null);
			// CController.gameScreen = new GameScreen(user_id);	// 再利用が難しいためなし
			CController.resultScreen = new ResultScreen();
			CController.waitScreen = new WaitScreen(-1);

		});
	}
}
