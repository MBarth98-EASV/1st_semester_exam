module easv.app {

    requires javafx.fxml;
    requires javafx.media;
    requires javafx.controls;

    opens easv.app to javafx.fxml;
    exports easv.app;
}