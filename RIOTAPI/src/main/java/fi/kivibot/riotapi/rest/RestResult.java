package fi.kivibot.riotapi.rest;

/**
 *
 * @author Nicklas
 */
public class RestResult<T> {

    private final T data;
    private final ResponseCode responseCode;

    public RestResult(T data, ResponseCode responseCode) {
        this.data = data;
        this.responseCode = responseCode;
    }

    public T getData() {
        return data;
    }

    public ResponseCode getResponseCode() {
        return responseCode;
    }

    

}
