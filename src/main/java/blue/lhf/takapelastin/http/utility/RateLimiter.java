package blue.lhf.takapelastin.http.utility;

import blue.lhf.takapelastin.http.exception.*;

import java.net.http.*;
import java.time.*;
import java.util.concurrent.locks.*;
import java.util.function.*;

/**
 * A rate limiter for Java's HttpClient, which can be plugged into an asynchronous request.
 * Waits for the given duration if the response has a status code of 429 Too Many Requests.
 * When such an error is encountered, a {@link RateLimitException} is thrown.
 * */
public class RateLimiter<T> implements UnaryOperator<HttpResponse<T>> {
    private final Duration duration;

    /**
     * Constructs a RateLimiter.
     * @param duration The duration to wait for when a 429 Too Many Requests response is received.
     * */
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
