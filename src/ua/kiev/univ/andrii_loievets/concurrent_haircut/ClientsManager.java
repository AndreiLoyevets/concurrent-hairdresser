package ua.kiev.univ.andrii_loievets.concurrent_haircut;

import java.util.concurrent.Callable;

/**
 *
 * @author Tourist
 */
public class ClientsManager implements Callable<Object>{
    private static final int CLIENTS_INTENSITY = 100;
    private final Buffer<Integer> outQueue;
    
    public ClientsManager(Buffer<Integer> outQueue) {
        this.outQueue = outQueue;
    }

    @Override
    @SuppressWarnings("SleepWhileInLoop")
    public Object call() throws Exception {
        int clientID = 0;
        
        while (!Thread.currentThread().isInterrupted()) {
            outQueue.put(clientID++);
            Thread.sleep((long) (Math.random() * CLIENTS_INTENSITY));
        }
        return new Object();
    }
    
}
