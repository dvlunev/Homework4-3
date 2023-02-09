package me.lunev.homework43;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserRepositoryTest {

    private UserRepository userRepository;

    private User user1;
    private User user2;
    private User user3;

    @BeforeEach
    public void setUp() {
        user1 = new User("ivanov", "ivanov124564");
        user2 = new User("petrov", "petrov124564");
        user3 = new User("sidorov", "sidorov124564");

        userRepository = new UserRepository();
    }

    @Test
    public void getEmptyUsersList() {
        Collection<User> expected = userRepository.getAllUsers();
        Collection<User> actual = new ArrayList<User>();
        assertEquals(expected, actual);
    }

    @Test
    public void getUsersList() {
        Collection<User> expected = userRepository.getAllUsers();
        Collection<User> actual = new ArrayList<User>();

        userRepository.addUser(user1);
        userRepository.addUser(user2);
        userRepository.addUser(user3);

        actual.add(user1);
        actual.add(user2);
        actual.add(user3);

        assertEquals(expected, actual);
    }

    @Test
    public void getUserByLogin() {
        userRepository.addUser(user1);
        Optional<User> expected = userRepository.getUserByLogin("ivanov");
        Optional<User> actual = Optional.of(user1);

        assertEquals(expected, actual);
    }

    @Test
    public void getNoUserByLogin() {
        Optional<User> expected = userRepository.getUserByLogin("ivanov");
        Optional<User> actual = Optional.empty();

        assertEquals(expected, actual);
    }

    @Test
    public void getUserByLoginAndPassword() {
        userRepository.addUser(user1);
        Optional<User> expected = userRepository.getUserByLoginAndPassword("ivanov", "ivanov124564");
        Optional<User> actual = Optional.of(user1);

        assertEquals(expected, actual);
    }

    @Test
    public void getUserByLoginAndWrongPassword() {
        userRepository.addUser(user1);
        Optional<User> expected = userRepository.getUserByLoginAndPassword("ivanov", "wrongPassword");
        Optional<User> actual = Optional.empty();

        assertEquals(expected, actual);
    }

    @Test
    public void getUserByWrongLoginAndPassword() {
        userRepository.addUser(user1);
        Optional<User> expected = userRepository.getUserByLoginAndPassword("wrongLogin", "ivanov124564");
        Optional<User> actual = Optional.empty();

        assertEquals(expected, actual);
    }
}
