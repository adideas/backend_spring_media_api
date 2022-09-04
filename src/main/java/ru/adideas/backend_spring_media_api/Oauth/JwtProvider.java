package ru.adideas.backend_spring_media_api.Oauth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import ru.adideas.backend_spring_media_api.Configurations.AccessKeys;
import ru.adideas.backend_spring_media_api.User.User;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Component
public class JwtProvider {
    private final AccessKeys accessKeys;
    private final JwtRepository jwtRepository;

    @Autowired
    public JwtProvider(AccessKeys accessKeys, JwtRepository jwtRepository) {
        this.accessKeys = accessKeys;
        this.jwtRepository = jwtRepository;
    }

    private Date expiresAt(Integer count, ChronoUnit unit) {
        return Date.from(
                LocalDateTime
                        .now()
                        .plus(count, unit)
                        .atZone(ZoneId.systemDefault())
                        .toInstant()
        );
    }

    public String getAccessToken(User user, String jti) {
        Algorithm algorithm = Algorithm.HMAC256(this.accessKeys.getAccessBinary());
        return JWT.create()
                // .withExpiresAt(expiresAt(5, ChronoUnit.MINUTES))
                .withExpiresAt(expiresAt(30, ChronoUnit.DAYS))
                .withClaim("jti", jti)
                .withSubject(String.valueOf(user.getId()))
                .sign(algorithm);
    }

    public String getRefreshToken(User user, String jti) {
        Algorithm algorithm = Algorithm.HMAC256(this.accessKeys.getRefreshBinary());
        return JWT.create()
                .withExpiresAt(expiresAt(30, ChronoUnit.DAYS))
                .withClaim("jti", jti)
                .withSubject(String.valueOf(user.getId()))
                .sign(algorithm);
    }

    private DecodedJWT checkToken(String token, byte[] secret) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm).build();
            return verifier.verify(token);
        } catch (Exception ignore) {
        }
        return null;
    }

    public String toString() {
        return accessKeys.getAccessKey().getAlgorithm();
    }

    public DecodedJWT checkAccessToken(String accessToken) {
        return checkToken(accessToken, this.accessKeys.getAccessBinary());
    }

    public DecodedJWT checkRefreshToken(String refreshToken) {
        return checkToken(refreshToken, this.accessKeys.getRefreshBinary());
    }
}
