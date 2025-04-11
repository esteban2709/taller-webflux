package co.nequi.model.user.gateways;

import co.nequi.model.user.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IUserRepositoryGateway {

    Mono<User> getUserById(Long id);

    Mono<User> saveUser(User user);

    Flux<User> getAllUsers();

    Flux<User> getUsersByName(String name);
}
