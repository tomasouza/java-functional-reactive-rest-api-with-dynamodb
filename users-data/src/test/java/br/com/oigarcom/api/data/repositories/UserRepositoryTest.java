package br.com.oigarcom.api.data.repositories;

import br.com.oigarcom.api.data.TestContext;
import br.com.oigarcom.api.users.data.documents.UserDocument;
import br.com.oigarcom.api.users.data.exceptions.RepositoryException;
import br.com.oigarcom.api.users.data.mappers.UserDocumentMapper;
import br.com.oigarcom.api.users.data.repositories.UserRepository;
import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.DeleteItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.ScanSpec;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.amazonaws.services.dynamodbv2.local.main.ServerRunner;
import com.amazonaws.services.dynamodbv2.local.server.DynamoDBProxyServer;
import org.junit.jupiter.api.*;

import java.util.List;

import static br.com.oigarcom.api.data.utils.TestUtils.createUserTable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserRepositoryTest {

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
    @DisplayName("Testing save and get actions")
    public void saveAndGetTest() {
        TestContext.userRepository.save(getSampleUser());
        UserDocument userDocument = TestContext.userRepository.get("email");
        Assertions.assertEquals("name", userDocument.getName());
        Assertions.assertEquals("password", userDocument.getPassword());
        Assertions.assertEquals("email", userDocument.getEmail());
        Assertions.assertEquals("User [Name=name, Email=email]", userDocument.toString());
    }

    @Test
    @DisplayName("Testing update action")
    public void updateTest() {
        UserDocument userDocument = getSampleUser();
        TestContext.userRepository.save(userDocument);
        userDocument.setPassword("newPassword");
        userDocument.setName("newName");
        TestContext.userRepository.update(userDocument);
        UserDocument updatedUserDocument = TestContext.userRepository.get("email");
        Assertions.assertEquals("newName", updatedUserDocument.getName());
        Assertions.assertEquals("newPassword", updatedUserDocument.getPassword());
        Assertions.assertEquals("email", updatedUserDocument.getEmail());
        Assertions.assertEquals("User [Name=newName, Email=email]", updatedUserDocument.toString());
    }

    @Test
    @DisplayName("Testing delete action")
    public void deleteTest(){
        UserDocument userDocument = getSampleUser();
        TestContext.userRepository.save(userDocument);
        TestContext.userRepository.delete(userDocument.getEmail());
        UserDocument deletedUserDocument = TestContext.userRepository.get("email");
        Assertions.assertNull(deletedUserDocument);
    }

    @Test
    @DisplayName("Testing list action")
    public void listTest(){
        UserDocument userDocument = getSampleUser();
        TestContext.userRepository.save(userDocument);
        List<UserDocument> userDocuments = TestContext.userRepository.list(10, null);
        UserDocument listedUserDocument = userDocuments.get(0);
        Assertions.assertEquals("name", listedUserDocument.getName());
        Assertions.assertEquals("password", listedUserDocument.getPassword());
        Assertions.assertEquals("email", listedUserDocument.getEmail());
        Assertions.assertEquals("User [Name=name, Email=email]", listedUserDocument.toString());
    }

    @Test
    @DisplayName("Testing amazon service exception when saving")
    public void saveAmazonServiceExceptionTest(){
        Table table = mock(Table.class);
        when(table.putItem(any(Item.class))).thenThrow(AmazonServiceException.class);
        var userRepository = new UserRepository(table, new UserDocumentMapper());
        Assertions.assertThrows(RepositoryException.class, () -> userRepository.save(getSampleUser()));

    }

    @Test
    @DisplayName("Testing amazon client exception when saving")
    public void saveAmazonClientExceptionTest(){
        Table table = mock(Table.class);
        when(table.putItem(any(Item.class))).thenThrow(AmazonClientException.class);
        var userRepository = new UserRepository(table, new UserDocumentMapper());
        Assertions.assertThrows(RepositoryException.class, () -> userRepository.save(getSampleUser()));

    }

    @Test
    @DisplayName("Testing amazon client exception when getting")
    public void getAmazonClientExceptionTest(){
        Table table = mock(Table.class);
        when(table.getItem(any(GetItemSpec.class))).thenThrow(AmazonClientException.class);
        var userRepository = new UserRepository(table, new UserDocumentMapper());
        Assertions.assertThrows(RepositoryException.class, () -> userRepository.get(getSampleUser().getEmail()));

    }

    @Test
    @DisplayName("Testing amazon service exception when getting")
    public void getAmazonServiceExceptionTest(){
        Table table = mock(Table.class);
        when(table.getItem(any(GetItemSpec.class))).thenThrow(AmazonServiceException.class);
        var userRepository = new UserRepository(table, new UserDocumentMapper());
        Assertions.assertThrows(RepositoryException.class, () -> userRepository.get(getSampleUser().getEmail()));

    }

    @Test
    @DisplayName("Testing amazon client exception when updating")
    public void updateAmazonClientExceptionTest(){
        Table table = mock(Table.class);
        when(table.updateItem(any(UpdateItemSpec.class))).thenThrow(AmazonClientException.class);
        var userRepository = new UserRepository(table, new UserDocumentMapper());
        Assertions.assertThrows(RepositoryException.class, () -> userRepository.update(getSampleUser()));

    }

    @Test
    @DisplayName("Testing amazon service exception when updating")
    public void updateAmazonServiceExceptionTest(){
        Table table = mock(Table.class);
        when(table.updateItem(any(UpdateItemSpec.class))).thenThrow(AmazonServiceException.class);
        var userRepository = new UserRepository(table, new UserDocumentMapper());
        Assertions.assertThrows(RepositoryException.class, () -> userRepository.update(getSampleUser()));

    }

    @Test
    @DisplayName("Testing amazon client exception when deleting")
    public void deleteAmazonClientExceptionTest(){
        Table table = mock(Table.class);
        when(table.deleteItem(any(DeleteItemSpec.class))).thenThrow(AmazonClientException.class);
        var userRepository = new UserRepository(table, new UserDocumentMapper());
        Assertions.assertThrows(RepositoryException.class, () -> userRepository.delete(getSampleUser().getEmail()));

    }

    @Test
    @DisplayName("Testing amazon service exception when deleting")
    public void deleteAmazonServiceExceptionTest(){
        Table table = mock(Table.class);
        when(table.deleteItem(any(DeleteItemSpec.class))).thenThrow(AmazonServiceException.class);
        var userRepository = new UserRepository(table, new UserDocumentMapper());
        Assertions.assertThrows(RepositoryException.class, () -> userRepository.delete(getSampleUser().getEmail()));

    }

    @Test
    @DisplayName("Testing amazon client exception when listing")
    public void listAmazonClientExceptionTest(){
        Table table = mock(Table.class);
        when(table.scan(any(ScanSpec.class))).thenThrow(AmazonClientException.class);
        var userRepository = new UserRepository(table, new UserDocumentMapper());
        Assertions.assertThrows(RepositoryException.class, () -> userRepository.list(10, null));

    }

    @Test
    @DisplayName("Testing amazon service exception when listing")
    public void listAmazonServiceExceptionTest(){
        Table table = mock(Table.class);
        when(table.scan(any(ScanSpec.class))).thenThrow(AmazonServiceException.class);
        var userRepository = new UserRepository(table, new UserDocumentMapper());
        Assertions.assertThrows(RepositoryException.class, () -> userRepository.list(10, "lastEvaluatedKey"));

    }


    private UserDocument getSampleUser() {
        return UserDocument.builder()
                .name("name")
                .password("password")
                .email("email")
                .build();
    }

}
