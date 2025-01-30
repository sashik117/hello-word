package console;

import entity.Word;
import java.util.Scanner;
import java.util.UUID;
import reprository.WordRepository;
import service.WordService;
import unitofwork.UnitOfWork;

public class WordConsoleApp {

    private static final WordService wordService = new WordService(new WordRepository(),
        new UnitOfWork());
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean running = true;

        while (running) {
            System.out.println("\n=== Word Management ===");
            System.out.println("1. Create Word");
            System.out.println("2. Read All Words");
            System.out.println("3. Update Word");
            System.out.println("4. Delete Word");
            System.out.println("5. Generate Random Words");
            System.out.println("6. Save Words to File");
            System.out.println("7. Load Words from File");
            System.out.println("8. Delete All Words");
            System.out.println("9. Exit");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    createWord();
                    break;
                case "2":
                    readWords();
                    break;
                case "3":
                    updateWord();
                    break;
                case "4":
                    deleteWord();
                    break;
                case "5":
                    generateRandomWords();
                    break;
                case "6":
                    saveWordsToFile();
                    break;
                case "7":
                    loadWordsFromFile();
                    break;
                case "8":
                    deleteAllWords();
                    break;
                case "9":
                    running = false;
                    System.out.println("Exiting Word Management.");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void generateRandomWords() {
        System.out.print("Enter the number of random words to generate: ");
        int count = Integer.parseInt(scanner.nextLine());
        wordService.generateRandomWords(count);
        System.out.println(count + " random words generated.");
    }

    private static void saveWordsToFile() {
        System.out.print("Enter file path to save words: ");
        String filePath = scanner.nextLine();
        wordService.saveWordsToFile(filePath);
        System.out.println("Words saved to " + filePath);
    }

    private static void loadWordsFromFile() {
        System.out.print("Enter file path to load words from: ");
        String filePath = scanner.nextLine();
        wordService.loadWordsFromFile(filePath);
        System.out.println("Words loaded from " + filePath);
    }

    private static void createWord() {
        System.out.print("Enter word text: ");
        String text = scanner.nextLine();
        System.out.print("Enter category: ");
        String category = scanner.nextLine();
        System.out.print("Enter complexity (1-5): ");
        int complexity = Integer.parseInt(scanner.nextLine());

        Word word = wordService.createWord(text, category, complexity);
        System.out.println("Word created: " + word);
    }

    private static void readWords() {
        System.out.println("\n=== All Words ===");
        for (Word word : wordService.getAllWords()) {
            System.out.println(word);
        }
    }

    private static void updateWord() {
        System.out.print("Enter Word ID to update: ");
        String idInput = scanner.nextLine();

        UUID id;
        try {
            id = UUID.fromString(idInput);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid ID format.");
            return;
        }

        Word existingWord = wordService.getAllWords().stream()
            .filter(word -> word.getId().equals(id))
            .findFirst()
            .orElse(null);

        if (existingWord == null) {
            System.out.println("Word not found.");
            return;
        }

        System.out.println("Current Word: " + existingWord);
        System.out.print("Enter new text (or leave blank to keep current): ");
        String newText = scanner.nextLine();
        System.out.print("Enter new category (or leave blank to keep current): ");
        String newCategory = scanner.nextLine();
        System.out.print("Enter new complexity (or leave blank to keep current): ");
        String complexityInput = scanner.nextLine();

        int newComplexity = complexityInput.isEmpty() ? existingWord.getComplexity()
            : Integer.parseInt(complexityInput);
        Word updatedWord = wordService.updateWord(
            id,
            newText.isEmpty() ? existingWord.getText() : newText,
            newCategory.isEmpty() ? existingWord.getCategory() : newCategory,
            newComplexity
        );

        System.out.println("Word updated: " + updatedWord);
    }

    private static void deleteWord() {
        System.out.print("Enter Word ID to delete: ");
        String idInput = scanner.nextLine();

        UUID id;
        try {
            id = UUID.fromString(idInput);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid ID format.");
            return;
        }

        boolean isDeleted = wordService.deleteWord(id);
        if (isDeleted) {
            System.out.println("Word deleted.");
        } else {
            System.out.println("Word not found.");
        }
    }

    private static void deleteAllWords() {
        wordService.deleteAllWords();
        System.out.println("All words deleted.");
    }
}
