package com.example.new_highandlow;

import java.io.IOException;
import java.util.Random;

import jakarta.websocket.DeploymentException;

import com.google.gson.Gson;

//WebSocketClientSample.java
public class CController implements Runnable{
	String User_id;
	String passwd;

	static CServerConnector wsManager;
	static LobbyScreen lobbyScreen;
	static StartScreen startScreen;
	static WaitScreen waitScreen;
	static GameScreen gameScreen;
	static ResultScreen resultScreen;
	/*
	 *  サーバ側のエンドポイントと合わせる．2箇所確認する．
	 *  1. mainメソッド内でserverインスタンスを生成する際のContextRoot
	 *     サンプル例）static String contextRoot = "/app";
	 *  2. エンドポイントクラスのアノテーション（@ServerEndpoint("エンドポイント名")）
	 *     サンプル例）@ServerEndpoint("/sample")
	 *  この場合指定すべきエンドポイントは上記2つとサーバのアドレス、ポート番号、プロトコルから決定され、
	 *  例えば、"ws://localhost:8080/app/sample"といった形になる。
	 *  この例ではプロトコルはWebSocket（ws），ポートは8080，サーバアドレスはlocalhost
	 *  使い分けるときは適宜Stringとして分割して定義し結合すれば良い．
	 */
	static String serverAppEndpoint = "ws://localhost:8082/app/playgame"; //appサーバー用
	static String serverLobbyEndpoint = "ws://localhost:8080/lobby/";


	static int id = 0;
	static String password = "password";
	static Gson gson = new Gson();

	public static void main(String[] args) throws IOException, DeploymentException {

		wsManager = new CServerConnector(serverAppEndpoint);
		wsManager.connect();

		new CController();
	}

	CController() {
		new Thread(this).start();
		// 一例として乱数値をIDにしてみる。複数起動できるかの例示のために使っている。
		Random random = new Random();
		CController.id = random.nextInt(100);
	}

