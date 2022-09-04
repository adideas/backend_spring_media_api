package ru.adideas.backend_spring_media_api.Oauth;

import org.springframework.lang.NonNull;

public class JwtResponse {
    private final String type = "Bearer";
    public final String accessToken;
    public final String refreshToken;

    public JwtResponse(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
