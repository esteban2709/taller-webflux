package co.nequi.r2dbc.adapter;

import co.nequi.model.user.User;
import co.nequi.model.user.gateways.IUserRepositoryGateway;
import co.nequi.r2dbc.entity.UserEntity;
import co.nequi.r2dbc.mapper.IUserEntityMapper;
import co.nequi.r2dbc.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class UserPersistenceAdapter implements IUserRepositoryGateway {

    private final IUserRepository userRepository;
    private final IUserEntityMapper userEntityMapper;

    @Override
    public Mono<User> getUserById(Long id) {
        return userRepository.findById(id).map(userEntityMapper::toUser);
    }

    @Override
    public Mono<User> saveUser(User user) {
        System.out.println(user);
        return userRepository.insertUser(user.getId(), user.getFirstName(), user.getLastName(),user.getEmail()).map(userEntityMapper::toUser);
    }

    @Override
    public Flux<User> getAllUsers() {
        return userRepository.findAll()
                .map(userEntityMapper::toUser);
    }

    @Override
    public Flux<User> getUsersByName(String name) {
        return userRepository.findAllByFirstName(name)
                .map(userEntityMapper::toUser);
    }
}
