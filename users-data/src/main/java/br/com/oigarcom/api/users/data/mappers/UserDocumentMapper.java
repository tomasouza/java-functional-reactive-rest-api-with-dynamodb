package br.com.oigarcom.api.users.data.mappers;

import br.com.oigarcom.api.users.core.entities.User;
import br.com.oigarcom.api.users.data.documents.UserDocument;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.ScanOutcome;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static br.com.oigarcom.api.users.data.utils.UserTableConstants.*;

public class UserDocumentMapper {

    public UserDocument mapToUserDocument(Item userItem) {
        return UserDocument.builder()
                .name(userItem.getString(NAME))
                .password(userItem.getString(PASSWORD))
                .email(userItem.getString(EMAIL))
                .build();
    }

    public UserDocument mapToUserDocument(User user) {
        return UserDocument.builder()
                .name(user.getName())
                .password(user.getPassword())
                .email(user.getEmail().toLowerCase())
                .build();
    }

    public Mono<User> mapToMonoUser(UserDocument userDocument) {
        return Mono.just(User.builder()
                .name(userDocument.getName())
                .password(userDocument.getPassword())
                .email(userDocument.getEmail().toLowerCase())
                .build());
    }

    public Item mapToItem(UserDocument userDocument) {
        return new Item()
                .withPrimaryKey(EMAIL, userDocument.getEmail())
                .withString(NAME, userDocument.getName())
                .withString(PASSWORD, userDocument.getPassword())
                .withString(EMAIL, userDocument.getEmail());
    }

    public List<UserDocument> mapToUserDocuments(ItemCollection<ScanOutcome> items) {
        List<UserDocument> documents = new ArrayList<>();
        for (Item item : items) {
            documents.add(mapToUserDocument(item));
        }
        return documents;
    }

    public Flux<User> mapToUserFlux(List<UserDocument> userDocuments) {
        return Flux.fromIterable(userDocuments.stream()
                .map(this::mapToUser)
                .collect(Collectors.toList()));
    }

    public User mapToUser(UserDocument userDocument) {
        return User.builder()
                .name(userDocument.getName())
                .password(userDocument.getPassword())
                .email(userDocument.getEmail())
                .build();
    }
}
