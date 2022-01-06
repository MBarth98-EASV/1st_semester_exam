package easv.app.Utils.Json;

import com.google.gson.*;
import easv.app.be.api.MovieModel;
import easv.app.be.api.search.SearchModel;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

public class SearchModelDeserializer implements JsonDeserializer<SearchModel>
{

    @Override
    public SearchModel deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException
    {
        JsonArray searchResult = jsonElement.getAsJsonObject().get("Search").getAsJsonArray();

        int elementCount = jsonElement.getAsJsonObject().get("totalResults").getAsInt();

        String response = jsonElement.getAsJsonObject().get("Response").getAsString();

        MovieModelList movieModels = jsonDeserializationContext.deserialize(searchResult, MovieModelList.class);

        return new SearchModel(movieModels, elementCount, response);
    }
}
