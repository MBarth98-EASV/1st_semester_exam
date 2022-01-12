package easv.app.dal.db;

public class DBMovieData {
    private int id;
    private String title;
    private int rating;
    private String filepath;
    private String imdbid;
    private String lastviewed;
    private String genre;

    public DBMovieData(int id, String title, int rating, String filepath, String imdbid, String lastviewed)
    {
        this.id = id;
        this.title = title;
        this.rating = rating;
        this.filepath = filepath;
        this.imdbid = imdbid;
        this.lastviewed = lastviewed;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public String getImdbid() {
        return imdbid;
    }

    public void setImdbid(String imdbid) {
        this.imdbid = imdbid;
    }

    public String getLastviewed() {
        return lastviewed;
    }

    public void setLastviewed(String lastviewed) {
        this.lastviewed = lastviewed;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}
