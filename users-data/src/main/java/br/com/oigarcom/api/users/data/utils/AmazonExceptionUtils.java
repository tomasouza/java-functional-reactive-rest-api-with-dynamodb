package br.com.oigarcom.api.users.data.utils;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;

public class AmazonExceptionUtils {
    public static String getAmazonMessage(AmazonServiceException ase) {
        return new StringBuilder()
                .append(" Could not complete operation ")
                .append(" Error Message: " + ase.getMessage())
                .append(" HTTP Status: " + ase.getStatusCode())
                .append(" AWS Error Code: " + ase.getErrorCode())
                .append(" Error Type: " + ase.getErrorType())
                .append(" Request ID: " + ase.getRequestId())
                .toString();
    }

    public static String getAmazonMessage(AmazonClientException ace) {
        return new StringBuilder()
                .append(" An Amazon Internal error occurred communicating with DynamoDB ")
                .append( "Error Message:  " + ace.getMessage())
                .toString();
    }
}