	public void run() {
		while(true) {
			if(wsManager.isConnected()) {
				//System.out.println("sendMessage()");
				// 試しにSampleMessageのインスタンスを作ってみる
				//Message sendMessage = new Message("test", "user_id");
				// クラスオブジェクトをString (JSON) に変換する
				//String sendMessageJson = gson.toJson(sendMessage);
				// 変換後の書式を表示してみる。（JSON）
				//System.out.println(sendMessageJson);
				//wsManager.sendMessage(sendMessageJson);

				// LobbyUnitTest

				CServerConnector lobby = new CServerConnector(serverLobbyEndpoint);
				lobby.connect();

				// registerUser
				Message message2 = new Message("2","user");
				message2.messageContent.password = "password";

				// login
				Message message4 = new Message("4", "user");
				message4.messageContent.password = "password";

				// checkRoomState
				Message message6 = new Message("6","user");
				message6.messageContent.room_id = 2;

				// getScore
				Message message8 = new Message("8","user");

				// getRule
				Message message9 = new Message("9","user");

				// logout
				Message message7 = new Message("7","user");

				lobby.sendMessage(gson.toJson(message2));
				lobby.sendMessage(gson.toJson(message4));
				lobby.sendMessage(gson.toJson(message6));
				lobby.sendMessage(gson.toJson(message8));
				lobby.sendMessage(gson.toJson(message9));
				lobby.sendMessage(gson.toJson(message7));



			}
			try {
				Thread.sleep(100000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public boolean checkPasswordStrength(){
		return this.passwd.matches("(?=.*[A-Z]).{8,12}"); //パスワードが条件を満たしていればtrue　それ以外はfalse
	}

	public void registerUser(){
		System.out.println("sendMessage()");
		// 試しにSampleMessageのインスタンスを作ってみる
		Message sendMessage = new Message("2", User_id);
		// クラスオブジェクトをString (JSON) に変換する
		sendMessage.messageContent.password=this.passwd;
		String sendMessageJson = gson.toJson(sendMessage);
		// 変換後の書式を表示してみる。JSON
		System.out.println(sendMessageJson);
		wsManager = new CServerConnector(serverLobbyEndpoint);
		if(wsManager.connect()){
			wsManager.sendMessage(sendMessageJson);
		}
		else{
			lobbyScreen.displayMessage("※接続に失敗しました");
		}
	}

	public void login(){
		System.out.println("sendMessage()");
		// 試しにSampleMessageのインスタンスを作ってみる
		Message sendMessage = new Message("4", User_id);
		// クラスオブジェクトをString (JSON) に変換する
		sendMessage.messageContent.password=this.passwd;
		String sendMessageJson = gson.toJson(sendMessage);
		// 変換後の書式を表示してみる。JSON
		System.out.println(sendMessageJson);
		wsManager = new CServerConnector(serverLobbyEndpoint);
		if(wsManager.connect()){
			wsManager.sendMessage(sendMessageJson);
		}
		else{
			lobbyScreen.displayMessage("※接続に失敗しました");
		}
	}

	public void logout(String user_id){
		System.out.println("sendMessage()");
		// 試しにSampleMessageのインスタンスを作ってみる
		Message sendMessage = new Message("7", user_id);
		// クラスオブジェクトをString (JSON) に変換する
		String sendMessageJson = gson.toJson(sendMessage);
		// 変換後の書式を表示してみる。JSON
		System.out.println(sendMessageJson);
		wsManager = new CServerConnector(serverLobbyEndpoint);
		wsManager.disconnect();
		wsManager.sendMessage(sendMessageJson);
	}


	public void getScore(String user){
		System.out.println("sendMessage()");
		// 試しにSampleMessageのインスタンスを作ってみる
		Message sendMessage = new Message("1000", user);
		// クラスオブジェクトをString (JSON) に変換する
		String sendMessageJson = gson.toJson(sendMessage);
		// 変換後の書式を表示してみる。JSON
		System.out.println(sendMessageJson);
		wsManager = new CServerConnector(serverLobbyEndpoint);
		wsManager.connect();
		wsManager.sendMessage(sendMessageJson);
	}

	public void getRule(){
		System.out.println("sendMessage()");
		// 試しにSampleMessageのインスタンスを作ってみる
		Message sendMessage = new Message("1001", User_id);
		// クラスオブジェクトをString (JSON) に変換する
		String sendMessageJson = gson.toJson(sendMessage);
		// 変換後の書式を表示してみる。JSON
		System.out.println(sendMessageJson);
		wsManager = new CServerConnector(serverLobbyEndpoint);
		wsManager.connect();
		wsManager.sendMessage(sendMessageJson);
	}

	public void enter(String user_id,int room_id){
		SController sc = new SController(user_id);
		sc.Room_id = room_id;
		sc.User_id = user_id;
		System.out.println("sendMessage()");
		// 試しにSampleMessageのインスタンスを作ってみる
		Message sendMessage = new Message("1003", user_id);
		sendMessage.messageContent.room_id = room_id;
		// クラスオブジェクトをString (JSON) に変換する
		String sendMessageJson = gson.toJson(sendMessage);
		// 変換後の書式を表示してみる。（JSON）
		System.out.println(sendMessageJson);
		wsManager = new CServerConnector(serverAppEndpoint);
		if(wsManager.connect()){
			wsManager.sendMessage(sendMessageJson);
			startScreen.setVisible(false);
			waitScreen = new WaitScreen(User_id,room_id);
			waitScreen.setVisible(true);
		}
		else{
			startScreen.displayMessage("※接続に失敗しました");
		}
	}

	public boolean checkRoomState(String user_id, int room_id){
		SController sc = new SController(user_id);
		sc.Room_id = room_id;
		sc.User_id = user_id;
		System.out.println("sendMessage()");
		// 試しにSampleMessageのインスタンスを作ってみる
		Message sendMessage = new Message("6", user_id);
		// クラスオブジェクトをString (JSON) に変換する
		sendMessage.messageContent.room_id=room_id;
		String sendMessageJson = gson.toJson(sendMessage);
		// 変換後の書式を表示してみる。JSON
		System.out.println(sendMessageJson);
		wsManager = new CServerConnector(serverLobbyEndpoint);
		if(wsManager.connect()){
			wsManager.sendMessage(sendMessageJson);
			return true;
		}
		else{
			startScreen.displayMessage("※接続に失敗しました");
			return false;
		}
	}



}
