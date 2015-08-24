package fi.kivibot.riotapi.rest;

/**
 *
 * @author Nicklas
 */
public class RestResult<T> {
    
    private final T value;
    private final int returnCode;

    public RestResult(T value, int returnCode) {
        this.value = value;
        this.returnCode = returnCode;
    }

    public T getValue() {
        return value;
    }

    public int getReturnCode() {
        return returnCode;
    }
    
}
