package com.thetechmaddy.todolistapp.db.repo;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.thetechmaddy.todolistapp.db.CrudOp;
import com.thetechmaddy.todolistapp.db.TodoRoomDatabase;
import com.thetechmaddy.todolistapp.db.dao.TodoDao;
import com.thetechmaddy.todolistapp.models.Todo;
import com.thetechmaddy.todolistapp.utils.CollectionUtils;

import java.util.List;

public class TodoRepository implements CrudOp<Todo> {

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

    @Override
    public void save(@NonNull Todo todo) {
        TodoRoomDatabase.DATABASE_WRITE_EXECUTOR.execute(() -> todoDao.insert(todo));
    }

    @Override
    public void saveAll(List<Todo> todos) {
        TodoRoomDatabase.DATABASE_WRITE_EXECUTOR.execute(() -> todoDao.insertAll(todos));
    }

    @Override
    public void saveAllBatched(List<Todo> todos, int batchSize) {
        List<List<Todo>> lists = CollectionUtils.split(todos, batchSize);
        for (List<Todo> list : lists) {
            TodoRoomDatabase.DATABASE_WRITE_EXECUTOR.execute(() -> todoDao.insertAll(list));
        }
    }

    @Override
    public void delete(@NonNull Todo todo) {
        TodoRoomDatabase.DATABASE_WRITE_EXECUTOR.execute(() -> todoDao.deleteById(todo.getId()));
    }

}

