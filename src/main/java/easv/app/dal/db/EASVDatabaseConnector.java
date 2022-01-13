/**
 * @Author Philip E. Zadeh
 */

package easv.app.dal.db;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class EASVDatabaseConnector {

    private SQLServerDataSource dbaccess;

    public EASVDatabaseConnector()
    {
        dbaccess = new SQLServerDataSource();
        dbaccess.setServerName("10.176.111.31");
        dbaccess.setDatabaseName("CSe21A_29_MovieManager");
        dbaccess.setUser("CSe21A_29");
        dbaccess.setPassword("itsikkerhed");
        dbaccess.setPortNumber(1433);
        dbaccess.setTrustServerCertificate(true);
    }

    public Connection getConnection()
    {
        try {
            return dbaccess.getConnection();
        }
        catch (SQLException e)
        {
            //Alert alert = new Alert(Alert.AlertType.ERROR, "Could not connect to the database.");
            //alert.showAndWait();
            return null;
        }
    }
}
