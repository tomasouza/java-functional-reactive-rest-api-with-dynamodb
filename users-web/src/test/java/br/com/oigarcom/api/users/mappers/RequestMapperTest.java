package br.com.oigarcom.api.users.mappers;

import br.com.oigarcom.api.users.web.mappers.RequestMapper;
import br.com.oigarcom.api.users.web.requests.CreateUserRequest;
import org.junit.jupiter.api.Test;

public class RequestMapperTest {

    @Test
    public void requestMapperTest() {
        RequestMapper requestMapper = new RequestMapper();
        requestMapper.mapToPresentableUser(CreateUserRequest.builder()
                .name("name")
                .email("email")
                .password("password")
                .build());
    }
}
