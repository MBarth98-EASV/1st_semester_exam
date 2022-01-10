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
                    INSERT INTO Movie (title, rating, filelink, imdblink, lastviewed)
                    VALUES ('%s', '%s', '%s', '%s','&s')
                    """.formatted(movie.getTitle(), movie.getRatings(), movie.getFilepath(), movie.getImdbID()));
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
                        """.formatted(movie.getId()));

                this.execute("""
                        DELETE FROM Movie WHERE id = %s
                        """.formatted(movie.getId()));
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

    }

    private void updateMovie(Movie movie)
    {
        try (Connection connection = dbaccess.getConnection())
        {
            if (movie != null)
            {
                this.execute("""
                        UPDATE Movie SET title = '%s',
                        rating = '%s',
                        filelink = '%s',
                        imdblink = '%s',
                        lastviewed = '%s'
                        """.formatted(movie.getTitle(), movie.getRatings(), movie.getFilepath(), movie.getImdbID()));
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

    private Movie createMovieFromDatabase (ResultSet result)
    {
        try (Connection connection = dbaccess.getConnection())
        {
            int movieid, rating, lastviewed;
            String movieFilepath, movieIMDBlink, movieTitle;

            movieTitle = result.getString("title");
            movieid = result.getInt("id");
            movieFilepath = result.getString("filepath");
            movieIMDBlink = result.getString("imdblink");

            return new Movie(movieid, movieTitle, movieFilepath, movieIMDBlink);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return null;
        }
    }
}
