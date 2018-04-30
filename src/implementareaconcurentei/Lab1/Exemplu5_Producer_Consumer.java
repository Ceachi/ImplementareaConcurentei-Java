
package implementareaconcurentei.Lab1;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 * 
 * PRODUCATOR ----> BUFFER -----> CONSUMATOR
 * Doua threaduri comunica prin intemrmediul unui buffer (memorie partajata):
 *  - thread-ul Producator creaza datele si le pune in buffer
 *  - thread-ul Consumator ia datele din buffer si le prelucreaza
 * Probleme de coordonare:
 *  1. Producatorul si consumatorul nu vor accesa bufferul simultan.
 *  2. Producatorul nu va pune in buffer date noi daca datele din buffer nu au 
 * fost consumate.
 *  3. Cele doua thread-uri se vor anunta unul pe altul cand starea buferului 
 * s-a schimbat
 * 
 * Modelul Producator Consumator
 * implementarea foloseste blocuri cu garzi
 * thread-ul este suspendat pana cand o anume conditie este satisfacuta
 *
 *
 * @author CeachiBogdan
 */

class PCDrop {
    private String message;
    private boolean empty = true;
    
    public synchronized String take() {
        while(empty) {
            try {
                wait();
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
        empty = false;
        this.message = message;
        notifyAll();
    }
}
class Producer implements Runnable {
    private PCDrop drop;
    public Producer(PCDrop drop) {
        this.drop = drop;
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
                drop.put(importantInfo[i]);
                
                Thread.sleep(random.nextInt(5000));
            } catch (InterruptedException ex) {
                System.out.println(ex.getMessage());
            }
        }
        drop.put("DONE");
    }
}
class Consumer implements Runnable {
    private PCDrop drop;
    public Consumer(PCDrop drop) {
        this.drop = drop;
    }

    @Override
    public void run() {
        Random random = new Random();
        for(String message = drop.take(); ! message.equals("DONE"); message = drop.take()) {
            System.out.format("MESSAGE RECEIVED %s%n", message);
            try {
                Thread.sleep(random.nextInt(5000));
            } catch (InterruptedException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
}








public class Exemplu5_Producer_Consumer {
    
    public static void main(String[] args) {
        
        PCDrop drop = new PCDrop();
    
        Thread t1 = new Thread(new Producer(drop));
        Thread t2 = new Thread(new Consumer(drop));
        
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