package blue.lhf.takapelastin.http.parsers;

import blue.lhf.takapelastin.http.exception.*;
import blue.lhf.takapelastin.http.handlers.*;
import com.google.gson.*;

import java.io.*;

public class JSONParser<T> implements StreamHandler<T> {
    private final Gson gson = new Gson();
    private final Class<T> targetClass;

    public JSONParser(final Class<T> targetClass) {
        this.targetClass = targetClass;
    }

    @Override
    public T handle(InputStream stream) {
        try (final var reader = new InputStreamReader(stream)) {
            return gson.fromJson(reader, targetClass);
        } catch (IOException e) {
            throw DataException.unfinishedData(e);
        }
    }
}
