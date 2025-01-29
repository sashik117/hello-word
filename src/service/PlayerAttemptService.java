package service;

import entity.PlayerAttempt;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PlayerAttemptService {

    private final List<PlayerAttempt> attempts = new ArrayList<>();

    // Create
    public PlayerAttempt create(String attemptText, boolean isCorrect) {
        PlayerAttempt attempt = new PlayerAttempt(attemptText, isCorrect);
        attempts.add(attempt);
        return attempt;
    }

    // Read (Get by ID)
    public PlayerAttempt getById(UUID id) {
        return attempts.stream()
            .filter(attempt -> attempt.getId().equals(id))
            .findFirst()
            .orElse(null);
    }

    // Update
    public boolean update(UUID id, String newAttemptText, boolean newIsCorrect) {
        PlayerAttempt attempt = getById(id);
        if (attempt != null) {
            attempts.remove(attempt);
            attempts.add(new PlayerAttempt(newAttemptText, newIsCorrect));
            return true;
        }
        return false;
    }

    // Delete
    public boolean delete(UUID id) {
        return attempts.removeIf(attempt -> attempt.getId().equals(id));
    }

    // Search by text
    public List<PlayerAttempt> searchByText(String text) {
        return attempts.stream()
            .filter(attempt -> attempt.getAttemptText().toLowerCase().contains(text.toLowerCase()))
            .toList();
    }

    // Filter by correctness
    public List<PlayerAttempt> filterByCorrectness(boolean isCorrect) {
        return attempts.stream()
            .filter(attempt -> attempt.isCorrect() == isCorrect)
            .toList();
    }
}