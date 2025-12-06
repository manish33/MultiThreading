package L_ServiceExecutor.C_SingleThreaded;

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

        ExecutorService service =  Executors.newSingleThreadExecutor();
        for(int i=0;i<10;i++){
            service.submit(new SmartThread());
        }


    }
}

// Usage notes:
// When to use: newSingleThreadExecutor() is best when tasks must execute sequentially with strict ordering
// and you want a dedicated thread (e.g., event processing, serialization of access to a resource).
// Data structures/behavior: Backed by a ThreadPoolExecutor with one worker thread and an unbounded LinkedBlockingQueue.
// Only one task runs at a time; tasks are queued and executed in FIFO order. If the thread dies, a new one is created.
