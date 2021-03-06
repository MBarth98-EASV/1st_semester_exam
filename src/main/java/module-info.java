/**
 *
 */
module easv.app {

    requires javafx.media;
    requires com.microsoft.sqlserver.jdbc;
    requires java.sql;
    requires com.fasterxml.jackson.annotation;
    requires java.naming;
    requires javafx.fxml;
    requires javafx.controls;
    requires gson;
    requires retrofit2;
    requires retrofit2.converter.gson;
    requires java.desktop;
    requires org.controlsfx.controls;

    exports easv.app;
    exports easv.app.be;
    exports easv.app.controllers;
    exports easv.app.utils.customComponent;
}