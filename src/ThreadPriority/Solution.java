package ThreadPriority;


class SmartThread implements Runnable {
    String name;
    Thread t1;

    SmartThread(String name) {
        this.name = name;
        t1 = new Thread(this,"man");
        t1.setPriority(Thread.MIN_PRIORITY);  //1
        t1.setPriority(Thread.NORM_PRIORITY);  //5
        t1.setPriority(Thread.MAX_PRIORITY);  //10
        t1.start();
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            System.out.println(i+t1.getName());
            Thread.currentThread().setName("new name");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

// priority is 1 to 10. 10 is max priority
public class Solution {

    public static void main(String[] args) {
        SmartThread st1 = new SmartThread("man");

    }
}

