package co.nequi.model.user;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
//import lombok.NoArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class User {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
}
