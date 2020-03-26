package hei.devweb.cityexplorer.exceptions;

public class TooManyActivitiesException extends RuntimeException {
    public TooManyActivitiesException() {
        super();
    }

    public TooManyActivitiesException(String message) {
        super(message);
    }

    public TooManyActivitiesException(String message, Throwable cause) {
        super(message, cause);
    }

    public TooManyActivitiesException(Throwable cause) {
        super(cause);
    }

    protected TooManyActivitiesException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
