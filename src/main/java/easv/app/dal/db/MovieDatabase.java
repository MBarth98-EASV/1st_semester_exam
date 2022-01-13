package easv.app.dal.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import easv.app.be.MovieModel;

public class MovieDatabase implements IDatabaseCRUD<MovieModel>
{
    private final EASVDatabaseConnector dbaccess;

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

    private void addMovie(MovieModel movie)
    {
        this.execute("""
                INSERT INTO Movie (title, rating, filelink, imdblink, lastviewed)
                VALUES ('%s', '%s', '%s', '%s','&s')
                """.formatted(movie.getTitle(), movie.getRatings(), movie.getPath(), movie.getID()));
    }

<<<<<<< Updated upstream
    private void deleteMovie(MovieModel movie)
    {
        if (movie != null)
=======
    public ArrayList<DBMovieData> getAllMovies() throws SQLException
    {
        ArrayList<DBMovieData> movies = new ArrayList<>();

        ResultSet results = this.query("""
                SELECT * FROM Movie
                """);

        while (results.next())
>>>>>>> Stashed changes
        {
            this.execute("""
                    DELETE FROM CatMovie WHERE movieid = %s
                    """.formatted(movie.getID()));

            this.execute("""
                    DELETE FROM Movie WHERE id = %s
                    """.formatted(movie.getID()));
        }

    }

    private void updateMovie(MovieModel movie)
    {
        if (movie != null)
        {
            this.execute("""
                    UPDATE Movie SET title = '%s',
                    rating = '%s',
                    filelink = '%s',
                    imdblink = '%s',
                    lastviewed = '%s'
                    """.formatted(movie.getTitle(), movie.getRatings(), movie.getPath(), movie.getID(), movie.getLastViewed()));
        }
    }

    private void getAllMoves()
    {

    }

    private MovieModel createMovieFromDatabase(ResultSet result) throws SQLException
    {
        String movieId, rating, lastviewed;
        String movieFilepath, movieIMDBlink, movieTitle;
        movieTitle = result.getString("title");
        movieId = result.getString("id");
        movieFilepath = result.getString("filepath");
        movieIMDBlink = result.getString("imdblink");
        return null;
       // return new MovieModel(movieId, movieTitle, movieFilepath, movieIMDBlink);
    }

    @Override
    public void create(MovieModel input)
    {
        this.execute("""
                INSERT INTO Movie (title, rating, filelink, imdblink, lastviewed)
                VALUES ('%s', '%s', '%s', '%s','&s')
                """.formatted(input.getTitle(), input.getRatings(), input.getPath(), input.getID()));
    }

    @Override
    public MovieModel read(MovieModel input)
    {
        return null;
    }

    @Override
    public MovieModel[] readAll()
    {
        return new MovieModel[0];
    }

    @Override
    public void update(MovieModel input)
    {
        if (input != null)
        {
            this.execute("""
                    UPDATE Movie SET title = '%s',
                    rating = '%s',
                    filelink = '%s',
                    imdblink = '%s',
                    lastviewed = '%s'
                    """.formatted(input.getTitle(), input.getRatings(), input.getPath(), input.getID(), input.getLastViewed()));
        }
    }

    @Override
    public void delete(MovieModel input)
    {
        if (input != null)
        {
            this.execute("""
                    DELETE FROM CatMovie WHERE movieid = %s
                    """.formatted(input.getID()));

            this.execute("""
                    DELETE FROM Movie WHERE id = %s
                    """.formatted(input.getID()));
        }
    }
}
