package br.com.oigarcom.api.users.core.usecases;

import br.com.oigarcom.api.users.core.entities.Page;
import br.com.oigarcom.api.users.core.entities.User;
import br.com.oigarcom.api.users.core.gateways.UserGateway;
import br.com.oigarcom.api.users.core.mappers.PageMapper;
import br.com.oigarcom.api.users.core.mappers.UserMapper;
import br.com.oigarcom.api.users.core.presentables.PresentablePage;
import br.com.oigarcom.api.users.core.presentables.PresentableUser;
import br.com.oigarcom.api.users.utils.TestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static br.com.oigarcom.api.users.utils.TestUtils.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UsecasesTest {

    @Test
    @DisplayName("Testing save usecase")
    public void saveTest() {
        UserGateway mockedUserGateway = mock(UserGateway.class);
        when(mockedUserGateway.save(any(User.class))).thenReturn(Mono.just(getTestUser()));
        SaveUser saveUser = new SaveUser(mockedUserGateway, new UserMapper());
        var savedUserMono = saveUser.execute(Mono.just(getTestPresentableUser()));
        StepVerifier.create(savedUserMono)
                .assertNext(TestUtils::assertTestPresentableUSer)
                .expectComplete()
                .verify();
    }

    @Test
    @DisplayName("Testing get usecase")
    public void getTest() {
        UserGateway mockedUserGateway = mock(UserGateway.class);
        when(mockedUserGateway.get(any(String.class))).thenReturn(Mono.just(getTestUser()));
        GetUser getUser = new GetUser(mockedUserGateway, new UserMapper());
        var gotUserMono = getUser.execute("email@email.com");
        StepVerifier.create(gotUserMono)
                .assertNext(TestUtils::assertTestPresentableUSer)
                .expectComplete()
                .verify();
    }

    @Test
    @DisplayName("Testing update usecase")
    public void updateTest() {
        UserGateway mockedUserGateway = mock(UserGateway.class);
        User testUser = getTestUser();
        testUser.setPassword("password");
        testUser.setName("name");
        when(mockedUserGateway.update(any(User.class))).thenReturn(Mono.just(testUser));
        UpdateUser updateUser = new UpdateUser(mockedUserGateway, new UserMapper());
        var updatedUserMono = updateUser.execute(getTestUser());
        StepVerifier.create(updatedUserMono)
                .assertNext(TestUtils::assertTestPresentableUSer)
                .expectComplete()
                .verify();
    }

    @Test
    @DisplayName("Testing delete usecase")
    public void deleteTest() {
        UserGateway mockedUserGateway = mock(UserGateway.class);
        when(mockedUserGateway.delete(any(String.class))).thenReturn(Mono.empty());
        DeleteUser deleteUser = new DeleteUser(mockedUserGateway);
        var deletedUserMono = deleteUser.execute(getTestUser().getEmail());
        StepVerifier.create(deletedUserMono)
                .expectComplete()
                .verify();
    }

    @Test
    @DisplayName("Testing list usecase")
    public void listTest() {
        UserGateway mockedUserGateway = mock(UserGateway.class);
        when(mockedUserGateway.list(any(Page.class)))
                .thenReturn(Flux.fromStream(getTestUserList().stream()));
        ListUsers listUsers = new ListUsers(mockedUserGateway, new UserMapper(), new PageMapper());
        Flux<PresentableUser> listedPresentableUsers = listUsers.execute(getTestPresentablePage());
        StepVerifier.create(listedPresentableUsers)
                .assertNext(TestUtils::assertTestPresentableUSer)
                .assertNext(TestUtils::assertSecondTestPresentableUSer)
                .expectComplete()
                .verify();
    }

    @Test
    @DisplayName("Testing objects toString method")
    public void toStringTest(){
        String expectedPresentableUser = "[Name = name, Email = email@email.com]";
        String expectedUser = "[Name = name, Email = email@email.com]";
        String expectedPage = "[PageSize = 10, LastValuatedKey = null]";
        String expectedUserBuilder = "User.UserBuilder(name=null, password=null, email=null)";
        String expectedPresentableUserBuilder = "PresentableUser.PresentableUserBuilder(name=null, email=null, password=null)";
        String expectedPresentablePageBuilder = "PresentablePage.PresentablePageBuilder(pageSize=0, lastValuatedKey=null)";
        String expectedPageBuilder = "Page.PageBuilder(pageSize=0, lastValuatedKey=null)";
        Assertions.assertEquals(expectedPresentableUser, getTestPresentableUser().toString());
        Assertions.assertEquals(expectedUser, getTestUser().toString());
        Assertions.assertEquals(expectedPage, getTestPresentablePage().toString());
        Assertions.assertEquals(expectedPage, getTestPage().toString());
        Assertions.assertEquals(expectedPageBuilder, Page.builder().toString());
        Assertions.assertEquals(expectedUserBuilder, User.builder().toString());
        Assertions.assertEquals(expectedPresentableUserBuilder, PresentableUser.builder().toString());
        Assertions.assertEquals(expectedPresentablePageBuilder, PresentablePage.builder().toString());
        Assertions.assertNotNull(getTestPage().getPageSize());
        Assertions.assertNull(getTestPage().getLastValuatedKey());
    }

}
