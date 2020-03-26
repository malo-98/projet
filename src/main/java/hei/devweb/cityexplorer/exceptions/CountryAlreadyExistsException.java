package hei.devweb.cityexplorer.exceptions;

public class CountryAlreadyExistsException extends RuntimeException {

    public CountryAlreadyExistsException() {
    }

    public CountryAlreadyExistsException(String message) {
        super(message);
    }

    public CountryAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public CountryAlreadyExistsException(Throwable cause) {
        super(cause);
    }

    public CountryAlreadyExistsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
