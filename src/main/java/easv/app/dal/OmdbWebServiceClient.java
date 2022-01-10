package easv.app.dal;

import easv.app.be.json.SearchedMovieInfo;
import easv.app.be.json.SearchResult;
import easv.app.dal.api.OpenMovieNetwork;

import java.util.Arrays;

public class OmdbWebServiceClient
{
    public OmdbWebServiceClient() {}

    public static void main(String[] args)
    {
        try
        {
           //SearchResult search = OpenMovieNetwork.getInstance().search("avengers");

            System.out.println(OpenMovieNetwork.getInstance().get(null, "a", false));

            //for (SearchedMovieInfo mm : search.movies)
            //{
            //    System.out.println(OpenMovieNetwork.getInstance().get(mm.ID, "", false));
            //}

        }
        catch (Exception ex)
        {
            System.out.println("Exception: " + ex.getMessage());
        }
    }
}
