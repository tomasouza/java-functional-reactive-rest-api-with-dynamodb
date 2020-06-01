package br.com.oigarcom.api.users.web.validators;

import br.com.oigarcom.api.users.core.presentables.PresentableUser;
import br.com.oigarcom.api.users.web.requests.CreateUserRequest;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.web.server.ServerWebInputException;

@Component
public class UserValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return PresentableUser.class.equals(clazz);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "name", "name.empty");
        ValidationUtils.rejectIfEmpty(errors, "email", "email.empty");
        ValidationUtils.rejectIfEmpty(errors, "password", "password.empty");
    }

    public void validate(CreateUserRequest createUserRequest) {
        Errors errors = new BeanPropertyBindingResult(createUserRequest, "user");
        validate(createUserRequest, errors);
        if (errors.hasErrors()) {
            throw new ServerWebInputException(errors.toString());
        }
    }
}
