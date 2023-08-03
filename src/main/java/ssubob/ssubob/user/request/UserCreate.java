package ssubob.ssubob.user.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserCreate {
    @NotBlank(message = "email is blank")
    private String email;
    @NotBlank(message = "password is blank")
    private String password;
    @NotBlank(message = "nickname is blank")
    private String nickname;

    @Builder
    public UserCreate(String email, String password, String nickname) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
    }
}
