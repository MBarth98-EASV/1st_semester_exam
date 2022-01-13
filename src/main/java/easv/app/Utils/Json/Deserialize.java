package easv.app.Utils.Json;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.annotations.SerializedName;
import javafx.scene.control.Alert;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Deserialize<T> implements com.google.gson.JsonDeserializer<T>
{
    private final Class<T> targetClass;

    public Deserialize(Class<T> cls)
    {
        targetClass = cls;
    }

    /**
     * exceptions thrown in this method cannot be forwarded, since the stack unwinding on interface definitions are special,
     * and only the defined exceptions in the interface declaration is allocated on the stack.
     *
     * in this case we have no control over the interface since it is a part of an external library; editing it will most likely be unrecoverable.
     * */
    @Override
    public T deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException
    {
        try
        {
            T result = targetClass.getConstructor().newInstance();

            for (Field field : targetClass.getDeclaredFields())
            {
                SerializedName serializerName = field.getAnnotation(SerializedName.class);

                // is serializable
                if (serializerName != null)
                {
                    if (!field.canAccess(result))
                    {
                        // we need to find a set method or ignore it
                        continue;
                    }

                    var obj = jsonElement.getAsJsonObject().get(serializerName.value());
                    field.set(result, jsonDeserializationContext.deserialize(obj, field.getType()));
                }
            }

            return result;
        }
        catch (InstantiationException | InvocationTargetException | IllegalAccessException | NoSuchMethodException e)
        {
            e.printStackTrace();
            return null;
        }
    }
}
