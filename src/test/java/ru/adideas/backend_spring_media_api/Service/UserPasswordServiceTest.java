package ru.adideas.backend_spring_media_api.Service;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;
import ru.adideas.backend_spring_media_api.User.UserPasswordService;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Random;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserPasswordServiceTest {

    UserPasswordService service;

    private static String randomPasswordByte() {
        byte[] array = new byte[12];
        new Random().nextBytes(array);
        return new String(array, StandardCharsets.UTF_8);
    }

    private static String randomPasswordPeople() {
        return RandomStringUtils.randomAlphanumeric(12);
    }

    private static Stream<String> dataArrayRandomPasswords() {
        ArrayList<String> passwords = new ArrayList<>();
        for(int i = 0; i < 15; i++) {
            passwords.add(randomPasswordByte());
        }
        for(int i = 0; i < 15; i++) {
            passwords.add(randomPasswordPeople());
        }
        return passwords.stream();
    }

    @ParameterizedTest
    @MethodSource("dataArrayRandomPasswords")
    void checkBcrypt(String password) {
        if (service == null) {
            service = new UserPasswordService();
        }
        String hash = service.encode(password);
        assertTrue(service.check(password, hash));
    }
}
