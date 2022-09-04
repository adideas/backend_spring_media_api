package ru.adideas.backend_spring_media_api.Configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.stereotype.Component;
import ru.adideas.backend_spring_media_api.BackendSpringMediaApiApplication;
import ru.adideas.backend_spring_media_api.Comment.CommentService;
import ru.adideas.backend_spring_media_api.News.NewsService;
import ru.adideas.backend_spring_media_api.Register.RegisterDTO;
import ru.adideas.backend_spring_media_api.Role.RoleRepository;
import ru.adideas.backend_spring_media_api.User.User;
import ru.adideas.backend_spring_media_api.User.UserService;

import java.util.HashMap;
import java.util.HashSet;

@Component
@ConstructorBinding
public class UserDataSeed implements CommandLineRunner {

    private final UserService userService;
    private final RoleRepository roleRepository;
    private final NewsService newsService;
    private final CommentService commentService;

    @Autowired
    public UserDataSeed(UserService userService, RoleRepository roleRepository, NewsService newsService, CommentService commentService) {
        this.userService = userService;
        this.roleRepository = roleRepository;
        this.newsService = newsService;
        this.commentService = commentService;
    }

    @Override
    public void run(String... args) throws Exception {
        if(BackendSpringMediaApiApplication.isJUnitTest()) {
            return;
        }

        if (this.userService.countUsers() < 1) {
            HashSet<RegisterDTO> users = new HashSet<RegisterDTO>() {{
                add(new RegisterDTO("admin", "admin@email.ru", "123456"));
                add(new RegisterDTO("manager", "manager@email.ru", "123456"));
                add(new RegisterDTO("rick", "rick@email.ru", "123456"));
                add(new RegisterDTO("morty", "morty@email.ru", "123456"));
                add(new RegisterDTO("jesika", "jesika@email.ru", "123456"));
            }};

            for (RegisterDTO registerDTO : users) {
                this.userService.makeNewUser(registerDTO);
            }

            HashMap<String, Integer> indexes = new HashMap<>();
            for (RegisterDTO registerDTO : users) {
                indexes.put(registerDTO.getEmail(), this.mapGetUser(registerDTO));
            }

            this.userService.requestAddUserInBan(indexes.get("jesika@email.ru"), indexes.get("rick@email.ru"));

            this.userService.requestAddUserInFriend(indexes.get("admin@email.ru"), indexes.get("manager@email.ru"));
            this.userService.requestAddUserInFriend(indexes.get("manager@email.ru"), indexes.get("admin@email.ru"));
            this.userService.requestAddUserInFriend(indexes.get("rick@email.ru"), indexes.get("morty@email.ru"));
            this.userService.requestAddUserInFriend(indexes.get("morty@email.ru"), indexes.get("rick@email.ru"));
            this.userService.requestAddUserInFriend(indexes.get("morty@email.ru"), indexes.get("jesika@email.ru"));
            this.userService.requestAddUserInFriend(indexes.get("jesika@email.ru"), indexes.get("morty@email.ru"));

            if (this.roleRepository.count() < 1) {
                this.roleRepository.create(1, "ADMIN");
                this.roleRepository.create(2, "MANAGER");
            }

            this.userService.addRole(indexes.get("admin@email.ru"), 1);
            this.userService.addRole(indexes.get("admin@email.ru"), 2);
            this.userService.addRole(indexes.get("manager@email.ru"), 2);

            if (this.newsService.count() < 1) {
                this.newsService.make(1,"Hello my friend!", indexes.get("admin@email.ru"));
                this.newsService.make(2,"Rick where are you?", indexes.get("morty@email.ru"));
                this.newsService.make(3,"Brrr. U la-la", indexes.get("rick@email.ru"));
                this.newsService.make(4,"Morty you little piece of shit!", indexes.get("rick@email.ru"));
                this.newsService.make(5,"Jessika you banned Rick??!", indexes.get("morty@email.ru"));

                this.commentService.make(1, indexes.get("jesika@email.ru"), "Hi =)");
                this.commentService.make(1, indexes.get("rick@email.ru"), "Fu** you!");
                this.commentService.make(1, indexes.get("morty@email.ru"), "Rick a you ok?");

                this.commentService.make(2, indexes.get("rick@email.ru"), "Im in garage...");

                this.commentService.make(3, indexes.get("admin@email.ru"), "...");

                this.commentService.make(4, indexes.get("rick@email.ru"), "No!");
                this.commentService.make(4, indexes.get("morty@email.ru"), "Shutup rick!");

                this.commentService.make(4, indexes.get("jesika@email.ru"), "Yep");
            }
        }
    }

    private Integer mapGetUser(RegisterDTO registerDTO) {
        User user = this.userService.findByEmail(registerDTO.getEmail());
        if (user == null) {
            return 0;
        }
        return user.getId();
    }
}
