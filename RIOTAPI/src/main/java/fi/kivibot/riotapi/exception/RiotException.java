package fi.kivibot.riotapi.exception;

/**
 *
 * @author Nicklas
 */
public class RiotException extends Exception {

    public RiotException() {
    }

    public RiotException(String message) {
        super(message);
    }

    public RiotException(String message, Throwable cause) {
        super(message, cause);
    }

    public RiotException(Throwable cause) {
        super(cause);
    }

}
