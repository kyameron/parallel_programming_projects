import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;


public class BoundedStack<T> {
	ReentrantLock mLock;
	Condition notEmptyCondition,notFullCondition;
	AtomicInteger size;
	Node nodeArray[];
	int capacity;
	
	public BoundedStack(int mCapacity){
		capacity = mCapacity;
		nodeArray = new Node[mCapacity+1];
		size = new AtomicInteger(0);
		mLock = new ReentrantLock();
		notEmptyCondition = mLock.newCondition();
		notFullCondition = mLock.newCondition();
	}
	
	public void push(T x){
		boolean mustWakeDequeuers = false;
		mLock.lock();
		try{
			Node e = new Node(x);
			nodeArray[size.intValue()+1] = e;
			if(size.getAndIncrement() == 0){
				mustWakeDequeuers = true;
			}
		}finally{
			mLock.unlock();
		}
		if(mustWakeDequeuers){
			mLock.lock();
			try{
				notEmptyCondition.signalAll();
			}finally{
				mLock.unlock();
			}
		}
		
	}
	
	public T pop(){
		T result = null;
		boolean mustWakeEnqueuers = false;
		mLock.lock();
		try{
			while(size.get() == 0){
				try {
					notEmptyCondition.await();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			result = (T) nodeArray[size.intValue()];
			nodeArray[size.intValue()] = null;
			if(size.getAndDecrement() == capacity){
				mustWakeEnqueuers = true;
			}
		}finally{
			mLock.unlock();
		}
		if(mustWakeEnqueuers){
			mLock.lock();
			try{
				notFullCondition.signalAll();
			}finally{
				mLock.unlock();
			}
		}
		
		return result;
	}
	
	
	
}
