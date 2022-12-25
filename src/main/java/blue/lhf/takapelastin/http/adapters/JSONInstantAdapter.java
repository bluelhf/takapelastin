package blue.lhf.takapelastin.http.adapters;

import com.google.gson.*;
import com.google.gson.stream.*;

import java.io.*;
import java.time.*;

public class JSONInstantAdapter extends TypeAdapter<Instant> {
    @Override
    public void write(JsonWriter out, Instant value) throws IOException {
        if (value == null) {
            out.value((String) null);
            return;
        }
        out.value(value.toString());
    }

    @Override
    public Instant read(JsonReader in) throws IOException {
        return Instant.parse(in.nextString());
    }
}
