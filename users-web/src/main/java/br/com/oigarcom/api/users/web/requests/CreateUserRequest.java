package br.com.oigarcom.api.users.web.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserRequest {
    private String name;
    private String email;
    private String password;
    @Override
    public String toString() {
        return "[Name = "+ this.name
                +", Email = "+ this.email +"]";
    }
}
