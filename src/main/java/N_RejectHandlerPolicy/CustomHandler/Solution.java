package N_RejectHandlerPolicy.CustomHandler;

import java.util.concurrent.*;

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

class CustomHanler implements RejectedExecutionHandler{

    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        executor= (ThreadPoolExecutor) Executors.newFixedThreadPool(executor.getMaximumPoolSize() + 5);
        executor.submit(r);
    }
}

public class Solution {

    public static void main(String[] args) {

        ThreadPoolExecutor service =  new ThreadPoolExecutor(1,2,10, TimeUnit.MILLISECONDS,new SynchronousQueue<>(),new CustomHanler());
        for(int i=0;i<3;i++){
            service.submit(new SmartThread());
        }


    }


}

