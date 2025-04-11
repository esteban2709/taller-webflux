package co.nequi.model.user.gateways;

import co.nequi.model.user.User;
import reactor.core.publisher.Mono;

public interface IUserClientGateway {

    Mono<User> getUserById(Long id);
}
