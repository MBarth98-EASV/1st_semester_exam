package easv.app.Utils.Json;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Deserializer<T> implements com.google.gson.JsonDeserializer<T>
{
    private final Class<T> targetClass;

    public Deserializer(Class<T> cls)
    {
        targetClass = cls;
    }

    private T instantiate(Object... args)
    {
        try
        {
            return targetClass.getDeclaredConstructor().newInstance(args);
        }
        catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException ex)
        {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public T deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException
    {
        List<Object> callerArgs = new ArrayList<>();

        // get most descriptive constructor
        Constructor<?> constructor = Arrays.stream(targetClass.getConstructors()).max(Comparator.comparingInt(value -> value.getParameterTypes().length)).orElseThrow();

        // ordered parameter list
        for (var param : constructor.getParameters())
        {
            System.out.println(type);
            System.out.println(param);
            System.out.println(jsonElement);
        }

        if (callerArgs.isEmpty())
        {
            // only a parameterless or default constructor exist
            return instantiate();
        }
        else
        {
            // create new instance with data from json
            return instantiate(callerArgs);
        }
    }
}
