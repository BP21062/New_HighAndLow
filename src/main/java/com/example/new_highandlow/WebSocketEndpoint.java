package com.example.new_highandlow;

import jakarta.websocket.*;

import com.google.gson.Gson;

import java.io.IOException;

@ClientEndpoint
public class WebSocketEndpoint {

	// 多分あまり行儀が良くない
	static Gson gson = new Gson();
	static String order;

	Session session;

	@OnOpen
	public void onOpen(Session session) {
		System.out.println("[client] onOpen:" + session.getId());
		this.session = session;
	}

	@OnMessage
	public void onMessage(String message) {

		// 変換：String -> SampleMessage
		Message receivedMessage = gson.fromJson(message, Message.class);

		// ルールの画像で流れるため
		if (!receivedMessage.order.equals("2004")) {
			// 受信した生のメッセージ
			System.out.println("[App] onMessage from (session: " + session.getId() + ") msg: " + message);
		}else{
			System.out.println("[client] onMessage: rule");
		}

		// 各要素を見てみる
		if (receivedMessage.order.equals("2000")) {

			// 作成に成功したらtrue, 失敗したらfalse
			if (receivedMessage.result) {
				CController.lobbyScreen.displayMessage("※データベースへの登録完了");
				try {
					session.close();
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			} else {
				CController.lobbyScreen.displayMessage("※ユーザーIDが重複しています");
				try {
					session.close();
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
		}
		if (receivedMessage.order.equals("2001")) {
			if (receivedMessage.result) {
				CController.lobbyScreen.displayMessage("ログイン成功");
				CController.user_id = receivedMessage.messageContent.user_id;
				CController.lobbyScreen.changeScreen("Start");
			} else {
				CController.lobbyScreen.displayMessage("※ログインに失敗しました");
				try {
					session.close();
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
		}
		// WaitScreenからScoreScreenへの画面推移
		if (receivedMessage.order.equals("2003")) {
			CController.startScreen.changeScreen("Score", receivedMessage);
		}
		// WaiScreenからRuleScreenへの画面推移
		if (receivedMessage.order.equals("2004")) {
			CController.startScreen.changeScreen("Rule", receivedMessage);
		}

		/*
		 * checkRoomStateのboolean判定が帰ってくる
		 * receivedMessage.result=trueなら、そのまま入室
		 * receivedMessage.result=falseなら、満室表示して部屋選択からやり直し
		 * 
		 * ⇒動作検証できていないのと、最悪の場合正常処理だけするよう変更します
		 */
		if (receivedMessage.order.equals("2002")) {
			if (receivedMessage.result) {
				StartScreen.room_state_flag = true; // 入室許可
			} else {
				CController.startScreen.displayMessage("※部屋が満室です");
				StartScreen.room_state_flag = false; // 入室拒否
			}
		}

		// 5002(ゲーム開始の画面推移用)⇒1004(画面推移完了通知)
		if (receivedMessage.order.equals("5002")) {
			Message sendMessage = new Message("1004", receivedMessage.messageContent.user_id);
			sendMessage.result = true;
			sendMessage.messageContent.room_id = receivedMessage.messageContent.room_id;
			String send_message = gson.toJson(sendMessage);
			sendMessage(send_message);
		}

		// 5003(ゲーム画面への推移用)
		if (receivedMessage.order.equals("5003")) {
				// ゲーム開始はメッセージが来た時のみ
				//CController.gameScreen = new GameScreen();
				if(receivedMessage.messageContent.game_loop == 1){
					// waitScreenで待機しているのでwaitScreenを経由
					CController.gameScreen = new GameScreen();
					CController.gameScreen.displayCurrentScore(receivedMessage.messageContent.score_list);
					CController.waitScreen.changeScreen("Game");
				}else{
					CController.gameScreen.displayCurrentScore(receivedMessage.messageContent.score_list);
					CController.gameScreen.changeScreen("Game");
				}
		}
		// 5004(ゲーム画面の更新)
		if (receivedMessage.order.equals("5004")) {
			CController.gameScreen.displaySecondCardInformation(receivedMessage.messageContent.pattern_list);
			CController.gameScreen.displayFirstCard(receivedMessage.messageContent.image_data);
		}
		if (receivedMessage.order.equals("5005")) {
			CController.gameScreen.displaySecondCard(receivedMessage.messageContent.image_data);
		}
		// 5006(結果画面の表示)
		if (receivedMessage.order.equals("5006")) {
			CController.gameScreen.changeScreen("Result");
			CController.resultScreen.displayResult(receivedMessage.messageContent.score_list,
					receivedMessage.messageContent.user_list);
		}
		// 変換：SampleMessage -> String
		// System.out.println(gson.toJson(receivedMessage));
	}

	@OnError
	public void onError(Throwable t) {
		System.out.println("[client] onError:" + t.getMessage());
	}

	@OnClose
	public void onClose(final Session client, final CloseReason reason) throws IOException {
		/* セッション解放時の処理 */
		String log = client.getId() + " was closed by " + reason.getCloseCode() + "[" + reason.getCloseCode().getCode()
				+ "]";
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
