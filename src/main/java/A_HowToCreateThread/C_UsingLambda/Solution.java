package A_HowToCreateThread.C_UsingLambda;

public class Solution {

    public static void main(String[] args) {


        Thread st = new Thread(()->{
            for(int i=0;i<100;i++){
                System.out.println(i);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
        }
        });

        st.start();
    }
}
