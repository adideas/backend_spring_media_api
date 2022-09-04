package ru.adideas.backend_spring_media_api.Controller;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.adideas.backend_spring_media_api.Mock.RoleRepositoryForTest;
import ru.adideas.backend_spring_media_api.Mock.UserRepositoryForTest;
import ru.adideas.backend_spring_media_api.Register.RegisterDTO;
import ru.adideas.backend_spring_media_api.User.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserControllerTest {
    UserService userService;
    UserController userController;

    UserControllerTest() {
        userService = new UserService(
                new UserRepositoryForTest(),
                new UserPasswordService(),
                new RoleRepositoryForTest()
        );
        userService.makeNewUser(new RegisterDTO("Admin", "email@email.ru", "123456"));
        userService.makeNewUser(new RegisterDTO("manager", "email1@email.ru", "123456"));
        userController = new UserController(userService);
    }

    @Test
    public void userControllerTestTest() {
        User user = userService.findByEmail("email@email.ru");
        User user1 = userService.findByEmail("email1@email.ru");

        assertNotNull(user);
        assertNotNull(user1);

        UserAuthentication auth = (UserAuthentication) new UserAuthentication(userService).setSubject(user);

        ResponseEntity<?> responseFriend = userController.requestAddFriend(user1.getId(), auth);
        assertSame(responseFriend.getStatusCode(), HttpStatus.OK);

        assertEquals(user, userController.currentUser(auth));
        assertEquals(userController.friends(auth), userService.getCollectionFriends(auth.getPrincipal()));
        assertEquals(userController.friends(auth).size(), userService.getCollectionFriends(auth.getPrincipal()).size());
        assertTrue(userService.getCollectionFriends(auth.getPrincipal()).size() > 0);

        ResponseEntity<?> responseDelFriend = userController.requestDelInFriend(user1.getId(), auth);
        assertSame(responseFriend.getStatusCode(), HttpStatus.OK);
        assertTrue(userService.getCollectionFriends(auth.getPrincipal()).size() < 1);

        //

        ResponseEntity<?> responseBan = userController.requestAddInBan(user1.getId(), auth);
        assertSame(responseBan.getStatusCode(), HttpStatus.OK);

        assertEquals(userController.banned(auth), userService.getCollectionBannedUsers(auth.getPrincipal()));
        assertEquals(userController.banned(auth).size(), userService.getCollectionBannedUsers(auth.getPrincipal()).size());
        assertTrue(userService.getCollectionBannedUsers(auth.getPrincipal()).size() > 0);

        ResponseEntity<?> responseDelBan = userController.requestDelInBan(user1.getId(), auth);
        assertSame(responseFriend.getStatusCode(), HttpStatus.OK);
        assertTrue(userService.getCollectionBannedUsers(auth.getPrincipal()).size() < 1);

    }
}
