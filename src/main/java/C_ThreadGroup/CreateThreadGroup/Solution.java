package C_ThreadGroup.CreateThreadGroup;

public class Solution {

    public static void main(String[] args) {
        ThreadGroup tg1 = new ThreadGroup("FirstThreadGroup");
        System.out.println(tg1.getParent().getName());
        ThreadGroup tg2 = new ThreadGroup(tg1,"FirstThreadGroup");
        System.out.println(tg2.getParent().getName());


        Thread t1 = new Thread(tg1,"thread 1");
        Thread t2 = new Thread(tg1,"thread 1");

       tg1.list();
        System.out.println(tg1.activeCount());
        System.out.println(tg1.activeGroupCount());
        Thread[] t = new Thread[tg1.activeCount()];
        tg1.enumerate(t);
        for(Thread x: t){
            System.out.println(x.getName());
        }
    }
}
