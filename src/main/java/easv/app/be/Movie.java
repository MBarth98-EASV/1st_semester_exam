package easv.app.be;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Movie {

    @JsonProperty("Title")
    private String title;
    @JsonProperty("Year")
    private String year;
    private String imdbID;
    @JsonProperty("Type")
    private String type;
    @JsonProperty("Poster")
    private String poster;
    private String ratings;
    private String runtime;
    private String rated;
    private String genre;
    private String director;
    private String writer;
    private String actors;
    private String plot;
    private String language;
    private String country;

    private String path;

    public Movie(String title, String year, String imdbID, String type, String poster, String ratings,
                         String runtime, String rated, String genre, String director, String writer,
                 String actors, String plot, String language, String country, String path) {
        this.title = title;
        this.year = year;
        this.imdbID = imdbID;
        this.type = type;
        this.poster = poster;
        this.ratings = ratings;
        this.runtime = runtime;
        this.rated = rated;
        this.genre = genre;
        this.director = director;
        this.writer = writer;
        this.actors = actors;
        this.plot = plot;
        this.language = language;
        this.country = country;
        this.path = path;
    }

    public Movie(String title, String year, String imdbID, String genre) {
        this.title = title;
        this.year = year;
        this.imdbID = imdbID;
        this.genre = genre;
    }

    public Movie() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getImdbID() {
        return imdbID;
    }

    public void setImdbID(String imdbID) {
        this.imdbID = imdbID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getRatings() {
        return ratings;
    }

    public void setRatings(String ratings) {
        this.ratings = ratings;
    }

    public String getRuntime() {
        return runtime;
    }

    public void setRuntime(String setRuntime) {
        runtime = setRuntime;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String setGenre) {
        genre = setGenre;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String setDirector) {
        director = setDirector;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String setWriter) {
        writer = setWriter;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String setActors) {
        actors = setActors;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String setPlot) {
        plot = setPlot;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String setLanguage) {
        language = setLanguage;
    }

    public String getRated(){
        return rated;
    }

    public void setRated(String setRated){
        rated = setRated;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "Movie: " +
                "title = " + title + ' ' +
                ", year = " + year + ' ' +
                ", imdbID = " + imdbID + ' ' +
                ", type = " + type + ' ' +
                ", poster = " + poster + ' ' +
                ", imdbRating = " + ratings + ' ' +
                ", runtime = " + runtime + ' ' +
                ", language = " + language + ' ' +
                ", rated = " + rated + ' ' +
                ", plot = " + plot + ' ' +
                ", director = " + director + ' ' +
                ", writer = " + writer + ' ' +
                ", genre = " + genre + ' ';
    }

}
