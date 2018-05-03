
package implementareaconcurentei.Lab1;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 * 
 *5.	Implementati problema producator-consumator cu urmatoarea modificare:
thread-ul producator produce un 10 numere intregi (eventual generate cu random) dupa 

care scrie un mesaj si isi inceteaza
 executia; thread-ul consumator consuma numerele (in ordinea in care sunt produse) si 

le afiseaza  dublate.

 * @author CeachiBogdan
 */

class Buffer2 { // implementarea Bufferului, acesta se face cu metode sincronizate
    private String message;
    private boolean empty = true;
    
    public synchronized String take() { // accesul se face prin metode sincronizate
        while(empty) { 
            try {
                wait(); // Object.wait  to suspend the current thread 
                
                /*
                 * The invocation of wait does not return until 
                 * another thread has issued a notification that some special event may have occurred
                 *  — though not necessarily the event this thread is waiting for:
                 */
            } catch (InterruptedException ex) {
                System.out.println(ex.getMessage());
            }
        }
        
        empty = true;
        notifyAll();
        return message;
    }
    public synchronized void put(String message) {
        while(!empty) {
            try {
                wait();
            } catch (InterruptedException ex) {
               System.out.println(ex.getMessage());
            }
        }
        
        System.out.println("Producer put  in the buffer message  = " + message);
        empty = false;
        this.message = message; // pune mesajul
        notifyAll(); // anunta
    }
}
class Producer2 implements Runnable {
    private Buffer2 buffer;
    public Producer2(Buffer2 buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {
        
        
        Random random = new Random();
        // generate 10 numbers to sent via buffer
        int lenght = 10;
        for (int i = 0; i < lenght; i++) {
            try {
            	Integer obj = random.nextInt(20);
                buffer.put(obj.toString());
                
                Thread.sleep(random.nextInt(5000));
            } catch (InterruptedException ex) {
                System.out.println(ex.getMessage());
            }
        }
        buffer.put("DONE");
    }
}
class Consumer2 implements Runnable {
    private Buffer2 buffer;
    public Consumer2(Buffer2 buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {
        Random random = new Random();
        for(String message = buffer.take(); ! message.equals("DONE"); message = buffer.take()) {
        	Integer obj = Integer.parseInt(message);
        	System.out.println("Consumer takes the input = " + obj);
            try {
                Thread.sleep(random.nextInt(5000));
            } catch (InterruptedException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
}








public class Exercitiu5 {
    
    public static void main(String[] args) {
        
        Buffer2 buffer = new Buffer2();
    
        Thread t1 = new Thread(new Producer2(buffer));
        Thread t2 = new Thread(new Consumer2(buffer));
        
        t1.start();
        t2.start();
        
    }
}


/*
OUTPUT:
MESSAGE RECEIVED Mares ear oats
MESSAGE RECEIVED Does eat oats
MESSAGE RECEIVED Little lambs eat ivy
MESSAGE RECEIVED A kid will eat ivy too
*/