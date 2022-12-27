package blue.lhf.takapelastin.http.utility;

import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.*;

/**
 * Utilities for managing {@link CompletableFuture}s.
 * */
public class Futures {
    private Futures() {
    }

    /**
     * Converts a list of completable futures returning map entries to a single one returning the entire map.
     * Insertions are done naively in completion order: if two completable futures in the input list return
     * entries with the same key, only the later-completing of the two will be present in the resulting map.
     * */
    public static <T, Q> CompletableFuture<Map<T, Q>> join(final List<CompletableFuture<Entry<T, Q>>> futures) {
        CompletableFuture<Map<T, Q>> future = CompletableFuture.completedFuture(new ConcurrentHashMap<>());
        for (final CompletableFuture<Entry<T, Q>> itemFuture : futures) {
            future = future.thenCombine(itemFuture, (map, entry) -> {
                map.put(entry.getKey(), entry.getValue());
                return map;
            });
        }

        return future;
    }
}
