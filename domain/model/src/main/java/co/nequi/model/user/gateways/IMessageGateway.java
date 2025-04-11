package co.nequi.model.user.gateways;

import co.nequi.model.user.User;
import reactor.core.publisher.Mono;

public interface IMessageGateway {

    Mono<User> save(User user);
}
