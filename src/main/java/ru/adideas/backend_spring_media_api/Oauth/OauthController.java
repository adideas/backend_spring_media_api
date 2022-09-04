package ru.adideas.backend_spring_media_api.Oauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.adideas.backend_spring_media_api.Oauth.DTO.JwtAuthDTO;
import ru.adideas.backend_spring_media_api.Oauth.DTO.JwtRefreshDTO;

import javax.security.auth.message.AuthException;
import javax.validation.Valid;

@RestController
@RequestMapping("api/auth")
@Validated
public class OauthController {

    private final JwtService jwtService;

    @Autowired
    public OauthController(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @PostMapping("login")
    public JwtResponse login(@Valid @RequestBody JwtAuthDTO request) throws AuthException {
        return jwtService.auth(request);
    }

    @PostMapping("refresh")
    public JwtResponse refresh(@Valid @RequestBody JwtRefreshDTO request) throws AuthException {
        return jwtService.refresh(request);
    }
}
