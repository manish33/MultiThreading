package P_InterstingProblems.A_oddEvenPrinter;

class OddEvenPrinter {
    private final int max;
    private int current = 1;
    private boolean isOddTurn = true; // start with odd

    public OddEvenPrinter(int max) { this.max = max; }

    public synchronized void printOdd() {
        while (current <= max) {
            while (!isOddTurn) {
                try { wait(); } catch (InterruptedException e) { Thread.currentThread().interrupt(); return; }
            }
            if (current > max) { notifyAll(); break; }
            System.out.println("Odd: " + current + " by " + Thread.currentThread().getName());
            current++;
            isOddTurn = false;
            notifyAll();
        }
        notifyAll();
    }

    public synchronized void printEven() {
        while (current <= max) {
            while (isOddTurn) {
                try { wait(); } catch (InterruptedException e) { Thread.currentThread().interrupt(); return; }
            }
            if (current > max) { notifyAll(); break; }
            System.out.println("Even: " + current + " by " + Thread.currentThread().getName());
            current++;
            isOddTurn = true;
            notifyAll();
        }
        notifyAll();
    }
}

public class Solution {
    public static void main(String[] args) {
        int max = 20;
        OddEvenPrinter printer = new OddEvenPrinter(max);

        Thread odd = new Thread(printer::printOdd, "Odd-Thread");
        Thread even = new Thread(printer::printEven, "Even-Thread");

        odd.start();
        even.start();

        try {
            odd.join();
            even.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

// Notes:
// Single-file variant of odd-even printing using wait/notifyAll.
