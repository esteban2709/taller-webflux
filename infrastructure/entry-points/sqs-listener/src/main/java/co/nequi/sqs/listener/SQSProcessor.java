package co.nequi.sqs.listener;

import co.nequi.model.user.User;
import co.nequi.usecase.user.MessageUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.services.sqs.model.Message;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.function.Function;

@Slf4j
@Service
@RequiredArgsConstructor
public class SQSProcessor implements Function<Message, Mono<Void>> {
    private final MessageUseCase messageUseCase;
    private final ObjectMapper objectMapper;

    @Override
    public Mono<Void> apply(Message message) {
        return Mono.fromCallable(() -> objectMapper.readValue(message.body(), User.class))
                .map(user -> {
                    user.setFirstName(user.getFirstName().toUpperCase());
                    user.setLastName(user.getLastName().toUpperCase());
                    user.setEmail(user.getEmail().toUpperCase());
                    return user;
                })
                .flatMap(user -> messageUseCase.saveMessage(user))
                .onErrorResume(e -> Mono.empty())
                .then();
    }

}
