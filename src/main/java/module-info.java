module easv.app {

    requires javafx.fxml;
    requires javafx.media;
    requires javafx.controls;
    requires com.microsoft.sqlserver.jdbc;
    requires java.sql;
    requires com.fasterxml.jackson.annotation;

    opens easv.app to javafx.fxml;
    exports easv.app;
}