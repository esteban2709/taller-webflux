package co.nequi.usecase.user;

import co.nequi.model.user.User;
import co.nequi.model.user.gateways.IMessageGateway;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class MessageUseCase {
    private final IMessageGateway messageGateway;

    public Mono<Void> saveMessage(User user) {
        return messageGateway.save(user).then();
    }
}
