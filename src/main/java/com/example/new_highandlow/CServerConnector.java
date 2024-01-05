package com.example.new_highandlow;

import java.io.IOException;
import java.net.URI;

import javax.websocket.ContainerProvider;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

//WebSocketManagerSample.java
public class CServerConnector{
	Session session;
	WebSocketContainer container;
	URI uri;

	CServerConnector(String uriString) {
		container = ContainerProvider.getWebSocketContainer();
		uri = URI.create(uriString);
	}

	public boolean isConnected() {
		System.out.println("[client] isConnected(): " + session.isOpen());
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

	public void disconnect() throws IOException {
		if(!session.isOpen()) {
			session.close();
		}
	}
}
