package Z_CallableFuture;

import java.util.concurrent.*;

class SmartThread implements Callable<Integer> {

   int i=0;

    @Override
    public Integer call() throws Exception {
        return i++;
    }
}

public class Solution {


    public static void main(String[] args) throws ExecutionException, InterruptedException {
       ExecutorService executor=  Executors.newFixedThreadPool(10);
       Future<Integer> fututre[] = new Future[10];
       for(int i=0;i<10;i++){
           fututre[i] = executor.submit(new SmartThread());
           System.out.println(fututre[i].get().intValue());
       }


    }



}
