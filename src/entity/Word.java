package entity;

import java.util.UUID;

public class Word {

    private UUID id;
    private String text;
    private String category;
    private int complexity;

    public Word(UUID id, String text, String category, int complexity) {
        this.id = id;
        this.text = text;
        this.category = category;
        this.complexity = complexity;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getComplexity() {
        return complexity;
    }

    public void setComplexity(int complexity) {
        this.complexity = complexity;
    }

    @Override
    public String toString() {
        return "Word{" +
            "id=" + id +
            ", text='" + text + '\'' +
            ", category='" + category + '\'' +
            ", complexity=" + complexity +
            '}';
    }
}
