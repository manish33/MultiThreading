package Q_CompletableFuture;

import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * Bite-size CompletableFuture examples (simplified):
 * - Basic retrieval: get(), getNow(default), complete(value)
 * - supplyAsync/runAsync: start async tasks
 * - thenApply/thenAccept: transform or consume
 * - thenCompose vs thenApply: nested vs flat
 * - thenCombine: combine independent tasks
 * - allOf/anyOf: wait for many or first
 * - exceptionally/handle: error handling
 * - timeouts: orTimeout/completeOnTimeout
 * - Final example: custom executors for CPU vs IO steps
 */
public class Solution {
    public static void main(String[] args) throws Exception {
        // Keep it simple: use default common pool for most examples

        // 0) Basic retrieval API: get(), getNow(default), complete(value)
        CompletableFuture<String> base = new CompletableFuture<>();

        // getNow(default): returns immediately; if not completed, returns provided default
        String immediate = base.getNow("not-ready");
        System.out.println("getNow: " + immediate); // "not-ready" because base not completed yet

        // complete(value): completes the CF manually (if not already); returns true if this call completed it
        boolean didComplete = base.complete("ready");
        System.out.println("complete(): " + didComplete); // true (first time)

        // get(): blocks until completed; here it returns the value we just completed with
        String blocking = base.get();
        System.out.println("get(): " + blocking); // "ready"

        // 1) supplyAsync: return a value asynchronously
        CompletableFuture<Integer> cf1 = CompletableFuture.supplyAsync(() -> {
            sleep(200);
            return 21;
        });

        // 2) thenApply: transform result
        CompletableFuture<Integer> cf2 = cf1.thenApply(x -> x * 2); // 42
        cf2.thenAccept(x -> System.out.println("thenAccept: " + x));

        // 3) runAsync: no result
        CompletableFuture<Void> cfRun = CompletableFuture.runAsync(() -> {
            sleep(100);
            System.out.println("runAsync done");
        });
        cfRun.join(); // wait for completion

        // How to get a result? Use supplyAsync (returns a value)
        CompletableFuture<String> cfSupply = CompletableFuture.supplyAsync(() -> {
            sleep(80);
            return "computed";
        });
        System.out.println("supplyAsync result: " + cfSupply.join());

        // Or derive a result after a runAsync via thenApply on a completed value
        CompletableFuture<String> derived = cfRun.thenApply(v -> "done -> result");
        System.out.println("derived after runAsync: " + derived.join());

        // 4) thenCompose vs thenApply (nested future vs flat)
        CompletableFuture<CompletableFuture<Integer>> nested = cf1.thenApply(x ->
            CompletableFuture.supplyAsync(() -> x + 1)
        );
        System.out.println("nested (double-join): " + nested.join().join());
        CompletableFuture<Integer> flat = cf1.thenCompose(x ->
            CompletableFuture.supplyAsync(() -> x + 1)
        );
        System.out.println("thenCompose (flat): " + flat.join());

        // 5) thenCombine: combine independent futures
        CompletableFuture<Integer> a = CompletableFuture.supplyAsync(() -> 10);
        CompletableFuture<Integer> b = CompletableFuture.supplyAsync(() -> 32);
        CompletableFuture<Integer> sum = a.thenCombine(b, Integer::sum); // 42
        System.out.println("thenCombine sum: " + sum.join());

        // 6) allOf: wait for many (simple pattern without pools)
        CompletableFuture<String> f1 = CompletableFuture.supplyAsync(() -> { sleep(100); return "Result 1"; });
        CompletableFuture<String> f2 = CompletableFuture.supplyAsync(() -> { sleep(120); return "Result 2"; });
        CompletableFuture<String> f3 = CompletableFuture.supplyAsync(() -> { sleep(140); return "Result 3"; });
        CompletableFuture<Void> allFutures = CompletableFuture.allOf(f1, f2, f3);
        allFutures.thenRun(() -> {
            String r1 = f1.join();
            String r2 = f2.join();
            String r3 = f3.join();
            System.out.println(r1 + ", " + r2 + ", " + r3);
        }).join(); // wait so output is printed before main exits

        // 7) anyOf: first result wins
        CompletableFuture<Object> any = CompletableFuture.anyOf(
            CompletableFuture.supplyAsync(() -> { sleep(200); return "slow"; }),
            CompletableFuture.supplyAsync(() -> { sleep(50); return "fast"; })
        );
        System.out.println("anyOf: " + any.join());

        // 8) exceptionally: error recovery
        CompletableFuture<Integer> cfErr = CompletableFuture.<Integer>supplyAsync(() -> {
            throw new RuntimeException("boom");
        }).exceptionally(ex -> {
            System.out.println("caught: " + ex.getMessage());
            return -1; // fallback
        });
        System.out.println("exceptionally result: " + cfErr.join());

        // 9) handle: inspect success/failure
        CompletableFuture<String> cfHandle = CompletableFuture.supplyAsync(() -> {
            if (System.currentTimeMillis() % 2 == 0) throw new IllegalStateException("bad luck");
            return "ok";
        }).handle((val, ex) -> ex != null ? ("handled: " + ex.getClass().getSimpleName()) : ("handled: " + val));
        System.out.println(cfHandle.join());

        // 10) timeouts (simple)
        CompletableFuture<String> cfTimeout = CompletableFuture.supplyAsync(() -> {
            sleep(500);
            return "late";
        }).orTimeout(200, TimeUnit.MILLISECONDS)
          .exceptionally(ex -> "timeout: " + ex.getClass().getSimpleName());
        System.out.println(cfTimeout.join());

        CompletableFuture<String> cfDefaultOnTimeout = CompletableFuture.supplyAsync(() -> {
            sleep(500);
            return "late";
        }).completeOnTimeout("default", 200, TimeUnit.MILLISECONDS);
        System.out.println(cfDefaultOnTimeout.join());

        // 11) Final example: use different pools per step (CPU vs IO)
        ExecutorService cpuPool = Executors.newFixedThreadPool(2, r -> new Thread(r, "CPU-Worker"));
        ExecutorService ioPool = Executors.newFixedThreadPool(4, r -> new Thread(r, "IO-Worker"));
        try {
            CompletableFuture<String> pipeline = CompletableFuture.supplyAsync(() -> {
                // CPU-intensive step (e.g., parsing, computation)
                busyCpu(100_000);
                return "parsed";
            }, cpuPool)
            .thenCompose(parsed -> CompletableFuture.supplyAsync(() -> {
                // IO-intensive step (e.g., network/db call)
                sleep(200);
                return parsed + " + fetched";
            }, ioPool))
            .thenApplyAsync(s -> {
                // CPU-intensive transformation
                busyCpu(200_000);
                return s.toUpperCase();
            }, cpuPool);

            System.out.println("pipeline (CPU/IO pools): " + pipeline.join());
        } finally {
            cpuPool.shutdown();
            ioPool.shutdown();
        }

        // 2a) thenApply vs thenApplyAsync: where does the continuation run?
        // thenApply: usually runs in the same thread that completed the previous stage (synchronous continuation).
        CompletableFuture<String> baseCF = CompletableFuture.supplyAsync(() -> {
            sleep(50);
            return "data";
        });
        CompletableFuture<String> syncApplied = baseCF.thenApply(s -> {
            String thread = Thread.currentThread().getName();
            return "thenApply on " + thread + " -> " + s.toUpperCase();
        });
        System.out.println(syncApplied.join());

        // thenApplyAsync: schedules the continuation to run asynchronously, typically on the ForkJoinPool
        CompletableFuture<String> asyncAppliedDefault = baseCF.thenApplyAsync(s -> {
            String thread = Thread.currentThread().getName();
            return "thenApplyAsync(default) on " + thread + " -> " + s + "!";
        });
        System.out.println(asyncAppliedDefault.join());

        // thenApplyAsync with a custom executor: control which threads execute the continuation
        ExecutorService contPool = Executors.newFixedThreadPool(2, r -> new Thread(r, "Continuations"));
        try {
            CompletableFuture<String> asyncAppliedCustom = baseCF.thenApplyAsync(s -> {
                String thread = Thread.currentThread().getName();
                return "thenApplyAsync(custom) on " + thread + " -> " + s + "?";
            }, contPool);
            System.out.println(asyncAppliedCustom.join());
        } finally {
            contPool.shutdown();
        }

        // When to use:
        // - thenApply: lightweight, synchronous transformations; keeps locality (often the same thread).
        // - thenApplyAsync: offload heavier work, avoid blocking the completing thread, or control execution via a custom executor.
        // Note: For dependent async steps that themselves return CompletableFuture, prefer thenCompose to avoid nesting.
    }

    private static void sleep(long ms) {
        try { Thread.sleep(ms); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
    }
    private static void busyCpu(int n) {
        long x = 0; for (int i = 0; i < n; i++) { x += i; }
    }
}
