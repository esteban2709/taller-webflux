package co.nequi.api.exeptionhandler;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponse {
    private int status;
    private String title;
    private String detail;
    private String timestamp;
}
