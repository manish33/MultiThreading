package I_Synchronized.ProducerConsumerBlockingQueue;


import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

class Producer implements Runnable {
    BlockingQueue<Integer> bq;
    Random rand = new Random();
    Producer( BlockingQueue<Integer> bq){
        this.bq=bq;
    }
    @Override
    public void run() {




        while (true) {
            int n= rand.nextInt(100);
            System.out.println("value produce "+n);
            try {
                bq.put(n);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class Consumer  implements Runnable {

    BlockingQueue<Integer> bq;
    Consumer( BlockingQueue<Integer> bq){
        this.bq=bq;
    }
    @Override
    public void run() {
        while (true) {
            try {

                System.out.println("value consumerd " + bq.take());
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}

public class Solution {

    public static void main(String[] args) {
        if(97==97.00){
            System.out.println("wtf");
        }
        BlockingQueue<Integer> bq = new LinkedBlockingDeque<>(100);
        Thread producer1 = new Thread(new Producer(bq));
        Thread producer2 = new Thread(new Producer(bq));
        Thread consumer1 = new Thread(new Consumer(bq));
        producer1.start();
        producer2.start();
        consumer1.start();

    }




}
