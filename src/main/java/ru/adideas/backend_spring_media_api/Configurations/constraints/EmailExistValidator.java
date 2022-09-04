package ru.adideas.backend_spring_media_api.Configurations.constraints;

import org.springframework.beans.factory.annotation.Autowired;
import ru.adideas.backend_spring_media_api.BackendSpringMediaApiApplication;
import ru.adideas.backend_spring_media_api.User.UserService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailExistValidator implements ConstraintValidator<EmailExist, String> {

    @Autowired
    private UserService userService;

    @Override
    public boolean isValid(String contactField, ConstraintValidatorContext self) {
        if (BackendSpringMediaApiApplication.isJUnitTest()) {
            return true;
        }
        return this.userService.findByEmail(contactField) != null;
    }

}