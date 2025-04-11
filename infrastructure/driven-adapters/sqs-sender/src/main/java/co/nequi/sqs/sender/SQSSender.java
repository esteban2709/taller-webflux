package co.nequi.sqs.sender;

import co.nequi.model.user.User;
import co.nequi.model.user.gateways.IQueueGateway;
import co.nequi.sqs.sender.config.SQSSenderProperties;
import co.nequi.sqs.sender.dto.UserDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;
import software.amazon.awssdk.services.sqs.model.SendMessageResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
@Log4j2
@RequiredArgsConstructor
public class SQSSender implements IQueueGateway {
    private final SQSSenderProperties properties;
    private final SqsAsyncClient client;
    private final ObjectMapper objectMapper;

    @Override
    public Mono<String> sendMessage(User user) {
        String json = null;
        try {
            json = objectMapper.writeValueAsString(user);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        String finalJson = json;
        return Mono.fromCallable(() -> buildRequest(finalJson))
                .flatMap(request -> Mono.fromFuture(client.sendMessage(request)))
                .doOnNext(response -> log.debug("Message sent {}", response.messageId()))
                .map(SendMessageResponse::messageId);
    }

    private SendMessageRequest buildRequest(String message) {
        return SendMessageRequest.builder()
                .queueUrl(properties.queueUrl())
                .messageBody(message)
                .build();
    }
}
