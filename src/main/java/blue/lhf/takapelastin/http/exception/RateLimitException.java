package blue.lhf.takapelastin.http.exception;

public class RateLimitException extends RuntimeException {
    public RateLimitException() {
        super("Rate limit reached.");
    }
}
