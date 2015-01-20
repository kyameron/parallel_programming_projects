/*
 * Cameron Boyd
 * CECS 327 
 * Homework 4
 * 
 * Shared Bathroom:
 * 
 * As many men, or women <-- AtomicInteger
 * may enter into the bathroom <-- CS (where they can brush their teeth)
 * at one time, but opposite sex users can not enter together.
 */

import java.util.concurrent.atomic.AtomicInteger;


public class SharedBathroom 
{
	
	//Counter for individuals in the bathroom
	static AtomicInteger men = new AtomicInteger(0);
	static AtomicInteger women = new AtomicInteger(0);
	
	//Monitor object, to allow inter-thread communication
	static Object monitor = new Object();
	public static void main(String[] args){
		
		
		//Threads of class Male (M) or Female (F)
		Thread t1 = new Thread(new User("M","joe"));
		Thread t3 =new Thread(new User("F","tamara"));
		Thread t2 = new Thread(new User("M","timmy"));
		
		
		t3.start();
		t1.start();
		t2.start();
		
				
	}
}
