package implementareaconcurentei.Lab1;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/*
 * 
 * 
3.	In programul de la 2 creati  6 thread-uri cu acelasi comportament, fiecare 

avand numele “nume1”, …, “nume6”; 
modificati programul astfel: 
     pentru fiecare numar generate aleator un timp in milisecunde si apelati sleep 

intre doua afisari.
Pentru generarea numerelor aleatoare folositi
https://docs.oracle.com/javase/tutorial/essential/concurrency/threadlocalrandom.html
 */


public class Exercitiu3 {

	
	public static void main (String args[]) {
		Runnable runnable = () -> {
			for (int i = 1; i <= 10; i++) {
				try {
					System.out.println(Thread.currentThread().getName() + " : " + i);
					Thread.sleep(ThreadLocalRandom.current() .nextInt(500, 2000));
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		
		List<Thread> list = new ArrayList<>();
		
		for (int i = 1; i <= 6; i++) {
			Thread t = new Thread(runnable);
			
			t.setName("nume" + i);
			list.add(t);
		}
		
		list.forEach((x) -> x.start());
		
		
	}
}
