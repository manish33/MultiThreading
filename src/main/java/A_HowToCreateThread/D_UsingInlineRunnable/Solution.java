package A_HowToCreateThread.D_UsingInlineRunnable;

public class Solution {

    public static void main(String[] args) {
        Thread st = new Thread(new Runnable() {
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
        });


        Thread st2 = new Thread("thread-2"){
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
        };

        st.start();
        st2.start();
    }
}
