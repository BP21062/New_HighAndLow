module com.example.new_highandlow {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
	requires javax.websocket.api;
	requires com.google.gson;


	opens com.example.new_highandlow to javafx.fxml;
    exports com.example.new_highandlow;
}