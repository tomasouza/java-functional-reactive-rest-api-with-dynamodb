package br.com.oigarcom.api.users.web.mappers;

import br.com.oigarcom.api.users.core.presentables.PresentableUser;
import br.com.oigarcom.api.users.web.requests.CreateUserRequest;
import org.springframework.stereotype.Component;

@Component
public class RequestMapper {

    public PresentableUser mapToPresentableUser(CreateUserRequest request){
        return PresentableUser.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(request.getPassword())
                .build();
    }
}
