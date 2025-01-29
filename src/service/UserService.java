package service;

import entity.User;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserService {

    private final List<User> users = new ArrayList<>();

    // Метод створення користувача
    public User createUser(String name) {
        User user = new User(name);
        users.add(user);
        return user;
    }

    // Метод отримання всіх користувачів
    public List<User> getAllUsers() {
        return new ArrayList<>(users);
    }

    // Метод отримання користувача за ID
    public User getUserById(UUID id) {
        return users.stream()
            .filter(user -> user.getId().equals(id))
            .findFirst()
            .orElse(null);
    }

    // Метод оновлення користувача
    public User updateUser(UUID id, String newName) {
        User userToUpdate = getUserById(id);

        if (userToUpdate != null) {
            users.remove(userToUpdate);
            User updatedUser = new User(newName);
            updatedUser.incrementAttempts();
            updatedUser.incrementScore();
            users.add(updatedUser);
            return updatedUser;
        }

        return null;
    }

    // Метод видалення користувача
    public boolean deleteUser(UUID id) {
        User userToDelete = getUserById(id);
        if (userToDelete != null) {
            users.remove(userToDelete);
            return true;
        }
        return false;
    }
}
