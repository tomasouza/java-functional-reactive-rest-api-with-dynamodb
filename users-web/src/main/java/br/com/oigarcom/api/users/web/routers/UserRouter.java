package br.com.oigarcom.api.users.web.routers;

import br.com.oigarcom.api.users.web.handlers.UserHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class UserRouter {

    @Bean
    public RouterFunction<ServerResponse> route(UserHandler userHandler) {

        return RouterFunctions.route()
                .GET("/users", userHandler::listUsers)
                .POST("/users", userHandler::saveUser)
                .GET("/users/{email}", userHandler::getUser)
                .PATCH("/users/{email}", userHandler::updateUser)
                .DELETE("/users/{email}", userHandler::deleteUser)
                .build();
    }
}
