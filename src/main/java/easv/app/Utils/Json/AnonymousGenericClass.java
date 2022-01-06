package easv.app.Utils.Json;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;


public class AnonymousGenericClass
{
    /**
     *
     * */
    public static <T, U> Type create(Class<T> base, Class<U> template)
    {
        return ((ParameterizedType) base.getGenericSuperclass()).getActualTypeArguments()[0] = template;
    }
}
