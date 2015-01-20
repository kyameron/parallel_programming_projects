/*
 * Cameron Boyd
 * CECS 327 
 * Homework 4
 * 
 * User class defines a user of the bathroom by name and gender
 */

public class User implements Runnable
{
	
	private String gender = "";
	private String toothpaste = "the toothpaste";
	private String teeth = "";
	
	private Object monitor;
	
	public User(String mGender,String mName){
		
		gender = mGender;
		Thread.currentThread().setName(mName);
		teeth = Thread.currentThread().getName() + "";
		monitor = SharedBathroom.monitor;
	}
	
	public void brush(){
		
		System.out.println(teeth + ", who is a(n) " 
				+ gender + " brushed with " + toothpaste);
	}
	
	public synchronized void enterMale(){
		
			try {
				synchronized(monitor){
					while(SharedBathroom.women.get() != 0){
						monitor.wait();
					}
					
					SharedBathroom.men.getAndIncrement();
					System.out.println(SharedBathroom.men + " dudes in the bathroom");
				}
				
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
	
	
	public void leaveMale(){
		synchronized(monitor){
			SharedBathroom.men.getAndDecrement();
			System.out.println(SharedBathroom.men + " dudes in the bathroom");
			monitor.notifyAll();
		}
		
	}
	
	public synchronized void enterFemale(){
	
			try {
				synchronized(monitor){
					while(SharedBathroom.men.get() != 0){
						monitor.wait();
					}
					SharedBathroom.women.getAndIncrement();
					System.out.println(SharedBathroom.women + " ladies in the bathroom");
				}		

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	}
	
	public void leaveFemale(){
		synchronized(monitor){
			SharedBathroom.women.getAndDecrement();
			System.out.println(SharedBathroom.women + " ladies in the bathroom");	
			monitor.notifyAll();
		}
		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
//		while(true){
		
		//Two types of threads each attempt to access the bathroom
		//brush, and then leave. 
		
			if(gender.equals("M")){
				enterMale();
				brush();
				leaveMale();
			}else if(gender.equals("F")){
				enterFemale();
				brush();
				leaveFemale();
			}
			else{
				System.out.println(gender);
			}
//		}
		
	}
}
