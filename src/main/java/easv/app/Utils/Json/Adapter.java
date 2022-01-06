package easv.app.Utils.Json;

import com.google.gson.JsonDeserializer;

public abstract class Adapter<T extends Adapter<?>>
{
    public Adapter(Class<T> _this)
    {
        if (Adapters.get(_this.getName()) == null)
        {
            Adapters.add(_this.getName(), deserializer());
        }
    }

    protected abstract JsonDeserializer<T> deserializer();
}
