package entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;


public class Game {

    private final UUID id;
    private final Date startTime;
    private final User player;
    private final Word word;
    private final List<Hint> hints;
    private final List<PlayerAttempt> attempts;
    private Date endTime;
    private boolean isActive;

    public Game(User player, Word word) {
        this.id = UUID.randomUUID();
        this.startTime = new Date();
        this.isActive = true;
        this.player = player;
        this.word = word;
        this.hints = new ArrayList<>();
        this.attempts = new ArrayList<>();
    }

    public UUID getId() {
        return id;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public boolean isActive() {
        return isActive;
    }

    public void endGame() {
        this.isActive = false;
        this.endTime = new Date();
    }

    public User getPlayer() {
        return player;
    }

    public Word getWord() {
        return word;
    }

    public List<Hint> getHints() {
        return hints;
    }

    public void addHint(Hint hint) {
        hints.add(hint);
    }

    public List<PlayerAttempt> getAttempts() {
        return attempts;
    }

    public void addAttempt(PlayerAttempt attempt) {
        attempts.add(attempt);
    }
}
