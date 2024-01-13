module com.example.new_highandlow {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
	requires com.google.gson;
	requires javax.websocket.api;


	opens com.example.new_highandlow to javafx.fxml, com.google.gson;
	exports com.example.new_highandlow;
}