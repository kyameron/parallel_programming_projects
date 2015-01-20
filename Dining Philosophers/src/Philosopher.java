/*
	In this solution, all chopsticks start off
	dirty. Philosophers are very generous, so
	they clean the chopstick and give it to whoever
	requested it in the requests array.
*/
public class Philosopher implements Runnable{
	Thread[] requests = new Thread[5];
	
	Thread me;
		
	Chopstick chop[];
	
	public Philosopher(Chopstick mChop[]){
		chop = mChop;
	}
	
	public synchronized void eat(){
		System.out.println(me + " is eating");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			chop[getID()].dirty();
			chop[(getID()+1)%5].dirty();
	
	}
	
	public synchronized void giveStick(){
		if(requests[getID()]!=null || (requests[(getID()+1)%5]!=null)){
			if(chop[getID()].user == me && chop[getID()].isDirty()){
				chop[getID()].clean();
				chop[getID()].user = requests[getID()];
				requests[getID()] = null;
				System.out.println("ID "+getID()+" Giving 0 to "+chop[getID()].user);					
			}
			if(chop[(getID()+1)%5].user == me && chop[(getID()+1)%5].isDirty()){
				chop[(getID()+1)%5].clean();
				chop[getID()].user = requests[(getID()+1)%5];
				requests[getID()] = null;
				System.out.println("ID "+getID()+" Giving 1 to "+chop[(getID()+1)%5].user);			
			}
		}
	}
	
	public int getID(){
		return Integer.parseInt(Thread.currentThread().getName());
	}

	@Override
	public void run() {
		while(true){
			
		me = Thread.currentThread();
		//requests
			giveStick();
		
		
			if(chop[getID()].user != me){
				requests[getID()] = me;
//				System.out.println(getID() + " requesting 0!");					
			}
			if(chop[(getID()+1)%5].user != me){
				requests[(getID()+1)%5] = me;
//				System.out.println(getID() + " requesting 1!");
			}
			else if((chop[getID()].user == me && !chop[getID()].isDirty())
					&& (chop[(getID()+1)%5].user == me && !chop[(getID()+1)%5].isDirty())
					){
				eat();									
			}
			else{
//					System.out.println("--------------------");
//					System.out.println("Unaccounted for case for " + me);
//					System.out.println("--------------------");
//					System.out.println("Chopstick 0 ");
//					System.out.println(chop[getID()].user + " Dirty: "+chop[getID()].isDirty());
//					System.out.println("Chopstick 1 ");
//					System.out.println(chop[(getID()+1)%5].user + " Dirty: "+chop[(getID()+1)%5].isDirty());
//					System.out.println("--------------------");				
			}
		}
	}
}
