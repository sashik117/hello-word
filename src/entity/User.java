package entity;

import java.util.UUID;

public class User {

    private final UUID id;
    private final String name;
    private int score;
    private int attempts;

    public User(String name) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.score = 0;
        this.attempts = 0;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public void incrementScore() {
        this.score++;
    }

    public int getAttempts() {
        return attempts;
    }

    public void incrementAttempts() {
        this.attempts++;
    }

    @Override
    public String toString() {
        return "User{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", score=" + score +
            ", attempts=" + attempts +
            '}';
    }
}