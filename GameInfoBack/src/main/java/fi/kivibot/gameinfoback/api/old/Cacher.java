package fi.kivibot.gameinfoback.api.old;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nicklas
 */
public class Cacher {

    private long max_age = 5 * 60 * 1000; //5 minutes
    private int max_count = 5000; //5k

    private class InternalPair implements Comparable<InternalPair> {

        public long last;
        public Object val;
        public String key;

        public InternalPair(long last, Object val, String key) {
            this.last = last;
            this.val = val;
            this.key = key;
        }

        @Override
        public int compareTo(InternalPair o) {
            return Long.compare(last, o.last);
        }

    }

    private Map<String, InternalPair> map = new HashMap<>();
    private PriorityQueue<InternalPair> queue = new PriorityQueue<>();
    private Thread t;

    public synchronized <O> O get(String key) {
        InternalPair p = map.get(key);
        if (p != null) {
            p.last = System.currentTimeMillis();
            System.out.println("get " + key);
            return (O) p.val;
        }
        System.out.println("noget " + key);
        return null;
    }

    public synchronized void set(String key, Object val) {
        System.out.println("set " + key);
        InternalPair ip = new InternalPair(System.currentTimeMillis(), val, key);
        map.put(key, ip);
        queue.add(ip);
        if (map.size() > max_count) {
            remove(map.size() - max_count);
        }
    }

    private synchronized void remove(int count) {
        for (int i = 0; i < count && !queue.isEmpty(); i++) {
            InternalPair ip = queue.poll();
            map.remove(ip.key);
            System.out.println("rm " + ip.key);
        }
    }

    private synchronized long cleanHead() {
        while (!queue.isEmpty()) {
            InternalPair ip = queue.poll();
            if (ip.last + max_age < System.currentTimeMillis()) {
                map.remove(ip.key);
                System.out.println("rm " + ip.key);
            } else {
                queue.add(ip);
                if (queue.peek().last + max_age > System.currentTimeMillis()) {
                    return queue.peek().last + max_age - System.currentTimeMillis();
                }
            }
        }
        return 1000;
    }

    public Cacher() {
        t = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(cleanHead());
                } catch (InterruptedException ex) {
                    Logger.getLogger(Cacher.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    public void start() {
        t.start();
    }

}
