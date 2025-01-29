package entity;

import java.util.UUID;

public class Hint {

    private final UUID id;
    private final String text;
    private final int level;

    public Hint(String text, int level) {
        this.id = UUID.randomUUID();
        this.text = text;
        this.level = level;
    }

    public UUID getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public int getLevel() {
        return level;
    }

    @Override
    public String toString() {
        return "Hint{" +
            "id=" + id +
            ", text='" + text + '\'' +
            ", level=" + level +
            '}';
    }
}
