package com.thetechmaddy.todolistapp.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.thetechmaddy.todolistapp.models.Todo;

import java.util.List;

@Dao
public interface TodoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Todo todo);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Todo> todos);

    @Query(value = "DELETE FROM todos WHERE id = :id")
    void deleteById(Long id);

    @Query(value = "DELETE FROM todos")
    void deleteAll();

    @Query(value = "SELECT * FROM todos ORDER BY timestamp ASC")
    LiveData<List<Todo>> findAllOrderByTimestamp();

}
