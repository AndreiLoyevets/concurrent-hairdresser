package ua.kiev.univ.andrii_loievets.concurrent_haircut;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author Tourist
 * @param <E>
 */
public class OuterQueue<E> implements Buffer<E>, Callable<Object> {

    private final Buffer<E> room;
    private final List<E> list;
    private final Lock lock;
    private final Condition notEmpty;

    public OuterQueue(Buffer<E> room) {
        this.room = room;
        list = new ArrayList<>();
        lock = new ReentrantLock(true);
        notEmpty = lock.newCondition();
    }

    @Override
    public void put(E item) throws InterruptedException {
        lock.lock();

        try {
            list.add(item);
            notEmpty.signalAll();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public E get() {
        return null;
    }

    @Override
    public boolean isEmpty() {
        lock.lock();

        try {
            return list.isEmpty();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public boolean isFull() {
        return false;
    }

    @Override
    public Object call() throws Exception {
        E item = null;
        while (!Thread.currentThread().isInterrupted()) {
            lock.lock();

            // wait while the queue is empty
            notEmpty.await();
            
            // new client came to the queue
            
            try {

                // get the client
                item = list.remove(0);
            } finally {
                lock.unlock();
            }

            // try the client to enter the room
            room.put(item);
        }

        return new Object();
    }

}
