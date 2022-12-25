package blue.lhf.takapelastin.http.utility;

import java.util.*;
import java.util.concurrent.*;

public class Futures {
    private Futures() {
    }

    public static <T, Q> CompletableFuture<Map<T, Q>> join(final List<CompletableFuture<Map.Entry<T, Q>>> futures) {
        CompletableFuture<Map<T, Q>> future = CompletableFuture.completedFuture(new ConcurrentHashMap<>());
        for (final CompletableFuture<Map.Entry<T, Q>> itemFuture : futures) {
            future = future.thenCombine(itemFuture, (map, entry) -> {
                map.put(entry.getKey(), entry.getValue());
                return map;
            });
        }

        return future;
    }
}
