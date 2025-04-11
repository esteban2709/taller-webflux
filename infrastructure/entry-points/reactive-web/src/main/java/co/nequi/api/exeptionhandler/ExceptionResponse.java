package co.nequi.api.exeptionhandler;

public enum ExceptionResponse {
    NO_DATA_FOUND("No data was found for the requested operation"),
    USER_ID_REQUIRED("User ID is required"),;

    private final String message;

    ExceptionResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}