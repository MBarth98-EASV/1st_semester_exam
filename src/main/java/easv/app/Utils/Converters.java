package easv.app.Utils;

import easv.app.be.MovieModel;
import easv.app.be.json.MovieInfo;
import easv.app.be.DBMovieData;

import java.util.ArrayList;
import java.util.List;

public class Converters
{

    public static List<MovieModel> convert(List<DBMovieData> dbDataList, List<MovieInfo> apiInfoList)
    {
        if (dbDataList.size() != apiInfoList.size())
        {
            throw new IllegalArgumentException("the lists must be the same number of elements");
        }

        List<MovieModel> movies = new ArrayList<>();

        // doesn't matter which size we use
        for (int i = 0; i < dbDataList.size(); i++)
        {
            movies.add(convert(dbDataList.get(i), apiInfoList.get(i)));
        }

        return movies;
    }

    public static MovieModel convert(DBMovieData dbData, MovieInfo apiInfo)
    {
        MovieModel movie = new MovieModel();

        movie.setPoster(apiInfo.imageURL);
        movie.setRuntime(apiInfo.runtime);
        movie.setWriter(apiInfo.writers);
        movie.setPlot(apiInfo.movieSummary);
        movie.setDirector(apiInfo.director);
        movie.setCountry(apiInfo.country);
        movie.setActors(apiInfo.actors);
        movie.setType(apiInfo.type);
        movie.setReleaseDate(apiInfo.releaseDate);
        movie.setAgeRating(apiInfo.ageRating);
        movie.setImdbRating(apiInfo.imdbRating);
        movie.setTitle(apiInfo.title);
        movie.setYear(apiInfo.year);

        if (dbData.getGenre() == null || dbData.getGenre().isBlank())
        {
            movie.setGenre(apiInfo.genre);
        }
        else
        {
            movie.setGenre(dbData.getGenre());
        }

        movie.setLastViewed(dbData.getLastViewed());
        movie.setID(dbData.getImdbid());

        movie.setPath(dbData.getFilepath());
        movie.setPersonalRating(dbData.getRating() + "");

        return movie;
    }
}
