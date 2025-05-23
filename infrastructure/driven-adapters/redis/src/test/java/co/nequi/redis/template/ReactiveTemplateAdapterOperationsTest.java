package co.nequi.redis.template;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.when;

class ReactiveRedisTemplateAdapterOperationsTest {

    @Mock
    private ReactiveRedisConnectionFactory connectionFactory;

    @Mock
    private ObjectMapper objectMapper;

    private ReactiveRedisTemplateAdapter adapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        when(objectMapper.map("value", Object.class)).thenReturn("value");

        adapter = new ReactiveRedisTemplateAdapter(connectionFactory, objectMapper);
    }

    @Test
    void testSave() {
//        StepVerifier.create(adapter.save("key", "value"))
//                .expectNext("value")
//                .verifyComplete();
    }

    @Test
    void testSaveWithExpiration() {

//        StepVerifier.create(adapter.save("key", "value", 2))
//                .expectNext("value")
//                .verifyComplete();
    }

    @Test
    void testFindById() {

//        StepVerifier.create(adapter.findById("key"))
//                .verifyComplete();
    }

}