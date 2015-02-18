package fi.kivibot.gameinfoback.api;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 *
 * @author Nicklas
 */
public class ApiFuture<A> implements Future<A> {

    private A value;
    private boolean done;

    public void set(A v) {
        synchronized (this) {
            value = v;
            done = true;
            this.notifyAll();
        }
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        return false;
    }

    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public boolean isDone() {
        return done;
    }

    @Override
    public A get() throws InterruptedException, ExecutionException {
        synchronized (this) {
            if (!isDone()) {
                this.wait();
            }
        }
        return value;
    }

    @Override
    public A get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        synchronized (this) {
            if (!isDone()) {
                this.wait(unit.toMillis(timeout));
            }
        }
        return value;
    }

}
