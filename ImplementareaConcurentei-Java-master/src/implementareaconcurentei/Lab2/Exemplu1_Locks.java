package implementareaconcurentei.Lab2;

import java.util.concurrent.locks.ReentrantLock;

/*
 * 
 * synchronized = acceseaza lacatul intern al resursei si
 * impune o programare structurata: primul thread care detine
 * resursa trebuie sa o si elibereze
 * 
 * obiectele din clasa Lock nu acceseaza lacatul resursei,
 * ci propriul lor lacat, permitant mai multa flexibilitate,
 * thread-urile pot accesa lacatele in orice ordine;
 * obiectele ReadWriteLock permit accesul simultan pentru 
 * mai multe threaduri
 * 
 * 
 * 
 * await(), cond.await(long time, TimeUnit unit)
 * 	thread-ul current intra in asteptare
 * 
 * signall()
 *  un singur thread curent intra in asteptare
 *  
 *  signalAll()
 *  toate thread-urile care asteapta sunt trezite
 *  
 *  pot exista mai multe conditii legate de un obiect Locks
 *  
 *  Lock obiectLock = new ReentrantLock();
 *  Condition condVar = obiectLock.newcondition();
 *  ( vezi video de pe yb ca sa intelegi condition)
 *  
 *  Imagini:
 *  https://www.google.com/search?q=java+locks&client=firefox-b-ab&source=lnms&tbm=isch&sa=X&ved=2ahUKEwjEtNPn0OnaAhUD3KQKHT77Cp4Q_AUoA3oECAAQBQ&biw=1536&bih=710#imgrc=SnZZqMntrdp26M:
 *  
 *  Un exemplu cu locks pe yb foarte bun:
 *  https://www.youtube.com/watch?v=fjMTaVykOpc
 */
public class Exemplu1_Locks {
	
	private Integer counter = 0;
	private final ReentrantLock lock = new ReentrantLock();
	
	public static void main(String[] args) {
		
		Exemplu1_Locks demo = new Exemplu1_Locks();
		
		Task task1 = demo.new Task();
		Task task2 = demo.new Task();
		
		Thread thread1 = new Thread(task1);
		
		Thread thread2 = new Thread(task2);
		
		thread1.start();
		thread2.start();
		
	}
	
	private class Task implements Runnable {
		public void run() {
			for(int i = 0; i < 5; i++) {
				performTask();
			}
		}
	}
	
	private  void performTask() {
		lock.lock();
		try {
			int temp = counter;
			counter++;
			System.out.println(Thread.currentThread().getName() +
					"- before: " + temp + "after:" + counter);
		}finally {
			lock.unlock();
		}
	}

}
