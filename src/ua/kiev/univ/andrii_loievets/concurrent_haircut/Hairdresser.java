package ua.kiev.univ.andrii_loievets.concurrent_haircut;

import java.util.concurrent.Callable;

/**
 * Hairdresser waits for the next client. takes the client from the room and
 * makes haircuts.
 * @author Andrii Loievets
 * @version 1.0 16-April-2014
 */
public class Hairdresser implements Callable<Object>{
    private final Buffer<Integer> room;
    
    public Hairdresser(Buffer<Integer> buffer) {
        this.room = buffer;
    }

    @Override
    @SuppressWarnings("SleepWhileInLoop")
    public Object call() throws Exception {
        while (!Thread.currentThread().isInterrupted()) {
            System.out.println("Очікую нового клієнта...");
            Integer client = room.get();
            System.out.println("Працюю над клієнтом  №" + client + "...");
            Thread.sleep((long) (Math.random() * 1000));
        }
        
        return null;
    }
}