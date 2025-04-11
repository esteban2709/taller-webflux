package co.nequi.config;

import co.nequi.consumer.UserRestConsumer;
import co.nequi.consumer.mapper.IUserResponseMapper;
import co.nequi.model.user.gateways.IMessageGateway;
import co.nequi.model.user.gateways.IUserClientGateway;
import co.nequi.model.user.gateways.IUserRepositoryGateway;
import co.nequi.r2dbc.adapter.UserPersistenceAdapter;
import co.nequi.r2dbc.mapper.IUserEntityMapper;
import co.nequi.r2dbc.repository.IUserRepository;
import co.nequi.redis.template.ReactiveRedisTemplateAdapter;
import co.nequi.sqs.sender.SQSSender;
import co.nequi.usecase.user.MessageUseCase;
import co.nequi.usecase.user.UserUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {

    private final IUserRepository userRepository;
    private final IUserEntityMapper userEntityMapper;
    private final IUserResponseMapper userResponseMapper;
    private final WebClient webClient;
    private final ReactiveRedisTemplateAdapter reactiveRedisTemplateAdapter;
    private final SQSSender sqsSender;


    @Bean
    public IUserRepositoryGateway userRepositoryGateway() {
        return new UserPersistenceAdapter(userRepository, userEntityMapper);
    }

    @Bean
    public IUserClientGateway userClientGateway() {
        return new UserRestConsumer(webClient, userResponseMapper);
    }

    @Bean
    public UserUseCase userUseCase(IUserClientGateway userClientGateway, IUserRepositoryGateway userRepositoryGateway) {
        return new UserUseCase( userRepositoryGateway, userClientGateway, reactiveRedisTemplateAdapter, sqsSender);
    }

    @Bean
    public MessageUseCase messageUseCase(IMessageGateway messageGateway) {
        return new MessageUseCase(messageGateway);
    }
}
