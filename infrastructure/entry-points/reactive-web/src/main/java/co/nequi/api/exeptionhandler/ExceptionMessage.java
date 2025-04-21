package co.nequi.api.exeptionhandler;

public enum ExceptionMessage {
    NO_DATA_FOUND("No data was found for the requested operation"),
    USER_ID_REQUIRED("User ID is required"),
    INVALID_REQUEST("Invalid request"), USER_NOT_FOUND("User not found"),
    CLIENT_ERROR("Client error"), EXTERNAL_SERVICE_ERROR("External service error"),;

    private final String message;

    ExceptionMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}