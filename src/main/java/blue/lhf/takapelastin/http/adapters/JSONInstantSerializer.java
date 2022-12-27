package blue.lhf.takapelastin.http.adapters;

import blue.lhf.takapelastin.server.StatusServer;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.Instant;

/**
 * Serializes the state of an {@link Instant} into JSON for usage in the {@link StatusServer}.
 * */
public class JSONInstantSerializer implements JsonSerializer<Instant> {
    @Override
    public JsonElement serialize(Instant instant, Type type, JsonSerializationContext jsonSerializationContext) {
        return new JsonPrimitive(instant.toString());
    }
}
