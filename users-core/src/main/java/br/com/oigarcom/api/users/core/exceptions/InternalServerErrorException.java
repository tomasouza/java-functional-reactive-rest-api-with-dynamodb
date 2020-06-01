package br.com.oigarcom.api.users.core.exceptions;

import static br.com.oigarcom.api.users.core.exceptions.ExceptionDetails.INTERNAL_SERVER_ERROR;

public class InternalServerErrorException extends UserApiException {
    public InternalServerErrorException(Throwable cause){
        super(500, INTERNAL_SERVER_ERROR.getCode(),INTERNAL_SERVER_ERROR.getMessage(), cause);
    }
}
