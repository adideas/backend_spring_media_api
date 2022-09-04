package ru.adideas.backend_spring_media_api.DTO;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.adideas.backend_spring_media_api.DTO.DataClass.DataRegisterDTOWithBoolean;
import ru.adideas.backend_spring_media_api.Register.RegisterDTO;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class RegisterDTOTest {

    ValidatorFactory validatorFactory;
    Validator validator;

    private static Stream<DataRegisterDTOWithBoolean> dataForCheckValidation() {
        return Stream.of(
                DataRegisterDTOWithBoolean.TRUE("name", "email@email.ru", "123456"),
                DataRegisterDTOWithBoolean.TRUE("name", "email@email.ru", "123456789012"),
                DataRegisterDTOWithBoolean.FALSE("name", "email@email.ru", "1234567890123"),
                DataRegisterDTOWithBoolean.FALSE("name", "email@email.ru", "12345"),
                DataRegisterDTOWithBoolean.FALSE("name", "@email.ru", "123456"),
                DataRegisterDTOWithBoolean.FALSE("name", "email@.ru", "123456"),
                DataRegisterDTOWithBoolean.FALSE("name", "email", "123456"),
                DataRegisterDTOWithBoolean.FALSE("name", "1234567890", "123456"),
                DataRegisterDTOWithBoolean.TRUE("name", "email@email.com.ru", "123456"),
                DataRegisterDTOWithBoolean.TRUE(
                        "12345678901234567890123456789012345678901234567890",
                        "email@email.ru",
                        "123456"
                ),
                DataRegisterDTOWithBoolean.FALSE(
                        "123456789012345678901234567890123456789012345678901",
                        "email@email.ru",
                        "123456"
                ),
                DataRegisterDTOWithBoolean.TRUE("12", "email@email.ru", "123456"),
                DataRegisterDTOWithBoolean.FALSE("2", "email@email.ru", "123456"),
                DataRegisterDTOWithBoolean.FALSE(null, "email@email.ru", "123456"),
                DataRegisterDTOWithBoolean.FALSE("name", null, "123456"),
                DataRegisterDTOWithBoolean.FALSE("name", "email@email.ru", null)
        );
    }

    @ParameterizedTest
    @MethodSource("dataForCheckValidation")
    void checkDtoValidate(DataRegisterDTOWithBoolean data) {
        if (validator == null) {
            validatorFactory = Validation.buildDefaultValidatorFactory();
            validator = validatorFactory.getValidator();
        }
        Set<ConstraintViolation<RegisterDTO>> violations = validator.validate(data.getRegisterDTO());

        if (data.getValid()) {
            assertTrue(violations.isEmpty());
        } else {
            assertFalse(violations.isEmpty());
        }
    }
}

