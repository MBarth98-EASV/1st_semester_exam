package easv.app.dal;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import easv.app.be.Movie;
import easv.app.be.SearchResult;
import easv.app.be.api.MovieModel;
import easv.app.be.api.search.SearchModel;
import easv.app.dal.api.OpenMovieNetwork;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

    public class OmdbWebServiceClient {

        public static final String SEARCH_URL = "http://www.omdbapi.com/?s=TITLE&apikey=f51b4727";
        public static final String SEARCH_IMDB_URL = "http://www.omdbapi.com/?i=IMDB&apikey=f51b4727";

        public OmdbWebServiceClient() {
        }


        public static List<Movie> getMovies(String title) {
            try {
                title = URLEncoder.encode(title, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            String requestURL = SEARCH_URL.replaceAll("TITLE", title);
            String json = sendGetRequest(requestURL);

            try {
                SearchResult result = new ObjectMapper().readValue(json, new TypeReference<SearchResult>() {});
                return result.movies;
            } catch (IOException e) {
                return new ArrayList<>();
            }
        }

        /**
         * returns as JSON: Title, Year, Rated, Released, Runtime, Genre, Director, Writer, Actors, Plot, Language, Country, Awards, Poster, Ratings
         * @param movie  Movie to get IMDB ID from.
         * @return
         */
        public static Movie searchMovieByIMDB(Movie movie){
            String requestURL = SEARCH_IMDB_URL.replaceAll("IMDB", movie.getImdbID());
            String json = sendGetRequest(requestURL);
            HashMap<String, String> map = new HashMap<>();
            ObjectMapper mapper = new ObjectMapper();
            try {
                map = mapper.readValue(json, HashMap.class);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            movie.setRatings(map.get("imdbRating"));
            movie.setRuntime(map.get("Runtime"));
            movie.setLanguage(map.get("Language"));
            movie.setRated(map.get("Rated"));
            movie.setPlot(map.get("Plot"));
            movie.setDirector(map.get("Director"));
            movie.setWriter(map.get("Writer"));
            movie.setGenre(map.get("Genre"));

            //return sendGetRequest(requestURL);
            return movie;
        }

        /**
         * Sends a get request to the OMDB API. Uses an applicable URL, and creates an HTTPURL connection.
         * The API returns a stream of characters matching the query, which is appended to the response stringbuffer and returned.
         * @param requestURL an applicable url for an API query.
         * @return result of query as a JSON string.
         */
        public static String sendGetRequest(String requestURL){
            StringBuffer response = new StringBuffer();

            try {
                URL url = new URL(requestURL);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Accept", "*/*");
                connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                InputStream stream = connection.getInputStream();
                InputStreamReader reader = new InputStreamReader(stream);
                BufferedReader buffer = new BufferedReader(reader);
                String line;
                while ((line = buffer.readLine()) != null) {
                    response.append(line);
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return response.toString();
        }


        public static void main(String[] args)
        {
            try
            {
                SearchModel search = OpenMovieNetwork.getInstance().get("avengers").body();

                System.out.println(search.getResponse());
                System.out.println(search.getItemCount());
                for (MovieModel mm : search.getMovies())
                {
                    System.out.println(mm);
                }

            } catch (Exception ex) {
                System.out.println("Exception: " + ex.getMessage());
            }
        }
    }
