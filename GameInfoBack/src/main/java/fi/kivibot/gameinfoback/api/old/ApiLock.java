package fi.kivibot.gameinfoback.api.old;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 *
 * @author Nicklas
 */
public class ApiLock {

    private boolean locked = false;

    public synchronized void lock() throws InterruptedException {
        while (locked) {
            wait();
        }
        locked = true;
    }

    public synchronized void unlock() {
        locked = false;
        notifyAll();
    }

    public synchronized void waitForUnlock() throws InterruptedException {
        while (locked) {
            wait();
        }
    }

    public synchronized void waitForUnlock(long t, TimeUnit u) throws InterruptedException, TimeoutException {
        long end = System.currentTimeMillis() + u.toMillis(t);
        while (locked) {
            wait(u.toMillis(t) / 10 + 1);
            if (System.currentTimeMillis() > end) {
                throw new TimeoutException();
            }
        }
    }

}
