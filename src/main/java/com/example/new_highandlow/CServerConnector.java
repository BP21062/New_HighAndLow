package com.example.new_highandlow;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Objects;

import javax.websocket.*;

import static com.example.new_highandlow.CController.gson;


//WebSocketManagerSample.java
//@ClientEndpoint
public class CServerConnector{

	Session session;
	WebSocketContainer container;
	URI uri;

	CServerConnector(String uriString) {
		container = ContainerProvider.getWebSocketContainer();
		uri = URI.create(uriString);
	}


	public boolean isConnected() {
		return this.session.isOpen();
	}

	public void sendMessage(String text) {
		System.out.println("[client] sendMessage(): " + text);
		try {
			this.session.getBasicRemote().sendText(text);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean connect() {
		System.out.println("[client] connect(): " + uri);
		try {
			this.session = container.connectToServer(new WebSocketEndpoint(), uri);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public void disconnect(){
		System.out.println("[client] disconnect(): " + uri);
		try {
			if(!session.isOpen()) {
				session.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
