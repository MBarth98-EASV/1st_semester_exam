package easv.app.dal.db;

import easv.app.App;
import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;

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
            Alert alert = new Alert(Alert.AlertType.ERROR,"Error executing statement. Please try again");
            alert.getDialogPane().getStylesheets().add(App.class.getResource("styles/DialogPane.css").toExternalForm());
            alert.showAndWait();
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
            Alert alert = new Alert(Alert.AlertType.ERROR,"Error executing statement. Please try again");
            alert.getDialogPane().getStylesheets().add(App.class.getResource("styles/DialogPane.css").toExternalForm());
            alert.showAndWait();
            return null;
        }
    }


    public ArrayList<DBMovieData> getAllMovies() throws SQLException
    {
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
                """.formatted(input.getImdbid(), input.getTitle(), input.getRating(), input.getFilepath(), input.getImdbid(), input.getLastViewed(), input.getImdbid()));
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
                    UPDATE Movie
                    SET title = '%s', rating = '%s', filepath = '%s', lastviewed = '%s'
                    WHERE imdbid = '%s'
                    """.formatted(input.getTitle(), input.getRating(), input.getFilepath(), input.getLastViewed(), input.getImdbid()));
        }
    }

    @Override
    public void delete(DBMovieData input) throws SQLException
    {
        if (input != null)
        {
            ResultSet results = this.query("""
                    SELECT FROM Movie WHERE imdbid = '%s'
                    """.formatted(input.getImdbid()));

            results.next();
            int movieid = results.getInt("id");

            this.execute("""
                    DELETE FROM CatMovie WHERE movieid = %s
                    """.formatted(movieid));

            this.execute("""
                    DELETE FROM Movie WHERE imdbid = %s
                    """.formatted(movieid));
        }
    }

    public void delete(String input) throws SQLException {
        if (input != null)
        {
            ResultSet results = this.query("""
                    SELECT FROM Movie WHERE imdbid = '%s'
                    """.formatted(input));

            results.next();
            int movieid = results.getInt("id");

            this.execute("""
                    DELETE FROM CatMovie WHERE movieid = '%s'
                    """.formatted(movieid));

            this.execute("""
                    DELETE FROM Movie where id = '%s'
                    """.formatted(movieid));
        }
    }
}
