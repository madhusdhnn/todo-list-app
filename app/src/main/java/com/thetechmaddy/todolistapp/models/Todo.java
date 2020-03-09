package com.thetechmaddy.todolistapp.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.OffsetDateTime;
import java.util.Objects;

@Entity(tableName = "todos")
public class Todo {

    @PrimaryKey(autoGenerate = true)
    private Long id;
    private String text;
    private OffsetDateTime timestamp;

    public Todo(String text, OffsetDateTime timestamp) {
        this.text = text;
        this.timestamp = timestamp;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public OffsetDateTime getTimestamp() {
        return timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Todo todo = (Todo) o;
        return text.equals(todo.text) &&
                timestamp.equals(todo.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text, timestamp);
    }

    @NonNull
    @Override
    public String toString() {
        return "Todo{" +
                "text='" + text + '\'' +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }

}
