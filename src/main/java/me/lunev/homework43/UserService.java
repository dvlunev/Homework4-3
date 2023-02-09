package me.lunev.homework43;

import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void createNewUser(String login, String password) {
        User user = new User(login, password);

        if (StringUtils.isBlank(login) || StringUtils.isBlank(password)) {
            throw new IllegalArgumentException("Логин и пароль должны быть заполнены");
        }

        boolean userExist = this.userRepository
                .getAllUsers()
                .stream()
                .anyMatch(u -> u.equals(user));
        if (userExist) {
            throw new UserNonUniqueException("Пользователь с таким логином уже существует");
        }

        this.userRepository.addUser(user);
    }

    public List<String> getAllUsersLogins() {
        return userRepository.getAllUsers().stream()
                .map(User::getLogin)
                .collect(Collectors.toList());
    }

    public boolean checkUserByLogin(String login) {
        return userRepository.getUserByLogin(login).isPresent();
    }

    public boolean checkUserByLoginAndPassword(String login, String password) {
        return userRepository.getUserByLoginAndPassword(login, password).isPresent();
    }
}
