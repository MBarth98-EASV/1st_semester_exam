package easv.app.dal.db;

import easv.app.be.DBMovieData;
import javafx.scene.control.Alert;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MovieDatabase implements IDatabaseCRUD<DBMovieData>
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

    @Override
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

    @Override
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

    private void updateMovieGenres(int movieid, String oldgenre, String newgenre)
    {
        try {
            ResultSet getnewgenreid = dbaccess.query("""
                    SELECT FROM Category WHERE genre = '%s'
                    """.formatted(newgenre));

            getnewgenreid.next();

            int newgenreid = getnewgenreid.getInt("id");

            dbaccess.execute("""
                    UPDATE CatMovie
                    SET categoryid = '%s'
                    FROM Category
                    INNER JOIN CatMovie ON Category.id = CatMovie.categoryid
                    WHERE CatMovie.movieid = '%s' AND genre = '%s'
                    """.formatted(newgenreid, movieid, oldgenre));
        } catch (SQLException e)
        {
            alert("Something went wrong.");
        }
    }
}