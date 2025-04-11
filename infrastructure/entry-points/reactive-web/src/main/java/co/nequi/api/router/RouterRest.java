package co.nequi.api.router;

import co.nequi.api.handler.UserHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterRest {

    private static final String USERS_PATH = "/user";

    @Bean
    public RouterFunction<ServerResponse> routerFunction(UserHandler userHandler) {
        return route()
                .POST(USERS_PATH, userHandler::createUser)
                .GET(USERS_PATH + "/name", userHandler::getUserByName)
                .GET(USERS_PATH + "/{id}", userHandler::getUser)
                .GET(USERS_PATH, userHandler::getAllUsers)
                .build();
    }
}
