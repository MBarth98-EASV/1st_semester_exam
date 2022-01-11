package easv.app.dal.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import easv.app.be.Movie;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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
                    """.formatted(movie.getTitle(), movie.getRatings(), movie.getPath(), movie.getImdbID()));
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
                        """.formatted(movie.getImdbID()));

                this.execute("""
                        DELETE FROM Movie WHERE id = %s
                        """.formatted(movie.getImdbID()));
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
                        """.formatted(movie.getTitle(), movie.getRatings(), movie.getPath(), movie.getImdbID()));
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    private ObservableList<Movie> getAllMovies() {
        return getAllMoves("SELECT * FROM Movie");
    }

    private ObservableList<Movie> getAllMoves(String sql)
    {
        try (Connection connection = dbaccess.getConnection())
        {
            ObservableList<Movie> movies = FXCollections.observableArrayList();

            ResultSet set = this.query(sql);

            while (set.next())
            {
                Movie movie = createMovieFromDatabase(set);
                if (movie != null)
                {
                    movies.add(movie);
                }
            }
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
            String movieid, rating, lastviewed;
            String movieFilepath, movieIMDBlink, movieTitle;

            movieTitle = result.getString("title");
            movieid = result.getString("id");
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
