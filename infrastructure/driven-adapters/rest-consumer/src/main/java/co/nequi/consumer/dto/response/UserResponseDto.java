package co.nequi.consumer.dto.response;

import lombok.Data;

@Data
public class UserResponseDto {

    private Long id;
    private String first_name;
    private String last_name;
    private String email;
}
