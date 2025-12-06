package L_ServiceExecutor.D_ScheduledThreadPool;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

class SmartThread implements Runnable{
    int i=0;
    @Override
    public void run() {

        System.out.println(i++);
        }

}

public class Solution {

    public static void main(String[] args) {

        ScheduledExecutorService service =  Executors.newScheduledThreadPool(10);

           // service.scheduleAtFixedRate(new SmartThread(),0,3, TimeUnit.SECONDS);
           // service.schedule(new SmartThread(),2,TimeUnit.SECONDS);
           service.scheduleWithFixedDelay(new SmartThread(),0,3, TimeUnit.SECONDS);



    }
}

// Usage notes:
// When to use: newScheduledThreadPool(n) is for delayed and periodic task execution (timers, heartbeats,
// maintenance jobs). Choose n based on the max concurrent scheduled tasks.
// Data structures/behavior: Backed by a ScheduledThreadPoolExecutor using a time-ordered DelayQueue internally.
// Supports schedule(..), scheduleAtFixedRate(..), scheduleWithFixedDelay(..). Threads are reused like a fixed pool.
// Fixed-rate tries to maintain a regular rate (can catch up), fixed-delay waits the specified delay after task completion.
