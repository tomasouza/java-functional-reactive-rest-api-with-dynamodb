package br.com.oigarcom.api.users.core.usecases;

import br.com.oigarcom.api.users.core.exceptions.GatewayException;
import br.com.oigarcom.api.users.core.exceptions.InternalServerErrorException;
import br.com.oigarcom.api.users.core.exceptions.UserAlreadyExistsException;
import br.com.oigarcom.api.users.core.gateways.UserGateway;
import br.com.oigarcom.api.users.core.mappers.UserMapper;
import br.com.oigarcom.api.users.core.presentables.PresentableUser;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class SaveUser extends UseCase {

    private final UserGateway userGateway;
    private final UserMapper userMapper;

    public SaveUser(UserGateway userGateway, UserMapper userMapper) {
        this.userGateway = userGateway;
        this.userMapper = userMapper;
    }

    public Mono<PresentableUser> execute(Mono<PresentableUser> presentableUserMono) {
        return presentableUserMono.map(userMapper::mapToUser)
                .flatMap(userGateway::save)
                .onErrorMap(GatewayException.class, this::handleGatewayException)
                .map(userMapper::mapToPresentable);
    }

    private Throwable handleGatewayException(GatewayException e) {
        if (e.getCode() == 400)
            return new UserAlreadyExistsException(e);
        return new InternalServerErrorException(e);
    }
}
