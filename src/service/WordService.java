package service;

import com.github.javafaker.Faker;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import entity.Word;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class WordService {

    private final Faker faker = new Faker(); // Для генерації випадкових слів
    private List<Word> words = new ArrayList<>(); // Список слів

    public String getWordById(UUID id) {
        // Заглушка: повертає тестове слово
        return "ExampleWord";
    }

    // Створення нового слова
    public Word createWord(String text, String category, int complexity) {
        UUID id = UUID.randomUUID(); // Генерація нового UUID
        Word word = new Word(id, text, category, complexity); // Створення об'єкта слова
        words.add(word); // Додавання слова до списку
        return word;
    }

    // Отримання всіх слів
    public List<Word> getAllWords() {
        return words;
    }

    // Оновлення слова
    public Word updateWord(UUID id, String text, String category, int complexity) {
        Word word = words.stream().filter(w -> w.getId().equals(id)).findFirst().orElse(null);
        if (word != null) {
            word.setText(text);
            word.setCategory(category);
            word.setComplexity(complexity);
        }
        return word;
    }

    // Видалення слова
    public boolean deleteWord(String identifier) {
        return words.removeIf(word -> word.getId().toString().equals(identifier) || word.getText()
            .equals(identifier));
    }

    // Видалення всіх слів:
    public void deleteAllWords() {
        words.clear(); // Видаляє всі слова зі списку
    }


    // Генерація випадкових слів
    public List<Word> generateRandomWords(int count) {
        List<Word> randomWords = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            String text = faker.lorem().word();
            String category = faker.book().genre();
            int complexity = faker.number().numberBetween(1, 6); // складність від 1 до 5
            randomWords.add(createWord(text, category, complexity)); // Додавання до списку
        }
        return randomWords;
    }

    // Збереження слів у JSON файл
    public void saveWordsToFile(String filePath) {
        Gson gson = new Gson();
        try (Writer writer = new FileWriter(filePath)) {
            gson.toJson(words, writer); // Записуємо список слів у JSON форматі
            System.out.println("Words saved to file successfully.");
        } catch (IOException e) {
            System.err.println("Error saving words to file: " + e.getMessage());
        }
    }

    // Завантаження слів з JSON файлу
    public void loadWordsFromFile(String filePath) {
        Gson gson = new Gson();
        try (Reader reader = new FileReader(filePath)) {
            Type listType = new TypeToken<List<Word>>() {
            }.getType();
            words = gson.fromJson(reader, listType);

            // Якщо файл порожній або дані не були завантажені, ініціалізуємо список
            if (words == null) {
                words = new ArrayList<>();
            }
            System.out.println("Words loaded from file successfully.");
        } catch (IOException e) {
            System.err.println("Error loading words from file: " + e.getMessage());
        }
    }
}
