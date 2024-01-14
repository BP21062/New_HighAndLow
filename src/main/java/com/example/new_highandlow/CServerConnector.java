package com.example.new_highandlow;

import java.io.IOException;
import java.net.URI;

import jakarta.websocket.*;

import static com.example.new_highandlow.CController.gson;

//WebSocketManagerSample.java
//@ClientEndpoint
public class CServerConnector {

	Session session;
	WebSocketContainer container;
	URI uri;

	CServerConnector(String uriString) {
		container = ContainerProvider.getWebSocketContainer();
		uri = URI.create(uriString);
	}

	@OnOpen
	public void onOpen(Session session) {
		System.out.println("[client] onOpen" + session.getId());
	}

	@OnMessage
	public void onMessage(String message) {

		// 受信した生のメッセージ
		System.out.println("[client] onMessage: " + message);

		// 変換：String -> SampleMessage
		Message receivedMessage = gson.fromJson(message, Message.class);

		// 各要素を見てみる
		if (receivedMessage.order.equals("5002")) {
			// SController sc = new SController();
			// sc.changeScreen("Game");
		}

		// 変換：SampleMessage -> String
		// System.out.println(gson.toJson(receivedMessage));
	}

	@OnError
	public void onError(Throwable t) {
		System.out.println("[client] onError");
		System.out.println(t.getCause());
	}

	@OnClose
	public void onClose(Session session) {
		System.out.println("[client] onClose: " + session.getId());
	}

	public boolean isConnected() {
		// System.out.println("[client] isConnected(): " + session.isOpen());
		return session.isOpen();
	}

	public void sendMessage(String text) {
		System.out.println("[client] sendMessage(): " + text);
		try {
			session.getBasicRemote().sendText(text);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean connect() {
		System.out.println("[client] connect(): " + uri);
		try {
			session = container.connectToServer(new WebSocketEndpoint(), uri);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public void disconnect() {
		System.out.println("[client] disconnect(): " + uri);
		try {
			if (!session.isOpen()) {
				session.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
