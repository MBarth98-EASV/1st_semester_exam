package dal.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import easv.app.be.Movie;

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

    private ResultSet query(String sql)
    {
        try (Connection connection = dbaccess.getConnection()) {
            Statement statement = dbaccess.getConnection().createStatement();
            return statement.executeQuery(sql);
        }
        catch (SQLException e)
        {
            return null;
        }
    }

    private void addMovie(Movie movie)
    {
        try (Connection connection = dbaccess.getConnection())
        {
            this.execute("""
                    INSERT INTO Movie (name, rating, filelink, imdblink, lastviewed)
                    VALUES ('%s', '%s', '%s', '%s','&s')
                    """.formatted(movie.getTitle(), movie.getRatings(), movie.id, movie.getImdbID(), movie.missing));
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    private void getMovie(int id)
    {
        try (Connection connection = dbaccess.getConnection())
        {

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    private void getMovie(String title)
    {
        try (Connection connection = dbaccess.getConnection())
        {

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    private void deleteMovie(Movie movie)
    {
        try (Connection connection = dbaccess.getConnection())
        {
            if (movie != null)
            {
                this.execute("""
                        DELETE FROM CatMovie WHERE movieid = %s
                        """.formatted(movie.id));

                this.execute("""
                        DELETE FROM Movie WHERE id = %s
                        """.formatted(movie.id));
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

    }

    private void getAllMoves()
    {
        try (Connection connection = dbaccess.getConnection())
        {

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

    }

    private void testMethod()
    {

    }
}
