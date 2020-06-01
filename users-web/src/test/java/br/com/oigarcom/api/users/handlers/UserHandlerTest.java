package br.com.oigarcom.api.users.handlers;

import br.com.oigarcom.api.users.configuration.TestConfiguration;
import br.com.oigarcom.api.users.core.presentables.PresentableUser;
import br.com.oigarcom.api.users.core.usecases.*;
import br.com.oigarcom.api.users.web.handlers.UserHandler;
import br.com.oigarcom.api.users.web.requests.CreateUserRequest;
import br.com.oigarcom.api.users.web.requests.UpdateUserRequest;
import br.com.oigarcom.api.users.web.routers.UserRouter;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.List;

import static br.com.oigarcom.api.users.utils.TestUtils.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@WebFluxTest
@ActiveProfiles("test")
@ContextConfiguration(classes = {UserHandler.class, UserRouter.class, TestConfiguration.class})
public class UserHandlerTest {

    @Autowired
    WebTestClient webTestClient;
    @MockBean
    GetUser getUser;
    @MockBean
    ListUsers listUsers;
    @MockBean
    UpdateUser updateUser;
    @MockBean
    DeleteUser deleteUser;
    @MockBean
    SaveUser saveUser;

    @Test
    public void getUserTest(){
        given(getUser.execute("email@email.com"))
                .willReturn(Mono.just(getTestPresentableUser()));
        Flux<PresentableUser> presentableUserFlux = webTestClient
                .get()
                .uri("/users/email@email.com")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .returnResult(PresentableUser.class)
                .getResponseBody();
        String expectedName = "name";
        String expectedEmail = "email@email.com";
        String expectedPassword = "password";
        StepVerifier.create(presentableUserFlux)
                .assertNext(presentableUser -> {
                    Assert.assertEquals(expectedName, presentableUser.getName());
                    Assert.assertEquals(expectedEmail, presentableUser.getEmail());
                    Assert.assertEquals(expectedPassword, presentableUser.getPassword());
                }).expectComplete()
                .verify();
    }

    @Test
    public void saveUserTest(){
        given(saveUser.execute(ArgumentMatchers.any()))
                .willReturn(Mono.just(getTestPresentableUser()));
        Flux<PresentableUser> presentableUserFlux = webTestClient
                .post()
                .uri("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(getCreateUserRequest()), CreateUserRequest.class)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .returnResult(PresentableUser.class)
                .getResponseBody();
        String expectedName = "name";
        String expectedEmail = "email@email.com";
        String expectedPassword = "password";
        StepVerifier.create(presentableUserFlux)
                .assertNext(presentableUser -> {
                    Assert.assertEquals(expectedName, presentableUser.getName());
                    Assert.assertEquals(expectedEmail, presentableUser.getEmail());
                    Assert.assertEquals(expectedPassword, presentableUser.getPassword());
                }).expectComplete()
                .verify();
    }

    @Test
    public void updateUserTest(){
        given(getUser.execute("email@email.com"))
                .willReturn(Mono.just(getTestPresentableUser()));
        given(updateUser.execute(ArgumentMatchers.any()))
                .willReturn(Mono.just(getTestUpdatedPresentableUser()));
        Flux<PresentableUser> presentableUserFlux = webTestClient
                .patch()
                .uri("/users/email@email.com")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(getUpdateUserRequest()), UpdateUserRequest.class)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .returnResult(PresentableUser.class)
                .getResponseBody();
        String expectedName = "newName";
        String expectedEmail = "email@email.com";
        String expectedPassword = "newPassword";
        StepVerifier.create(presentableUserFlux)
                .assertNext(presentableUser -> {
                    Assert.assertEquals(expectedName, presentableUser.getName());
                    Assert.assertEquals(expectedEmail, presentableUser.getEmail());
                    Assert.assertEquals(expectedPassword, presentableUser.getPassword());
                }).expectComplete()
                .verify();
    }

    @Test
    public void deleteUserTest(){
        given(deleteUser.execute("email@email.com"))
                .willReturn(Mono.empty());
        Flux<Void> voidFlux = webTestClient
                .delete()
                .uri("/users/email@email.com")
                .exchange()
                .expectStatus().isNoContent()
                .returnResult(Void.class)
                .getResponseBody();
        StepVerifier.create(voidFlux)
                .expectComplete()
                .verify();
    }

    @Test
    public void listUsersTest(){
        List<PresentableUser> presentableUsers = getPresentableUsers();
        given(listUsers.execute(any()))
                .willReturn(Flux.fromIterable(presentableUsers));
        Flux<PresentableUser> presentableUserFlux = webTestClient
                .get()
                .uri("/users")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .returnResult(PresentableUser.class)
                .getResponseBody();
        String expectedName = "name";
        String expectedEmail = "email@email.com";
        String expectedPassword = "password";
        String secondExpectedName = "secondName";
        String secondExpectedEmail = "secondemail@email.com";
        String secondExpectedPassword = "secondPassword";
        StepVerifier.create(presentableUserFlux)
                .assertNext(presentableUser -> {
                    Assert.assertEquals(expectedName, presentableUser.getName());
                    Assert.assertEquals(expectedEmail, presentableUser.getEmail());
                    Assert.assertEquals(expectedPassword, presentableUser.getPassword());
                })
                .assertNext(presentableUser -> {
                    Assert.assertEquals(secondExpectedName, presentableUser.getName());
                    Assert.assertEquals(secondExpectedEmail, presentableUser.getEmail());
                    Assert.assertEquals(secondExpectedPassword, presentableUser.getPassword());
                })
                .expectComplete()
                .verify();
    }

    @Test
    public void createUserRequestToStringTest() {
        var expected = "[Name = name, Email = email@email.com]";
        var expectedBuild = "CreateUserRequest.CreateUserRequestBuilder(name=null, email=null, password=null)";
        Assert.assertEquals(expected, getCreateUserRequest().toString());
        Assert.assertEquals(expectedBuild, CreateUserRequest.builder().toString());
    }

    @Test
    public void updateUserRequestToStringTest() {
        var expected = "[Name = newName]";
        var expectedBuild = "UpdateUserRequest.UpdateUserRequestBuilder(name=null, password=null)";
        Assert.assertEquals(expected, getUpdateUserRequest().toString());
        Assert.assertEquals(expectedBuild, UpdateUserRequest.builder().toString());
    }

    private List<PresentableUser> getPresentableUsers() {
        List<PresentableUser> presentableUsers = new ArrayList<>();
        presentableUsers.add(getTestPresentableUser());
        presentableUsers.add(getSecondTestPresentableUser());
        return presentableUsers;
    }


}
