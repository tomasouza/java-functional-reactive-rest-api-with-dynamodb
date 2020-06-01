package br.com.oigarcom.api.users.utils;

import br.com.oigarcom.api.users.core.entities.User;
import br.com.oigarcom.api.users.core.presentables.PresentableUser;
import br.com.oigarcom.api.users.web.requests.CreateUserRequest;
import br.com.oigarcom.api.users.web.requests.UpdateUserRequest;
import reactor.core.publisher.Mono;

public class TestUtils {
    public static Mono<User> getTestUserMono(){
        return Mono.just(User.builder()
            .name("name")
            .email("email@email.com")
            .password("password")
            .build());
    }

    public static PresentableUser getTestPresentableUser() {
        return PresentableUser.builder()
                .name("name")
                .email("email@email.com")
                .password("password")
                .build();
    }

    public static CreateUserRequest getCreateUserRequest() {
        return CreateUserRequest.builder()
                .name("name")
                .email("email@email.com")
                .password("password")
                .build();
    }

    public static UpdateUserRequest getUpdateUserRequest() {
        return UpdateUserRequest.builder()
                .name("newName")
                .password("newPassword")
                .build();
    }

    public static PresentableUser getTestUpdatedPresentableUser() {
        return PresentableUser.builder()
                .name("newName")
                .password("newPassword")
                .email("email@email.com")
                .build();
    }

    public static PresentableUser getSecondTestPresentableUser() {
        return PresentableUser.builder()
                .name("secondName")
                .password("secondPassword")
                .email("secondemail@email.com")
                .build();
    }

}
