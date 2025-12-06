package K_Lock.D_LockCondition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Solution {

    // A simple bounded buffer
    static class BoundedBuffer {
        private final int[] buffer;
        private int count = 0;   // number of items
        private int putPtr = 0;  // write position
        private int takePtr = 0; // read position

        private final ReentrantLock lock = new ReentrantLock();
        // same lock multiple conditions
        private final Condition notEmpty = lock.newCondition();
        private final Condition notFull = lock.newCondition();

        public BoundedBuffer(int size) {
            this.buffer = new int[size];
        }

        public void put(int item) throws InterruptedException {
            lock.lock();
            try {
                while (count == buffer.length) {
                    System.out.println("Buffer FULL → Producer waiting...");
                    notFull.await();      // wait until space available
                }
                buffer[putPtr] = item;
                putPtr = (putPtr + 1) % buffer.length;
                count++;

                System.out.println("Produced: " + item);

                notEmpty.signal();        // signal consumer
            } finally {
                lock.unlock();
            }
        }

        public int take() throws InterruptedException {
            lock.lock();
            try {
                while (count == 0) {
                    System.out.println("Buffer EMPTY → Consumer waiting...");
                    notEmpty.await();     // wait until item available
                }
                int item = buffer[takePtr];
                takePtr = (takePtr + 1) % buffer.length;
                count--;

                System.out.println("Consumed: " + item);

                notFull.signal();         // signal producer
                return item;
            } finally {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        BoundedBuffer buffer = new BoundedBuffer(5);

        // Producer thread
        Thread producer = new Thread(() -> {
            int i = 1;
            try {
                while (true) {
                    buffer.put(i++);
                    Thread.sleep(300); // slow down producer
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        // Consumer thread
        Thread consumer = new Thread(() -> {
            try {
                while (true) {
                    buffer.take();
                    Thread.sleep(700); // slow down consumer
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        producer.start();
        consumer.start();
    }
}

//https://www.youtube.com/watch?v=_Wg-jxcuPaw&list=PLp5xrQBgWON4JZzPOcF1JVhJ13RasA5Kt&index=4