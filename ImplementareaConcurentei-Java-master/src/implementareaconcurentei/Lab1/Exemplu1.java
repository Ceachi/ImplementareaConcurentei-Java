
package implementareaconcurentei.Lab1;

/**
 *
 *
 *Din prima carte de pe moodle sa ne uitam la pagina 33
 * @author CeachiBogdan
 */
public class Exemplu1 {
    
    
    static class MyThread implements Runnable {

        @Override
        public void run() {
            Runnable runnable = () -> System.out.println(Thread.currentThread().getName());
            Thread t2 = new Thread(runnable);
            t2.start();
            System.out.println(Thread.currentThread().getName());
        }
        
    }
    
    public static void main(String[] args) throws InterruptedException {
        MyThread m1 = new MyThread();
        Thread t1 = new Thread(m1);
        
        t1.start();
        //t1.yield();
        //t1.join();
       System.out.println(Thread.currentThread().getName());
    }
    
}
