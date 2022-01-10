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
    requires java.datatransfer;
    requires org.controlsfx.controls;
    requires gson;
    requires retrofit2;
    requires retrofit2.converter.gson;

    exports easv.app;
    exports easv.app.be;
    exports easv.app.controllers;
}