package ru.adideas.backend_spring_media_api;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.adideas.backend_spring_media_api.Mock.RoleRepositoryForTest;
import ru.adideas.backend_spring_media_api.Mock.UserRepositoryForTest;
import ru.adideas.backend_spring_media_api.Register.RegisterDTO;
import ru.adideas.backend_spring_media_api.User.User;
import ru.adideas.backend_spring_media_api.User.UserAuthentication;
import ru.adideas.backend_spring_media_api.User.UserPasswordService;
import ru.adideas.backend_spring_media_api.User.UserService;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserAuthenticationTest {

    UserService userService;

    UserAuthenticationTest() {
        userService = new UserService(
                new UserRepositoryForTest(),
                new UserPasswordService(),
                new RoleRepositoryForTest()
        );
    }

    @Test
    public void userAuthenticationTest() {
        userService.makeNewUser(new RegisterDTO("Admin", "email@email.ru", "123456"));

        User user = userService.findByEmail("email@email.ru");

        assertNotNull(user);

        UserAuthentication userAuthentication = new UserAuthentication(userService);
        userAuthentication.setAuthenticated(true);

        assertFalse(userAuthentication.isAuthenticated());

        Authentication authentication = userAuthentication.setSubject(user);

        SecurityContextHolder.getContext().setAuthentication(
                authentication
        );

        UserAuthentication auth = (UserAuthentication) SecurityContextHolder
                .getContext().getAuthentication();

        assertEquals(auth.getPrincipal(), user.getId());
        assertEquals(auth.getDetails(), user);
        assertEquals(auth.getAuthorities(), userService.getGrantedAuthority(user));
        assertEquals(auth.getName(), user.getName());
        assertEquals(auth.getCredentials(), user.getEmail());
    }
}
