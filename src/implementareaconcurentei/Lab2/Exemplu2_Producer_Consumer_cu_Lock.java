
package implementareaconcurentei.Lab2;

import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 * TODO:
 * ceva nu mi-a iesit la exemplul asta
 * @author CeachiBogdan
 */

class Buffer { 
    private String message;
    private boolean empty = true;
    
    // adaugam Lock si Condition lock
    private Lock bufferLock = new ReentrantLock();
    private Condition condVar = bufferLock.newCondition();  
    // --------------------------------------------------------------
    
    
    public synchronized String take() {
    	bufferLock.lock(); // adaugat nou
    	try {//nou
    		while(empty) { 
                try {
                    condVar.await(); // nou, unde aveai wait, acum ai condition.await
                } catch (InterruptedException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        empty = true;
        condVar.signalAll(); /// in loc de notifyAll avem signalAll cu condition
        return message;
    	}finally {
    		bufferLock.unlock();
    	}
    }
    public synchronized void put(String message) {
    	bufferLock.lock(); // adaugat nou
    	try {
	        while(!empty) {
	            try {
	               condVar.await(); // la fel
	            } catch (InterruptedException ex) {
	               System.out.println(ex.getMessage());
	            }
	        }
	        
	        System.out.println("Producer put  in the buffer message  = " + message);
	        empty = false;
	        this.message = message; // pune mesajul
	        condVar.signalAll(); // la fel si pentru notifyAll pui condVar
    	}finally {
    		bufferLock.unlock();
    	}
    }
}
class Producer implements Runnable {
    private Buffer buffer;
    public Producer(Buffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {
        String importantInfo[] = {
            "Mares ear oats",
            "Does eat oats",
            "Little lambs eat ivy",
            "A kid will eat ivy too"
        };
        Random random = new Random();
        
        for (int i = 0; i < importantInfo.length; i++) {
            try {
                buffer.put(importantInfo[i]);
                
                Thread.sleep(random.nextInt(5000));
            } catch (InterruptedException ex) {
                System.out.println(ex.getMessage());
            }
        }
        buffer.put("DONE");
    }
}
class Consumer implements Runnable {
    private Buffer buffer;
    public Consumer(Buffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {
        Random random = new Random();
        for(String message = buffer.take(); ! message.equals("DONE"); message = buffer.take()) {
            System.out.format("Consumer takes the message =  %s%n", message);
            try {
                Thread.sleep(random.nextInt(5000));
            } catch (InterruptedException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
}








public class Exemplu2_Producer_Consumer_cu_Lock {
    
    public static void main(String[] args) {
        
        Buffer buffer = new Buffer();
    
        Thread t1 = new Thread(new Producer(buffer));
        Thread t2 = new Thread(new Consumer(buffer));
        
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