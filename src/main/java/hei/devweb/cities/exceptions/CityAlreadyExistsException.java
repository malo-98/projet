package hei.devweb.cities.exceptions;

public class CityAlreadyExistsException extends RuntimeException {
    public CityAlreadyExistsException() {
        super();
    }

    public CityAlreadyExistsException(String message) {
        super(message);
    }

    public CityAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public CityAlreadyExistsException(Throwable cause) {
        super(cause);
    }

    protected CityAlreadyExistsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
