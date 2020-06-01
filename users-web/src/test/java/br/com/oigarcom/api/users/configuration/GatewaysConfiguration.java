package br.com.oigarcom.api.users.configuration;

import br.com.oigarcom.api.users.core.gateways.UserGateway;
import br.com.oigarcom.api.users.core.mappers.UserMapper;
import br.com.oigarcom.api.users.data.providers.DataProviderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewaysConfiguration {

    @Bean
    public UserGateway userGateway() {
        return DataProviderFactory.getUserDataProvider();
    }

    @Bean
    public UserMapper userMapper() {
        return new UserMapper();
    }
}
