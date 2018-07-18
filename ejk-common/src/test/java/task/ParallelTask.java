package task;

import com.google.common.collect.Maps;

import java.util.HashMap;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public interface ParallelTask<T> extends Supplier<T> {

    default CompletableFuture<HashMap> addTask(String key) {
        return CompletableFuture.supplyAsync(this).thenApply(data -> {
            HashMap<Object, Object> map = Maps.newHashMap();
            map.put(key, data);
            return map;
        });
    }
}
