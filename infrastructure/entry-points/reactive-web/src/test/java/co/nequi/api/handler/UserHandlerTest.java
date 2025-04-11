package co.nequi.api.handler;

import co.nequi.model.user.User;
import co.nequi.usecase.user.UserUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserHandlerTest {

    @InjectMocks
    private UserHandler userHandler;

    @Mock
    private UserUseCase userUseCase;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setFirstName("Esteban");
        user.setLastName("Gutierrez");
        user.setEmail("esteban@mail.com");
    }

    @Test
    void createUser() {
        ServerRequest request = mock(ServerRequest.class);
        when(request.queryParam("id")).thenReturn(java.util.Optional.of("1"));
        when(userUseCase.saveUser(any(Long.class))).thenReturn(Mono.just(user));

        Mono<ServerResponse> response = userHandler.createUser(request);

        assertNotNull(response);
        verify(userUseCase, times(1)).saveUser(1L);
    }

    @Test
    void getUser() {
        ServerRequest request = mock(ServerRequest.class);
        when(request.pathVariable("id")).thenReturn("1");
        when(userUseCase.getUserById(any(Long.class))).thenReturn(Mono.just(user));

        Mono<ServerResponse> response = userHandler.getUser(request);

        assertNotNull(response);
        verify(userUseCase, times(1)).getUserById(1L);
    }

    @Test
    void getAllUsers() {
        ServerRequest request = mock(ServerRequest.class);
        when(userUseCase.getAllUsers()).thenReturn(Flux.fromIterable(Collections.singletonList(user)));

        Mono<ServerResponse> response = userHandler.getAllUsers(request);

        assertNotNull(response);
        verify(userUseCase, times(1)).getAllUsers();
    }

    @Test
    void getUserByName() {
        ServerRequest request = mock(ServerRequest.class);
        when(request.queryParam("name")).thenReturn(java.util.Optional.of("Esteban"));
        when(userUseCase.getUsersByName(any(String.class))).thenReturn(Flux.fromIterable(List.of(user)));

        Mono<ServerResponse> response = userHandler.getUserByName(request);

        assertNotNull(response);
        verify(userUseCase, times(1)).getUsersByName("Esteban");
    }
}