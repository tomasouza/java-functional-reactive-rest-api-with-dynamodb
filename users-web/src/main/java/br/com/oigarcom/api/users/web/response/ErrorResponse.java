package br.com.oigarcom.api.users.web.response;

import lombok.Builder;

@Builder
public class ErrorResponse {
    private final String code;
    private final String message;
}
