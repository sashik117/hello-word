package service;

import dto.UserLoginDto;
import dto.UserRegisterDto;
import entity.User;
import java.util.Optional;
import org.mindrot.jbcrypt.BCrypt;
import reprository.UserRepository;

public class UserService {

    private final UserRepository userRepository;
    private final EmailService emailService;

    public UserService(UserRepository userRepository, EmailService emailService) {
        this.userRepository = userRepository;
        this.emailService = emailService;
    }

    public User register(UserRegisterDto dto) {
        if (userRepository.existsByEmail(dto.email())) {
            throw new RuntimeException("Email вже використовується");
        }

        String hashedPassword = BCrypt.hashpw(dto.password(), BCrypt.gensalt());
        User user = new User(dto.name(), dto.email(), hashedPassword);
        userRepository.save(user);

        emailService.sendEmail(dto.email(), "Реєстрація",
            "Вітаємо, " + dto.name() + "! Ви успішно зареєстровані.");

        return user;
    }

    public User login(UserLoginDto dto) {
        Optional<User> userOpt = userRepository.findByEmail(dto.email());
        if (userOpt.isEmpty()) {
            throw new RuntimeException("Користувача не знайдено");
        }

        User user = userOpt.get();
        if (!BCrypt.checkpw(dto.password(), user.getPassword())) {
            throw new RuntimeException("Неправильний пароль");
        }

        return user;
    }
}
