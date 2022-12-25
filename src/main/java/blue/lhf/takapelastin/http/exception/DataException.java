package blue.lhf.takapelastin.http.exception;

public class DataException extends RuntimeException {
    public DataException(final String message, final Exception cause) {
        super(message, cause);
    }

    public static DataException invalidData(final Exception exception) {
        return new DataException("Invalid XML data was received.", exception);
    }

    public static DataException unfinishedData(final Exception exception) {
        return new DataException("Could not read all data due to an exception", exception);
    }
}
