package easv.app.be;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class MovieModel
{
    /*
    *   BELOW: bindable properties
    */

    private final StringProperty ID = new SimpleStringProperty();
    private final StringProperty title = new SimpleStringProperty();
    private final StringProperty year = new SimpleStringProperty();
    private final StringProperty ageRating = new SimpleStringProperty();
    private final StringProperty releaseDate = new SimpleStringProperty();
    private final StringProperty runtime = new SimpleStringProperty();
    private final StringProperty imdbRating = new SimpleStringProperty();
    private final StringProperty genre = new SimpleStringProperty();
    private final StringProperty director = new SimpleStringProperty();
    private final StringProperty writer = new SimpleStringProperty();
    private final StringProperty actors = new SimpleStringProperty();
    private final StringProperty plot = new SimpleStringProperty();
    private final StringProperty language = new SimpleStringProperty();
    private final StringProperty country = new SimpleStringProperty();
    private final StringProperty personalRating = new SimpleStringProperty();
    private final StringProperty path = new SimpleStringProperty();
    private final StringProperty lastViewed = new SimpleStringProperty();
    private final StringProperty type = new SimpleStringProperty();
    private final ObjectProperty<ImageView> poster = new SimpleObjectProperty<>();


    /*
    *   BELOW: standard getters and setters
    */

    public String getTitle()
    {
        return title.get();
    }

    public StringProperty titleProperty()
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

    public StringProperty IDProperty()
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

    public StringProperty yearProperty()
    {
        return year;
    }

    public void setYear(String year)
    {
        this.year.set(year);
    }

    public String getAgeRating()
    {
        return ageRating.get();
    }

    public StringProperty ageRatingProperty()
    {
        return ageRating;
    }

    public void setAgeRating(String ageRating)
    {
        this.ageRating.set(ageRating);
    }

    public String getReleaseDate()
    {
        return releaseDate.get();
    }

    public StringProperty releaseDateProperty()
    {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate)
    {
        this.releaseDate.set(releaseDate);
    }

    public String getType()
    {
        return type.get();
    }

    public StringProperty typeProperty()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type.set(type);
    }


    public String getRuntime()
    {
        return runtime.get();
    }

    public StringProperty runtimeProperty()
    {
        return runtime;
    }

    public void setRuntime(String runtime)
    {
        this.runtime.set(runtime);
    }

    public String getImdbRating()
    {
        return imdbRating.get();
    }

    public StringProperty imdbRatingProperty()
    {
        return imdbRating;
    }

    public void setImdbRating(String rated)
    {
        this.imdbRating.set(rated);
    }

    public String[] getGenre()
    {
        // todo: refactor

        

        String[] returnArray;
        String[] initialArray = genre.get().split(",");
        String str1;
        String str2;
        String str3;
        if (initialArray.length > 3){
            return returnArray = new String[] {initialArray[0].strip(), initialArray[1].strip(), initialArray[2].strip()};
        }
        switch (initialArray.length){
            case 1:
                str1 = initialArray[0].strip();
                returnArray = new String[] {str1, "N/A", "N/A"};
                return returnArray;
            case 2:
                str1 = initialArray[0].strip();
                str2 = initialArray[1].strip();
                returnArray = new String[] {str1, str2, "N/A"};
                return returnArray;
            case 3:
                str1 = initialArray[0].strip();
                str2 = initialArray[1].strip();
                str3 = initialArray[2].strip();
                returnArray = new String[] {str1, str2, str3};
                return returnArray;
            default:
                returnArray = new String[] {"N/A", "N/A", "N/A"};
                return returnArray;
        }
    }

    public StringProperty genreProperty()
    {
        return genre;
    }

    public void setGenre(String genre)
    {
        this.genre.set(genre);
    }

    public String getDirector()
    {
        return director.get();
    }

    public StringProperty directorProperty()
    {
        return director;
    }

    public void setDirector(String director)
    {
        this.director.set(director);
    }

    public String getWriter()
    {
        return writer.get();
    }

    public StringProperty writerProperty()
    {
        return writer;
    }

    public void setWriter(String writer)
    {
        this.writer.set(writer);
    }

    public String getActors()
    {
        return actors.get();
    }

    public StringProperty actorsProperty()
    {
        return actors;
    }

    public void setActors(String actors)
    {
        this.actors.set(actors);
    }

    public String getPlot()
    {
        return plot.get();
    }

    public StringProperty plotProperty()
    {
        return plot;
    }

    public void setPlot(String plot)
    {
        this.plot.set(plot);
    }

    public String getLanguage()
    {
        return language.get();
    }

    public StringProperty languageProperty()
    {
        return language;
    }

    public void setLanguage(String language)
    {
        this.language.set(language);
    }

    public String getCountry()
    {
        return country.get();
    }

    public StringProperty countryProperty()
    {
        return country;
    }

    public void setCountry(String country)
    {
        this.country.set(country);
    }

    public String getPersonalRating()
    {
        return personalRating.get();
    }

    public StringProperty personalRatingProperty()
    {
        return personalRating;
    }

    public void setPersonalRating(String personalRating)
    {
        this.personalRating.set(personalRating);
    }

    public String getPath()
    {
        return path.get();
    }

    public StringProperty pathProperty()
    {
        return path;
    }

    public void setPath(String path)
    {
        this.path.set(path);
    }

    public String getLastViewed()
    {
        return lastViewed.get();
    }

    public StringProperty lastViewedProperty()
    {
        return lastViewed;
    }

    public void setLastViewed(String lastViewed)
    {
        this.lastViewed.set(lastViewed);
    }

    public ImageView getPoster()
    {
        return poster.get();
    }

    public ObjectProperty<ImageView> posterProperty()
    {
        return poster;
    }

    public void setPoster(ImageView poster)
    {
        this.poster.set(poster);
    }

    public void setPoster(String posterURL)
    {
        if (posterURL == null || posterURL.equals("N/A"))
        {
            this.poster.set(new ImageView((Image)null));
        }
        else
        {
            this.poster.set(new ImageView(posterURL));
        }
    }


    @Override
    public String toString()
    {
        return "MovieModel{" +
                "ID=" + ID +
                ", title=" + title +
                ", year=" + year +
                ", ageRating=" + ageRating +
                ", releaseDate=" + releaseDate +
                ", runtime=" + runtime +
                ", rated=" + imdbRating +
                ", genre=" + genre +
                ", director=" + director +
                ", writer=" + writer +
                ", actors=" + actors +
                ", plot=" + plot +
                ", language=" + language +
                ", country=" + country +
                ", personalRating=" + personalRating +
                ", path=" + path +
                ", lastViewed=" + lastViewed +
                ", type=" + type +
                ", poster=" + poster +
                '}';
    }
}
