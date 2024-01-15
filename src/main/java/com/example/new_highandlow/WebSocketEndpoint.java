package com.example.new_highandlow;

import jakarta.websocket.*;

import com.google.gson.Gson;

import java.io.IOException;

@ClientEndpoint
public class WebSocketEndpoint{

	// 多分あまり行儀が良くない
	static Gson gson = new Gson();
	static String order;

	Session session;

	@OnOpen
	public void onOpen(Session session){
		System.out.println("[client] onOpen:" + session.getId());
		this.session = session;
	}

	@OnMessage
	public void onMessage(String message){

		// 受信した生のメッセージ
		//System.out.println("[client] onMessage: " + message);

		// 変換：String -> SampleMessage
		Message receivedMessage = gson.fromJson(message, Message.class);

		// 各要素を見てみる
		if(receivedMessage.order.equals("2000")){
			if(receivedMessage.result == false){
				CController.lobbyScreen.displayMessage("※データベースへの登録完了");
				try{
					session.close();
				}catch(IOException e){
					throw new RuntimeException(e);
				}
			}
			else{
				CController.lobbyScreen.displayMessage("※ユーザーIDが重複しています");
				try{
					session.close();
				}catch(IOException e){
					throw new RuntimeException(e);
				}
			}
		}
		if(receivedMessage.order.equals("2001")){
			if(receivedMessage.result){
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

		/*checkRoomStateのboolean判定が帰ってくる
		   receivedMessage.result=trueなら、そのまま入室
		  receivedMessage.result=falseなら、満室表示して部屋選択からやり直し

		  ⇒動作検証できていないのと、最悪の場合正常処理だけするよう変更します
		*/
		if(receivedMessage.order.equals("2002")){
			if(receivedMessage.result=true){
				StartScreen.room_state_flag = true; //入室許可
			}
			else{
				CController.startScreen.displayMessage("※部屋が満室です");
				StartScreen.room_state_flag = false; //入室拒否
			}
		}

		if(receivedMessage.order.equals("5002")){
			CController.waitScreen.StartGame(receivedMessage.messageContent.room_id);
		}

		if(receivedMessage.order.equals("5003")){
			CController.gameScreen.displayCurrentScore(receivedMessage.messageContent.score_list,
					receivedMessage.messageContent.user_list, receivedMessage.messageContent.room_id);
		}
		if(receivedMessage.order.equals("5004")){
			CController.gameScreen.displayCardInformation(receivedMessage.messageContent.pattern_list,
					receivedMessage.messageContent.image_data, receivedMessage.messageContent.room_id);
		}
		if(receivedMessage.order.equals("5005")){
			CController.gameScreen.displaySecondCard(receivedMessage.messageContent.image_data,
					receivedMessage.messageContent.room_id, receivedMessage.messageContent.score_list);
		}

		if(receivedMessage.order.equals("5006")){
			CController.resultScreen.displayResult(receivedMessage.messageContent.score_list,
					receivedMessage.messageContent.user_list, receivedMessage.messageContent.room_id);
		}
		// 変換：SampleMessage -> String
		//System.out.println(gson.toJson(receivedMessage));
	}

	@OnError
	public void onError(Throwable t){
		System.out.println("[client] onError");
		System.out.println(t.getMessage());
	}

	@OnClose
	public void onClose(final Session client, final CloseReason reason) throws IOException{
		/* セッション解放時の処理 */
		String log = client.getId() + " was closed by " + reason.getCloseCode() + "[" + reason.getCloseCode().getCode() + "]";
		System.out.println(log);
	}
	public void sendMessage(String text) {
		System.out.println("[client] sendMessage(): " + text);
		try {
			this.session.getBasicRemote().sendText(text);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}