package ru.adideas.backend_spring_media_api.Configurations.constraints;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ru.adideas.backend_spring_media_api.BackendSpringMediaApiApplication;
import ru.adideas.backend_spring_media_api.User.UserService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class EmailUniqueValidator implements ConstraintValidator<EmailUnique, String> {

    @Autowired
    private UserService userService;

    @Override
    public boolean isValid(String contactField, ConstraintValidatorContext self) {
        if (BackendSpringMediaApiApplication.isJUnitTest()) {
            return true;
        }
        return this.userService.findByEmail(contactField) == null;
    }

}

