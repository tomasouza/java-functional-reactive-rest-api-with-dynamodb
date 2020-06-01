package br.com.oigarcom.api.users.utils;

import br.com.oigarcom.api.users.core.entities.Page;
import br.com.oigarcom.api.users.core.entities.User;
import br.com.oigarcom.api.users.core.presentables.PresentablePage;
import br.com.oigarcom.api.users.core.presentables.PresentableUser;
import org.mockito.internal.exceptions.ExceptionIncludingMockitoWarnings;

import java.util.ArrayList;
import java.util.List;

public class TestUtils {

    private static final String ASSERTION_ERROR_MESSAGE = "Assertion error. Expected: [%s] Actual:[%s] ";

    public static void assertSecondTestPresentableUSer(PresentableUser presentableUser) {
        String expectedName = "secondName";
        String expectedEmail = "secondEmail@email.com";
        String expectedPassword = "secondPassword";
        if(!expectedName.equals(presentableUser.getName())){
            throw assertionError(presentableUser.getName(), expectedName);
        }
        if(!expectedEmail.equals(presentableUser.getEmail())){
            throw assertionError(presentableUser.getEmail(), expectedEmail);
        }
        if(!expectedPassword.equals(presentableUser.getPassword())){
            throw assertionError(presentableUser.getPassword(), expectedPassword);
        }
    }

    public static void assertTestPresentableUSer(PresentableUser presentableUser) {
        String expectedName = "name";
        String expectedEmail = "email@email.com";
        String expectedPassword = "password";
        if(!expectedName.equals(presentableUser.getName())){
            throw assertionError(presentableUser.getName(), expectedName);
        }
        if(!expectedEmail.equals(presentableUser.getEmail())){
            throw assertionError(presentableUser.getEmail(), expectedEmail);
        }
        if(!expectedPassword.equals(presentableUser.getPassword())){
            throw assertionError(presentableUser.getPassword(), expectedPassword);
        }
    }

    public static PresentablePage getTestPresentablePage() {
        return PresentablePage.builder()
                .pageSize(10)
                .lastValuatedKey(null)
                .build();
    }

    public static List<User> getTestUserList() {
        var users = new ArrayList<User>();
        users.add(getTestUser());
        users.add(getSecondTestUser());
        return users;
    }

    public static User getSecondTestUser() {
        return User.builder()
                .name("secondName")
                .email("secondEmail@email.com")
                .password("secondPassword")
                .build();
    }

    public static User getTestUser() {
        return User.builder()
                .name("name")
                .password("password")
                .email("email@email.com")
                .build();
    }

    private static ExceptionIncludingMockitoWarnings assertionError(String actual, String expected) {
        return new ExceptionIncludingMockitoWarnings(
                String.format(ASSERTION_ERROR_MESSAGE, expected, actual), new Exception());
    }

    public static PresentableUser getTestPresentableUser() {
        return PresentableUser.builder()
                .name("name")
                .email("email@email.com")
                .password("password")
                .build();
    }


    public static Page getTestPage() {
        return Page.builder()
                .pageSize(10)
                .lastValuatedKey(null)
                .build();
    }
}
