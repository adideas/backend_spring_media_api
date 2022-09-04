package ru.adideas.backend_spring_media_api.User;

import com.fasterxml.jackson.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import java.util.Collection;

@Component
public class UserAuthentication implements Authentication {

    private final UserService userService;
    private User user;

    @Autowired
    public UserAuthentication(UserService userService) {
        this.userService = userService;
    }

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.userService.getGrantedAuthority(user);
    }

    @Override
    @JsonIgnore
    public Object getCredentials() {
        return this.user != null ? this.user.getEmail() : null;
    }

    @Override
    @JsonProperty("user")
    public User getDetails() {
        return this.user != null ? this.user : null;
    }

    @Override
    @JsonIgnore
    public Integer getPrincipal() {
        return this.user != null ? this.user.getId() : null;
    }

    @Override

    @JsonIgnore
    public boolean isAuthenticated() {
        return this.user != null;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        // Not allowed
    }

    @Override
    @JsonIgnore
    public String getName() {
        return this.user != null ? this.user.getName() : null;
    }

    public Authentication setSubject(User user) {
        UserAuthentication userAuthentication = new UserAuthentication(userService);
        userAuthentication.user = user;
        return userAuthentication;
    }
}
