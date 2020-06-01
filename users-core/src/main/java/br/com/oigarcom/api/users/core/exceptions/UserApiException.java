package br.com.oigarcom.api.users.core.exceptions;

import lombok.Getter;

@Getter
public class UserApiException extends RuntimeException {
    private String code;
    private Integer statusCode;
    UserApiException(Integer statusCode, String code, String message, Throwable cause){
        super(code + " " + message, cause);
        this.code = code;
    }
}
