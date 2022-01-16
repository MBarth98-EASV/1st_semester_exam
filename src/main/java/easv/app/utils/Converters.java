package easv.app.utils;

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

    /**
     * create a MovieModel using data from Database and web api
     * */
    public static MovieModel convert(DBMovieData dbData, MovieInfo apiInfo)
    {
        MovieModel movie = new MovieModel();

        // api data (imdb)
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
        movie.setYear(apiInfo.year);

        // use data where available (data changed by user is prioritized)
        movie.setTitle(dbData.getTitle() == null || dbData.getTitle().isBlank() ? apiInfo.title : dbData.getTitle());
        movie.setGenre(dbData.getGenre() == null || dbData.getGenre().isBlank() ? apiInfo.genre : dbData.getGenre());

        // data stored in database
        movie.setLastViewed(dbData.getLastViewed());
        movie.setID(dbData.getImdbid());
        movie.setPath(dbData.getFilepath());
        movie.setPersonalRating(dbData.getRating() + "");

        return movie;
    }
}
