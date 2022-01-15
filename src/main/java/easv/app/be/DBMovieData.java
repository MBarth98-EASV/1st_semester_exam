package easv.app.be;

public class DBMovieData {
    private int id;
    private String title;
    private int rating;
    private String filepath;
    private String imdbid;
    private String lastviewed;
    private String genre;

    /**
     * Class for instantiating a movie from the database for later conversion to MovieModel.
     * @param id
     * @param title
     * @param rating
     * @param filepath
     * @param imdbid
     * @param lastviewed
     */
    public DBMovieData(int id, String title, int rating, String filepath, String imdbid, String lastviewed)
    {
        this.id = id;
        this.title = title;
        this.rating = rating;
        this.filepath = filepath;
        this.imdbid = imdbid;
        this.lastviewed = lastviewed;
    }

    public DBMovieData(String imdbid, String title, String[] genreCSV, int rating, String file, String lastviewed)
    {
        this.imdbid = imdbid;
        this.title = title;
        this.genre = "%s, %s, %s".formatted(genreCSV[0], genreCSV[1], genreCSV[2]);
        this.rating = rating;
        this.filepath = file;
        this.lastviewed = lastviewed;
    }

    public DBMovieData() {}

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

    public String getLastViewed() {
        return lastviewed;
    }

    public void setLastViewed(String lastviewed) {
        this.lastviewed = lastviewed;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}
