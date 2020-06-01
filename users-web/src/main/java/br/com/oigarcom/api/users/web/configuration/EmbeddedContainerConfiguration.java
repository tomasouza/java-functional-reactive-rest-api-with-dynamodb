package br.com.oigarcom.api.users.web.configuration;

import org.springframework.boot.web.embedded.netty.NettyReactiveWebServerFactory;
import org.springframework.boot.web.reactive.server.ReactiveWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmbeddedContainerConfiguration {
    @Bean
    public ReactiveWebServerFactory servletContainer() {
        return new NettyReactiveWebServerFactory();
    }
}
