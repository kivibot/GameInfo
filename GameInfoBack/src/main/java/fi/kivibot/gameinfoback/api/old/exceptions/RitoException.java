package fi.kivibot.gameinfoback.api.old.exceptions;

/**
 *
 * @author Nicklas
 */
public class RitoException extends Exception {

    private final int returnCode;

    public RitoException(int returnCode) {
        super("Wut rito??? " + returnCode);
        this.returnCode = returnCode;
    }

    public int getReturnCode() {
        return returnCode;
    }

}
