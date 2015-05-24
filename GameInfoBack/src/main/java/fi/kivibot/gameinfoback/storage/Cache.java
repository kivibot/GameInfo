package fi.kivibot.gameinfoback.storage;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *  Thread-safe
 * @author Nicklas
 */
public class Cache {

    private final Map<Object, Object> map;
    private final long maxSize;

    public Cache() {
        this(10);
    }
    
    public Cache(long size) {
        maxSize = size;
        this.map = Collections.synchronizedMap(new LinkedHashMap<Object, Object>() {
            @Override
            protected boolean removeEldestEntry(Map.Entry<Object, Object> eldest) {
                if(this.size() > maxSize){
                    System.out.println("uncache "+eldest.getKey());
                }
                return this.size() > maxSize;
            }
        });
    }

    public void put(Object key, Object value) {
        System.out.println("put " + key + " " + value);
        map.put(key, value);
    }

    public Object get(Object key) {
        return map.get(key);
    }

}
