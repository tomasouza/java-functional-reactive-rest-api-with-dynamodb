package br.com.oigarcom.api.users.core.exceptions;

import static br.com.oigarcom.api.users.core.exceptions.ExceptionDetails.USER_ALREADY_EXISTS;

public class UserAlreadyExistsException extends UserApiException {
    public UserAlreadyExistsException(Throwable cause){
        super(409, USER_ALREADY_EXISTS.getCode(),USER_ALREADY_EXISTS.getMessage(), cause);
    }
}
