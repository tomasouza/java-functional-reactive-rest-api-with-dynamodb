package br.com.oigarcom.api.users.web.handlers;

import br.com.oigarcom.api.users.core.entities.User;
import br.com.oigarcom.api.users.core.presentables.PresentablePage;
import br.com.oigarcom.api.users.core.presentables.PresentableUser;
import br.com.oigarcom.api.users.core.usecases.*;
import br.com.oigarcom.api.users.web.mappers.RequestMapper;
import br.com.oigarcom.api.users.web.requests.CreateUserRequest;
import br.com.oigarcom.api.users.web.requests.UpdateUserRequest;
import br.com.oigarcom.api.users.web.validators.UserValidator;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.ServerResponse.noContent;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
public class UserHandler {

    private final ListUsers listUsers;
    private final SaveUser saveUser;
    private final GetUser getUser;
    private final UpdateUser updateUser;
    private final DeleteUser deleteUser;
    private final UserValidator userValidator;
    private final RequestMapper requestMapper;

    public UserHandler(ListUsers listUsers,
                       SaveUser saveUser,
                       GetUser getUser,
                       UpdateUser updateUser,
                       DeleteUser deleteUser,
                       UserValidator userValidator,
                       RequestMapper requestMapper) {
        this.listUsers = listUsers;
        this.saveUser = saveUser;
        this.getUser = getUser;
        this.updateUser = updateUser;
        this.deleteUser = deleteUser;
        this.userValidator = userValidator;
        this.requestMapper = requestMapper;
    }

    public Mono<ServerResponse> saveUser(ServerRequest request) {
        Mono<PresentableUser> presentableUserMono = request
                .bodyToMono(CreateUserRequest.class)
                .doOnNext(userValidator::validate)
                .map(requestMapper::mapToPresentableUser);
        return ok()
                .body(saveUser.execute(presentableUserMono), PresentableUser.class);
    }


    public Mono<ServerResponse> listUsers(ServerRequest request) {
        int pageSize = Integer
                .parseInt(request.queryParam("page_size")
                .orElse("10"));
        Optional<String> lastValuatedKey = request
                .queryParam("last_evaluated_value");
        PresentablePage presentablePage = PresentablePage.builder()
                .pageSize(pageSize)
                .lastValuatedKey(lastValuatedKey.orElse(null))
                .build();

        return ok().body(listUsers.execute(presentablePage), PresentableUser.class);
    }

    public Mono<ServerResponse> getUser(ServerRequest request) {
        String email = request.pathVariable("email");
        return getUser.execute(email)
                .flatMap(user -> ok().contentType(APPLICATION_JSON).bodyValue(user))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> updateUser(ServerRequest request) {
        String email = request.pathVariable("email");
        return request.bodyToMono(UpdateUserRequest.class)
                .flatMap(updateUserRequest -> getUser.execute(email)
                    .flatMap(user -> {
                        User userToUpdate = User.builder()
                                .email(user.getEmail())
                                .name(Optional.ofNullable(updateUserRequest.getName())
                                        .orElse(user.getName()))
                                .password(Optional.ofNullable(updateUserRequest.getPassword())
                                        .orElse(user.getPassword()))
                                .build();
                        return updateUser.execute(userToUpdate)
                                .flatMap(updatedUser -> ok().contentType(APPLICATION_JSON).bodyValue(updatedUser))
                                .switchIfEmpty(ServerResponse.unprocessableEntity().build());
                })).switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> deleteUser(ServerRequest request) {
        String email = request.pathVariable("email");
        return noContent().build(deleteUser.execute(email));
    }

}
