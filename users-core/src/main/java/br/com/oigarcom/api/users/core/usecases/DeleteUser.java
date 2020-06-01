package br.com.oigarcom.api.users.core.usecases;

import br.com.oigarcom.api.users.core.gateways.UserGateway;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class DeleteUser extends UseCase {

    private final UserGateway userGateway;

    public DeleteUser(UserGateway userGateway) {
        this.userGateway = userGateway;
    }

    public Mono<Void> execute(String email) {
        return userGateway.delete(email);
    }
}
