package L_ServiceExecutor.B_CachedThreadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class SmartThread implements Runnable{

    @Override
    public void run() {
        for(int i=0;i<100;i++){
            System.out.println(i);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

public class Solution {

    public static void main(String[] args) {

        ExecutorService service =  Executors.newCachedThreadPool();
        for(int i=0;i<10;i++){
            service.submit(new SmartThread());
        }


    }
}
