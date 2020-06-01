package br.com.oigarcom.api.users.core.exceptions;

import lombok.Getter;

@Getter
public enum ExceptionDetails {
    INTERNAL_SERVER_ERROR("UG-0000", "An unexpected error has occurred."),
    USER_ALREADY_EXISTS("UG-0001", "User already exists.");

    private String code;
    private String message;

    ExceptionDetails(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
