package easv.app.Utils.Json;

import com.google.gson.*;
import easv.app.be.MovieModel;
import easv.app.be.SearchModel;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class SearchModelDeserializer implements JsonDeserializer<SearchModel>
{

    @Override
    public SearchModel deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException
    {
        JsonArray searchResult = jsonElement.getAsJsonObject().get("Search").getAsJsonArray();

        int elementCount = jsonElement.getAsJsonObject().get("totalResults").getAsInt();

        String response = jsonElement.getAsJsonObject().get("Response").getAsString();
        ArrayList<MovieModel> movieModels = jsonDeserializationContext.deserialize(searchResult, AnonymousGenericClass.create(ArrayList.class, MovieModel.class));

        return new SearchModel(movieModels, elementCount, response);
    }
}
