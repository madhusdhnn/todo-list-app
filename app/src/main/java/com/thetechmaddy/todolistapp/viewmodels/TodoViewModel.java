package com.thetechmaddy.todolistapp.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.thetechmaddy.todolistapp.db.repo.TodoRepository;
import com.thetechmaddy.todolistapp.models.Todo;

import java.util.List;

public class TodoViewModel extends AndroidViewModel {

    private TodoRepository todoRepository;
    private LiveData<List<Todo>> allTodos;

    public TodoViewModel(@NonNull Application application) {
        super(application);
        this.todoRepository = new TodoRepository(application);
        this.allTodos = todoRepository.getAllTodos();
    }

    public LiveData<List<Todo>> getAllTodos() {
        return allTodos;
    }

    public void insert(Todo todo) {
        this.todoRepository.insert(todo);
    }

    public void deleteTodo(Long id) {
        this.todoRepository.deleteById(id);
    }

}
