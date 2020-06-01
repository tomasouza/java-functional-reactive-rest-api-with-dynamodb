package br.com.oigarcom.api.users.core.usecases;

import br.com.oigarcom.api.users.core.gateways.UserGateway;
import br.com.oigarcom.api.users.core.mappers.UserMapper;
import br.com.oigarcom.api.users.core.presentables.PresentableUser;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class GetUser extends UseCase {

    private final UserGateway userGateway;
    private final UserMapper userMapper;

    public GetUser(UserGateway userGateway, UserMapper userMapper) {
        this.userGateway = userGateway;
        this.userMapper = userMapper;
    }

    public Mono<PresentableUser> execute(String email) {
        return userGateway.get(email)
                .map(userMapper::mapToPresentable);
    }
}
