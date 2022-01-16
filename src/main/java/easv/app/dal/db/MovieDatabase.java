package easv.app.dal.db;

import easv.app.be.DBMovieData;
import javafx.scene.control.Alert;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MovieDatabase
{
    private final EASVDatabaseConnector dbaccess;

    public MovieDatabase()
    {
        dbaccess = new EASVDatabaseConnector();
    }

    public ArrayList<DBMovieData> getAllMovies()
    {
        try {
            ArrayList<DBMovieData> movies = new ArrayList<>();

            ResultSet results = dbaccess.query("""
                    SELECT * FROM Movie
                    """);

            while (results.next()) {
                DBMovieData data = new DBMovieData();

                data.setTitle(results.getString("title"));
                data.setFilepath(results.getString("filepath"));
                data.setImdbid(results.getString("imdbid"));
                data.setRating(results.getInt("rating"));
                data.setLastViewed(results.getString("lastviewed"));
                data.setId(results.getInt("id"));

                movies.add(data);
            }

            return movies;
        } catch (SQLException e)
        {
            alert("Something went wrong.");
            return null;
        }
    }

    private void alert (String alertmessage)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR, alertmessage);
        alert.showAndWait();
    }

    public ArrayList<DBMovieData> getMoviesByGenre(String genre){
        //Some SQL statement...
        return new ArrayList<>();
    }

    public List<String> getCategories() throws SQLException {
        List<String> list = new ArrayList<>();
        ResultSet results = dbaccess.query("SELECT * FROM Category");

        while (results.next())
        {
            list.add(results.getString("genre"));
        }
        return list;
    }

    private int addGenre(String genre) throws SQLException {
        if (genre != null && !genre.isBlank())
        {
            ResultSet results = dbaccess.query("""
                    IF NOT EXISTS (SELECT * FROM Category WHERE genre='%s')
                    INSERT INTO Category VALUES ('%s')
                                            
                    SELECT * from Category WHERE genre='%s'
                    """.formatted(genre.trim(), genre.trim(), genre.trim()));

            results.next();

            return results.getInt("id");
        }
        return -1;
    }

    private DBMovieData addMovie(DBMovieData partialData)
    {
        try {
            ResultSet movieresult = dbaccess.query("""
                    IF NOT EXISTS (SELECT * FROM Movie WHERE imdbid='%s')
                    INSERT INTO Movie (title, rating, filepath, imdbid, lastviewed)
                    VALUES ('%s', '%s', '%s', '%s','%s')
                                    
                    SELECT * FROM Movie WHERE imdbid='%s'
                    """.formatted(
                    partialData.getImdbid(),
                    partialData.getTitle(),
                    partialData.getRating(),
                    partialData.getFilepath(),
                    partialData.getImdbid(),
                    partialData.getLastViewed(),
                    partialData.getImdbid())
            );

            movieresult.next();

            return new DBMovieData(
                    movieresult.getInt("id"),
                    movieresult.getString("title"),
                    movieresult.getInt("rating"),
                    movieresult.getString("filepath"),
                    movieresult.getString("imdbid"),
                    movieresult.getString("lastviewed")
            );
        } catch (SQLException e)
        {
            alert("Something went wrong.");
            return null;
        }
    }

    public DBMovieData create(DBMovieData input)
    {
        try {

            DBMovieData movie = addMovie(input);

            if (!input.getGenre().isBlank() && input.getGenre() != null) {
                String[] separatedGenres = input.getGenre().split(",");

                for (String genre : separatedGenres) {
                    int id = this.addGenre(genre);

                    dbaccess.execute("""
                            INSERT INTO CatMovie VALUES ('%s', '%s')
                            """.formatted(movie.getId(), id));
                }
            }

            return movie;
        } catch (SQLException e)
        {
            alert("Something went wrong.");
            return null;
        }
    }

    public void update(DBMovieData input)
    {
        // todo update movie genres with content from input...
        System.out.println("genres: " + input.getGenre());

        if (input != null)
        {
            dbaccess.execute("""
                    UPDATE Movie
                    SET title = '%s', rating = '%s', filepath = '%s', lastviewed = '%s'
                    WHERE imdbid = '%s'
                    """.formatted(input.getTitle(), input.getRating(), input.getFilepath(), input.getLastViewed(), input.getImdbid()));
        }
    }

    public void delete(DBMovieData input)
    {
        if (input != null)
        {
            this.delete(input.getImdbid());
        }
    }

    public void delete(String imdbID)
    {
        try {
            if (imdbID != null) {
                ResultSet results = dbaccess.query("""
                        SELECT * FROM Movie WHERE imdbid = '%s'
                        """.formatted(imdbID));

                while (results.next()) {
                    int movieid = results.getInt("id");

                    this.deleteCatMovie(movieid);
                    this.deleteMovie(movieid);
                }
            }
        } catch (SQLException e)
        {
            alert("Something went wrong.");
        }
    }

    private void deleteCatMovie(int id)
    {
        dbaccess.execute("""
                    DELETE FROM CatMovie WHERE movieid = '%s'
                    """.formatted(id));
    }

    private void deleteMovie(int id)
    {
        dbaccess.execute("""
                    DELETE FROM Movie where id = '%s'
                    """.formatted(id));
    }

    private void updateGenre(String oldname, String newname)
    {
        dbaccess.execute("""
                    UPDATE Category
                    SET genre ='%s'
                    WHERE genre = '%s'
                """.formatted(newname, oldname));
    }

    public void updateMovieGenres(String imdbid, String oldgenre, String newgenre)
    {
        try {
            ResultSet getmovieid = dbaccess.query("""
                    SELECT id FROM Movie WHERE imdbid = '%s'
                    """.formatted(imdbid));

            getmovieid.next();

            int movieid = getmovieid.getInt("id");

            ResultSet getnewgenreid = dbaccess.query("""
                    SELECT id FROM Category WHERE genre = '%s'
                    """.formatted(newgenre));

            getnewgenreid.next();
            int newgenreid = getnewgenreid.getInt("id");

            ResultSet getoldgenreid = dbaccess.query("""
                    SELECT id FROM Category WHERE genre = '%s'
                    """.formatted(oldgenre));

            getoldgenreid.next();
            int oldgenreid = getoldgenreid.getInt("id");

            dbaccess.execute("""
                            UPDATE CatMovie
                            SET categoryid = '%s'
                            WHERE movieid = '%s' AND categoryid ='%s'
                            """.formatted(newgenreid, movieid, oldgenreid));

        } catch (SQLException e)
        {
            alert("Something went wrong.");
        }
    }

    private void setMovieCatIds(String ImdbID, String[] oldGenres, String[] newGenres)
    {
        if (oldGenres.length != newGenres.length)
            throw new IllegalArgumentException();

        String sqlQuery = """
                SELECT id FROM Movie WHERE imdbid = '%s'               
                """;

        int movieID = 0;

        for (int i = 0; i < oldGenres.length; i++)
        {
            if (oldGenres[i].equals(newGenres[i]))
                continue;

            setMovieCatIds(movieID, oldGenres[i], newGenres[i]);
        }
    }

    private void setMovieCatIds(int movieID, String oldGenre, String newGenre)
    {

    }
}