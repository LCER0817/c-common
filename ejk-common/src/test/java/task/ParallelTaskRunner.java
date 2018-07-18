package task;

import com.google.common.collect.Maps;

import java.util.HashMap;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

public class ParallelTaskRunner {

    private HashMap<String, ParallelTask> tasks = Maps.newHashMap();

    public void addTask(String keyName, ParallelTask<Object> task) {
        this.tasks.put(keyName, task);
    }

    public HashMap run() throws Exception {
        CompletableFuture<HashMap>[] futures = tasks.entrySet().stream().map(entry -> entry.getValue().addTask(entry.getKey())).toArray(CompletableFuture[]::new);
        return CompletableFuture.allOf(futures).thenApply(e -> {
            HashMap hashMap = Stream.of(futures).collect(HashMap::new, (map, task) -> {
                map.putAll(task.join());
            }, HashMap::putAll);
            return hashMap;
        }).get();
    }


}
