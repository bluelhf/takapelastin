package blue.lhf.takapelastin.http.handlers;

import blue.lhf.takapelastin.http.exception.*;
import com.google.gson.*;

import java.io.*;

/**
 * Parses and unmarshals JSON data into the given class using Gson.
 * */
public class JSONHandler<T> implements StreamHandler<T> {
    private final Gson gson = new Gson();
    private final Class<T> targetClass;

    public JSONHandler(final Class<T> targetClass) {
        this.targetClass = targetClass;
    }

    @Override
    public T handle(InputStream stream) {
        try (final var reader = new InputStreamReader(stream)) {
            return gson.fromJson(reader, targetClass);
        } catch (IOException e) {
            throw DataException.unfinishedData(e);
        } catch (JsonSyntaxException e) {
            throw DataException.invalidData(e);
        }
    }
}
