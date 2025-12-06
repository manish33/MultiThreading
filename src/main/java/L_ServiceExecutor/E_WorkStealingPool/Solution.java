package L_ServiceExecutor.E_WorkStealingPool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

class SmartTask implements Runnable {
    private final int id;
    public SmartTask(int id) { this.id = id; }
    @Override
    public void run() {
        // Simulate variable work so some threads finish earlier and can steal tasks
        try {
            System.out.println("Task " + id + " running on " + Thread.currentThread().getName());
            Thread.sleep(100 + (id % 5) * 50);
            System.out.println("Task " + id + " done on " + Thread.currentThread().getName());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Task " + id + " interrupted");
        }
    }
}

public class Solution {
    public static void main(String[] args) throws Exception {
        // Work-stealing pool: number of worker threads is equal to available processors by default
        ExecutorService service = Executors.newWorkStealingPool();

        List<Future<?>> futures = new ArrayList<>();
        for (int i = 0; i < 16; i++) {
            futures.add(service.submit(new SmartTask(i)));
        }

        // newWorkStealingPool returns a ForkJoinPool; shutdown semantics differ slightly.
        // ForkJoinPool is non-blocking; we can wait briefly for tasks to finish.
        for (Future<?> f : futures) {
            f.get(); // wait for each task to complete
        }

        service.shutdown();
        service.awaitTermination(5, TimeUnit.SECONDS);
    }
}

// Usage notes:
// When to use: newWorkStealingPool() (ForkJoinPool) is great for many small, independent tasks that can
// run in parallel, especially when task durations vary. Idle workers will "steal" tasks from busy workers
// to balance load automatically.
// Data structures/behavior: Backed by ForkJoinPool which uses per-worker double-ended queues (Deque) and a
// global submission queue. Workers pop from their own deque and steal from the tail of others' deques.
// Ordering: This executor does NOT guarantee execution order. Tasks may complete out of submission order due to
// work-stealing and parallelism. If strict FIFO order is required, prefer SingleThreadExecutor or a fixed pool
// draining from a single LinkedBlockingQueue.

