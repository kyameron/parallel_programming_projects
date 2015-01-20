/*
	Cameron Boyd

	A thread writes to an index
*/

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

class Bakery implements Lock,TimestampSystem,Runnable{

	boolean[] flag;
	Label[] label;
	
	public Bakery(int n){
		flag = new boolean[n];
		label = new Label[n];
		for(int i = 0; i < n; i++){
			flag[i] = false;
			label[i] = new Label(0);
		}
	}
	
	@Override
	public void lock() {
		// TODO Auto-generated method stub
		int i = (int) Thread.currentThread().getId();
		flag[i] = true;
		int maxLabel = 0;
		for(int j = 0; j < label.length; j++){
			maxLabel = Math.max(maxLabel, label[j].getLabel());
		}
		label[i].setLabel(maxLabel++);
		for(int k = 0; k < label.length; k++){
			if(label[i].getLabel() < label[k].getLabel() ||
					((label[i] == label[k])&& (i<k))){
				break;		
			}else{
				
			}
		}
	}

	@Override
	public void lockInterruptibly() throws InterruptedException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Condition newCondition() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean tryLock() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean tryLock(long arg0, TimeUnit arg1)
			throws InterruptedException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void unlock() {
		// TODO Auto-generated method stub
		flag[(int) Thread.currentThread().getId()] = false;
	}

	@Override
	public Timestamp[] scan() {
		// TODO Auto-generated method stub
		
		return null;
	}

	@Override
	public void label(Timestamp timestamp, int i) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("Go speed racer " + Thread.currentThread().getId());
	}
	
}
