package ua.kiev.univ.andrii_loievets.concurrent_haircut;

/**
 *
 * @author Tourist
 * @version 1.0 16-April-2014
 */
public interface Buffer<E> {
    void put(E item) throws InterruptedException;
    E get() throws InterruptedException;
    boolean isEmpty();
    boolean isFull();
}
