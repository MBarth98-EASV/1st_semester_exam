package easv.app.be;

import easv.app.be.json.SearchedMovieInfo;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class SearchedMovieModel
{
    public static final SearchedMovieModel fromSearchInfo(final SearchedMovieInfo info)
    {
        SearchedMovieModel model = new SearchedMovieModel();

        model.setImdbID(info.ID);
        model.setTitle(info.title);
        model.setType(info.type);
        model.setYear(info.year);
        model.setImageURL(info.imageURL);

        return model;
    }


    /*
     *   BELOW: bindable properties
     */

    private final StringProperty ImdbID = new SimpleStringProperty();
    private final StringProperty title = new SimpleStringProperty();
    private final StringProperty year = new SimpleStringProperty();
    private final StringProperty type = new SimpleStringProperty();
    private final StringProperty imageURL = new SimpleStringProperty();

    /*
     *   BELOW: standard getters and setters
     */

    public void setImdbID(String id)
    {
        this.ImdbID.set(id);
    }

    public void setTitle(String title)
    {
        this.title.set(title);
    }

    public void setYear(String year)
    {
        this.year.set(year);
    }

    public void setType(String type)
    {
        this.type.set(type);
    }

    public void setImageURL(String url)
    {
        this.imageURL.set(url);
    }

    public String getImdbID()
    {
        return ImdbID.get();
    }

    public StringProperty imdbIDProperty()
    {
        return ImdbID;
    }

    public String getTitle()
    {
        return title.get();
    }

    public StringProperty titleProperty()
    {
        return title;
    }

    public String getYear()
    {
        return year.get();
    }

    public StringProperty yearProperty()
    {
        return year;
    }

    public String getType()
    {
        return type.get();
    }

    public StringProperty typeProperty()
    {
        return type;
    }

    public String getImageURL()
    {
        return imageURL.get();
    }

    public StringProperty imageURLProperty()
    {
        return imageURL;
    }

    /*@Override
    public String toString()
    {
        return "SearchedMovieModel{" +
                "ImdbID=" + ImdbID +
                ", title=" + title +
                ", year=" + year +
                ", type=" + type +
                ", imageURL=" + imageURL +
                '}';
    }

     */
    @Override
    public String toString(){
        return title.get() + " (" + year.get() + ")";
    }
}
