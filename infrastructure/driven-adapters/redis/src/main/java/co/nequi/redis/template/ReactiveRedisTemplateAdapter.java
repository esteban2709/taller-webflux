package co.nequi.redis.template;

import co.nequi.model.user.User;
import co.nequi.model.user.gateways.IUserCacheGateway;
import co.nequi.model.user.gateways.IUserRepositoryGateway;
import co.nequi.redis.template.helper.ReactiveTemplateAdapterOperations;
import lombok.extern.slf4j.Slf4j;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Component
@Slf4j
public class ReactiveRedisTemplateAdapter extends ReactiveTemplateAdapterOperations<User/* change for domain model */
        , String, User/* change for adapter model */>
 implements IUserCacheGateway
{
    public ReactiveRedisTemplateAdapter(ReactiveRedisConnectionFactory connectionFactory, ObjectMapper mapper) {
        /**
         *  Could be use mapper.mapBuilder if your domain model implement builder pattern
         *  super(repository, mapper, d -> mapper.mapBuilder(d,ObjectModel.ObjectModelBuilder.class).build());
         *  Or using mapper.map with the class of the object model
         */
        super(connectionFactory, mapper, d -> mapper.map(d, User.class/* change for domain model */));
    }

    @Override
    public Mono<User> getUserById(Long id) {
        return this.findById(geyKey(id));
    }

    @Override
    public Mono<User> saveUser(User user) {
        return this.save(geyKey(user.getId()), user);
    }
    public static String geyKey(Long id) {
        return "user:" + id;
    }
}
