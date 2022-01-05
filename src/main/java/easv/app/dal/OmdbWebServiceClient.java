package easv.app.dal;

import com.fasterxml.jackson.core.*;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import easv.app.be.*;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

    public class OmdbWebServiceClient {

        public static final String SEARCH_URL = "http://www.omdbapi.com/?s=TITLE&apikey=f51b4727";
        public static final String SEARCH_IMDB_URL = "http://www.omdbapi.com/?i=IMDB&apikey=f51b4727";
        private static final String S = "{\"Title\":\"Batman Begins\",\"Year\":\"2005\",\"imdbID\":\"tt0372784\",\"Type\":\"movie\",\"Poster\":\"https://m.media-amazon.com/images/M/MV5BOTY4YjI2N2MtYmFlMC00ZjcyLTg3YjEtMDQyM2ZjYzQ5YWFkXkEyXkFqcGdeQXVyMTQxNzMzNDI@._V1_SX300.jpg\"},{\"Title\":\"Batman v Superman: Dawn of Justice\",\"Year\":\"2016\",\"imdbID\":\"tt2975590\",\"Type\":\"movie\",\"Poster\":\"https://m.media-amazon.com/images/M/MV5BYThjYzcyYzItNTVjNy00NDk0LTgwMWQtYjMwNmNlNWJhMzMyXkEyXkFqcGdeQXVyMTQxNzMzNDI@._V1_SX300.jpg\"},{\"Title\":\"Batman\",\"Year\":\"1989\",\"imdbID\":\"tt0096895\",\"Type\":\"movie\",\"Poster\":\"https://m.media-amazon.com/images/M/MV5BMTYwNjAyODIyMF5BMl5BanBnXkFtZTYwNDMwMDk2._V1_SX300.jpg\"},{\"Title\":\"Batman Returns\",\"Year\":\"1992\",\"imdbID\":\"tt0103776\",\"Type\":\"movie\",\"Poster\":\"https://m.media-amazon.com/images/M/MV5BOGZmYzVkMmItM2NiOS00MDI3LWI4ZWQtMTg0YWZkODRkMmViXkEyXkFqcGdeQXVyODY0NzcxNw@@._V1_SX300.jpg\"},{\"Title\":\"Batman Forever\",\"Year\":\"1995\",\"imdbID\":\"tt0112462\",\"Type\":\"movie\",\"Poster\":\"https://m.media-amazon.com/images/M/MV5BNDdjYmFiYWEtYzBhZS00YTZkLWFlODgtY2I5MDE0NzZmMDljXkEyXkFqcGdeQXVyMTMxODk2OTU@._V1_SX300.jpg\"},{\"Title\":\"Batman & Robin\",\"Year\":\"1997\",\"imdbID\":\"tt0118688\",\"Type\":\"movie\",\"Poster\":\"https://m.media-amazon.com/images/M/MV5BMGQ5YTM1NmMtYmIxYy00N2VmLWJhZTYtN2EwYTY3MWFhOTczXkEyXkFqcGdeQXVyNTA2NTI0MTY@._V1_SX300.jpg\"},{\"Title\":\"The Lego Batman Movie\",\"Year\":\"2017\",\"imdbID\":\"tt4116284\",\"Type\":\"movie\",\"Poster\":\"https://m.media-amazon.com/images/M/MV5BMTcyNTEyOTY0M15BMl5BanBnXkFtZTgwOTAyNzU3MDI@._V1_SX300.jpg\"},{\"Title\":\"Batman: The Animated Series\",\"Year\":\"1992–1995\",\"imdbID\":\"tt0103359\",\"Type\":\"series\",\"Poster\":\"https://m.media-amazon.com/images/M/MV5BOTM3MTRkZjQtYjBkMy00YWE1LTkxOTQtNDQyNGY0YjYzNzAzXkEyXkFqcGdeQXVyOTgwMzk1MTA@._V1_SX300.jpg\"},{\"Title\":\"Batman: Under the Red Hood\",\"Year\":\"2010\",\"imdbID\":\"tt1569923\",\"Type\":\"movie\",\"Poster\":\"https://m.media-amazon.com/images/M/MV5BNmY4ZDZjY2UtOWFiYy00MjhjLThmMjctOTQ2NjYxZGRjYmNlL2ltYWdlL2ltYWdlXkEyXkFqcGdeQXVyNTAyODkwOQ@@._V1_SX300.jpg\"},{\"Title\":\"Batman: The Dark Knight Returns, Part 1\",\"Year\":\"2012\",\"imdbID\":\"tt2313197\",\"Type\":\"movie\",\"Poster\":\"https://m.media-amazon.com/images/M/MV5BMzIxMDkxNDM2M15BMl5BanBnXkFtZTcwMDA5ODY1OQ@@._V1_SX300.jpg\"}";
        private static final String filename = "E:\\- Media\\Movies\\John Wick Chapter 3 - Parabellum (2019) [WEBRip] [720p] [YTS.LT]\\John.Wick.Chapter.3.-.Parabellum.2019.720p.WEBRip.x264-[YTS.LT].mp4";

        public OmdbWebServiceClient() {
        }


        /**
         * Search Params: Title, Year, imdbID, Type, Poster.
         * @param title
         * @return  ArrayList<HashMap<String, String>>
         */
        public static String searchMovieByTitleAsString(String title){
            try {
                title = URLEncoder.encode(title, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            String requestURL = SEARCH_URL.replaceAll("TITLE", title);

            String json = sendGetRequest(requestURL).substring(10, sendGetRequest(requestURL).length()-40); //Removes unwanted chars.
            return json;
        }

        public static List<Movie> searchMovieByTitle(String title){
            try {
                title = URLEncoder.encode(title, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            String requestURL = SEARCH_URL.replaceAll("TITLE", title);
            String json = sendGetRequest(requestURL).substring(10, sendGetRequest(requestURL).length()-40); //Removes unwanted chars.

            ObjectMapper objectMapper = new ObjectMapper();

            List<Movie> movieList = null;
            ArrayList<Movie> returnList = new ArrayList<>();
            try {
                movieList = objectMapper.readValue(json, new TypeReference<ArrayList<Movie>>() {});
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            for (Movie mov : movieList){
                if (mov.getType().toLowerCase().equals("movie")){
                    returnList.add(mov);
                }
            }
            if (returnList == null){
                return new ArrayList<Movie>();
            } else return returnList;
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


        public static String trimFileName(String filePath){
            File f = new File(filePath);
            String filename = f.getName();
            if (filename.contains("."))
                filename = filename.substring(0, filename.lastIndexOf('.')); //Removes extension.
            if (filename.contains("."))
                filename = filename.substring(0, filename.indexOf("."));

            return filename;
        }



        public static void main(String[] args) {
            //System.out.println(searchMovieByTitle(trimFileName(filename)));
            //System.out.println(searchMovieByIMDB(searchMovieByTitle(trimFileName(filename)).get(0)));
            System.out.println(getMovies("avengers"));
            //System.out.println(trimFileName(filename));
        }

    }


