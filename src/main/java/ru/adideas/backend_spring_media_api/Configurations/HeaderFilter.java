package ru.adideas.backend_spring_media_api.Configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;
import ru.adideas.backend_spring_media_api.Oauth.JwtService;
import ru.adideas.backend_spring_media_api.User.User;
import ru.adideas.backend_spring_media_api.User.UserAuthentication;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@ConstructorBinding
public class HeaderFilter extends GenericFilterBean {

    private static final String HEADER = "Authorization";

    private JwtService jwtService;
    private UserAuthentication userAuthentication;

    @Autowired
    public void setJwtService(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Autowired
    public void setUserAuthentication(UserAuthentication userAuthentication) {
        this.userAuthentication = userAuthentication;
    }


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        handleToken(request);
        filterChain.doFilter(request, response);
    }

    private void handleToken(ServletRequest request) {
        try {
            final String token = getTokenFromRequest((HttpServletRequest) request);
            if (token == null) {
                return;
            }
            final User subject = jwtService.checkAccessToken(token);
            if (subject == null) {
                return;
            }
            SecurityContextHolder.getContext().setAuthentication(
                    userAuthentication.setSubject(subject)
            );
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private String getTokenFromRequest(HttpServletRequest request) {
        final String bearer = request.getHeader(HEADER);
        if (StringUtils.hasText(bearer) && bearer.startsWith("Bearer ")) {
            return bearer.substring(7);
        }
        return null;
    }

}