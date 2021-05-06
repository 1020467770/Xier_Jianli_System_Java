package cn.sqh.domain.result;
import lombok.Data;
import org.springframework.security.core.userdetails.User;


@Data
public class LoginResult {

    private User user;
    private String csrfToken;

}
