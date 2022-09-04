package ru.adideas.backend_spring_media_api.Register;

import ru.adideas.backend_spring_media_api.Configurations.constraints.EmailUnique;

import javax.validation.constraints.*;

public class RegisterDTO {

    public RegisterDTO() {
        //
    }

    public RegisterDTO(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    @NotNull()
    @Size(min = 2, max = 50)
    private String name;

    @NotNull
    @Email
    @EmailUnique
    private String email;

    @NotNull
    @Size(min = 6, max = 12)
    private String password;

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
