package ru.adideas.backend_spring_media_api.DTO.DataClass;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import ru.adideas.backend_spring_media_api.Oauth.DTO.JwtAuthDTO;

public record DataJwtAuthWithBoolean(boolean valid, String username, String password) {
    public boolean getValid() {
        return valid;
    }

    public JwtAuthDTO getDTO() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JSONObject jsonObject = new JSONObject() {{
                if (username != null) {
                    put("username", username);
                }
                if (password != null) {
                    put("password", password);
                }
            }};
            return objectMapper.readValue(jsonObject.toString(), JwtAuthDTO.class);
        } catch (Exception e) {
            return null;
        }
    }
}
