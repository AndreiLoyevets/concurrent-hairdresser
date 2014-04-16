package ua.kiev.univ.andrii_loievets.concurrent_haircut;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Launches hairdresser, clients manager and the outer queue in different threads.
 * @author Andrii Loievets
 * @version 1.0 16-April-2014
 */
public class Driver {
    
    public static void main(String [] args) {
        Buffer<Integer> room = new Room<>();
        Buffer<Integer> outerQueue = new OuterQueue<>(room);
        Hairdresser hairdresser = new Hairdresser(room);
        ClientsManager cm = new ClientsManager(outerQueue);
        ExecutorService es = Executors.newFixedThreadPool(3);
        
        es.submit((Callable<Object>) outerQueue);
        es.submit(hairdresser);
        es.submit(cm);
    }
}