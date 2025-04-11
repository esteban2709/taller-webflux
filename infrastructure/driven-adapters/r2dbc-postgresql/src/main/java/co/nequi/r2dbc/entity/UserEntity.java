package co.nequi.r2dbc.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@NoArgsConstructor
@Table("users")
public class UserEntity {

    @Id
    private Long id;

    private String firstName;
    private String lastName;
    private String email;
}