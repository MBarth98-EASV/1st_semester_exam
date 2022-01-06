package easv.app.Utils.Json;

import com.google.gson.*;
import easv.app.be.MovieModel;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MovieModelDeserializer implements JsonDeserializer<ArrayList<MovieModel>>
{
    @Override
    public ArrayList<MovieModel> deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException
    {
        JsonArray array = jsonElement.getAsJsonArray();

        for (int i = 0; i < array.size(); i++)
        {
            System.out.println(array.get(i).getAsJsonObject());
        }

        return new ArrayList<>();
    }
}
