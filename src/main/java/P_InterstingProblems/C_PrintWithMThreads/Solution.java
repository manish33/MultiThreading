package P_InterstingProblems.C_PrintWithMThreads;

public class Solution {
    // Shared state
    static class Shared {
        final int max;      // N
        final int threads;  // M
        int current = 1;    // next number to print
        int turn = 0;       // which thread id should print (0..M-1)
        Shared(int max, int threads) { this.max = max; this.threads = threads; }
    }

    // Worker that prints when it's its turn
    static class Worker implements Runnable {
        private final Shared shared;
        private final int id; // 0..M-1
        Worker(Shared shared, int id) { this.shared = shared; this.id = id; }
        @Override
        public void run() {
            while (true) {
                synchronized (shared) {
                    // If done, wake any waiter and exit
                    if (shared.current > shared.max) { shared.notifyAll(); break; }
                    // Wait for our turn
                    while (shared.turn != id) {
                        try { shared.wait(); } catch (InterruptedException e) { Thread.currentThread().interrupt(); return; }
                        if (shared.current > shared.max) { shared.notifyAll(); return; }
                    }
                    // It's our turn: print and advance
                    System.out.println(shared.current + " using thread " + id + " (" + Thread.currentThread().getName() + ")");
                    shared.current++;
                    shared.turn = (shared.turn + 1) % shared.threads;
                    shared.notifyAll();
                }
            }
        }
    }

    public static void main(String[] args) {
        int N = 10; // total numbers to print
        int M = 3;  // total threads
        Shared shared = new Shared(N, M);

        Thread[] threads = new Thread[M];
        for (int i = 0; i < M; i++) {
            threads[i] = new Thread(new Worker(shared, i), "Worker-" + i);
            threads[i].start();
        }

        for (int i = 0; i < M; i++) {
            try { threads[i].join(); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        }
    }
}

