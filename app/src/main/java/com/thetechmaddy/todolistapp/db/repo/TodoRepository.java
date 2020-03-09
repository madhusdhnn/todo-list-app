package com.thetechmaddy.todolistapp.db.repo;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.thetechmaddy.todolistapp.db.TodoRoomDatabase;
import com.thetechmaddy.todolistapp.db.dao.TodoDao;
import com.thetechmaddy.todolistapp.models.Todo;

import java.util.List;

public class TodoRepository {

    private TodoDao todoDao;
    private LiveData<List<Todo>> allTodos;

    public TodoRepository(Application application) {
        TodoRoomDatabase db = TodoRoomDatabase.getDatabase(application);
        this.todoDao = db.todoDao();
        this.allTodos = todoDao.findAllOrderByTimestamp();
    }

    public LiveData<List<Todo>> getAllTodos() {
        return allTodos;
    }

    public void insert(Todo todo) {
        TodoRoomDatabase.DATABASE_WRITE_EXECUTOR.execute(() -> todoDao.insert(todo));
    }

    public void deleteById(Long id) {
        TodoRoomDatabase.DATABASE_WRITE_EXECUTOR.execute(() -> todoDao.deleteById(id));
    }

}

