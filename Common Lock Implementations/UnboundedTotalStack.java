/*
 * Cameron Boyd
 * CECS 327
 * Homework 6
 * 
 * List-based concurrent unbounded total stack.
 * 
 */

import java.util.EmptyStackException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;


public class UnboundedTotalStack<T> {
	ReentrantLock pushLock, popLock;
	AtomicInteger size;
	Node head;
	
	public UnboundedTotalStack(){
		head = new Node(null);
		size = new AtomicInteger(0);
		pushLock = new ReentrantLock();
		popLock = new ReentrantLock();
	}

	public void push(T x){
		pushLock.lock();
		try{
			Node e = new Node(x);
			e.next = head.next;
			head.next = e;
		}finally{
			pushLock.unlock();
		}
	}
	
	public T pop() throws EmptyStackException{
		T result;
		popLock.lock();
		try{
			if(head.next == null){
				throw new EmptyStackException();
			}
			result = (T) head.next.value;
			head.next = head.next.next;
		}finally{
			popLock.unlock();
		}
		return result;
	}
}
