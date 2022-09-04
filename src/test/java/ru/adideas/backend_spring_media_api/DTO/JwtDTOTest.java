package ru.adideas.backend_spring_media_api.DTO;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.adideas.backend_spring_media_api.DTO.DataClass.DataJwtAuthWithBoolean;
import ru.adideas.backend_spring_media_api.DTO.DataClass.DataJwtRefreshWithBoolean;
import ru.adideas.backend_spring_media_api.Oauth.DTO.JwtAuthDTO;
import ru.adideas.backend_spring_media_api.Oauth.DTO.JwtRefreshDTO;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@RunWith(SpringRunner.class)
public class JwtDTOTest {

    ValidatorFactory validatorFactory;
    Validator validator;

    private static Stream<DataJwtAuthWithBoolean> dataForJwtAuthDtoTest() {
        return Stream.of(
                new DataJwtAuthWithBoolean(true, "email@email.ru", "123456"),
                new DataJwtAuthWithBoolean(false, "email@email.ru", "12"),
                new DataJwtAuthWithBoolean(false, "email.ru", "123456"),
                new DataJwtAuthWithBoolean(false, "email", "123456"),
                new DataJwtAuthWithBoolean(false, "@email.ru", "123456"),
                new DataJwtAuthWithBoolean(false, "email@email.ru", null),
                new DataJwtAuthWithBoolean(false, null, null),
                new DataJwtAuthWithBoolean(false, null, "123456")
        );
    }

    private static Stream<DataJwtRefreshWithBoolean> dataForJwtRefreshDtoTest() {
        return Stream.of(
                new DataJwtRefreshWithBoolean(true, "1234567890"),
                new DataJwtRefreshWithBoolean(false, null)
        );
    }

    @ParameterizedTest
    @MethodSource("dataForJwtAuthDtoTest")
    public void jwtAuthDtoTest(DataJwtAuthWithBoolean data) {
        if (validator == null) {
            validatorFactory = Validation.buildDefaultValidatorFactory();
            validator = validatorFactory.getValidator();
        }
        Set<ConstraintViolation<JwtAuthDTO>> violations = validator.validate(data.getDTO());

        if (data.getValid()) {
            assertTrue(violations.isEmpty());
        } else {
            assertFalse(violations.isEmpty());
        }
    }

    @ParameterizedTest
    @MethodSource("dataForJwtRefreshDtoTest")
    public void jwtRefreshDtoTest(DataJwtRefreshWithBoolean data) {
        if (validator == null) {
            validatorFactory = Validation.buildDefaultValidatorFactory();
            validator = validatorFactory.getValidator();
        }
        Set<ConstraintViolation<JwtRefreshDTO>> violations = validator.validate(data.getDTO());

        if (data.getValid()) {
            assertTrue(violations.isEmpty());
        } else {
            assertFalse(violations.isEmpty());
        }
    }
}

