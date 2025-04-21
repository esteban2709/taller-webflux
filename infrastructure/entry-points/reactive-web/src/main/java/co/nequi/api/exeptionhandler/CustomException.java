package co.nequi.api.exeptionhandler;

public class CustomException extends Exception {

    private final ExceptionMessage exceptionMessage;

    public CustomException(String message, ExceptionMessage exceptionMessage) {
        super(message);
        this.exceptionMessage = exceptionMessage;
    }

    public CustomException(Throwable throwable, ExceptionMessage exceptionMessage) {
        super(throwable);
        this.exceptionMessage = exceptionMessage;
    }

}