package me.lunev.homework43;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.verification.NoInteractions;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService userService;

    @Test
    void whenCreateNewUserWithValidParameters() {
        when(userRepository.getAllUsers()).thenReturn(List.of());
        when(userRepository.addUser(any(User.class))).thenReturn(null);
        userService.createNewUser("ivanov", "ivanov124564");
        verify(userRepository).addUser(any());
    }

    @Test
    void whenCreateNewUserWithInvalidParameters() {
        assertThatThrownBy(()->userService.createNewUser(""," "))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Логин и пароль должны быть заполнены");
        verify(userRepository, new NoInteractions()).getAllUsers();
        verify(userRepository, new NoInteractions()).addUser(any());
    }

    @Test
    void whenCreateNewUserAlreadyExist() {
        when(userRepository.getAllUsers()).thenReturn(List.of(new User("ivanov", "ivanov124564")));
        assertThatThrownBy(()->userService.createNewUser("ivanov", "ivanov124564"))
                .isInstanceOf(UserNonUniqueException.class)
                .hasMessage("Пользователь с таким логином уже существует");
        verify(userRepository).getAllUsers();
    }

    @Test
    void whenGetAllUsersLoginsContains() {
        when(userRepository.getAllUsers()).thenReturn(List.of(
                new User("ivanov", "ivanov124564"),
                new User("petrov", "petrov124564")
        ));
        assertThat(userService.getAllUsersLogins()).isEqualTo(List.of(
                "ivanov",
                "petrov"
        ));
    }

    @Test
    void whenGetAllUsersLoginsNotContains() {
        when(userRepository.getAllUsers()).thenReturn(List.of());
        assertThat(userService.getAllUsersLogins()).isEqualTo(List.of());
    }

    @Test
    void whenCheckUserByLoginIsTrue() {
        when(userRepository.getUserByLogin("ivanov")).thenReturn(Optional.of(new User("ivanov", "ivanov124564")));
        assertThat(userService.checkUserByLogin("ivanov")).isTrue();
    }

    @Test
    void whenCheckUserByLoginIsFalse() {
        when(userRepository.getUserByLogin("ivanov")).thenReturn(Optional.empty());
        assertThat(userService.checkUserByLogin("ivanov")).isFalse();
    }

    @Test
    void whenCheckUserByLoginAndPasswordIsTrue() {
        when(userRepository.getUserByLoginAndPassword("ivanov", "ivanov124564")).thenReturn(Optional.of(new User("ivanov", "ivanov124564")));
        assertThat(userService.checkUserByLoginAndPassword("ivanov", "ivanov124564")).isTrue();
    }

    @Test
    void whenCheckUserByLoginAndPasswordIsFalse() {
        when(userRepository.getUserByLoginAndPassword("ivanov", "ivanov124564")).thenReturn(Optional.empty());
        assertThat(userService.checkUserByLoginAndPassword("ivanov", "ivanov124564")).isFalse();
    }
}
