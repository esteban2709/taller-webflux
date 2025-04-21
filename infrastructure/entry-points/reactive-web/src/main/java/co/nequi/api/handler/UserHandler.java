package co.nequi.api.handler;

import co.nequi.api.exeptionhandler.BusinessException;
import co.nequi.api.exeptionhandler.CustomException;
import co.nequi.api.exeptionhandler.ErrorResponse;
import co.nequi.api.exeptionhandler.ExceptionMessage;
import co.nequi.api.exeptionhandler.TechnicalException;
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
        return Mono.fromCallable(() ->
                        Long.valueOf(request.queryParam("id").orElseThrow(
                                () -> new BusinessException("Id requerido", ExceptionMessage.INVALID_REQUEST))))
                .flatMap(userUseCase::saveUser)
                .flatMap(user -> ServerResponse.ok().bodyValue(user))
                .onErrorResume(this::handleError);
    }

    public Mono<ServerResponse> getUser(ServerRequest request) {
        return Mono.fromCallable(() -> Long.valueOf(request.pathVariable("id")))
                .flatMap(userUseCase::getUserById)
                .flatMap(user -> ServerResponse.ok().bodyValue(user))
                .onErrorResume(this::handleError);
    }

    public Mono<ServerResponse> getAllUsers(ServerRequest request) {
        return userUseCase.getAllUsers()
                .collectList()
                .flatMap(users -> ServerResponse.ok().bodyValue(users))
                .onErrorResume(this::handleError);
    }

    public Mono<ServerResponse> getUserByName(ServerRequest request) {
        return Mono.fromCallable(() ->
                        request.queryParam("name").orElseThrow(() ->
                                new BusinessException("Nombre requerido", ExceptionMessage.INVALID_REQUEST)))
                .flatMapMany(userUseCase::getUsersByName)
                .collectList()
                .flatMap(users -> ServerResponse.ok().bodyValue(users))
                .onErrorResume(this::handleError);
    }

    private Mono<ServerResponse> handleError(Throwable throwable) {
        if (throwable instanceof BusinessException) {
            BusinessException ex = (BusinessException) throwable;
            return ServerResponse.badRequest().bodyValue(buildErrorBody(ex, "BUSINESS_ERROR"));
        } else if (throwable instanceof TechnicalException) {
            TechnicalException ex = (TechnicalException) throwable;
            return ServerResponse.status(500).bodyValue(buildErrorBody(ex, "TECHNICAL_ERROR"));
        } else {
            return ServerResponse.status(500)
                    .bodyValue(new ErrorResponse("Error inesperado", "INTERNAL_ERROR"));
        }
    }

    private ErrorResponse buildErrorBody(CustomException ex, String code) {
        return new ErrorResponse(
                ex.getMessage(),
                code
        );
    }
}
