package easv.app.Utils.Json;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * <b>EXPERIMENTAL & RESEARCH CLASS</b><p></p>
 *
 * <p>
 * this class is an unused and experimental generic json deserializer that will create new
 * objects using javas reflection system to find the most suitable constructor and possibly
 * using set methods if variables aren't public (may not be needed, because during runtime we can request that
 * a private variable becomes accessible).
 * </p>
 *  <p></p>
 * <p>
 * the unsolved problem is forwarding constructor parameters when the type is generic and very limited class
 * information is available, regarding the datatypes especially if one or more are classes with typed parameters.
 * (i.e. ArrayList<<c>T</c>>)
 * </p>
 *  <p></p>
 * <p>
 * parameters that expects an implementation of interface has so far resulted in a wrong parameter list.
 * </p>
 *  <p></p>
 * <p>
 * <b>ALL THIS MUST BE DONE WHILE CONVERTING JSON ELEMENTS TO THE CORRECT DATA TYPE</b>
 * </p>
 *
 * @author mads-
 * */
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
