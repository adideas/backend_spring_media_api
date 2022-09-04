package ru.adideas.backend_spring_media_api.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import ru.adideas.backend_spring_media_api.Oauth.DTO.JwtRefreshDTO;
import ru.adideas.backend_spring_media_api.Oauth.JwtResponse;
import ru.adideas.backend_spring_media_api.Oauth.OauthController;
import ru.adideas.backend_spring_media_api.Service.JwtServiceTest;
import ru.adideas.backend_spring_media_api.User.User;

import javax.security.auth.message.AuthException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class OauthControllerTest extends JwtServiceTest {
    OauthController oauthController;

    OauthControllerTest() throws JSONException, JsonProcessingException {
        super();
        oauthController = new OauthController(jwtService);
    }

    @Test
    public void oauthControllerTest() throws AuthException, JSONException, JsonProcessingException {
        JwtResponse jwtResponse = oauthController.login(jwtAuthDTO);
        assertNotNull(jwtResponse);
        User user = jwtService.checkAccessToken(jwtResponse.accessToken);
        assertNotNull(user);

        JwtRefreshDTO jwtRefreshDTO = objectMapper.readValue(new JSONObject() {{
            put("refresh", jwtResponse.refreshToken);
        }}.toString(), JwtRefreshDTO.class);

        JwtResponse jwtResponse1 = oauthController.refresh(jwtRefreshDTO);
        assertNotNull(jwtResponse1);
        User user1 = jwtService.checkAccessToken(jwtResponse1.accessToken);
        assertNotNull(user1);
    }
}
