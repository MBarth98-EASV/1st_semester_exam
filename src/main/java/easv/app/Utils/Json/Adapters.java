package easv.app.Utils.Json;

import com.google.gson.*;
import easv.app.be.api.MovieModel;
import easv.app.be.api.search.SearchModel;

import java.lang.reflect.Type;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;


public class Adapters
{
    private static final Dictionary<String, JsonDeserializer<?>> Deserializers = new Hashtable<>();

    public static <T> void add(String name, JsonDeserializer<T> deserializer)
    {
        Deserializers.put(name, deserializer);
    }

    public static JsonDeserializer<?> get(String className)
    {
        return Deserializers.get(className);
    }
}
