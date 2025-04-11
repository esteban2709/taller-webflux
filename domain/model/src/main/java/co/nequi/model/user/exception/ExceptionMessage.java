package co.nequi.model.user.exception;

public enum ExceptionMessage {
    USER_NOT_FOUND("User not found"),
    USER_ALREADY_EXISTS("User already exists"),;

    private final String message;

    ExceptionMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
