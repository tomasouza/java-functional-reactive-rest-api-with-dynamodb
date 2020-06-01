package br.com.oigarcom.api.data.builders;

import br.com.oigarcom.api.users.data.documents.UserDocument;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class UserDocumentBuilderTest {

    @Test
    @DisplayName("Testing user builder")
    public void buildUserDocumentTest(){
        String name = "name";
        String password = "password";
        String email = "email@email.com";
        UserDocument userDocument = UserDocument.builder()
                .name(name)
                .password(password)
                .email(email)
                .build();
        Assertions.assertEquals(name, userDocument.getName());
        Assertions.assertEquals(password, userDocument.getPassword());
        Assertions.assertEquals(email, userDocument.getEmail());
    }
}
