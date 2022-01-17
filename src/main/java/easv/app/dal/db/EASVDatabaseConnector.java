package easv.app.dal.db;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import easv.app.App;
import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

public class EASVDatabaseConnector {

    private SQLServerDataSource dbaccess = null;

    public EASVDatabaseConnector() {
        dbaccess = new SQLServerDataSource();
        dbaccess.setServerName("10.176.111.31");
        dbaccess.setDatabaseName("CSe21A_29_MovieManager");
        dbaccess.setUser("CSe21A_29");
        dbaccess.setPassword("itsikkerhed");
        dbaccess.setPortNumber(1433);
        dbaccess.setTrustServerCertificate(true);
    }

    public Connection getConnection() {
        try {
            return dbaccess.getConnection();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Could not connect to the database.");
            alert.getDialogPane().getStylesheets().add(Objects.requireNonNull(App.class.getResource("styles/DialogPane.css")).toExternalForm());
            alert.showAndWait();
            return null;
        }
    }

    public void execute(String sql) throws SQLException {
        Statement statement = getConnection().createStatement();
        statement.execute(sql);
    }

    public ResultSet query(String sql) {
        try {
            Statement statement = getConnection().createStatement();

            return statement.executeQuery(sql);
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error executing statement. Please try again");
            alert.getDialogPane().getStylesheets().add(Objects.requireNonNull(App.class.getResource("styles/DialogPane.css")).toExternalForm());
            alert.showAndWait();
            return null;
        }
    }
}
