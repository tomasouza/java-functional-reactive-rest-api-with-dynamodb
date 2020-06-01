package br.com.oigarcom.api.data.utils;

import br.com.oigarcom.api.users.data.exceptions.RepositoryException;
import br.com.oigarcom.api.users.data.utils.AmazonExceptionUtils;
import br.com.oigarcom.api.users.data.utils.RepositoryExceptionUtils;
import br.com.oigarcom.api.users.data.utils.UserTableConstants;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class UtilsTest {

    @Test
    @DisplayName("Testing the user table constants")
    public void userTableConstantsTest(){
        UserTableConstants userTableConstants = new UserTableConstants();
        String expectedConstants = "[USER_TABLE_NAME = Users, NAME = name, PASSWORD = password, EMAIL = email]";
        Assertions.assertEquals(expectedConstants, userTableConstants.toString());
    }

    @Test
    @DisplayName("Testing the utils classes")
    public void utilsTest(){
        var amazonExceptionUtils = new AmazonExceptionUtils();
        Assertions.assertNotNull(amazonExceptionUtils);
        var repositoryExceptionUtils = new RepositoryExceptionUtils();
        Assertions.assertNotNull(repositoryExceptionUtils);
    }
}
