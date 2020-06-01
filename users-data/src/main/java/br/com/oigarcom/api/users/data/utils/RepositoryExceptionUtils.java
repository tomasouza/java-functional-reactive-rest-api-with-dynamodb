package br.com.oigarcom.api.users.data.utils;

import br.com.oigarcom.api.users.data.exceptions.RepositoryException;
import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;

import static br.com.oigarcom.api.users.data.utils.AmazonExceptionUtils.getAmazonMessage;

public class RepositoryExceptionUtils {
    public static RepositoryException handleAmazonClientException(AmazonClientException ace, String errorMessage) {
        String amazonMessage = getAmazonMessage(ace);
        return new RepositoryException(500, errorMessage + amazonMessage,
                ace);
    }

    public static RepositoryException handleAmazonServiceException(AmazonServiceException ase, String errorMessage) {
        String amazonMessage = getAmazonMessage(ase);
        return new RepositoryException(ase.getStatusCode(), errorMessage + amazonMessage, ase);
    }
}
