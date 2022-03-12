package dartsgame.oauth;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class User {
    private String email;
    private String password;
    private String role;

}
