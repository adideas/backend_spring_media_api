package ru.adideas.backend_spring_media_api.Service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;
import ru.adideas.backend_spring_media_api.Mock.RoleRepositoryForTest;
import ru.adideas.backend_spring_media_api.Mock.UserRepositoryForTest;
import ru.adideas.backend_spring_media_api.Register.RegisterDTO;
import ru.adideas.backend_spring_media_api.Role.Role;
import ru.adideas.backend_spring_media_api.User.User;
import ru.adideas.backend_spring_media_api.User.UserPasswordService;
import ru.adideas.backend_spring_media_api.User.UserService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceTest {
    UserService userService;

    UserServiceTest() {
        userService = new UserService(
                new UserRepositoryForTest(),
                new UserPasswordService(),
                new RoleRepositoryForTest()
        );
    }

    @Test
    public void userServiceTest1() {
        userService.makeNewUser(new RegisterDTO("Admin", "email@email.ru", "123456"));
        userService.makeNewUser(new RegisterDTO("manager", "email1@email.ru", "123456"));

        User user = userService.findByEmail("email@email.ru");

        assertTrue(userService.countUsers() > 0);
        assertNotNull(user);
        assertTrue(userService.checkUserPassword(user, "123456"));
        assertFalse(userService.checkUserPassword(user, "1234568"));

        User user1 = userService.findUserById(user.getId());
        assertNotNull(user1);
        assertEquals(user1.getId(), user.getId());

        User user2 = userService.findBySubject(user.getId());
        assertNotNull(user2);
        assertEquals(user2.getId(), user.getId());

        assertNull(userService.findByEmail("email2@email.ru"));
        assertNull(userService.findUserById(100));
    }

    @Test
    public void userServiceTest2() {
        userService.makeNewUser(new RegisterDTO("Admin", "email@email.ru", "123456"));
        userService.makeNewUser(new RegisterDTO("manager", "email1@email.ru", "123456"));
        ArrayList<User> users = new ArrayList<User>() {{
            add(userService.findByEmail("email@email.ru"));
            add(userService.findByEmail("email1@email.ru"));
        }};
        userService.requestAddUserInFriend(users.get(0).getId(), users.get(1).getId());
        assertTrue(userService.getCollectionFriends(users.get(0).getId()).size() > 0);

        userService.removeUserInFriend(users.get(0).getId(), users.get(1).getId());
        assertTrue(userService.getCollectionFriends(users.get(0).getId()).size() < 1);

        userService.requestAddUserInBan(users.get(0).getId(), users.get(1).getId());
        assertTrue(userService.getCollectionBannedUsers(users.get(0).getId()).size() > 0);

        userService.removeUserInBan(users.get(0).getId(), users.get(1).getId());
        assertTrue(userService.getCollectionBannedUsers(users.get(0).getId()).size() < 1);

        Collection<?> grantedAuthorities = userService.getGrantedAuthority(users.get(0));
        assertTrue(grantedAuthorities.size() > 0);

        List<?> listGranted = grantedAuthorities.stream().toList();

        assertTrue(listGranted.get(0) instanceof GrantedAuthority);
        assertTrue(listGranted.get(0) instanceof Role);

        if (listGranted.get(0) instanceof GrantedAuthority authority) {
            authority.getAuthority();
        }

        if (listGranted.get(0) instanceof Role role) {
            assertEquals(role.getGranted(), role.getAuthority());
            assertTrue(role.getId() > 0);
        }
    }
}
