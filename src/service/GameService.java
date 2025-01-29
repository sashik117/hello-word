package service;

import com.github.javafaker.Faker;
import com.google.gson.Gson;
import entity.Game;
import entity.Hint;
import entity.PlayerAttempt;
import entity.User;
import entity.Word;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class GameService {

    private final Faker faker;
    private final Gson gson;
    private final Map<UUID, Game> activeGames; // Додаємо мапу для збереження активних ігор

    public GameService() {
        this.faker = new Faker();
        this.gson = new Gson();
        this.activeGames = new HashMap<>(); // Ініціалізуємо мапу
    }

    public User createUser() {
        return new User(faker.name().fullName());
    }

    public Word createWord() {
        UUID id = UUID.randomUUID();
        String text = faker.lorem().word();
        String category = "Загальна";
        int complexity = faker.number().numberBetween(1, 6);
        return new Word(id, text, category, complexity);
    }

    public Hint createHint(String description, int level) {
        return new Hint(description, level);
    }

    public Game createGame(User user, Word word) {
        Game game = new Game(user, word);
        activeGames.put(game.getId(), game); // Додаємо гру в список активних
        return game;
    }

    public PlayerAttempt createPlayerAttempt(String guess, boolean isCorrect) {
        return new PlayerAttempt(guess, isCorrect);
    }

    public void saveGameToFile(Game game, String fileName) {
        try (FileWriter writer = new FileWriter(fileName)) {
            gson.toJson(game, writer);
            System.out.println("Дані збережено у файл " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Game> getAllGames() {
        return new ArrayList<>(activeGames.values());
    }

    public boolean endGame(UUID gameId) {
        if (activeGames.containsKey(gameId)) {
            activeGames.remove(gameId);
            System.out.println("Гра завершена: " + gameId);
            return true;
        } else {
            System.out.println("Гру не знайдено або вона вже завершена.");
            return false;
        }
    }
}
