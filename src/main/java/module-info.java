module easv.app {
    requires javafx.controls;
    requires javafx.fxml;


    opens easv.app to javafx.fxml;
    exports easv.app;
}