package fi.kivibot.gameinfoback.api.exceptions;

/**
 *
 * @author Nicklas
 */
public class RequestException extends Exception {

    private final int returnCode;

    public RequestException(int returnCode) {
        super("The request returned code: " + returnCode);
        this.returnCode = returnCode;
    }

    public int getReturnCode() {
        return returnCode;
    }
}
