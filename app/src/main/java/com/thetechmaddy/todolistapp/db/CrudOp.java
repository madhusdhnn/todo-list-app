package com.thetechmaddy.todolistapp.db;

import androidx.annotation.NonNull;

import java.util.List;

public interface CrudOp<T> {

    void save(@NonNull T t);

    void saveAll(List<T> tList);

    void saveAllBatched(List<T> tList, int batchSize);

    void delete(@NonNull T t);

}
