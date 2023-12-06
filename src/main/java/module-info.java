module com.example.new_highandlow {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.new_highandlow to javafx.fxml;
    exports com.example.new_highandlow;
}