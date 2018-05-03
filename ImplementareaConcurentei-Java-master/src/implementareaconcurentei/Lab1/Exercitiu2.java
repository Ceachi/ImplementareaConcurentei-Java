package implementareaconcurentei.Lab1;

import java.util.concurrent.ThreadLocalRandom;

/*
 * 
 * 
 * 2.	Scrieti un program in care threadul principal creaza un thread secundar si ii da un nume.
Thread-ul secundar isi scrie numele si afiseaza numerele de la 1 la 10 in forma:
"nume:numar".
 */


public class Exercitiu2 {

	
	public static void main (String args[]) {
		
		Runnable obj = () -> {
			for (int i = 1; i <= 10; i++) {
				System.out.println(Thread.currentThread().getName() + " : " + i);
			}
		};
		
		Thread t1 = new Thread(obj);
		t1.setName("Bogdan");
		t1.start();
		
		
		
	}
}
