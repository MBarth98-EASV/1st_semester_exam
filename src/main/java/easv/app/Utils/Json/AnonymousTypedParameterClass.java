package easv.app.Utils.Json;

import java.lang.reflect.ParameterizedType;

/**
 * this class was/is used instead of creating a new class everytime a typed parameter instance needs a wrapper class.
 *
 * <p>
 * this solves the problem that a generic class does not produce a typed class before compilation.
 * </p>
 *
 * <p>
 * meaning that dot-notation on ordinary classes can give you a reference to the class type,
 * whereas a generic class cannot.
 * </p>
 * <br/>
 * <p>
 *     <code>
 *         String.class
 *     </code> - Valid
 * </p>
 * <p>
 *     <code>
 *         ArrayList<<c>T</c>>.class
 *     </code> - Invalid
 * </p>
 * <p>
 *     <code>
 *          AnonymousTypedParameterClass.create(ArrayList.class, T.class)
 *     </code> - Valid
 * </p>
 *
 * @author mads-
 * */
public class AnonymousTypedParameterClass
{
    public static <T, U> Class<?> create(Class<T> base, Class<U>template)
    {
        ParameterizedType newType = (ParameterizedType) base.getGenericSuperclass();

        newType.getActualTypeArguments()[0] = template;

        return newType.getClass();
    }
}
