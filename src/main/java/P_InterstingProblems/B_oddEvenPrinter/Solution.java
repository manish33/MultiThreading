package P_InterstingProblems.B_oddEvenPrinter;

public class Solution {
    // Shared state holder
    static class Shared {
        final int max;
        int current = 1;
        boolean isOddTurn = true; // start with odd
        Shared(int max) { this.max = max; }
    }

    // Odd printer with synchronization in run()
    static class OddPrinter implements Runnable {
        private final Shared shared;
        OddPrinter(Shared shared) { this.shared = shared; }
        @Override public void run() {
            while (true) {
                synchronized (shared) {
                    if (shared.current > shared.max) { shared.notifyAll(); break; }
                    while (!shared.isOddTurn) {
                        try { shared.wait(); } catch (InterruptedException e) { Thread.currentThread().interrupt(); return; }
                        if (shared.current > shared.max) { shared.notifyAll(); return; }
                    }
                    System.out.println("Odd: " + shared.current + " by " + Thread.currentThread().getName());
                    shared.current++;
                    shared.isOddTurn = false;
                    shared.notifyAll();
                }
            }
        }
    }

    // Even printer with synchronization in run()
    static class EvenPrinter implements Runnable {
        private final Shared shared;
        EvenPrinter(Shared shared) { this.shared = shared; }
        @Override public void run() {
            while (true) {
                synchronized (shared) {
                    if (shared.current > shared.max) { shared.notifyAll(); break; }
                    while (shared.isOddTurn) {
                        try { shared.wait(); } catch (InterruptedException e) { Thread.currentThread().interrupt(); return; }
                        if (shared.current > shared.max) { shared.notifyAll(); return; }
                    }
                    System.out.println("Even: " + shared.current + " by " + Thread.currentThread().getName());
                    shared.current++;
                    shared.isOddTurn = true;
                    shared.notifyAll();
                }
            }
        }
    }

    public static void main(String[] args) {
        int max = 20;
        Shared shared = new Shared(max);
        Thread odd = new Thread(new OddPrinter(shared), "Odd-Thread");
        Thread even = new Thread(new EvenPrinter(shared), "Even-Thread");
        odd.start();
        even.start();
        try { odd.join(); even.join(); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
    }
}
