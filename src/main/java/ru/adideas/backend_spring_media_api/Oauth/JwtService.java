package ru.adideas.backend_spring_media_api.Oauth;

import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import ru.adideas.backend_spring_media_api.Oauth.DTO.JwtAuthDTO;
import ru.adideas.backend_spring_media_api.Oauth.DTO.JwtRefreshDTO;
import ru.adideas.backend_spring_media_api.User.User;
import ru.adideas.backend_spring_media_api.User.UserService;

import javax.security.auth.message.AuthException;
import java.util.Date;

@Service
@ConstructorBinding
public class JwtService {
    private final JwtRepository jwtRepository;
    private final JwtProvider jwtProvider;
    private final UserService userService;

    @Autowired
    public JwtService(UserService userService, JwtProvider jwtProvider, JwtRepository jwtRepository) {
        this.userService = userService;
        this.jwtProvider = jwtProvider;
        this.jwtRepository = jwtRepository;
    }

    private JwtResponse getJwtResponse(User user, Integer tokenId) {
        String strTokenId = String.valueOf(tokenId);
        return new JwtResponse(
                this.jwtProvider.getAccessToken(user, strTokenId),
                this.jwtProvider.getRefreshToken(user, strTokenId)
        );
    }

    private Integer checkToken(DecodedJWT claims) {
        if (claims == null) {
            return null;
        }
        Date expires_at = claims.getExpiresAt();
        if (expires_at == null) {
            return null;
        }
        if (expires_at.getTime() < new Date().getTime()) {
            return null;
        }
        return Integer.parseInt(claims.getId());
    }

    final public JwtResponse auth(JwtAuthDTO request) throws AuthException {
        User user = this.userService.findByEmail(request.getUsername());
        if (user != null && userService.checkUserPassword(user, request.getPassword())) {
            JwtModel jwtModel = jwtRepository.save(
                    new JwtModel().setUser(user)
            );
            return getJwtResponse(user, jwtModel.getId());
        }
        throw new AuthException("User name or password incorrect");
    }

    @Nullable
    final public JwtResponse refresh(JwtRefreshDTO request) {
        final DecodedJWT claims = jwtProvider.checkRefreshToken(request.getRefresh());
        final Integer tokenId = checkToken(claims);
        if (tokenId == null) {
            return null;
        }
        Integer userId = this.jwtRepository.getUser(tokenId);
        if (userId == null) {
            return null;
        }
        User user = userService.findUserById(userId);
        if (user == null) {
            return null;
        }
        return getJwtResponse(user, tokenId);
    }

    @Nullable
    final public User checkAccessToken(String token) {
        final DecodedJWT claims = this.jwtProvider.checkAccessToken(token);
        final Integer tokenId = checkToken(claims);
        if (tokenId == null || this.jwtRepository.exist(tokenId) < 1) {
            return null;
        }
        return this.userService.findBySubject(
                Integer.parseInt(claims.getSubject())
        );
    }
}
