package br.com.oigarcom.api.users.web.configuration;

import br.com.oigarcom.api.users.core.gateways.UserGateway;
import br.com.oigarcom.api.users.data.providers.DataProviderFactory;
import com.amazonaws.services.dynamodbv2.local.main.ServerRunner;
import com.amazonaws.services.dynamodbv2.local.server.DynamoDBProxyServer;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;

@Configuration
public class GatewaysConfiguration {

    private static DynamoDBProxyServer server;

    @Bean
    @Profile("production")
    public UserGateway userGateway() {
        return DataProviderFactory.getUserDataProvider();
    }

    @Bean
    @Profile("!production")
    public UserGateway embeddedUserGateway() {
        return DataProviderFactory.getEmbeddedUserDataProvider();
    }

    @EventListener(ApplicationReadyEvent.class)
    @Profile("!production")
    public void StartupEmbeddedDynamo() {
        final String[] localArgs = { "-inMemory" };
        try {
            server = ServerRunner.createServerFromCommandLineArgs(localArgs);
            server.start();
            DataProviderFactory.getEmbeddedUserDataProvider().createUserTable();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
