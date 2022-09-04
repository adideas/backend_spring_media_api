package ru.adideas.backend_spring_media_api.Register;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.adideas.backend_spring_media_api.User.UserService;

import javax.validation.Valid;

@RestController
@RequestMapping("api/register")
@Validated
public class RegisterController {
    private final UserService userService;

    @Autowired
    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<Object> register(@Valid @RequestBody RegisterDTO registerDTO) {
        this.userService.makeNewUser(registerDTO);
        return ResponseEntity.ok().build();
    }
}
