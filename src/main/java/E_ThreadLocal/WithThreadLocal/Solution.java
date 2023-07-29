package E_ThreadLocal.WithThreadLocal;

class Multiplication {
   private static ThreadLocal<Integer> digitNumber= new ThreadLocal<Integer>();
    public void setDigitNumber(Integer number){
        this.digitNumber.set(number);
    }
    public  Integer getDigitNumber(){
        return this.digitNumber.get();
    }
    public void printTable(){

        for(int i=1;i<11;i++){
            System.out.println(Thread.currentThread().getName()+":  "+digitNumber.get()+" x "+i+" = "+digitNumber.get()*i);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void removeThreadLocal(){
        digitNumber.remove();
    }
}

public class Solution {
    public static void main(String[] args) {

        Multiplication m1= new Multiplication();

     //   Multiplication m2= new Multiplication(); (use this to solve the problem in thread 2);
       new Thread(()->{
            m1.setDigitNumber(3);
            m1.printTable();
           m1.removeThreadLocal();
        }).start();
        new Thread(()->{
            m1.setDigitNumber(5);
            m1.printTable();
            m1.removeThreadLocal();
        }).start();


    }

}
