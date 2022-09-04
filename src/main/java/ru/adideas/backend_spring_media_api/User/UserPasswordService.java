package ru.adideas.backend_spring_media_api.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
@ConstructorBinding
public class UserPasswordService {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserPasswordService() {
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder(10, new SecureRandom());
    }

    public String encode(String password) {
        return this.bCryptPasswordEncoder.encode(password);
    }

    public boolean check(String password, String encodePassword) {
        return this.bCryptPasswordEncoder.matches(password, encodePassword);
    }
}
