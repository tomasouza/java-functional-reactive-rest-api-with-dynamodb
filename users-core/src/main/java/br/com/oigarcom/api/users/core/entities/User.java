package br.com.oigarcom.api.users.core.entities;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
public class User {

    @Setter
    private String name;
    @Setter
    private String password;
    private String email;
    @Override
    public String toString(){
        return "[Name = "+ this.name
                +", Email = "+ this.email +"]";
    }

}
