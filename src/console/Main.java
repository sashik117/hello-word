package console;

import com.google.gson.Gson;
import entity.User;
import entity.Word;
import java.io.FileWriter;
import java.io.IOException;
import service.GameService;

public class Main {

    public static void main(String[] args) {
        GameService gameService = new GameService();
        Gson gson = new Gson();

        // Створення користувача та слова
        User user = gameService.createUser();
        Word word = gameService.createWord();

        // Створення гри
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
