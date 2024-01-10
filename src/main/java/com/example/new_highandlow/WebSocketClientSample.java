package com.example.new_highandlow;

import java.io.IOException;
import java.util.Random;

import javax.websocket.DeploymentException;

import com.google.gson.Gson;

public class WebSocketClientSample implements Runnable  {

	static WebSocketManagerSample wsManager;
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
	//static String serverEndpoint = "ws://localhost:8080/app/sample";
	static String serverEndpoint = "ws://localhost:8080/app/example";


	static int id = 1;
	static String password = "password";
	static Gson gson = new Gson();

	static int sampleIncrement = 0;


	public static void main(String[] args) throws IOException, DeploymentException {

		wsManager = new WebSocketManagerSample(serverEndpoint);
		wsManager.connect();

		new WebSocketClientSample();
	}

	WebSocketClientSample() {
		new Thread(this).start();
		// 一例として乱数値をIDにしてみる。複数起動できるかの例示のために使っている。
		Random random = new Random();
		WebSocketClientSample.id = random.nextInt(100);
	}

	public void run() {
		while(true) {
			if(wsManager.isConnected()) {
				System.out.println("sendMessage()");
				// 試しにSampleMessageのインスタンスを作ってみる
				SampleMessage sendMessage = new SampleMessage(id, password, "sample message. no." + sampleIncrement++);
				// クラスオブジェクトをString (JSON) に変換する
				String sendMessageJson = gson.toJson(sendMessage);
				// 変換後の書式を表示してみる。（JSON）
				System.out.println(sendMessageJson);
				wsManager.sendMessage(sendMessageJson);
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
