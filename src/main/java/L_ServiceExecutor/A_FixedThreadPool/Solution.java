package L_ServiceExecutor.A_FixedThreadPool;

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

        ExecutorService service =  Executors.newFixedThreadPool(10);
        for(int i=0;i<10;i++){
            service.submit(new SmartThread());
        }
    }
}

// Usage notes:
// When to use: newFixedThreadPool(n) is ideal when you have a steady number of CPU-bound or IO tasks
// and want to cap concurrency to n threads. It provides bounded parallelism and predictable resource use.
// Data structures: Executors.newFixedThreadPool uses a ThreadPoolExecutor backed by a LinkedBlockingQueue
// (unbounded queue by default) and a fixed core/max pool size of n. Threads are reused; tasks beyond the
// running threads are queued in the LinkedBlockingQueue.
// Behavior: If all n threads are busy, new tasks wait in the queue. Threads are kept alive and reused.
