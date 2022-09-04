package ru.adideas.backend_spring_media_api.DTO.DataClass;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import ru.adideas.backend_spring_media_api.Oauth.DTO.JwtRefreshDTO;

public record DataJwtRefreshWithBoolean(boolean valid, String refresh) {
    public boolean getValid() {
        return valid;
    }

    public JwtRefreshDTO getDTO() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JSONObject jsonObject = new JSONObject() {{
                if (refresh != null) {
                    put("refresh", refresh);
                }
            }};
            return objectMapper.readValue(jsonObject.toString(), JwtRefreshDTO.class);
        } catch (Exception e) {
            return null;
        }
    }
}
