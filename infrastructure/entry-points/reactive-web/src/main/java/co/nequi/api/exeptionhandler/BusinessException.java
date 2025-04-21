package co.nequi.api.exeptionhandler;

public class BusinessException extends CustomException{
    public BusinessException(String message, ExceptionMessage exceptionMessage) {
        super(message, exceptionMessage);
    }

    public BusinessException(Throwable throwable, ExceptionMessage exceptionMessage) {
        super(throwable, exceptionMessage);
    }

}
