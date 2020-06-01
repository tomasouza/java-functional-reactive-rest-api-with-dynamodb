package br.com.oigarcom.api.data.providers;

import br.com.oigarcom.api.data.TestContext;
import br.com.oigarcom.api.users.core.entities.Page;
import br.com.oigarcom.api.users.core.entities.User;
import com.amazonaws.services.dynamodbv2.local.main.ServerRunner;
import com.amazonaws.services.dynamodbv2.local.server.DynamoDBProxyServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.internal.exceptions.ExceptionIncludingMockitoWarnings;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static br.com.oigarcom.api.data.utils.TestUtils.createUserTable;

public class UserDataProviderTest {

    private static final String ASSERTION_ERROR_MESSAGE = "Assertion error. Expected: [%s] Actual:[%s] ";
    private static DynamoDBProxyServer server;

    @BeforeAll
    public static void startDynamoServer() {
        final String[] localArgs = { "-inMemory" };

        try {
            server = ServerRunner.createServerFromCommandLineArgs(localArgs);
            server.start();
            createUserTable();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @AfterAll
    public static void shutdownDynamoServer() {
        if(server != null) {
            try {
                server.stop();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    @DisplayName("Testing save action")
    public void saveTest() {
        User testUser = getTestUser();
        StepVerifier.create(TestContext.userDataProvider.save(testUser))
                .assertNext(user -> {
                    String expectedName = "name";
                    String expectedEmail = "email";
                    String expectedPassword = "password";
                    if(!user.getName().equals(expectedName))
                        throw assertionError(user.getName(), expectedName);
                    if(!user.getEmail().equals(expectedEmail))
                        throw assertionError(user.getEmail(), expectedEmail);
                    if(!user.getPassword().equals(expectedPassword))
                        throw assertionError(user.getPassword(), expectedPassword);
                })
                .expectComplete()
                .verify();
    }

    @Test
    @DisplayName("Testing update action")
    public void updateTest() {
        User testUser = getTestUser();
        Mono<User> updatedUserMono =TestContext.userDataProvider.save(testUser)
        .flatMap(user -> {
            user.setName("newName");
            user.setPassword("newPassword");
            return TestContext.userDataProvider.update(user);
        });

        StepVerifier.create(updatedUserMono)
                .assertNext(user -> {
                    String expectedName = "newName";
                    String expectedEmail = "email";
                    String expectedPassword = "newPassword";
                    if(!user.getName().equals(expectedName))
                        throw assertionError(user.getName(), expectedName);
                    if(!user.getEmail().equals(expectedEmail))
                        throw assertionError(user.getEmail(), expectedEmail);
                    if(!user.getPassword().equals(expectedPassword))
                        throw assertionError(user.getPassword(), expectedPassword);
                })
                .expectComplete()
                .verify();
    }

    @Test
    @DisplayName("Testing get action")
    public void getTest() {
        User testUser = getTestUser();
        Mono<User> getUserMono = TestContext.userDataProvider.save(testUser)
                .flatMap(user -> TestContext.userDataProvider.get(user.getEmail()));

        StepVerifier.create(getUserMono)
                .assertNext(user -> {
                    String expectedName = "name";
                    String expectedEmail = "email";
                    String expectedPassword = "password";
                    if(!user.getName().equals(expectedName))
                        throw assertionError(user.getName(), expectedName);
                    if(!user.getEmail().equals(expectedEmail))
                        throw assertionError(user.getEmail(), expectedEmail);
                    if(!user.getPassword().equals(expectedPassword))
                        throw assertionError(user.getPassword(), expectedPassword);
                })
                .expectComplete()
                .verify();
    }

    @Test
    @DisplayName("Testing delete action")
    public void deleteTest() {
        User testUser = getTestUser();
        Mono<Void> deleteUserMono = TestContext.userDataProvider.save(testUser)
                .flatMap(user -> TestContext.userDataProvider.delete(user.getEmail()));

        StepVerifier.create(deleteUserMono)
                .expectComplete()
                .verify();


    }

    @Test
    @DisplayName("Testing list action")
    public void listTest() {
        User testUser = getTestUser();
        User secondTestUser = getSecondTestUser();
        TestContext.userDataProvider.save(testUser);
        TestContext.userDataProvider.save(secondTestUser);
        var userFlux = TestContext.userDataProvider.list(Page.builder()
                .pageSize(10)
                .lastValuatedKey(null)
                .build());

        StepVerifier.create(userFlux)
                .assertNext(user -> {
                    String expectedName = "name";
                    String expectedEmail = "email";
                    String expectedPassword = "password";
                    if(!user.getName().equals(expectedName))
                        throw assertionError(user.getName(), expectedName);
                    if(!user.getEmail().equals(expectedEmail))
                        throw assertionError(user.getEmail(), expectedEmail);
                    if(!user.getPassword().equals(expectedPassword))
                        throw assertionError(user.getPassword(), expectedPassword);
                })
                .assertNext(user -> {
                    String expectedName = "secondName";
                    String expectedEmail = "secondemail";
                    String expectedPassword = "secondPassword";
                    if(!user.getName().equals(expectedName))
                        throw assertionError(user.getName(), expectedName);
                    if(!user.getEmail().equals(expectedEmail))
                        throw assertionError(user.getEmail(), expectedEmail);
                    if(!user.getPassword().equals(expectedPassword))
                        throw assertionError(user.getPassword(), expectedPassword);
                })
                .expectComplete()
                .verify();


    }

    private ExceptionIncludingMockitoWarnings assertionError(String actual, String expected) {
        return new ExceptionIncludingMockitoWarnings(
                String.format(ASSERTION_ERROR_MESSAGE, expected, actual), new Exception());
    }

    private User getTestUser() {
        return User.builder()
                    .password("password")
                    .name("name")
                    .email("email")
                    .build();
    }

    private User getSecondTestUser() {
        return User.builder()
                .password("secondPassword")
                .name("secondName")
                .email("secondEmail")
                .build();
    }
}
