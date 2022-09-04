package ru.adideas.backend_spring_media_api.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.lang.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import ru.adideas.backend_spring_media_api.Register.RegisterDTO;
import ru.adideas.backend_spring_media_api.Role.RoleRepository;
import ru.adideas.backend_spring_media_api.User.Repository.UserRepository;

import java.util.Collection;

@Service
@ConstructorBinding
public class UserService {
    private final UserRepository userRepository;
    private final UserPasswordService passwordService;
    private final RoleRepository roleRepository;

    @Autowired
    public UserService(UserRepository userRepository, UserPasswordService passwordService, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordService = passwordService;
        this.roleRepository = roleRepository;
    }

    public Integer countUsers() {
        return this.userRepository.count();
    }

    @Nullable
    public User findByEmail(String username) {
        return this.userRepository.findByEmail(username).orElse(null);
    }

    @Nullable
    public User findBySubject(Integer subject_id) {
        return this.userRepository.find(subject_id).orElse(null);
    }

    @Nullable
    public User findUserById(Integer user_id) {
        return this.userRepository.find(user_id).orElse(null);
    }

    public boolean checkUserPassword(User user, String password) {
        return this.passwordService.check(password, user.getPassword());
    }

    public Collection<User> getCollectionFriends(Integer id) {
        return this.userRepository.findFriends(id);
    }

    public Collection<? extends GrantedAuthority> getGrantedAuthority(User user) {
        return this.roleRepository.getRolesFromUserId(user.getId());
    }

    public Collection<User> getCollectionBannedUsers(Integer id) {
        return this.userRepository.findBanned(id);
    }

    public boolean requestAddUserInFriend(Integer userId, Integer friendId) {
        if (this.userRepository.iCanAddFriend(userId, friendId) < 1) {
            this.userRepository.addUserInFriend(userId, friendId);
            return true;
        } else {
            return false;
        }
    }

    public void removeUserInFriend(Integer userId, Integer friendId) {
        this.userRepository.delUserInFriend(userId, friendId);
    }

    public boolean requestAddUserInBan(Integer userId, Integer userBannedId) {
        if (this.userRepository.iCanAddUserInBan(userId, userBannedId) < 1) {
            this.userRepository.addUserInBan(userId, userBannedId);

            this.userRepository.delUserInFriend(userId, userBannedId);
            this.userRepository.delUserInFriend(userBannedId, userId);

            return true;
        } else {
            return false;
        }
    }

    public void removeUserInBan(Integer userId, Integer friendId) {
        this.userRepository.delUserInBan(userId, friendId);
    }

    public void addRole(Integer userId, Integer roleId) {
        if (this.userRepository.iCanAddRole(userId, roleId) < 1) {
            this.userRepository.addUserRole(userId, roleId);
        }
    }

    public void makeNewUser(RegisterDTO registerDTO) {
        String password = this.passwordService.encode(registerDTO.getPassword());
        String email = registerDTO.getEmail();
        String name = registerDTO.getName();

        this.userRepository.make(name, email, password);
    }
}
