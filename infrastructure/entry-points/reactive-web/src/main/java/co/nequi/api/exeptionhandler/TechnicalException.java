package co.nequi.api.exeptionhandler;

public class TechnicalException extends CustomException{
    public TechnicalException(String message, ExceptionMessage exceptionMessage) {
        super(message, exceptionMessage);
    }

    public TechnicalException(Throwable throwable, ExceptionMessage exceptionMessage) {
        super(throwable, exceptionMessage);
    }
}
