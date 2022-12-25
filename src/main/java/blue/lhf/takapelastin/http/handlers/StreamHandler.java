package blue.lhf.takapelastin.http.handlers;

import java.io.*;
import java.net.http.*;
import java.util.function.*;

import static java.net.http.HttpResponse.BodySubscribers.*;

/**
 * An HTTP body handler that accepts an input stream and maps it to an arbitrary output type.
 * @param <T> The output type
 * */
public interface StreamHandler<T> extends HttpResponse.BodyHandler<Supplier<T>> {

    T handle(final InputStream stream);

    @Override
    default HttpResponse.BodySubscriber<Supplier<T>> apply(HttpResponse.ResponseInfo responseInfo) {
        return mapping(ofInputStream(), (stream) -> () -> handle(stream));
    }
}
