package easv.app.dal.db;

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
        try (Connection connection = dbaccess.getConnection())
        {
            Statement statement = connection.createStatement();
            statement.execute(sql);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    private ResultSet query(String sql)
    {
        try
        {
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
        this.execute("""
                INSERT INTO Movie (title, rating, filelink, imdblink, lastviewed)
                VALUES ('%s', '%s', '%s', '%s','&s')
                """.formatted(movie.getTitle(), movie.getRatings(), movie.getPath(), movie.getImdbID()));
    }

    private void deleteMovie(Movie movie)
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

    private void updateMovie(Movie movie)
    {
        if (movie != null)
        {
            this.execute("""
                    UPDATE Movie SET title = '%s',
                    rating = '%s',
                    filelink = '%s',
                    imdblink = '%s',
                    lastviewed = '%s'
                    """.formatted(movie.getTitle(), movie.getRatings(), movie.getPath(), movie.getImdbID(), movie.getLastViewed()));
        }
    }

    private void getAllMoves()
    {

    }

    private Movie createMovieFromDatabase(ResultSet result) throws SQLException
    {
        String movieId, rating, lastviewed;
        String movieFilepath, movieIMDBlink, movieTitle;
        movieTitle = result.getString("title");
        movieId = result.getString("id");
        movieFilepath = result.getString("filepath");
        movieIMDBlink = result.getString("imdblink");

        return new Movie(movieId, movieTitle, movieFilepath, movieIMDBlink);
    }
}
