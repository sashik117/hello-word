package service;

import com.github.javafaker.Faker;
import entity.Word;
import java.util.List;
import java.util.UUID;
import reprository.WordRepository;
import unitofwork.UnitOfWork;

public class WordService {

    private final WordRepository wordRepository;
    private final UnitOfWork unitOfWork;
    private final Faker faker = new Faker();

    public WordService(WordRepository wordRepository, UnitOfWork unitOfWork) {
        this.wordRepository = wordRepository;
        this.unitOfWork = unitOfWork;
        unitOfWork.load(); // Завантаження даних при старті
    }

    public Word createWord(String text, String category, int complexity) {
        Word word = new Word(UUID.randomUUID(), text, category, complexity);
        wordRepository.add(word);
        unitOfWork.commit();
        return word;
    }

    public void saveWordsToFile(String filePath) {
        unitOfWork.saveToFile(filePath);
    }

    public void loadWordsFromFile(String filePath) {
        unitOfWork.loadFromFile(filePath);
    }


    public List<Word> getAllWords() {
        return wordRepository.getAll();
    }

    public Word updateWord(UUID id, String text, String category, int complexity) {
        Word word = wordRepository.getById(id);
        if (word != null) {
            word.setText(text);
            word.setCategory(category);
            word.setComplexity(complexity);
            wordRepository.update(word);
            unitOfWork.commit();
        }
        return word;
    }

    public boolean deleteWord(UUID id) {
        boolean result = wordRepository.delete(id);
        if (result) {
            unitOfWork.commit();
        }
        return result;
    }

    public void deleteAllWords() {
        wordRepository.deleteAll();
        unitOfWork.commit();
    }

    public void generateRandomWords(int count) {
        for (int i = 0; i < count; i++) {
            createWord(faker.lorem().word(), faker.book().genre(),
                faker.number().numberBetween(1, 6));
        }
        getAllWords();
    }
}
