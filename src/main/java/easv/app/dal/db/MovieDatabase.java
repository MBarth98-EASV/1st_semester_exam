package easv.app.dal.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.Dictionary;
import java.util.Hashtable;

import easv.app.be.MovieModel;

public class MovieDatabase implements IDatabaseCRUD<DBMovieData>
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

            System.out.println(sql);
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
                VALUES ('%s', '%s', '%s', '%s','%s')
                """.formatted(movie.getTitle(), movie.getPersonalRating(), movie.getPath(), movie.getID()));
    }

    private void deleteMovie(MovieModel movie)
    {
        if (movie != null)
        {
            this.execute("""
                    DELETE FROM CatMovie WHERE movieid = %s
                    """.formatted(movie.getID()));

            this.execute("""
                    DELETE FROM Movie WHERE id = %s
                    """.formatted(movie.getID()));
        }

    }

    public ArrayList getAllMovies() throws SQLException {
        ArrayList<DBMovieData> movies = new ArrayList<>();

        ResultSet results = this.query("""
                SELECT * FROM Movie
                """);

        while (results.next())
        {
            movies.add(new DBMovieData(
                    results.getInt("id"),
                    results.getString("title"),
                    results.getInt("rating"),
                    results.getString("filepath"),
                    results.getString("imdbid"),
                    results.getString("lastviewed")
            ));
        }

        return movies;
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
                    """.formatted(movie.getTitle(), movie.getPersonalRating(), movie.getPath(), movie.getID(), movie.getLastViewed()));
        }
    }

    private Dictionary<Integer, String> getCategories() throws SQLException {
        Dictionary<Integer, String> dictionary = new Hashtable();
        ResultSet results = this.query("SELECT * FROM Category");

        while (results.next())
        {
               dictionary.put(results.getInt("id"), results.getString("genre"));
        }
        return dictionary;
    }

    private int addGenre(String genre) throws SQLException {
        if (genre != null && !genre.isBlank()) {
            ResultSet results = this.query("""
                    IF NOT EXISTS (SELECT * FROM Category WHERE genre='%s')
                    INSERT INTO Category VALUES ('%s')
                                            
                    SELECT * from Category WHERE genre='%s'
                    """.formatted(genre.trim(), genre.trim(), genre.trim()));

            return results.getInt("id");
        }
        return -1;
    }

    @Override
    public void create(DBMovieData input) throws SQLException {
        int movieid;

        ResultSet movieresult = this.query("""
                IF NOT EXISTS (SELECT * FROM Movie WHERE imdbid='%s')
                INSERT INTO Movie (title, rating, filepath, imdbid, lastviewed)
                VALUES ('%s', '%s', '%s', '%s','%s')
                
                SELECT * FROM Movie WHERE imdbid='%s'
                """.formatted(input.getImdbid(), input.getTitle(), input.getRating(), input.getFilepath(), input.getImdbid(), input.getLastviewed(), input.getImdbid()));
        movieresult.next();
        movieid = movieresult.getInt("id");

        if (!input.getGenre().isBlank() && input.getGenre() != null)
        {
            String[] separatedGenres = input.getGenre().split(",");
            ArrayList<Integer> ids = new ArrayList<>();

            for (String genre : separatedGenres)
            {

                ResultSet results = this.query("""
                        IF NOT EXISTS (SELECT * FROM Category WHERE genre='%s')
                        INSERT INTO Category VALUES ('%s')
                        
                        SELECT * from Category WHERE genre='%s'
                        """.formatted(genre.trim(), genre.trim(), genre.trim()));
                results.next();
                ids.add(results.getInt("id"));
            }

            for (int genreid : ids)
            {
                this.execute("""
                        INSERT INTO CatMovie VALUES ('%s', '%s')
                        """.formatted(movieid, genreid));
            }
        }
    }

    @Override
    public DBMovieData read(DBMovieData input)
    {
        return null;
    }

    @Override
    public DBMovieData[] readAll()
    {
        return new DBMovieData[0];
    }

    @Override
    public void update(DBMovieData input)
    {
        if (input != null)
        {
            this.execute("""
                    UPDATE Movie SET title = '%s',
                    rating = '%s',
                    filelink = '%s',
                    lastviewed = '%s'
                    
                    WHERE imdblink = '%s',
                    """.formatted(input.getTitle(), input.getRating(), input.getFilepath(), input.getLastviewed(), input.getImdbid()));
        }
    }

    @Override
    public void delete(DBMovieData input)
    {
        if (input != null)
        {
            this.execute("""
                    DELETE FROM CatMovie WHERE movieid = %s
                    """.formatted(input.getImdbid()));

            this.execute("""
                    DELETE FROM Movie WHERE imdbid = %s
                    """.formatted(input.getImdbid()));
        }
    }

    public void delete(String input) throws SQLException {
        if (input != null)
        {
            ResultSet results = this.query("""
                    SELECT FROM Movie WHERE imdbid = '%s'
                    """.formatted(input));

            int movieid = results.getInt("id");
            
        }
    }


    public static void main(String[] args) {
        try {
            testMethod("Action");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    static void testMethod(String input) throws SQLException {
        int movieid;

        MovieDatabase db = new MovieDatabase();
        ResultSet movieresult = db.query("""
                IF NOT EXISTS (SELECT * FROM Movie WHERE imdbid='%s')
                INSERT INTO Movie (title, rating, filepath, imdbid, lastviewed)
                VALUES ('%s', '%s', '%s', '%s','%s')
                
                SELECT * FROM Movie WHERE imdbid='%s'
                """.formatted("1", "input.getTitle()", 5, "input.getPath()", "1", "1970/1/1", "1"));
        movieresult.next();
        movieid = movieresult.getInt("id");

        System.out.println(movieid);
    }
}
