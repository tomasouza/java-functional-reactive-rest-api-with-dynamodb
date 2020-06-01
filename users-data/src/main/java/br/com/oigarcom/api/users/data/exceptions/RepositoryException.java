package br.com.oigarcom.api.users.data.exceptions;

public class RepositoryException extends RuntimeException {
    private int awsStatusCode;
    public RepositoryException(int awsStatusCode, String message, Throwable cause) {
        super(message, cause);
        this.awsStatusCode = awsStatusCode;
    }
    public int getCode(){
        return this.awsStatusCode;
    }
}
