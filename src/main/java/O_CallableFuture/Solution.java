package O_CallableFuture;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

/**
 * Tiny, self-contained examples showing:
 * - Callable<V>: returns a value (or throws) when the task finishes.
 * - Future<V>: handle to a task; lets you get result, check status, cancel.
 * - FutureTask<V>: a concrete implementation of Runnable+Future (can be run in a Thread).
 * - Executors.submit(Callable): returns a Future with the result (execute() is for Runnable only).
 * - get(): blocks until the result is available; get(timeout) supports timeouts.
 * - invokeAll/invokeAny: run collections of Callables.
 * - Always shutdown ExecutorService to free threads.
 */
class SmartTask implements Callable<Integer> {
    private final int value;
    private final long workMillis;

    SmartTask(int value, long workMillis) {
        this.value = value;
        this.workMillis = workMillis;
    }

    @Override
    public Integer call() throws Exception {
        // Simulate some work, may throw or be interrupted
        try {
            Thread.sleep(workMillis);
        } catch (InterruptedException e) {
            // Propagate interrupt so the executor can react appropriately
            Thread.currentThread().interrupt();
            throw e;
        }
        return value * 2; // return a computed value
    }
}

public class Solution {
    public static void main(String[] args) throws Exception {
        // 1) Using FutureTask directly with a raw Thread (no executor)
        FutureTask<Integer> ft = new FutureTask<>(new SmartTask(5, 200));
        new Thread(ft, "FutureTask-Thread").start();
        Integer ftResult = ft.get(); // blocks until done
        System.out.println("FutureTask result: " + ftResult); // 10

        // 2) Using an ExecutorService with submit(Callable)
        ExecutorService pool = Executors.newFixedThreadPool(4);
        try {
            Future<Integer> f1 = pool.submit(new SmartTask(10, 300));
            Future<Integer> f2 = pool.submit(() -> {
                // Lambda Callable returning a list
                Thread.sleep(150);
                return 42; // simple scalar result
            });

            // get(): wait until result is ready
            System.out.println("f1: " + f1.get()); // 20
            System.out.println("f2: " + f2.get()); // 42

            // 3) Timeout and cancellation
            Future<Integer> slow = pool.submit(new SmartTask(99, 2000));
            try {
                System.out.println("slow: " + slow.get(300, TimeUnit.MILLISECONDS));
            } catch (TimeoutException te) {
                System.out.println("slow timed out, cancelling...");
                boolean cancelled = slow.cancel(true); // may interrupt the running task
                System.out.println("cancelled: " + cancelled);
            }

            // 4) invokeAll: run a batch and collect results in order
            List<Callable<Integer>> batch = Arrays.asList(
                new SmartTask(1, 100),
                new SmartTask(2, 100),
                new SmartTask(3, 100)
            );
            List<Future<Integer>> batchFutures = pool.invokeAll(batch); // blocks until all complete
            for (Future<Integer> f : batchFutures) {
                System.out.println("batch: " + f.get()); // 2, 4, 6
            }

            // 5) invokeAny: returns first successful result, cancels the rest
            Integer any = pool.invokeAny(Arrays.asList(
                new SmartTask(7, 500),
                new SmartTask(8, 100), // likely finishes first
                new SmartTask(9, 300)
            ));
            System.out.println("invokeAny: " + any); // likely 16

            // 6) submit Runnable vs Callable
            // - execute(Runnable) returns void (no Future), use when you don't need a result.
            // - submit(Runnable) returns Future<?> (result is null on success), use for cancellation/status.
            pool.execute(() -> System.out.println("execute(Runnable) no Future"));
            Future<?> fr = pool.submit(() -> System.out.println("submit(Runnable) returns Future"));
            fr.get(); // wait until printed
        } finally {
            // Always shutdown to reclaim threads
            pool.shutdown();
            if (!pool.awaitTermination(2, TimeUnit.SECONDS)) {
                pool.shutdownNow();
            }
        }

        // Summary:
        // - Prefer Callable when you need a result or checked exceptions.
        // - Use Future.get() to fetch results; use timeout/cancel to avoid blocking forever.
        // - For ad-hoc tasks, FutureTask can be run with a plain Thread.
        // - For batches, use invokeAll/invokeAny.
    }
}
