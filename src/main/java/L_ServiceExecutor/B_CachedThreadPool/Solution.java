package L_ServiceExecutor.B_CachedThreadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class SmartThread implements Runnable{

    @Override
    public void run() {
        for(int i=0;i<100;i++){
            System.out.println(i);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

public class Solution {

    public static void main(String[] args) {

        ExecutorService service =  Executors.newCachedThreadPool();
        for(int i=0;i<10;i++){
            service.submit(new SmartThread());
        }


    }
}

// Usage notes:
// When to use: newCachedThreadPool() is suited for many short-lived, bursty asynchronous tasks.
// It creates new threads as needed and reuses idle threads; good when you don't want to limit concurrency
// but also don't want to keep a fixed number of threads.
// Data structures: Backed by a ThreadPoolExecutor with a SynchronousQueue (handoff queue, no capacity).
// Core pool size is 0; max is Integer.MAX_VALUE; idle threads are terminated after keep-alive (default 60s).
// Behavior: Tasks are directly handed off to threads; if no idle thread is available, a new thread is created.
