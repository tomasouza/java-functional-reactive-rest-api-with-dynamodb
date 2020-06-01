package br.com.oigarcom.api.users.core.exceptions;

public class GatewayException extends RuntimeException {
    private int statusCode;
    public GatewayException(int statusCode, String message, Throwable cause) {
        super(message, cause);
        this.statusCode = statusCode;
    }
    public int getCode(){
        return this.statusCode;
    }
}
