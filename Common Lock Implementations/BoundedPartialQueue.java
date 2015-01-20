/*
 * Cameron Boyd
 * CECS 327
 * Homework 6
 * 
 * Array-based concurrent partial queue
 */

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class BoundedPartialQueue<T> {
	ReentrantLock enqLock, deqLock;
	Condition notEmptyCondition, notFullCondition;
	AtomicInteger size;
	Node head, tail;
	Node nodeArray[];
	int capacity;
	
	public BoundedPartialQueue(int mCapacity){
		capacity = mCapacity;
		nodeArray = new Node[mCapacity];
		tail = head;
		head = nodeArray[0];
		size = new AtomicInteger(0);
		enqLock = new ReentrantLock();
		notFullCondition = enqLock.newCondition();
		deqLock = new ReentrantLock();
		notEmptyCondition = deqLock.newCondition();
	}
	
	public void enq(T x){
		boolean mustWakeDequeuers = false;
		enqLock.lock();
		try{
			while(size.get() == capacity){
				try {
					notFullCondition.await();
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			Node e = new Node(x);
			tail = nodeArray[(size.get()+1)%capacity];
			for(int i=0;i<capacity;i++){
				if(nodeArray[i] == null){
					nodeArray[i] = e;
					break;
				}
			}
			if(size.getAndIncrement() == 0){
				mustWakeDequeuers = true;
			}
		}finally{
			enqLock.unlock();
		}
		if(mustWakeDequeuers){
			deqLock.lock();
			try{
				notEmptyCondition.signalAll();
			}finally{
				deqLock.unlock();
			}
		}
		
	}
	
	public T deq(){
		T result = null;
		boolean mustWakeEnqueuers = false;
		deqLock.lock();
		try{
			while(size.get() == 0){
				try {
					notEmptyCondition.await();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			result = (T) head.value;
			for(int i=0;i<capacity;i++){
				if(nodeArray[i] == head){
					head = nodeArray[(i+1)%capacity];
					break;
				}
			}
			if(size.getAndDecrement() == capacity){
				mustWakeEnqueuers = true;
			}
		}finally{
			deqLock.unlock();
		}
		if(mustWakeEnqueuers){
			enqLock.lock();
			try{
				notFullCondition.signalAll();
			}finally{
				enqLock.unlock();
			}
		}
		
		return result;
	}
	
}
