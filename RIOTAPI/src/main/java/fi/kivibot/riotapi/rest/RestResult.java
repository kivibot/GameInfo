package fi.kivibot.riotapi.rest;

/**
 *
 * @author Nicklas
 */
public class RestResult<T> {

    private final T value;
    private final ResponseCode responseCode;

    public RestResult(T value, ResponseCode responseCode) {
        this.value = value;
        this.responseCode = responseCode;
    }

    public T getValue() {
        return value;
    }

    public ResponseCode getResponseCode() {
        return responseCode;
    }

}
