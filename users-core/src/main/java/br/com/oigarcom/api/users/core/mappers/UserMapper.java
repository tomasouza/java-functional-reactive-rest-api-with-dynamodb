package br.com.oigarcom.api.users.core.mappers;

import br.com.oigarcom.api.users.core.entities.User;
import br.com.oigarcom.api.users.core.presentables.PresentableUser;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public PresentableUser mapToPresentable(User user) {
        return PresentableUser.builder()
                .name(user.getName())
                .email(user.getEmail())
                .password(user.getPassword())
                .build();
    }

    public User mapToUser(PresentableUser presentableUser) {
        return User.builder()
                .name(presentableUser.getName())
                .email(presentableUser.getEmail())
                .password(presentableUser.getPassword())
                .build();
    }

}
