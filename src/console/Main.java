package console;

import com.google.gson.Gson;
import dto.UserLoginDto;
import dto.UserRegisterDto;
import entity.User;
import entity.Word;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import reprository.UserRepository;
import service.EmailService;
import service.GameService;
import service.UserService;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Gson gson = new Gson();

        // Ініціалізація сервісів
        UserRepository userRepository = new UserRepository();
        EmailService emailService = new EmailService();
        UserService userService = new UserService(userRepository, emailService);
        GameService gameService = new GameService();

        // Вибір: реєстрація чи вхід
        System.out.println("1 - Реєстрація, 2 - Вхід");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Пропускаємо символ нового рядка

        User user = null;
        if (choice == 1) {
            System.out.println("Введіть ім'я:");
            String name = scanner.nextLine();

            System.out.println("Введіть email:");
            String email = scanner.nextLine();

            System.out.println("Введіть пароль:");
            String password = scanner.nextLine();

            user = userService.register(new UserRegisterDto(name, email, password));
            System.out.println("Реєстрація успішна: " + user);
        } else if (choice == 2) {
            System.out.println("Введіть email:");
            String email = scanner.nextLine();

            System.out.println("Введіть пароль:");
            String password = scanner.nextLine();

            user = userService.login(new UserLoginDto(email, password));
            System.out.println("Вхід успішний: " + user);
        } else {
            System.out.println("Невірний вибір.");
            return;
        }

        // Створення слова та гри
        Word word = gameService.createWord();
        var game = gameService.createGame(user, word);

        // Збереження гри у файл
        try (FileWriter writer = new FileWriter("game_data.json")) {
            gson.toJson(game, writer);
            System.out.println("Дані збережено у файл game_data.json");
        } catch (IOException e) {
            System.err.println("Помилка збереження даних: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
