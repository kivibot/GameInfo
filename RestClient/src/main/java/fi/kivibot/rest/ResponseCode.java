package fi.kivibot.rest;

/**
 *
 * @author Nicklas
 */
public enum ResponseCode {

    OK(200),
    BAD_REQUEST(400),
    UNAUTHORIZED(401),
    FORBIDDEN(403),
    NOT_FOUND(404),
    TOO_MANY_REQUESTS(429),
    INTERNAL_SERVER_ERROR(500),
    BAD_GATEWAY(502),
    SERVICE_UNAVAILABLE(503),
    GATEWAY_TIMEOUT(504);

    private final int code;

    private ResponseCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public boolean isInformational() {
        return code >= 100 && code < 200;
    }

    public boolean isSuccess() {
        return code >= 200 && code < 300;
    }

    public boolean isRedirection() {
        return code >= 300 && code < 400;
    }

    public boolean isClientError() {
        return code >= 400 && code < 500;
    }

    public boolean isServerError() {
        return code >= 500 && code < 600;
    }

    public static ResponseCode parseInteger(int i) {
        for (ResponseCode rc : values()) {
            if (rc.getCode() == i) {
                return rc;
            }
        }
        throw new IllegalArgumentException("Unknown response: " + i);
    }

}
