package blue.lhf.takapelastin.http.parsers;

import blue.lhf.takapelastin.http.exception.*;
import blue.lhf.takapelastin.http.handlers.*;
import jakarta.xml.bind.*;
import org.w3c.dom.*;
import org.xml.sax.*;

import javax.xml.parsers.*;
import java.io.*;

public class XMLParser<T> implements StreamHandler<T> {
    private final Class<T> targetClass;
    private final Unmarshaller unmarshaller;
    private final DocumentBuilder builder;


    public XMLParser(final Class<T> targetClass) {
        this.targetClass = targetClass;

        try {
            final JAXBContext context = JAXBContext.newInstance(targetClass);
            this.unmarshaller = context.createUnmarshaller();

            // Quite the name...
            final DocumentBuilderFactory factory = DocumentBuilderFactory.newDefaultInstance();
            this.builder = factory.newDocumentBuilder();

        } catch (JAXBException | ParserConfigurationException e) {
            throw new IllegalStateException("XML parser could not be instantiated due to a terrible exception.", e);
        }
    }

    public Document parse(final InputStream is) throws DataException {
        try (final InputStream stream = is) {
            return builder.parse(stream);
        } catch (SAXException e) {
            throw DataException.invalidData(e);
        } catch (IOException e) {
            throw DataException.unfinishedData(e);
        }
    }

    @Override
    public T handle(InputStream stream) {
        try {
            final Document document = parse(stream);
            return unmarshaller.unmarshal(document, targetClass).getValue();
        } catch (JAXBException e) {
            throw DataException.invalidData(e);
        }
    }
}
