module com.example.new_highandlow {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
	//requires javax.websocket.api;
	requires com.google.gson;
	requires org.apache.tomcat.embed.websocket;


	opens com.example.new_highandlow to javafx.fxml, com.google.gson;
	exports com.example.new_highandlow;
}