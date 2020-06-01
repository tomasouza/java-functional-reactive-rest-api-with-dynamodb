package br.com.oigarcom.api.users.validators;

import br.com.oigarcom.api.users.core.presentables.PresentableUser;
import br.com.oigarcom.api.users.web.requests.CreateUserRequest;
import br.com.oigarcom.api.users.web.validators.UserValidator;
import org.apache.http.HttpStatus;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.web.server.ServerWebInputException;

public class ValidatorsTest {

    @Test
    public void ValidationTest() {
        var userValidator = new UserValidator();
        userValidator.validate(CreateUserRequest.builder()
                .name("name")
                .email("email")
                .password("password")
                .build());
    }

    @Test
    public void ValidationWithoutNameTest() {
        try {
            var userValidator = new UserValidator();
            userValidator.validate(CreateUserRequest.builder()
                    .email("email")
                    .password("password")
                    .build());
        } catch (ServerWebInputException e) {
            Assert.assertEquals(HttpStatus.SC_BAD_REQUEST, e.getStatus().value());
        }
    }

    @Test
    public void ValidationWithoutEmailTest() {
        try {
            var userValidator = new UserValidator();
            userValidator.validate(CreateUserRequest.builder()
                    .name("name")
                    .password("password")
                    .build());
        } catch (ServerWebInputException e) {
            Assert.assertEquals(HttpStatus.SC_BAD_REQUEST, e.getStatus().value());
        }
    }

    @Test
    public void ValidationWithoutPasswordTest() {
        try {
            var userValidator = new UserValidator();
            userValidator.validate(CreateUserRequest.builder()
                    .name("name")
                    .email("email")
                    .build());
        } catch (ServerWebInputException e) {
            Assert.assertEquals(HttpStatus.SC_BAD_REQUEST, e.getStatus().value());
        }
    }

    @Test
    public void ValidationSupportTest() {
        var userValidator = new UserValidator();
        Assert.assertEquals(true, userValidator.supports(PresentableUser.class));
    }
}
