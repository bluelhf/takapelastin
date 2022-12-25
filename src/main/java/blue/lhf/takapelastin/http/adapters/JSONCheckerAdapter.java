package blue.lhf.takapelastin.http.adapters;

import blue.lhf.takapelastin.checker.*;
import com.google.gson.*;

import java.lang.reflect.*;

public class JSONCheckerAdapter implements JsonSerializer<ViolationChecker> {
    @Override
    public JsonElement serialize(ViolationChecker src, Type typeOfSrc, JsonSerializationContext context) {
        if (src.getLastReport() == null) {
            final JsonObject object = new JsonObject();
            object.addProperty("message", "No data gathered yet. Please check back in a few seconds.");
            return object;
        }

        final JsonObject task = (JsonObject) context.serialize(src.getLastReport().getCapture());
        task.add("violations", context.serialize(src.getRegistry()));
        task.addProperty("tick", src.getTick());
        task.addProperty("updatePeriodMillis", src.getUpdatePeriod().toMillis());
        return task;
    }
}
