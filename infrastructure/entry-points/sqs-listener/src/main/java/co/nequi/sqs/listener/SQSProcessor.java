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
    // private final MyUseCase myUseCase;
    private final MessageUseCase messageUseCase;
    private final ObjectMapper objectMapper;

    @Override
    public Mono<Void> apply(Message message) {
        try {
            User user = objectMapper.readValue(message.body(), User.class);
            user.setFirstName(user.getFirstName().toUpperCase());
            user.setLastName(user.getLastName().toUpperCase());
            user.setEmail(user.getEmail().toUpperCase());
            messageUseCase.saveMessage(user);
        }catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        System.out.println("Listener cola" +  message.body());

        return Mono.empty();
        // return myUseCase.doAny(message.body());
    }

//    @Override
//    public Mono<Void> apply(Message message) {
//        return Mono.fromCallable(() -> objectMapper.readValue(message.body(), User.class))
//                .map(user -> {
//                    user.setFirstName(user.getFirstName().toUpperCase());
//                    user.setLastName(user.getLastName().toUpperCase());
//                    user.setEmail(user.getEmail().toUpperCase());
//                    return user;
//                })
//                .flatMap(user -> {
//                    System.out.println("Listener cola: " + message.body());
//                    return messageUseCase.saveMessage(user); // Este debe retornar Mono<Void>
//                })
////                .doOnError(e -> log.error("Error processing message: {}", e.getMessage(), e))
////                .onErrorResume(e -> Mono.empty())
//                ;
//    }
}
