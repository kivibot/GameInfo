package fi.kivibot.summonerbackend;

import com.google.common.cache.LoadingCache;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

/**
 *
 * @author Nicklas
 */
public class Cache<K, V> {
    
    private final LoadingCache<K, Optional<V>> loadingCache;

    public Cache(LoadingCache<K, Optional<V>> loadingCache) {
        this.loadingCache = loadingCache;
    }
    
    public V get(K key) throws ExecutionException {
        return loadingCache.get(key).orElse(null);
    }
    
    public Map<K, V> getAll(Collection<K> keys) throws ExecutionException {
        return loadingCache.getAll(keys).entrySet().stream()
                .filter(e -> e.getValue().isPresent())
                .collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue().get()));
    }
    
    public void put(K key, V value) {
        loadingCache.put(key, Optional.ofNullable(value));
    }
    
    public LoadingCache<K, Optional<V>> getInternalCache() {
        return loadingCache;
    }
    
}
