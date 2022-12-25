package blue.lhf.takapelastin.http.adapters;

import jakarta.xml.bind.annotation.adapters.*;

import java.time.*;

@SuppressWarnings("unused") // Used reflectively by JAXB
public class XMLInstantAdapter extends XmlAdapter<String, Instant> {
    @Override
    public Instant unmarshal(String s) {
        return Instant.parse(s);
    }

    @Override
    public String marshal(Instant instant) {
        return instant.toString();
    }
}
