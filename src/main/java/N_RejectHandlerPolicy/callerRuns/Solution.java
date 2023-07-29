package N_RejectHandlerPolicy.callerRuns;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

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

        ThreadPoolExecutor service =  new ThreadPoolExecutor(1,2,10, TimeUnit.MILLISECONDS,new SynchronousQueue<>(),new ThreadPoolExecutor.CallerRunsPolicy());
        for(int i=0;i<10;i++){
            service.submit(new SmartThread());
        }


    }
}
