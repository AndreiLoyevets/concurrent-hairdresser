package ua.kiev.univ.andrii_loievets.concurrent_haircut;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * RingBuffer implementation.
 *
 * @author Andrii Loievets
 * @version 1.0 16-April-2014
 * @param <E>
 */
public class Room<E> implements Buffer<E> {

    private final int NUM_CHAIRS = 10;
    private final Lock lock;
    private final Condition notFull;
    private final Condition notEmpty;
    private final Object[] chairs;
    private int oldest; // index of the oldest element
    private int newest; // index of the newest (latest added) element

    public Room() {
        lock = new ReentrantLock();
        notEmpty = lock.newCondition();
        notFull = lock.newCondition();
        chairs = new Object[NUM_CHAIRS];
        oldest = 0; // as soon as we put something, it gets into buffer[0]
        newest = -1; // when we put something new, we first increment newest
    }

    @Override
    public void put(E item) throws InterruptedException {
        lock.lock();

        try {
            if (isFull()) {
                System.out.println("Клієнт №" + item + " очікує в черзі на вулиці");
            }

            while (isFull()) {
                notFull.await();
            }

            int pos = (newest + 1) % chairs.length;
            chairs[pos] = item;
            newest = pos;
            System.out.println("Клієнт №" + item + " в кімнаті для очікування");

            notEmpty.signalAll();

        } finally {
            lock.unlock();
        }
    }

    @Override
    public E get() throws InterruptedException {
        lock.lock();

        try {
            while (isEmpty()) {
                notEmpty.await();
            }

            E item = (E) chairs[oldest];
            chairs[oldest] = null;
            oldest = (1 + oldest) % chairs.length;

            notFull.signalAll();

            return item;

        } finally {
            lock.unlock();
        }
    }

    @Override
    public boolean isEmpty() {
        lock.lock();

        try {
            for (int i = 0; i < chairs.length; ++i) {
                if (chairs[i] != null) {
                    return false;
                }
            }

            return true;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public boolean isFull() {
        for (int i = 0; i < chairs.length; ++i) {
            if (chairs[i] == null) {
                return false;
            }
        }

        return true;
    }
}
