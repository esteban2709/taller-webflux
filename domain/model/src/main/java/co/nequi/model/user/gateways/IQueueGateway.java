package co.nequi.model.user.gateways;

import co.nequi.model.user.User;
import reactor.core.publisher.Mono;

public interface IQueueGateway {
    Mono<String> sendMessage(User user);
}
