package O_CallableFuture;

import java.util.Arrays;
import java.util.List;
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

         new Thread(new FutureTask<Integer>(new SmartThread())).start();

       ExecutorService executor=  Executors.newFixedThreadPool(10);
       Future<Integer> fututre[] = new Future[10];
       for(int i=0;i<10;i++){
           // submit needs callable future
           // execute  needs runnable
           fututre[i] = executor.submit(new SmartThread());
           System.out.println(fututre[i].get().intValue());
       }

       Future<List<Integer>> fs = Executors.newFixedThreadPool(10).submit(()->{
           return Arrays.asList(1,2,3,4);
               }
       );
        List<Integer> integers = fs.get();




    }



}
