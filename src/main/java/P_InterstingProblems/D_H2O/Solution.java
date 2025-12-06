package P_InterstingProblems.D_H2O;

public class Solution {
    public static void main(String[] args) {

        H2O h2o = new H2O();

        String input = "OOHHHHHHHHOOHH";  // random sequence
        // expected output will be grouped into "HHO"

        Thread[] threads = new Thread[input.length()];
        int idx = 0;
        for (char c : input.toCharArray()) {
            if (c == 'H') {
                threads[idx++] = new Thread(() -> {
                    try {
                        h2o.hydrogen(() -> System.out.print("H"));
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }, "H");
            } else if (c == 'O') {
                threads[idx++] = new Thread(() -> {
                    try {
                        h2o.oxygen(() -> System.out.print("O"));
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }, "O");
            }
        }

        for (Thread t : threads) t.start();
        for (Thread t : threads) {
            try { t.join(); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        }
        System.out.println();
    }
}
