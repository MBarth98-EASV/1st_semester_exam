package easv.app.Utils.Json;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class DeserializeWithConstructor<T> implements com.google.gson.JsonDeserializer<T>
{
    private final Class<T> targetClass;

    public DeserializeWithConstructor(Class<T> cls)
    {
        targetClass = cls;
    }

    private T instantiate(Class<?>[] params, Object... args)
    {
        try
        {
            return targetClass.getDeclaredConstructor(params).newInstance(args);
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

        // only a parameterless or default constructor exist
        if (constructor.getParameterTypes().length == 0)
        {
            return instantiate(constructor.getParameterTypes());
        }

        // ordered parameter list
        for (var param : constructor.getParameterTypes())
        {
            System.out.println(type);
            System.out.println(param);
            System.out.println(jsonElement);

            // todo: make args correct
            callerArgs.add(null);
        }

        System.out.println(Arrays.toString(constructor.getParameterTypes()));
        System.out.println(callerArgs);

        // create new instance with data from json
        return instantiate(constructor.getParameterTypes(), callerArgs);
    }
}
