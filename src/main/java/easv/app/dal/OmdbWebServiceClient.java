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
            SearchResult search = OpenMovieNetwork.getInstance().search("abc");

            assert search != null;
            for (SearchedMovieInfo mm : Arrays.stream(search.movies).toList())
            {
                System.out.println(mm);
            }

        }
        catch (Exception ex)
        {
            System.out.println("Exception: " + ex.getMessage());
        }
    }
}
