package ru.adideas.backend_spring_media_api.Controller;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.adideas.backend_spring_media_api.Mock.RoleRepositoryForTest;
import ru.adideas.backend_spring_media_api.Mock.UserRepositoryForTest;
import ru.adideas.backend_spring_media_api.Register.RegisterController;
import ru.adideas.backend_spring_media_api.Register.RegisterDTO;
import ru.adideas.backend_spring_media_api.User.User;
import ru.adideas.backend_spring_media_api.User.UserPasswordService;
import ru.adideas.backend_spring_media_api.User.UserService;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class RegisterControllerTest {
    UserService userService;
    RegisterController registerController;

    RegisterControllerTest() {
        userService = new UserService(
                new UserRepositoryForTest(),
                new UserPasswordService(),
                new RoleRepositoryForTest()
        );
        registerController = new RegisterController(userService);
    }

    @Test
    public void registerControllerTest() {
        registerController.register(new RegisterDTO("Admin", "email@email.ru", "123456"));
        User user = userService.findByEmail("email@email.ru");
        assertNotNull(user);
    }
}
