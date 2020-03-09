package com.thetechmaddy.todolistapp.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CollectionUtils {

    public static <T> List<List<T>> split(List<T> list, int batchSize) {
        if (batchSize > list.size() || batchSize >= Integer.MAX_VALUE) {
            throw new IllegalArgumentException(String.format("Invalid batch size - %d", batchSize));
        } else if (batchSize == list.size() || batchSize == 0) {
            return Collections.singletonList(list);
        }

        int prevBatchOpSize = 0;
        List<List<T>> splitted = new ArrayList<>();
        for (int i = 1; i <= list.size(); i += batchSize) {
            int fromIndex = i - 1;
            int toIndex = prevBatchOpSize + batchSize;
            if (i < list.size() && toIndex < list.size()) {
                List<T> subList = list.subList(fromIndex, toIndex);
                splitted.add(subList);
                prevBatchOpSize += subList.size();
            } else {
                toIndex = list.size();
                splitted.add(list.subList(fromIndex, toIndex));
            }
        }
        return splitted;
    }

}
