package br.com.oigarcom.api.users.data.providers;

import br.com.oigarcom.api.users.core.entities.Page;
import br.com.oigarcom.api.users.core.entities.User;
import br.com.oigarcom.api.users.core.exceptions.GatewayException;
import br.com.oigarcom.api.users.core.exceptions.UserApiException;
import br.com.oigarcom.api.users.core.gateways.UserGateway;
import br.com.oigarcom.api.users.data.documents.UserDocument;
import br.com.oigarcom.api.users.data.exceptions.RepositoryException;
import br.com.oigarcom.api.users.data.mappers.UserDocumentMapper;
import br.com.oigarcom.api.users.data.repositories.UserRepository;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

import java.util.Optional;

import static br.com.oigarcom.api.users.data.utils.UserTableConstants.USER_TABLE_NAME;

public class UserDataProvider implements UserGateway {

    private static UserRepository userRepository;
    private static DynamoDB dynamoDB;
    private final UserDocumentMapper userDocumentMapper;

    public UserDataProvider(DynamoDB dynamoDB, UserDocumentMapper userDocumentMapper) {
        this.userDocumentMapper = userDocumentMapper;
        UserDataProvider.dynamoDB = dynamoDB;
        UserDataProvider.userRepository = new UserRepository(dynamoDB.getTable(USER_TABLE_NAME), userDocumentMapper);
    }

    @Override
    public Mono<User> save(User user) {
        UserDocument userDocument = userDocumentMapper.mapToUserDocument(user);
        userRepository.save(userDocument);
        return userDocumentMapper.mapToMonoUser(userDocument);
    }

    @Override
    public Mono<User> get(String email) {
        UserDocument userDocument = userRepository.get(email.toLowerCase());
        if(Optional.ofNullable(userDocument).isPresent())
            return userDocumentMapper.mapToMonoUser(userDocument);
        return Mono.empty();
    }

    @Override
    public Mono<Void> delete(String email) {
        userRepository.delete(email.toLowerCase());
        return Mono.empty();
    }

    @Override
    public Mono<User> update(User user) {
        UserDocument userDocument = userDocumentMapper.mapToUserDocument(user);
        userRepository.update(userDocument);
        return userDocumentMapper.mapToMonoUser(userDocument);
    }

    @Override
    public Flux<User> list(Page page) {
        List<UserDocument> userDocuments = userRepository.list(page.getPageSize(), page.getLastValuatedKey());
        return userDocumentMapper.mapToUserFlux(userDocuments);
    }

    @Override
    public Mono<Void> createUserTable(){
        userRepository.createUserTable(dynamoDB);
        return Mono.empty();
    }

}
