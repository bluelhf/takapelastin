package blue.lhf.takapelastin.http.exception;

import blue.lhf.takapelastin.http.utility.RateLimiter;

/**
 * An exception thrown by {@link RateLimiter} to indicate that a rate limit was reached.
 * */
public class RateLimitException extends RuntimeException {
    public RateLimitException() {
        super("Rate limit reached.");
    }
}
