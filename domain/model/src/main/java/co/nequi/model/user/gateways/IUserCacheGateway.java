package co.nequi.model.user.gateways;

import co.nequi.model.user.User;
import reactor.core.publisher.Mono;

public interface IUserCacheGateway {

    Mono<User> getUserById(Long id);
    Mono<User> saveUser(User user);

    }
