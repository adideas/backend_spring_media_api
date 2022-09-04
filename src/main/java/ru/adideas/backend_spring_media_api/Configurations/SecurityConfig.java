package ru.adideas.backend_spring_media_api.Configurations;

import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ru.adideas.backend_spring_media_api.User.UserAuthentication;

@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@ConstructorBinding
public class SecurityConfig {

    private final static String[] excludePost = {
            "/api/auth/login",
            "/api/auth/refresh",
            "/api/register"
    };

    @Bean
    public HeaderFilter headerFilter() {
        return new HeaderFilter();
    }

    @Bean
    public UserAuthentication auth() {
        return (UserAuthentication) SecurityContextHolder.getContext().getAuthentication();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity, HeaderFilter headerFilter) throws Exception {
        return httpSecurity
                .antMatcher("/api/**")
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeHttpRequests(
                        authorize -> authorize
                                .antMatchers(excludePost).permitAll()
                                .anyRequest().authenticated()
                                .and()
                                .addFilterAfter(headerFilter, UsernamePasswordAuthenticationFilter.class)
                ).build();
    }
}

