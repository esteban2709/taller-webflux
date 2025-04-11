package co.nequi.consumer;

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

    @Override
    public Mono<User> getUserById(Long id) {
        return client.get().
                uri("/" + id)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response -> {
                    if (response.statusCode() == HttpStatus.NOT_FOUND) {
                        return Mono.error(new RuntimeException("User with id " + id + " not found"));
                    }
                    return Mono.error(new RuntimeException("Client error: " + response.statusCode()));
                })
                .bodyToMono(ResponseDto.class)
                .log()
                .map(  res -> userResponseMapper.toModel(res.getData()));
    }


    // these methods are an example that illustrates the implementation of WebClient.
    // You should use the methods that you implement from the Gateway from the domain.
//    @CircuitBreaker(name = "testGet" /*, fallbackMethod = "testGetOk"*/)
//    public Mono<ObjectResponse> testGet() {
//        return client
//                .get()
//                .retrieve()
//                .bodyToMono(ObjectResponse.class);
//    }

// Possible fallback method
//    public Mono<String> testGetOk(Exception ignored) {
//        return client
//                .get() //
//                .retrieve()
//                .bodyToMono(String.class);
//    }

//    @CircuitBreaker(name = "testPost")
//    public Mono<ObjectResponse> testPost() {
//        ObjectRequest request = ObjectRequest.builder()
//            .val1("exampleval1")
//            .val2("exampleval2")
//            .build();
//        return client
//                .post()
//                .body(Mono.just(request), ObjectRequest.class)
//                .retrieve()
//                .bodyToMono(ObjectResponse.class);
//    }
}
