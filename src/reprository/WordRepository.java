package reprository;

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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class WordRepository {

    private final Map<UUID, Word> words = new HashMap<>();
    private final String filePath;
    private final boolean useCache;

    // Конструктор з параметрами для ініціалізації шляху до файлу та кешування
    public WordRepository(String filePath, boolean useCache) {
        this.filePath = filePath;
        this.useCache = useCache;
        loadFromFile();
    }

    // Конструктор без параметрів, встановлюємо значення за умовчанням
    public WordRepository() {
        this.filePath = "words.json"; // значення за умовчанням
        this.useCache = true; // значення за умовчанням
        loadFromFile();
    }

    // Додаємо нове слово до репозиторію
    public void add(Word word) {
        words.put(word.getId(), word);
        commit();
    }

    // Отримуємо слово за ID
    public Word getById(UUID id) {
        return words.get(id);
    }

    // Отримуємо всі слова
    public List<Word> getAll() {
        return new ArrayList<>(words.values());
    }

    // Оновлюємо існуюче слово
    public void update(Word word) {
        words.put(word.getId(), word);
        commit();
    }

    // Видаляємо слово за ID
    public boolean delete(UUID id) {
        boolean result = words.remove(id) != null;
        commit();
        return result;
    }

    // Видаляємо всі слова
    public void deleteAll() {
        words.clear();
        commit();
    }

    // Зберігаємо зміни до файлу, якщо використовується кешування
    public void commit() {
        if (useCache) {
            saveToFile();
        }
    }

    // Зберігаємо дані в файл
    private void saveToFile() {
        if (filePath == null || filePath.isEmpty()) {
            System.err.println("File path is not specified.");
            return;
        }
        Gson gson = new Gson();
        try (Writer writer = new FileWriter(filePath)) {
            gson.toJson(words.values(), writer);
        } catch (IOException e) {
            System.err.println("Error saving words to file: " + e.getMessage());
        }
    }

    // Завантажуємо дані з файлу
    private void loadFromFile() {
        if (filePath == null || filePath.isEmpty()) {
            System.err.println("File path is not specified.");
            return;
        }
        Gson gson = new Gson();
        try (Reader reader = new FileReader(filePath)) {
            Type listType = new TypeToken<List<Word>>() {
            }.getType();
            List<Word> loadedWords = gson.fromJson(reader, listType);
            if (loadedWords != null) {
                loadedWords.forEach(word -> words.put(word.getId(), word));
            }
        } catch (IOException e) {
            System.err.println("Error loading words from file: " + e.getMessage());
        }
    }
}
