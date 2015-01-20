/*
 * Cameron Boyd
 * CECS 327
 * Homework 6
 * 
 * Lock-free, array-based, bounded stack
 */

import java.util.EmptyStackException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;


public class LockFreeStackBounded<T> {
	 private AtomicReference<AtomicNode<T>> tail;
	 private AtomicReference<AtomicNode<T>> head;
	 
	private int capacity;
	private AtomicNode<T>[] nodeArray;
	private AtomicInteger size;
	 
	public LockFreeStackBounded(int mCapacity){
		head = new AtomicReference<AtomicNode<T>>(new AtomicNode<T>(null));
		tail = new AtomicReference<AtomicNode<T>>(head.get());
		
		capacity = mCapacity;
		
		//Make room for sentinel node
		nodeArray = new AtomicNode[capacity+1];
		
		size = new AtomicInteger(0);
		
		//Sentinel node
		nodeArray[0] = new AtomicNode<T>(null);
		
	}
	
	public void push(T value){
		AtomicNode<T> node = new AtomicNode<T>(value);
		while(true){
			//Handle full queue. Can not use Condition class
			int mSize = size.get();
			if(mSize >= capacity){
				continue;
			}
			//Insert into size+1 and increment size
			nodeArray[size.getAndIncrement()+1] = node;
			return;
		}
		
	}
	
	public AtomicNode pop() throws EmptyStackException{
		while(true){
			int mSize = size.get();
			if(mSize<=0 || mSize>capacity){
				throw new EmptyStackException();
			}else{	
				AtomicNode popMe = nodeArray[size.getAndDecrement()];
				return popMe;
			}
		}
	}
}

