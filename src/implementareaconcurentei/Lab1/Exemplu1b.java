package implementareaconcurentei.Lab1;



/*
 * Exemplu de join intre threaduri 
 * 
 * Vezi poza:
 * https://www.google.com/search?q=java+thread+join&client=firefox-b-ab&source=lnms&tbm=isch&sa=X&ved=2ahUKEwjJpsfe-OjaAhXC16QKHTKKBIMQ_AUoAnoECAAQBA&biw=1536&bih=710#imgrc=60jfushMs_ZOSM:
 * 
 * Cand dai t1.join(), t2.join(), main-ul asteapta ca t1 si t2 sa isi termine executia
 */

public class Exemplu1b {
	public static int count = 0;
	
	public static synchronized void counter() {
		count++;
	}

	public static void main(String[] args) {
		Runnable runnable = () -> {
			String currentThreadName = Thread.currentThread().getName();
			for (int i = 0; i < 10; i++) {
				// exemplul 1
				/*
				int prevCount = count;
				//count++;
				System.out.println(currentThreadName + " is incrementing count = " + prevCount + " to count = " + count++);
				*/
				// exemplu 2
				counter();
			}
		};
		
		// creeam 2 threaduri ce vor contoriza amandoua aceeiasi resursa
		Thread t1 = new Thread(runnable);
		Thread t2 = new Thread(runnable);
		
		// pornim cele doua threaduri
		t1.start();
		t2.start();
		
		try { // main asteapta ca t1 si t2  sa se termine
			t1.join();
			t2.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		// afisam valuarea lui count, main ajunge la linia asta inainte de t1 si t2 sa se termine, si o sa afiseze count = 0 daca nu facem cu join
		System.out.println("count value = " + count);
	}

}
