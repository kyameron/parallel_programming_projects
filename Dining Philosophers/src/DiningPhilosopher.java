/*
	Cameron Boyd
	CECS 327

	A solution to the classic Dining Philosophers problem
	Let all philosophers sit at the table.
	How did we only set 5 chopsticks?!
*/

public class DiningPhilosopher{
	public static void main(String[] args){

		//Make chopsticks array. Populate it.
		Chopstick chop[] = new Chopstick[5];
		for(int i=0; i< chop.length;i++){
			chop[i] = new Chopstick(i);
		}

		Philosopher philly = new Philosopher(chop);
				
		Thread[] threads = new Thread[5];
		for(int i = 0; i< threads.length;i++){
			threads[i] = new Thread(philly,""+i+"");
			if(i!=threads.length-1)
			chop[i].user = threads[i];
		}
		chop[threads.length-1].user = threads[threads.length-1];
		
		threads[0].start();
		threads[1].start();
		threads[2].start();
		threads[3].start();
		threads[4].start();
		
		
	}
}
