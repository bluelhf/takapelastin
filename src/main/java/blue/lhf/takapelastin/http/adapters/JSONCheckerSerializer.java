package blue.lhf.takapelastin.http.adapters;

import blue.lhf.takapelastin.checker.*;
import blue.lhf.takapelastin.model.DroneCapture;
import blue.lhf.takapelastin.model.registry.ViolationRegistry;
import blue.lhf.takapelastin.server.StatusServer;
import com.google.gson.*;

import java.lang.reflect.*;

/**
 * Serializes the state of a {@link ViolationChecker} into JSON for usage in the {@link StatusServer}.
 * <p>
 * Serialization does not contain the complete state of the checker: instead, some fields are omitted,
 * and some are flattened. In general, the serialized form of a {@link ViolationChecker} is as follows:
 *
 * <table>
 *     <caption>A table of different fields and their descriptions in the serialized JSON.</caption>
 *     <thead>
 *         <tr>
 *             <th>Property Name</th>
 *             <th>Description</th>
 *             <th>Additional Information</th>
 *         </tr>
 *     </thead>
 *     <tbody>
 *         <tr>
 *             <td><pre>drones?</pre></td>
 *             <td>The most recent data from the drone endpoint, replicated in JSON instead of XML.</td>
 *             <td>Inherited from {@link DroneCapture}</td>
 *         </tr>
 *         <tr>
 *             <td><pre>timestamp?</pre></td>
 *             <td>The time of the last observation, as reported by the on-site observation equipment.</td>
 *             <td>Inherited from {@link DroneCapture}</td>
 *         </tr>
 *         <tr>
 *              <td><pre>violations</pre></td>
 *              <td>The serialised {@link ViolationRegistry}</td>
 *         </tr>
 *         <tr>
 *             <td><pre>tick</pre></td>
 *             <td>The current tick number of the {@link ViolationChecker}</td>
 *         </tr>
 *         <tr>
 *             <td><pre>updatePeriodMillis</pre></td>
 *             <td>The time between the {@link ViolationChecker}'s updates (checks), in milliseconds.</td>
 *         </tr>
 *     </tbody>
 * </table>
 * <sup>
 *     ? indicates a field that may or may not be present. In particular, fields may be missing
 *     if no data has been gathered yet.
 * </sup>
 * */
public class JSONCheckerSerializer implements JsonSerializer<ViolationChecker> {
    @Override
    public JsonElement serialize(ViolationChecker src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject checker = new JsonObject();
        if (src.getLastReport() != null) {
            checker = (JsonObject) context.serialize(src.getLastReport().getCapture());
        }

        checker.add("violations", context.serialize(src.getRegistry()));
        checker.addProperty("tick", src.getTick());
        checker.addProperty("updatePeriodMillis", src.getUpdatePeriod().toMillis());
        return checker;
    }
}
