package ru.adideas.backend_spring_media_api.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("api/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public User currentUser(UserAuthentication auth) {
        return auth.getDetails();
    }

    @GetMapping("friends")
    public Collection<User> friends(UserAuthentication auth) {
        return userService.getCollectionFriends(auth.getDetails().getId());
    }

    @GetMapping("banned")
    public Collection<User> banned(UserAuthentication auth) {
        return userService.getCollectionBannedUsers(auth.getDetails().getId());
    }

    @PostMapping("/{id}/friend")
    public ResponseEntity<?> requestAddFriend(@PathVariable("id") Integer friend_id, UserAuthentication auth) {
        if (userService.requestAddUserInFriend(auth.getPrincipal(), friend_id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body("");
    }

    @DeleteMapping("/{id}/friend")
    public ResponseEntity<?> requestDelInFriend(@PathVariable("id") Integer friend_id, UserAuthentication auth) {
        userService.removeUserInFriend(auth.getPrincipal(), friend_id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/ban")
    public ResponseEntity<?> requestAddInBan(@PathVariable("id") Integer friend_id, UserAuthentication auth) {
        if (userService.requestAddUserInBan(auth.getPrincipal(), friend_id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body("");
    }

    @DeleteMapping("/{id}/ban")
    public ResponseEntity<?> requestDelInBan(@PathVariable("id") Integer friend_id, UserAuthentication auth) {
        userService.removeUserInBan(auth.getPrincipal(), friend_id);
        return ResponseEntity.ok().build();
    }
}
