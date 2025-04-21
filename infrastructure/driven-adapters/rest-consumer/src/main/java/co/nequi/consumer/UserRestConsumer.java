package co.nequi.consumer;

import co.nequi.api.exeptionhandler.BusinessException;
import co.nequi.api.exeptionhandler.ExceptionMessage;
import co.nequi.api.exeptionhandler.TechnicalException;
import co.nequi.consumer.dto.response.ResponseDto;
import co.nequi.consumer.mapper.IUserResponseMapper;
import co.nequi.model.user.User;
import co.nequi.model.user.gateways.IUserClientGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserRestConsumer implements IUserClientGateway {
    private final WebClient client;
    private final IUserResponseMapper userResponseMapper;

    private static final String SERVER_ERROR = "Error del servidor: ";
    private static final String CLIENT_ERROR = "Error de cliente: ";
    private static final String USER_NOT_FOUND = "Usuario no encontrado";

    @Override
    public Mono<User> getUserById(Long id) {
        return client.get()
                .uri("/" + id)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response -> {
                    if (response.statusCode() == HttpStatus.NOT_FOUND) {
                        return Mono.error(new BusinessException(USER_NOT_FOUND, ExceptionMessage.USER_NOT_FOUND));
                    }
                    return Mono.error(new BusinessException(CLIENT_ERROR + response.statusCode(), ExceptionMessage.CLIENT_ERROR));
                })
                .onStatus(HttpStatusCode::is5xxServerError, response ->
                        Mono.error(new TechnicalException(SERVER_ERROR + response.statusCode(), ExceptionMessage.EXTERNAL_SERVICE_ERROR))
                )
                .bodyToMono(ResponseDto.class)
                .log()
                .map(res -> userResponseMapper.toModel(res.getData()));
    }
}
