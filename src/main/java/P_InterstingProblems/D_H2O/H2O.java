package P_InterstingProblems.D_H2O;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

class H2O {
    // Semaphore to allow strictly 2 Hydrogen atoms at a time
    private final Semaphore hSem = new Semaphore(2);

    // Semaphore to allow strictly 1 Oxygen atom at a time
    private final Semaphore oSem = new Semaphore(1);

    // Barrier to make sure the 2 Hs and 1 O act as a single group
    private final CyclicBarrier barrier = new CyclicBarrier(3);

    public H2O() {
        // Constructor needed to initialize if fields aren't final/initialized inline
    }

    public void hydrogen(Runnable releaseHydrogen) throws InterruptedException {
        // 1. Occupy a Hydrogen slot (blocks if 2 Hs are already waiting)
        hSem.acquire();

        try {
            // 2. Wait for the other H and the O to arrive
            barrier.await();

            // 3. Release the element (Print "H")
            releaseHydrogen.run();

        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        } finally {
            // 4. Open the slot for the next Hydrogen
            hSem.release();
        }
    }

    public void oxygen(Runnable releaseOxygen) throws InterruptedException {
        // 1. Occupy the Oxygen slot (blocks if 1 O is already waiting)
        oSem.acquire();

        try {
            // 2. Wait for the 2 Hs to arrive
            barrier.await();

            // 3. Release the element (Print "O")
            releaseOxygen.run();

        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        } finally {
            // 4. Open the slot for the next Oxygen
            oSem.release();
        }
    }
}