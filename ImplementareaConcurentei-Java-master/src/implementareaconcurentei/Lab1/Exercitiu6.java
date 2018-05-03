
package implementareaconcurentei.Lab1;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 * 
6.	Modificati programul de la 2 astfel incat sa existe doua thread-uri 

consumator, unul consuma numerele pare, 
iar celalalt numerele impare. 
 *
 * @author CeachiBogdan
 */

class Buffer3 { // implementarea Bufferului, acesta se face cu metode sincronizate
    private String message;
    private boolean empty = true;
    public static boolean par = true;
    public static boolean impar = true;
    
    public synchronized String take(boolean parity) { // accesul se face prin metode sincronizate
    	while(parity) { 
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
        par = false;
        if(Integer.parseInt(message)  % 2 == 0) {
        	par = true;
        }
        this.message = message; // pune mesajul
        notifyAll(); // anunta
    }
}
class Producer3 implements Runnable {
    private Buffer3 buffer;
    public Producer3(Buffer3 buffer) {
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
class Consumer3 implements Runnable {
    private Buffer3 buffer;
    public Consumer3(Buffer3 buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {
        Random random = new Random();
        
        for(String message = buffer.take(buffer.par); ! message.equals("DONE"); message = buffer.take()) {
        	Integer obj = Integer.parseInt(message);
        	if(obj % 2 != 0) {
        		return;
        	}
        	
        	System.out.println("Consumer1 takes the input = " + obj);
            try {
                Thread.sleep(random.nextInt(5000));
            } catch (InterruptedException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
}

class Consumer4 implements Runnable {
    private Buffer3 buffer;
    public Consumer4(Buffer3 buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {
        Random random = new Random();
        for(String message = buffer.take(); ! message.equals("DONE"); message = buffer.take()) {
        	Integer obj = Integer.parseInt(message);
        	if(obj % 2 == 0) {
        		return;
        	}
        	
        	System.out.println("Consumer2 takes the input = " + obj);
            try {
                Thread.sleep(random.nextInt(5000));
            } catch (InterruptedException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
}







public class Exercitiu6 {
    
    public static void main(String[] args) {
        
        Buffer3 buffer = new Buffer3();
    
        Thread t1 = new Thread(new Producer3(buffer));
        Thread t2 = new Thread(new Consumer3(buffer));
        Thread t3 = new Thread(new Consumer4(buffer));
        
        t1.start();
        t2.start();
        t3.start();
        
    }
}


/*
OUTPUT:
MESSAGE RECEIVED Mares ear oats
MESSAGE RECEIVED Does eat oats
MESSAGE RECEIVED Little lambs eat ivy
MESSAGE RECEIVED A kid will eat ivy too
*/