package entity;

import java.util.UUID;

public class PlayerAttempt {

    private final UUID id;
    private final String attemptText;
    private final boolean isCorrect;

    public PlayerAttempt(String attemptText, boolean isCorrect) {
        this.id = UUID.randomUUID();
        this.attemptText = attemptText;
        this.isCorrect = isCorrect;
    }

    public UUID getId() {
        return id;
    }

    public String getAttemptText() {
        return attemptText;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    @Override
    public String toString() {
        return "PlayerAttempt{" +
            "id=" + id +
            ", attemptText='" + attemptText + '\'' +
            ", isCorrect=" + isCorrect +
            '}';
    }
}

