package br.com.oigarcom.api.users.core.usecases;

import br.com.oigarcom.api.users.core.entities.Page;
import br.com.oigarcom.api.users.core.gateways.UserGateway;
import br.com.oigarcom.api.users.core.mappers.PageMapper;
import br.com.oigarcom.api.users.core.mappers.UserMapper;
import br.com.oigarcom.api.users.core.presentables.PresentablePage;
import br.com.oigarcom.api.users.core.presentables.PresentableUser;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class ListUsers extends UseCase {

    private final UserGateway userGateway;
    private final UserMapper userMapper;
    private final PageMapper pageMapper;

    public ListUsers(UserGateway userGateway,
                     UserMapper userMapper, PageMapper pageMapper) {
        this.userGateway = userGateway;
        this.userMapper = userMapper;
        this.pageMapper = pageMapper;
    }

    public Flux<PresentableUser> execute(PresentablePage presentablePage){
        return userGateway.list(pageMapper.mapToPage(presentablePage))
                .map(userMapper::mapToPresentable);
    }
}
