package easv.app.Utils;

import easv.app.be.MovieModel;
import easv.app.be.json.MovieInfo;

public class Converter
{
    // temporary should be done in the data manager (combining api and database data)
    public static MovieModel convert(MovieInfo info)
    {
        MovieModel model = new MovieModel();

        model.setID(info.ID);
        model.setTitle(info.title);
        model.setYear(info.year);
        model.setPoster(info.imageURL);
        model.setAgeRating(info.ageRating);

        model.setReleaseDate(info.releaseDate);
        model.setType(info.type);
        model.setActors(info.actors);
        model.setCountry(info.country);
        model.setGenre(info.genre);
        model.setImdbRating(info.imdbRating);
        model.setDirector(info.director);
        model.setPlot(info.movieSummary);
        model.setWriter(info.writers);
        model.setRuntime(info.runtime);

        return model;
    }
}
