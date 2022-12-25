package blue.lhf.takapelastin.http.utility;

import blue.lhf.takapelastin.http.exception.*;

import java.net.http.*;
import java.time.*;
import java.util.concurrent.locks.*;
import java.util.function.*;

public class RateLimiter<T> implements UnaryOperator<HttpResponse<T>> {
    private final Duration duration;

    public RateLimiter(final Duration duration) {
        this.duration = duration;
    }

    @Override
    public HttpResponse<T> apply(HttpResponse<T> response) {
        if (response.statusCode() == 429) {
            LockSupport.parkNanos(duration.toNanos());
            throw new RateLimitException();
        } else {
            return response;
        }
    }
}
