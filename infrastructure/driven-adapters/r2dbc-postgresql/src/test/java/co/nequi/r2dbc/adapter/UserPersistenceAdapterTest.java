package co.nequi.r2dbc.adapter;

import co.nequi.model.user.User;
import co.nequi.r2dbc.entity.UserEntity;
import co.nequi.r2dbc.mapper.IUserEntityMapper;
import co.nequi.r2dbc.repository.IUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserPersistenceAdapterTest {

    @Mock
    private IUserRepository userRepository;

    @Mock
    private IUserEntityMapper userEntityMapper;

    @InjectMocks
    private UserPersistenceAdapter userPersistenceAdapter;

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
    void getUserById() {
        // Arrange
        when(userRepository.findById(any(Long.class))).thenReturn(Mono.just(mock(UserEntity.class)));
        when(userEntityMapper.toUser(any())).thenReturn(user);

        // Act
        Mono<User> result = userPersistenceAdapter.getUserById(1L);

        // Assert
        assertNotNull(result);
        User retrievedUser = result.block();
        assertNotNull(retrievedUser);
        assertEquals(user.getId(), retrievedUser.getId());
        verify(userRepository, times(1)).findById(1L);
        verify(userEntityMapper, times(1)).toUser(any());
    }

    @Test
    void saveUser() {
        // Arrange
        when(userRepository.insertUser(any(Long.class), anyString(), anyString(), anyString()))
                .thenReturn(Mono.just(mock(UserEntity.class)));
        when(userEntityMapper.toUser(any())).thenReturn(user);

        // Act
        Mono<User> result = userPersistenceAdapter.saveUser(user);

        // Assert
        assertNotNull(result);
        User savedUser = result.block();
        assertNotNull(savedUser);
        assertEquals(user.getId(), savedUser.getId());
        verify(userRepository, times(1)).insertUser(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail());
        verify(userEntityMapper, times(1)).toUser(any());
    }

    @Test
    void getAllUsers() {
        // Arrange
        when(userRepository.findAll()).thenReturn(Flux.fromIterable(Collections.singletonList(mock(UserEntity.class))));
        when(userEntityMapper.toUser(any())).thenReturn(user);

        // Act
        Flux<User> result = userPersistenceAdapter.getAllUsers();

        // Assert
        assertNotNull(result);
        List<User> users = result.collectList().block();
        assertNotNull(users);
        assertEquals(1, users.size());
        assertEquals(user.getId(), users.get(0).getId());
        verify(userRepository, times(1)).findAll();
        verify(userEntityMapper, times(1)).toUser(any());
    }

    @Test
    void getUsersByName() {
        // Arrange
        when(userRepository.findAllByFirstName(anyString())).thenReturn(Flux.fromIterable(Collections.singletonList(mock(UserEntity.class))));
        when(userEntityMapper.toUser(any())).thenReturn(user);

        // Act
        Flux<User> result = userPersistenceAdapter.getUsersByName("Esteban");

        // Assert
        assertNotNull(result);
        List<User> users = result.collectList().block();
        assertNotNull(users);
        assertEquals(1, users.size());
        assertEquals(user.getFirstName(), users.get(0).getFirstName());
        verify(userRepository, times(1)).findAllByFirstName("Esteban");
        verify(userEntityMapper, times(1)).toUser(any());
    }
}