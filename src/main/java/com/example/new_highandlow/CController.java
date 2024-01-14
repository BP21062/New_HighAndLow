package com.example.new_highandlow;

import javax.swing.*;

import com.google.gson.Gson;

//WebSocketClientSample.java
public class CController{
	public static String user_id;
	public static String passwd;

	static CServerConnector lobby_connect;
	static CServerConnector app_connect;
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


	public static boolean checkPasswordStrength(){
		return passwd.matches("(?=.*[A-Z]).{8,12}"); //パスワードが条件を満たしていればtrue　それ以外はfalse
	}

	public static void registerUser(){
		// 試しにSampleMessageのインスタンスを作ってみる
		Message sendMessage = new Message("2", user_id);
		// クラスオブジェクトをString (JSON) に変換する
		sendMessage.messageContent.password = passwd;
		String sendMessageJson = gson.toJson(sendMessage);
		// 変換後の書式を表示してみる。JSON
		System.out.println(sendMessageJson);
		lobby_connect = new CServerConnector(serverLobbyEndpoint);
		if(lobby_connect.connect()){
			lobby_connect.sendMessage(sendMessageJson);
		}else{
			lobbyScreen.displayMessage("※接続に失敗しました");
		}
	}

	public void login(){
		System.out.println("sendMessage()");
		// 試しにSampleMessageのインスタンスを作ってみる
		Message sendMessage = new Message("4", user_id);
		// クラスオブジェクトをString (JSON) に変換する
		sendMessage.messageContent.password = passwd;
		String sendMessageJson = gson.toJson(sendMessage);
		// 変換後の書式を表示してみる。JSON
		System.out.println(sendMessageJson);
		lobby_connect = new CServerConnector(serverLobbyEndpoint);
		if(lobby_connect.connect()){
			lobby_connect.sendMessage(sendMessageJson);
		}else{
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
		lobby_connect.disconnect();
	}


	public void getScore(String user){
		System.out.println("sendMessage()");
		// 試しにSampleMessageのインスタンスを作ってみる
		Message sendMessage = new Message("1000", user);
		// クラスオブジェクトをString (JSON) に変換する
		String sendMessageJson = gson.toJson(sendMessage);
		// 変換後の書式を表示してみる。JSON
		System.out.println(sendMessageJson);
		lobby_connect.sendMessage(sendMessageJson);
	}

	public static void getRule(){
		System.out.println("戦績を表示");
		// 試しにSampleMessageのインスタンスを作ってみる
		Message sendMessage = new Message("1001", user_id);
		// クラスオブジェクトをString (JSON) に変換する
		String sendMessageJson = gson.toJson(sendMessage);
		// 変換後の書式を表示してみる。JSON
		System.out.println(sendMessageJson);
		lobby_connect.sendMessage(sendMessageJson);
	}

	public void enter(String user_id, int room_id){
		SController sc = new SController(user_id);
		sc.room_id = room_id;
		sc.user_id = user_id;
		System.out.println("sendMessage()");
		// 試しにSampleMessageのインスタンスを作ってみる
		Message sendMessage = new Message("1003", user_id);
		sendMessage.messageContent.room_id = room_id;
		// クラスオブジェクトをString (JSON) に変換する
		String sendMessageJson = gson.toJson(sendMessage);
		// 変換後の書式を表示してみる。（JSON）
		System.out.println(sendMessageJson);
		app_connect = new CServerConnector(serverAppEndpoint);
		if(app_connect.connect()){
			app_connect.sendMessage(sendMessageJson);
			startScreen.setVisible(false);
			waitScreen = new WaitScreen(user_id, room_id);
			waitScreen.setVisible(true);
		}else{
			startScreen.displayMessage("※接続に失敗しました");
		}
	}

	public boolean checkRoomState(String user_id, int room_id){
		SController sc = new SController(user_id);
		sc.room_id = room_id;
		sc.user_id = user_id;
		System.out.println("sendMessage()");
		// 試しにSampleMessageのインスタンスを作ってみる
		Message sendMessage = new Message("6", user_id);
		// クラスオブジェクトをString (JSON) に変換する
		sendMessage.messageContent.room_id = room_id;
		String sendMessageJson = gson.toJson(sendMessage);
		// 変換後の書式を表示してみる。JSON
		System.out.println(sendMessageJson);
		if(lobby_connect.isConnected()){
			lobby_connect.sendMessage(sendMessageJson);
			return true;
		}else
			startScreen.displayMessage("※接続に失敗しました");
		return false;
	}

	public static void main(String[] args) {

		SwingUtilities.invokeLater(() -> {
			LobbyScreen lobbyScreen = new LobbyScreen();
			CController.lobbyScreen = lobbyScreen;
			lobbyScreen.setVisible(true);
		});
	}
}


