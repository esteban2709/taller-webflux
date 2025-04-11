package co.nequi.r2dbc.config;

import co.nequi.r2dbc.adapter.UserPersistenceAdapter;
import co.nequi.r2dbc.mapper.IUserEntityMapper;
import co.nequi.r2dbc.repository.IUserRepository;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestConfig {

    // Define beans necesarios para la prueba
    @Bean
    public UserPersistenceAdapter userPersistenceAdapter(IUserRepository userRepository, IUserEntityMapper userEntityMapper) {
        return new UserPersistenceAdapter(userRepository, userEntityMapper);
    }
}
