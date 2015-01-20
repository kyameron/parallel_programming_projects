
public class Chopstick {
	Chopstick next;
	Thread user;
	
	private boolean dirty;
	
	private int num;
	
	public Chopstick(int mNum){
		num = mNum;
		dirty = true;
	}
	
	public int getNum(){
		return num;
	}
	
	public boolean isDirty(){
		if(dirty){
			return true;
		}else{
			return false;
		}
	}
	
	public void dirty(){
		dirty = true;
	}
	
	public void clean(){
		dirty = false;
	}
	
	public Thread getUser(){
		return user;
	}
}
