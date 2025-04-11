package co.nequi.r2dbc.repository;

import co.nequi.r2dbc.entity.UserEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface IUserRepository extends ReactiveCrudRepository<UserEntity, Long> {

    @Query("SELECT * FROM users WHERE first_name ILIKE :firstName")
    Flux<UserEntity> findAllByFirstName(String firstName);

    @Query("INSERT INTO users (id, first_name, last_name, email) VALUES (:id, :firstName, :lastName, :email)")
    Mono<UserEntity> insertUser(@Param("id") Long id, @Param("firstName") String firstName, @Param("lastName") String lastName, @Param("email") String email);
}
