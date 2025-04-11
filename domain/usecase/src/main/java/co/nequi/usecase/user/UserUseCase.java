package co.nequi.usecase.user;

import co.nequi.model.user.User;
import co.nequi.model.user.exception.CustomException;
import co.nequi.model.user.exception.ExceptionMessage;
import co.nequi.model.user.gateways.IQueueGateway;
import co.nequi.model.user.gateways.IUserCacheGateway;
import co.nequi.model.user.gateways.IUserClientGateway;
import co.nequi.model.user.gateways.IUserRepositoryGateway;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class UserUseCase {

    private final IUserRepositoryGateway userRepositoryGateway;

    private final IUserClientGateway userClientGateway;
    private final IUserCacheGateway userCacheGateway;
    private final IQueueGateway queueGateway;

    public Mono<User> getUserById(Long id) {

            return userCacheGateway.getUserById(id).switchIfEmpty(
                    userRepositoryGateway.getUserById(id)
                    .switchIfEmpty(Mono.error(new CustomException(ExceptionMessage.USER_NOT_FOUND.getMessage())))
                    .doOnNext(user -> userCacheGateway.saveUser(user).subscribe())
            );
    }

    public Mono<User> saveUser(Long id) {
        return userRepositoryGateway.getUserById(id)
                .switchIfEmpty(
                        userClientGateway.getUserById(id)
                                .flatMap(user ->
                                        userRepositoryGateway.saveUser(user)
                                                .then(userRepositoryGateway.getUserById(id))
                                                .flatMap(userSaved -> {
                                                    // Enviar mensaje a la cola
                                                    return queueGateway.sendMessage(userSaved)
                                                            .then(Mono.just(userSaved));
                                                })
                                )
                );
    }
    public Mono<Boolean> existUser(Long id) {
        return userRepositoryGateway.getUserById(id)
                .flatMap(user -> Mono.error(new CustomException(ExceptionMessage.USER_ALREADY_EXISTS.getMessage())))
                .map(user -> true);
    }

    public Flux<User> getAllUsers() {
        return userRepositoryGateway.getAllUsers()
                .switchIfEmpty(Mono.error(new CustomException(ExceptionMessage.USER_NOT_FOUND.getMessage())));
    }

    public Flux<User> getUsersByName(String name) {
        return userRepositoryGateway.getUsersByName(name)
                .switchIfEmpty(Mono.error(new CustomException(ExceptionMessage.USER_NOT_FOUND.getMessage())));
    }

}
