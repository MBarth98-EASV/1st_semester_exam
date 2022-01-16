package easv.app.be;

import javafx.beans.property.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Arrays;
import java.util.List;


public class MovieModel extends SimpleObjectProperty<MovieModel>
{
    /*
    *   BELOW: bindable properties
    */

    public MovieModel()
    {
        this.set(this);
    }

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
        this.fireValueChangedEvent();
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
        this.fireValueChangedEvent();
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
        this.fireValueChangedEvent();
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
        this.fireValueChangedEvent();
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
        this.fireValueChangedEvent();
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
        this.fireValueChangedEvent();
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
        this.fireValueChangedEvent();
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
        this.fireValueChangedEvent();
    }

    public String[] getGenre()
    {
        if (genre.get() != null && !genre.get().isBlank())
        {
            String[] genres = genre.get().split(",");

            for (int i = 0; i < 3; i++)
            {
                if (genres[i] == null || genres[i].isBlank())
                {
                    genres[i] = "N/A";
                }
                else
                {
                    genres[i] = genres[i].strip();
                }
            }

            return genres;
        }

        return new String[]{"N/A", "N/A", "N/A"};
    }

    public StringProperty genreProperty()
    {
        return genre;
    }

    public void setGenre(String[] genre)
    {
        this.genre.set("%s, %s, %s".formatted(genre[0], genre[1], genre[2]));
        this.fireValueChangedEvent();
    }

    public void setGenre(String genre)
    {
        this.genre.set(genre);
        this.fireValueChangedEvent();
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
        this.fireValueChangedEvent();
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
        this.fireValueChangedEvent();
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
        this.fireValueChangedEvent();
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
        this.fireValueChangedEvent();
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
        this.fireValueChangedEvent();
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
        this.fireValueChangedEvent();
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
        this.fireValueChangedEvent();
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
        this.fireValueChangedEvent();
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
        this.fireValueChangedEvent();
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

        this.fireValueChangedEvent();
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
                ", country=" + country +
                ", personalRating=" + personalRating +
                ", path=" + path +
                ", lastViewed=" + lastViewed +
                ", type=" + type +
                ", poster=" + poster +
                '}';
    }
}
