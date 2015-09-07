package fi.kivibot.riotapi.exception;

/**
 *
 * @author Nicklas
 */
public class RateLimitException extends Exception {

    public RateLimitException() {
    }

    public RateLimitException(String message) {
        super(message);
    }

    public RateLimitException(String message, Throwable cause) {
        super(message, cause);
    }

    public RateLimitException(Throwable cause) {
        super(cause);
    }
    
}
