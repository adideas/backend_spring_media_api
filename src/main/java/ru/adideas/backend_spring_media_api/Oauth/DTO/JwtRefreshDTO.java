package ru.adideas.backend_spring_media_api.Oauth.DTO;

import javax.validation.constraints.NotNull;

public class JwtRefreshDTO {
    @NotNull
    private String refresh;

    public String getRefresh() {
        return this.refresh;
    }
}
