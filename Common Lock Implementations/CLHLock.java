/*
 * Cameron Boyd
 * CECS 327 
 * Homework 4
 * 
 * CLH lock with tryLock()implementation.
 * false means the lock is available
 */

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;


public class CLHLock implements Lock{
	
	AtomicReference<QNode> tail = new AtomicReference<QNode>(new QNode());
	ThreadLocal<QNode> myPred;
	ThreadLocal<QNode> myNode;

	public CLHLock(){
		tail = new AtomicReference<QNode>(new QNode());
		myNode = new ThreadLocal<QNode>(){
			protected QNode initialValue(){
				return null;
			}
		};
	}
	
	@Override
	public void lock() {
		// TODO Auto-generated method stub
		QNode qnode = myNode.get();
		qnode.locked = true;
		QNode pred = tail.getAndSet(qnode);
		myPred.set(pred);
		while(pred.locked){};
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
		QNode qnode = myNode.get();
		qnode.locked = true;
		QNode pred = tail.getAndSet(qnode);
		myPred.set(pred);
		if(pred.locked){
			return true;
		}
		else{
			return false;
		}
	}

	@Override
	public boolean tryLock(long time, TimeUnit unit)
			throws InterruptedException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void unlock() {
		// TODO Auto-generated method stub
		QNode qnode = myNode.get();
		qnode.locked = false;
		myNode.set(myPred.get());
	}
	
	class QNode{
		boolean locked = false;
		QNode next = null;
	}

}
