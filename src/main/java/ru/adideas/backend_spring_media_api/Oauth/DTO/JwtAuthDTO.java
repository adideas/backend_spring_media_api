package ru.adideas.backend_spring_media_api.Oauth.DTO;

import ru.adideas.backend_spring_media_api.Configurations.constraints.EmailExist;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class JwtAuthDTO {
    @NotNull
    @Email
    @EmailExist
    private String username;

    @NotNull
    @Size(min = 6, max = 12)
    private String password;

    public String getUsername() {
        return this.username;
    }
    public String getPassword() {
        return this.password;
    }
}
