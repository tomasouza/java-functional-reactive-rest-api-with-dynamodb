package br.com.oigarcom.api.users.core.presentables;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PresentableUser {
    private String name;
    private String email;
    private String password;
    @Override
    public String toString(){
        return "[Name = "+ this.name
                +", Email = "+ this.email +"]";
    }
}
