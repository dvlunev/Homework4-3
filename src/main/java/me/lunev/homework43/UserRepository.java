package me.lunev.homework43;

import java.util.*;

public class UserRepository {

    private final List<User> users = new ArrayList<>();

    Collection<User> getAllUsers() {
        return users;
    }

    Optional<User> getUserByLogin(String login) {
        for (User user : users) {
            if (user.getLogin().equals(login)) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

    Optional<User> getUserByLoginAndPassword(String login, String password) {
        for (User user : users) {
            if (user.getLogin().equals(login) && user.getPassword().equals(password)) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

   public User addUser(User user) {
        users.add(user);
        return user;
    }
}
