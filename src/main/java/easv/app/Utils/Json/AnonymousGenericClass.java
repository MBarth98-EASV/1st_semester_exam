package easv.app.Utils.Json;

import java.lang.reflect.ParameterizedType;

/**
 * this is a part of the research on json deserialization on generic types
 *
 * <p></p>
 *
 * <h2>Raison de vivre - why?</h2>
 * this class was/is used instead of creating a new class
 * everytime a typed parameter instance needs a wrapper class.
 *
 * <p></p>
 *
 * <h3>example</h3>
 * <code>
 *     public class IntegerList extends List<<c>int</c>>
 *     {
 *         // possibly an empty class
 *     }
 * </code>
 *
 * <p></p>
 *
 * <h2>details</h2>
 * <p>
 * this solves the problem that a generic class does not produce a typed class before compilation
 * and possibly not until the JIT (Just-In-Time) compiler stage during program execution.
 * <p>
 * meaning that dot-notation on ordinary classes can give you a reference to the class type,
 * whereas a generic class cannot.
 *
 * <p></p>
 *
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
 *          AnonymousGenericClass.create(ArrayList.class, T.class)
 *     </code> - Valid
 * </p>
 *
 * @author mads-
 * */
public class AnonymousGenericClass
{
    public static <T, U> Class<?> create(Class<T> base, Class<U>template)
    {
        ParameterizedType newType = (ParameterizedType) base.getGenericSuperclass();

        newType.getActualTypeArguments()[0] = template;

        return newType.getClass();
    }
}
