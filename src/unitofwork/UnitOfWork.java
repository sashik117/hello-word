package unitofwork;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import entity.Word;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.List;
import reprository.WordRepository;

public class UnitOfWork {

    private final WordRepository wordRepository;
    private final String filePath;

    // Конструктор з параметрами для ініціалізації wordRepository та filePath
    public UnitOfWork(WordRepository wordRepository, String filePath) {
        if (wordRepository == null || filePath == null || filePath.isEmpty()) {
            throw new IllegalArgumentException(
                "WordRepository and filePath cannot be null or empty");
        }
        this.wordRepository = wordRepository;
        this.filePath = filePath;
    }

    // Конструктор без параметрів, потрібно ініціалізувати змінні
    public UnitOfWork() {
        this.wordRepository = new WordRepository("words.json", true); // ініціалізація за умовчанням
        this.filePath = "words.json"; // ініціалізація за умовчанням
    }

    // Метод для збереження змін у файл
    public void commit() {
        Gson gson = new Gson();
        try (Writer writer = new FileWriter(filePath)) {
            gson.toJson(wordRepository.getAll(), writer);
            System.out.println("Changes saved successfully.");
        } catch (IOException e) {
            System.err.println("Error saving changes: " + e.getMessage());
        }
    }

    // Збереження даних у файл
    public void saveToFile(String filePath) {
        try (FileWriter writer = new FileWriter(this.filePath)) {
            Gson gson = new Gson();
            List<Word> words = wordRepository.getAll();
            gson.toJson(words, writer);
            System.out.println("Words saved to file.");
        } catch (IOException e) {
            System.err.println("Error saving to file: " + e.getMessage());
        }
    }

    // Завантаження даних з файлу
    public void loadFromFile(String filePath) {
        File file = new File(filePath);
        // Перевіряємо, чи існує файл, якщо ні — створюємо його
        if (!file.exists()) {
            System.err.println("File not found. Creating a new file.");
            try {
                file.createNewFile(); // Створюємо новий файл, якщо його немає
            } catch (IOException e) {
                System.err.println("Error creating the file: " + e.getMessage());
                return; // Якщо файл не вдається створити, припиняємо виконання методу
            }
        }

        // Завантажуємо дані з файлу
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            Gson gson = new Gson();
            Type listType = new TypeToken<List<Word>>() {
            }.getType();
            List<Word> words = gson.fromJson(reader, listType);
            if (words != null) {
                words.forEach(wordRepository::add);
            }
            System.out.println("Words loaded from file.");
        } catch (IOException e) {
            System.err.println("Error loading from file: " + e.getMessage());
        }
    }

    // Викликаємо метод завантаження з файлу
    public void load() {
        loadFromFile(filePath);
    }
}
