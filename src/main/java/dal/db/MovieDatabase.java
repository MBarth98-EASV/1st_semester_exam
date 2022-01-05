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

    private void execute(String sql)
    {
        try (Connection connection = dbaccess.getConnection()) {
            Statement statement = connection.createStatement();
            statement.execute(sql);

        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    private void addMovie(String name, int rating, String filelink, String imdblink, int lastviewed)
    {
        try (Connection connection = dbaccess.getConnection())
        {
            String sql = """
                    INSERT INTO Movie (name, rating, filelink, imdblink, lastviewed)
                    VALUES ('%s', '%s', '%s', '%s','&s')
                    """;

            this.execute(sql);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

    }

    private void getMovie()
    {

    }
}
