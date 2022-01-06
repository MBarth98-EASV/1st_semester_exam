package easv.app.Utils.Json;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import easv.app.be.api.MovieModel;

import java.lang.reflect.Type;

public class MovieModelDeserializer implements JsonDeserializer<MovieModelList>
{

    @Override
    public MovieModelList deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException
    {
        return new MovieModelList();
    }
}
