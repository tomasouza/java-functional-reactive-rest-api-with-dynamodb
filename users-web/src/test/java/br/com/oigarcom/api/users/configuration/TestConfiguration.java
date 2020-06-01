package br.com.oigarcom.api.users.configuration;

import br.com.oigarcom.api.users.core.mappers.UserMapper;
import br.com.oigarcom.api.users.web.mappers.RequestMapper;
import br.com.oigarcom.api.users.web.validators.UserValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestConfiguration {

    @Bean
    public UserValidator userValidator() {
        return new UserValidator();
    }
    @Bean
    public RequestMapper requestMapper() {
        return new RequestMapper();
    }

}
