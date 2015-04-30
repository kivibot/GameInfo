package fi.kivibot.gameinfoback.api.exception;

/**
 *
 * @author Nicklas
 */
public class RiotSideException extends Exception{

    public RiotSideException() {
    }

    public RiotSideException(String message) {
        super(message);
    }

    public RiotSideException(String message, Throwable cause) {
        super(message, cause);
    }

    public RiotSideException(Throwable cause) {
        super(cause);
    }

    public RiotSideException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
}
