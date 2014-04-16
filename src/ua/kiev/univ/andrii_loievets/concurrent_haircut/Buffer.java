package ua.kiev.univ.andrii_loievets.concurrent_haircut;

/**
 * General Buffer interface to put and get items of a specified type.
 * @author Andrii Loievets
 * @version 1.0 16-April-2014
 * @param <E>
 */
public interface Buffer<E> {
    void put(E item) throws InterruptedException;
    E get() throws InterruptedException;
    boolean isEmpty();
    boolean isFull();
}
