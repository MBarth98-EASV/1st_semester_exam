module easv.app {

    requires javafx.fxml;
    requires javafx.media;
    requires javafx.controls;
    requires com.microsoft.sqlserver.jdbc;

    opens easv.app to javafx.fxml;
    exports easv.app;
}