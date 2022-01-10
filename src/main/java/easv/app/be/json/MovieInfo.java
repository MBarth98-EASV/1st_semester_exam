package easv.app.be.json;

import com.google.gson.annotations.SerializedName;

/**
 * full structure of selected data that we retrieve from imdb through the omdb api (Open Movie Database api)
 * @author mads
 * @author Rasmus
 * */
public class MovieInfo
{
    /**
     * the imdb id - we also use it internally, to ensure that duplicated entries does not occur in OUR database
     * */
    @SerializedName("imdbID")
    public String ID;

    /**
     * full movie title
     * */
    @SerializedName("Title")
    public String title;

    /**
     * the year of release
     * */
    @SerializedName("Year")
    public String year;

    /**
     * movie, series or episode
     * */
    @SerializedName("Type")
    public String type;

    /**
     * the url to a front cover for the movie (may-be N/A)
     * */
    @SerializedName("Poster")
    public String imageURL;

    /**
     * the PG rating of the movie
     * */
    @SerializedName("Rated")
    public String ageRating;

    /**
     * the full date of first release
     * */
    @SerializedName("Released")
    public String releaseDate;

    /**
     * the length of the movie as a string formatted as '120 min'
     * */
    @SerializedName("Runtime")
    public String runtime;

    /**
     * comma seperated list of typically 3 genres
     * */
    @SerializedName("Genre")
    public String genre;

    /**
     * the name of the director
     * */
    @SerializedName("Director")
    public String director;

    /**
     * csv of playwrights
     * */
    @SerializedName("Writer")
    public String writers;

    /**
     * csv of notable actors
     * */
    @SerializedName("Actors")
    public String actors;

    /**
     *  the plot of the movie
     * */
    @SerializedName("Plot")
    public String movieSummary;

    /**
     * the county of origin
     * */
    @SerializedName("Country")
    public String country;

    /**
     * viewer rating between 0.0 - 10.0
     * */
    @SerializedName("imdbRating")
    public String imdbRating;

    @Override
    public String toString()
    {
        return "MovieInfo{" +
                "ID='" + ID + '\'' +
                ", title='" + title + '\'' +
                ", year='" + year + '\'' +
                ", type='" + type + '\'' +
                ", imageURL='" + imageURL + '\'' +
                ", ageRating='" + ageRating + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                ", runtime='" + runtime + '\'' +
                ", genre='" + genre + '\'' +
                ", director='" + director + '\'' +
                ", writers='" + writers + '\'' +
                ", actors='" + actors + '\'' +
                ", movieSummary='" + movieSummary + '\'' +
                ", country='" + country + '\'' +
                ", imdbRating='" + imdbRating + '\'' +
                '}';
    }
}
