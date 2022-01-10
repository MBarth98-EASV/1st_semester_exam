package easv.app.be;

import com.google.gson.annotations.SerializedName;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class MovieModel
{
    @SerializedName("imdbID")
    private SimpleStringProperty ID;

    @SerializedName("Title")
    private SimpleStringProperty title;

    @SerializedName("Year")
    private SimpleStringProperty year;

    @SerializedName("Rated")
    private SimpleStringProperty ageRating;

    @SerializedName("Released")
    private SimpleStringProperty releaseDate;

    /// ...

    @SerializedName("Type")
    private SimpleStringProperty type;

    @SerializedName("Poster")
    private SimpleStringProperty poster;



    public String getTitle()
    {
        return title.get();
    }

    public SimpleStringProperty titleProperty()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title.set(title);
    }

    public String getID()
    {
        return ID.get();
    }

    public SimpleStringProperty IDProperty()
    {
        return ID;
    }

    public void setID(String ID)
    {
        this.ID.set(ID);
    }

    public String getYear()
    {
        return year.get();
    }

    public SimpleStringProperty yearProperty()
    {
        return year;
    }

    public void setYear(String year)
    {
        this.year.set(year);
    }

    /// ...

    /*

    {
    "Title": "The Avengers",
    "Year": "2012",
    "Rated": "PG-13",
    "Released": "04 May 2012",

    "Runtime": "143 min",
    "Genre": "Action, Adventure, Sci-Fi",
    "Director": "Joss Whedon",
    "Writer": "Joss Whedon, Zak Penn",
    "Actors": "Robert Downey Jr., Chris Evans, Scarlett Johansson",
    "Plot": "Earth's mightiest heroes must come together and learn to fight as a team if they are going to stop the mischievous Loki and his alien army from enslaving humanity.",
    "Language": "English, Russian, Hindi",
    "Country": "United States",
    "Awards": "Nominated for 1 Oscar. 38 wins & 80 nominations total",
    "Poster": "https://m.media-amazon.com/images/M/MV5BNDYxNjQyMjAtNTdiOS00NGYwLWFmNTAtNThmYjU5ZGI2YTI1XkEyXkFqcGdeQXVyMTMxODk2OTU@._V1_SX300.jpg",
    "Ratings": [
        {
            "Source": "Internet Movie Database",
            "Value": "8.0/10"
        },
        {
            "Source": "Rotten Tomatoes",
            "Value": "91%"
        },
        {
            "Source": "Metacritic",
            "Value": "69/100"
        }
    ],
    "Metascore": "69",
    "imdbRating": "8.0",
    "imdbVotes": "1,323,743",
    "imdbID": "tt0848228",
    "Type": "movie",
    "DVD": "25 Sep 2012",
    "BoxOffice": "$623,357,910",
    "Production": "N/A",
    "Website": "N/A",
    "Response": "True"
}

    * */
}
