package easv.app.Utils.Json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonParserFactory
{
    public static Gson create(Class<?>... cls)
    {
        GsonBuilder gson = new GsonBuilder();

        for (Class<?> inst : cls)
        {
            gson.registerTypeAdapter(inst, new Deserialize<>(inst));
        }

        return gson.create();
    }
}
