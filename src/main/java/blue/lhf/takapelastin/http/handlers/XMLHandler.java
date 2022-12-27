package blue.lhf.takapelastin.http.handlers;

import blue.lhf.takapelastin.model.DroneReport;
import blue.lhf.takapelastin.http.exception.*;
import jakarta.xml.bind.*;
import org.glassfish.jaxb.runtime.v2.runtime.JAXBContextImpl;
import org.xml.sax.*;

import java.io.*;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * Parses and unmarshals XML data into the given class using JAXB.
 * */
public class XMLHandler<T> implements StreamHandler<T> {
    private final Class<T> targetClass;
    private final Unmarshaller unmarshaller;

    /**
     * Constructs an XML parser which unmarshals data to the given target class.
     * @param targetClass The class to convert XML data into, via JAXB unmarshalling.
     * @throws IllegalStateException If the XML parser or JAXB unmarshaller could not be instantiated.
     * */
    public XMLHandler(final Class<T> targetClass) {
        this.targetClass = targetClass;

        try {
            final JAXBContext context = new JAXBContextImpl.JAXBContextBuilder()
                .setXmlAccessorFactorySupport(true)
                .setClasses(new Class[]{DroneReport.class})
                .build();

            this.unmarshaller = context.createUnmarshaller();

        } catch (JAXBException e) {
            throw new IllegalStateException("XML parser could not be instantiated due to a terrible exception.", e);
        }
    }

    @Override
    public T handle(InputStream stream) {
        try (final InputStreamReader reader = new InputStreamReader(stream, UTF_8)) {
            final InputSource source = new InputSource(reader);
            return targetClass.cast(unmarshaller.unmarshal(source));
        } catch (JAXBException e) {
            throw DataException.invalidData(e);
        } catch (IOException e) {
            throw DataException.unfinishedData(e);
        }
    }
}
