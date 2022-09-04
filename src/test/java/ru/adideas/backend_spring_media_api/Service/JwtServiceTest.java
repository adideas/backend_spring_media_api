package ru.adideas.backend_spring_media_api.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.adideas.backend_spring_media_api.Configurations.AccessKeys;
import ru.adideas.backend_spring_media_api.Mock.JwtRepositoryForTest;
import ru.adideas.backend_spring_media_api.Mock.RoleRepositoryForTest;
import ru.adideas.backend_spring_media_api.Mock.UserRepositoryForTest;
import ru.adideas.backend_spring_media_api.Oauth.DTO.JwtAuthDTO;
import ru.adideas.backend_spring_media_api.Oauth.DTO.JwtRefreshDTO;
import ru.adideas.backend_spring_media_api.Oauth.JwtModel;
import ru.adideas.backend_spring_media_api.Oauth.JwtProvider;
import ru.adideas.backend_spring_media_api.Oauth.JwtResponse;
import ru.adideas.backend_spring_media_api.Oauth.JwtService;
import ru.adideas.backend_spring_media_api.Register.RegisterDTO;
import ru.adideas.backend_spring_media_api.User.User;
import ru.adideas.backend_spring_media_api.User.UserPasswordService;
import ru.adideas.backend_spring_media_api.User.UserService;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.security.auth.message.AuthException;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class JwtServiceTest {
    protected ObjectMapper objectMapper;
    protected JwtAuthDTO jwtAuthDTO;
    protected JwtService jwtService;
    protected UserService userService;
    protected JwtRepositoryForTest repository;

    private String getKey() {
        try {
            KeyGenerator keygen = KeyGenerator.getInstance("HmacSHA256");
            SecretKey key = keygen.generateKey();
            return Base64.getEncoder().encodeToString(key.getEncoded());
        } catch (Exception exception) {
            return "";
        }
    }

    protected JwtServiceTest() throws JsonProcessingException, JSONException {
        objectMapper = new ObjectMapper();
        userService = new UserService(new UserRepositoryForTest(), new UserPasswordService(), new RoleRepositoryForTest());

        userService.makeNewUser(new RegisterDTO("Admin", "email@email.ru", "123456"));
        jwtAuthDTO = objectMapper.readValue(new JSONObject() {{
            put("username", "email@email.ru");
            put("password", "123456");
        }}.toString(), JwtAuthDTO.class);

        AccessKeys accessKeys = objectMapper.readValue(new JSONObject() {{
            put("access", getKey());
            put("refresh", getKey());
        }}.toString(), AccessKeys.class);

        repository = new JwtRepositoryForTest();
        jwtService = new JwtService(userService, new JwtProvider(accessKeys, repository), repository);
    }

    @Test
    public void jwtServiceTest() throws AuthException, JSONException, JsonProcessingException {
        User user = userService.findByEmail("email@email.ru");
        JwtModel jwtModel = new JwtModel();
        jwtModel.setUser(user);
        repository.save(jwtModel);

        assertNotNull(user);
        JwtResponse jwtResponse = jwtService.auth(jwtAuthDTO);
        String accessToken = jwtResponse.accessToken;
        User user1 = jwtService.checkAccessToken(accessToken);
        assertNotNull(user1);
        assertEquals(user1.getId(), user.getId());

        JwtRefreshDTO jwtRefreshDTO = objectMapper.readValue(new JSONObject() {{
            put("refresh", jwtResponse.refreshToken);
        }}.toString(), JwtRefreshDTO.class);

        JwtResponse jwtResponse1 = jwtService.refresh(jwtRefreshDTO);
        assertNotNull(jwtResponse1);
        User user2 = jwtService.checkAccessToken(jwtResponse1.accessToken);
        assertNotNull(user2);
        assertEquals(user2.getId(), user.getId());
    }
}
