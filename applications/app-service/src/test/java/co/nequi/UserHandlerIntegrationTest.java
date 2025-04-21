//package co.nequi;
//
//import co.nequi.model.user.User;
//import co.nequi.usecase.user.UserUseCase;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.web.reactive.server.WebTestClient;
//import reactor.core.publisher.Mono;
//
//import static org.mockito.ArgumentMatchers.anyLong;
//
//@SpringBootTest
//@AutoConfigureWebTestClient
//class UserHandlerIntegrationTest {
//
//    @Autowired
//    private WebTestClient webTestClient;
//
//    @MockBean
//    private UserUseCase userUseCase;
//
//    @Test
//    void shouldCreateUserSuccessfully() {
//        // Arrange
//        User mockUser = new User(1L, "Carlos", "Torres", "carlos@mail.com");
//        Mockito.when(userUseCase.saveUser(anyLong())).thenReturn(Mono.just(mockUser));
//
//        // Act & Assert
//        webTestClient.post()
//                .uri("/user?id=1") // asegúrate de que esta ruta esté bien mapeada
//                .exchange()
//                .expectStatus().isOk()
//                .expectBody()
//                .jsonPath("$.id").isEqualTo(1)
//                .jsonPath("$.firstName").isEqualTo("Carlos")
//                .jsonPath("$.email").isEqualTo("carlos@mail.com");
//    }
//}