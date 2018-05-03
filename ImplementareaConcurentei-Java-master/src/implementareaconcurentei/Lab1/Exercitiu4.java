package implementareaconcurentei.Lab1;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/*
 * 
 * 

4.	 In programul de la 2 creati  6 thread-uri cu acelasi comportament, fiecare 

avand numele “nume1”, …, “nume6” si 
modificati prioritatea ultimelor trei, dandu-i valoarea maxima.
 */


public class Exercitiu4 {

	
	public static void main (String args[]) {
		Runnable runnable = () -> {
			for (int i = 1; i <= 10; i++) {
				try {

					Thread.sleep(ThreadLocalRandom.current() .nextInt(1400, 2000));
					System.out.println(Thread.currentThread().getName() + " : " + i);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					System.out.println(e.getMessage());
				}
			}
		};
		
		List<Thread> list = new ArrayList<>();
		
		for (int i = 1; i <= 6; i++) {
			Thread t = new Thread(runnable);
			t.setPriority(Thread.MIN_PRIORITY);
			if(i > 3) {
				t.setPriority(Thread.MAX_PRIORITY);
			}
			t.setName("nume" + i);
			list.add(t);
		}
		
		list.forEach((x) -> x.start());
		
		
	}
}
