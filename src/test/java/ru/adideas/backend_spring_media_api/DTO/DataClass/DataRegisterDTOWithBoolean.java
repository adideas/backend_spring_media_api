package ru.adideas.backend_spring_media_api.DTO.DataClass;

import ru.adideas.backend_spring_media_api.Register.RegisterDTO;

public record DataRegisterDTOWithBoolean(boolean valid, RegisterDTO registerDTO) {

    public static DataRegisterDTOWithBoolean TRUE(String name, String email, String password) {
        return new DataRegisterDTOWithBoolean(true, new RegisterDTO(name, email, password));
    }

    public static DataRegisterDTOWithBoolean FALSE(String name, String email, String password) {
        return new DataRegisterDTOWithBoolean(false, new RegisterDTO(name, email, password));
    }

    public RegisterDTO getRegisterDTO() {
        return registerDTO;
    }

    public boolean getValid() {
        return valid;
    }
}
