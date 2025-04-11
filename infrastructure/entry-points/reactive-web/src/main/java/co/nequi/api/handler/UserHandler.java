package co.nequi.api.handler;

import co.nequi.model.user.exception.ExceptionMessage;
import co.nequi.usecase.user.UserUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserHandler {

    private final UserUseCase userUseCase;

    public Mono<ServerResponse> createUser(ServerRequest request) {
        Long id = Long.valueOf(request.queryParam("id").orElseThrow(
                () -> new IllegalArgumentException(ExceptionMessage.USER_NOT_FOUND.getMessage())
        ));


        return userUseCase.saveUser(id)
                .flatMap(user -> ServerResponse.ok().bodyValue(user))
                //.onErrorResume(e -> ServerResponse.badRequest().bodyValue("Error creating user: " + e.getMessage()))
                ;
    }

    public Mono<ServerResponse> getUser(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable("id"));
        return userUseCase.getUserById(id)
                .flatMap(user -> ServerResponse.ok().bodyValue(user));
    }

    public Mono<ServerResponse> getAllUsers(ServerRequest request) {
        return userUseCase.getAllUsers()
                .collectList()
                .flatMap(users -> ServerResponse.ok().bodyValue(users));
    }

    public Mono<ServerResponse> getUserByName(ServerRequest request) {
        String name = request.queryParam("name").orElseThrow(
                () -> new IllegalArgumentException("Name is required")
        );
        return userUseCase.getUsersByName(name)
                .collectList()
                .flatMap(user -> ServerResponse.ok().bodyValue(user));
    }
}
