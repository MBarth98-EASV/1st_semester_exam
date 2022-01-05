package dal.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class MovieDatabase {

    private EASVDatabaseConnector dbaccess;

    public MovieDatabase()
    {
        dbaccess = new EASVDatabaseConnector();
    }

    private void execute()
    {
        try (Connection connection = dbaccess.getConnection()) {
            Statement statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void getMovie()
    {
    }
}
