package com.thetechmaddy.todolistapp.db;

import android.content.Context;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.thetechmaddy.todolistapp.converters.OffsetDateTimeConverter;
import com.thetechmaddy.todolistapp.db.dao.TodoDao;
import com.thetechmaddy.todolistapp.models.Todo;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Todo.class}, version = 1, exportSchema = false)
@TypeConverters({OffsetDateTimeConverter.class})
public abstract class TodoRoomDatabase extends RoomDatabase {

    public abstract TodoDao todoDao();

    private static volatile TodoRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService DATABASE_WRITE_EXECUTOR = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    private static RoomDatabase.Callback todoRoomDatabaseCallback = new RoomDatabase.Callback() {
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            DATABASE_WRITE_EXECUTOR.execute(() -> {
                TodoDao todoDao = INSTANCE.todoDao();
                todoDao.deleteAll();

                List<Todo> samples = new ArrayList<>();
                samples.add(new Todo("Learn Android", OffsetDateTime.now()));
                samples.add(new Todo("Learn Java", OffsetDateTime.now()));
                todoDao.insertAll(samples);
            });
        }
    };

    public static TodoRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (TodoRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), TodoRoomDatabase.class, "todo_database")
                            .addCallback(todoRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}