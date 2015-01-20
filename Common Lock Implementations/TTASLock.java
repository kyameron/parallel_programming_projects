/*
 * Cameron Boyd
 * CECS 327 
 * Homework 4
 * 
 * TTAS lock with tryLock()implementation.
 * false means the lock is available
 */

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class TTASLock implements Lock{
	AtomicBoolean state = new AtomicBoolean(false);
	public void lock(){
		while(true){
			while(state.get()){};
			if(!state.getAndSet(true))
				return;
		}
	}
	
	public void unlock(){
		state.set(false);
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
		if(state.get()){
			return true;
		}
		if(!state.getAndSet(true))
			return false;
		return true;
	}

	@Override
	public boolean tryLock(long time, TimeUnit unit)
			throws InterruptedException {
		// TODO Auto-generated method stub
		return false;
	}
}
