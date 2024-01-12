package com.example.new_highandlow;

import javax.websocket.*;

import com.google.gson.Gson;

import java.io.IOException;

@ClientEndpoint
public class WebSocketEndpoint{

	// 多分あまり行儀が良くない
	static Gson gson = new Gson();
	static String order;

	@OnOpen
	public void onOpen(Session session){
		System.out.println("[client] onOpen:" + session.getId());
	}

	@OnMessage
	public void onMessage(String message){

		// 受信した生のメッセージ
		System.out.println("[client] onMessage: " + message);

		// 変換：String -> SampleMessage
		Message receivedMessage = gson.fromJson(message, Message.class);

		// 各要素を見てみる
		if(receivedMessage.order.equals("5002")){
			order = "5002";
			CController.waitScreen.StartGame();
		}
		if(receivedMessage.order.equals("2000")){
			if(receivedMessage.result=true){
				CController.lobbyScreen.displayMessage("※データベースへの登録完了");
			}
			else{
				CController.lobbyScreen.displayMessage("※ユーザーIDが重複しています");
			}
		}
		if(receivedMessage.order.equals("2001")){
			if(receivedMessage.result=true){
				CController.lobbyScreen.displayMessage("ログイン成功");
				CController.lobbyScreen.changeScreen("Start",receivedMessage.messageContent.user_id);
			}
			else{
				CController.lobbyScreen.displayMessage("※ログインに失敗しました");
			}
		}
		if(receivedMessage.order.equals("2003")){
			CController.startScreen.changeScreen("Score",receivedMessage);
		}
		if(receivedMessage.order.equals("2004")){
			CController.startScreen.changeScreen("Rule",receivedMessage);
		}

		// 変換：SampleMessage -> String
		//System.out.println(gson.toJson(receivedMessage));
	}

	@OnError
	public void onError(Throwable t){
		System.out.println("[client] onError");
	}

	@OnClose
	public void onClose(final Session client, final CloseReason reason) throws IOException{
		/* セッション解放時の処理 */
		String log = client.getId() + " was closed by " + reason.getCloseCode() + "[" + reason.getCloseCode().getCode() + "]";
		System.out.println(log);
	}
}
