package blue.lhf.takapelastin.http.adapters;

import blue.lhf.takapelastin.checker.registry.*;
import com.google.gson.*;

import java.lang.reflect.*;

public class JSONRegistrySerializer implements JsonSerializer<ViolationRegistry> {

    @Override
    public JsonElement serialize(ViolationRegistry src, Type typeOfSrc, JsonSerializationContext context) {
        final JsonArray violations = new JsonArray();
        for (final var entry : src.entrySet()) {
            final JsonObject violation = (JsonObject) context.serialize(entry.getValue());
            violation.add("pilot", context.serialize(entry.getKey()));
            violations.add(violation);
        }

        return violations;
    }
}
