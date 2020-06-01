package br.com.oigarcom.api.users.core.gateways;

import br.com.oigarcom.api.users.core.entities.User;
import br.com.oigarcom.api.users.core.entities.Page;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public interface UserGateway {
    Mono<User> save(User user);
    Mono<User> get(String email);
    Mono<Void> delete(String email);
    Mono<User> update(User user);
    Flux<User> list(Page pageInfoFlux);
    Mono<Void> createUserTable();
}
