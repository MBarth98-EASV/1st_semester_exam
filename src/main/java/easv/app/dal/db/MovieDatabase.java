package easv.app.dal.db;

import easv.app.be.DBMovieData;
import javafx.scene.control.Alert;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MovieDatabase {
    private final EASVDatabaseConnector dbaccess;

    public MovieDatabase() {
        dbaccess = new EASVDatabaseConnector();
    }

    public ArrayList<DBMovieData> getAllMovies() {
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

                data.setGenre(this.getGenresForMovieCSV(data.getImdbid()));

                movies.add(data);
            }

            return movies;
        } catch (SQLException e) {
            alert("Something went wrong.");
            return null;
        }
    }

    private void alert(String alertmessage) {
        Alert alert = new Alert(Alert.AlertType.ERROR, alertmessage);
        alert.showAndWait();
    }

    public ArrayList<DBMovieData> getMoviesByGenre(String genre) {
        //Some SQL statement...
        return new ArrayList<>();
    }

    public List<String> getCategories() throws SQLException {
        List<String> list = new ArrayList<>();
        ResultSet results = dbaccess.query("SELECT * FROM Category");

        while (results.next()) {
            list.add(results.getString("genre"));
        }
        return list;
    }

    private String getGenresForMovieCSV(String imdbID) {
        String[] genres = getGenresForMovie(imdbID);

        if (genres == null)
            return null;

        return "%s, %s, %s".formatted(genres[0], genres[1], genres[2]);
    }

    /**
     *
     */
    private String[] getGenresForMovie(String imdbID) {
        // an optimization should be using stored procedures instead a query (the database can optimize it internally, and it reduces data transfer)
        String sql = """
                DECLARE @array TABLE (
                    id int,
                    catid int
                )
                                
                DECLARE @name_array TABLE (
                    _name varchar(255)
                )
                               
                insert into @array (id, catid) select distinct row_number() over (order by categoryid) as id, categoryid from (select categoryid from CatMovie where movieid = (select id from Movie where imdbid = '%s')) as CMc
                                
                declare @index int = 0
                declare @max_index int = (select count(*) from @array)
                                
                declare @cat_name varchar(255)
                declare @cat_id int
                                
                while @index <= @max_index
                begin
                    set @cat_id = (select catid from @array where id = @index)
                    set @cat_name = (select genre from Category where id = @cat_id)
                                
                    insert into @name_array (_name) values (@cat_name)
                                
                    set @index = @index + 1
                end
                                
                select * from @name_array
                """.formatted(imdbID);

        try {
            ResultSet result = dbaccess.query(sql);

            List<String> genres = new ArrayList<>();
            while (result.next()) {
                String genre = result.getString("_name");

                if (genre != null) {
                    genres.add(genre);
                }
            }

            return genres.toArray(new String[0]);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void deleteGenre(String genre) throws SQLException {
        String sql = """
                DELETE FROM Category where genre = '%s'
                """.formatted(genre);

        dbaccess.execute(sql);
    }

    public int addGenre(String genre) throws SQLException {
        if (genre != null && !genre.isBlank()) {
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

    private DBMovieData addMovie(DBMovieData partialData) {
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
        } catch (SQLException e) {
            alert("Something went wrong.");
            return null;
        }
    }

    public DBMovieData create(DBMovieData input) {
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
        } catch (SQLException e) {
            alert("Something went wrong.");
            return null;
        }
    }

    public void update(DBMovieData input) throws SQLException {
        System.out.println("genres: " + input.getGenre());

        if (input != null) {
            dbaccess.execute("""
                    UPDATE Movie
                    SET title = '%s', rating = '%s', filepath = '%s', lastviewed = '%s'
                    WHERE imdbid = '%s'
                    """.formatted(input.getTitle(), input.getRating(), input.getFilepath(), input.getLastViewed(), input.getImdbid()));
        }
    }

    public void delete(String imdbID) {
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
        } catch (SQLException e) {
            alert("Something went wrong.");
        }
    }

    private void deleteCatMovie(int id) throws SQLException {
        dbaccess.execute("""
                DELETE FROM CatMovie WHERE movieid = '%s'
                """.formatted(id));
    }

    private void deleteMovie(int id) throws SQLException {
        dbaccess.execute("""
                DELETE FROM Movie where id = '%s'
                """.formatted(id));
    }

    private void updateGenre(String oldname, String newname) throws SQLException {
        dbaccess.execute("""
                    UPDATE Category
                    SET genre ='%s'
                    WHERE genre = '%s'
                """.formatted(newname, oldname));
    }

    public void setMovieCatIds(String ImdbID, String[] oldGenres, String[] newGenres) throws SQLException {
        if (oldGenres.length != newGenres.length) {
            throw new IllegalArgumentException();
        }

        for (int i = 0; i < oldGenres.length; i++) {
            if (oldGenres[i].equals(newGenres[i])) {
                continue;
            }

            setMovieCatIds(ImdbID, oldGenres[i], newGenres[i]);
        }
    }

    private void setMovieCatIds(String imdbID, String oldGenre, String newGenre) throws SQLException {
        String sql = """
                UPDATE TOP (1) CatMovie
                SET categoryid = (SELECT id FROM Category WHERE genre = '%s')
                WHERE movieid = (SELECT id FROM Movie WHERE imdbid = '%s') AND categoryid = (SELECT id FROM Category WHERE genre = '%s')
                     """.formatted(newGenre, imdbID, oldGenre);

        dbaccess.execute(sql);
    }
}