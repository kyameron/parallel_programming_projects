/*
 * Cameron Boyd
 * CECS 327 
 * Homework 4
 * 
 * MCS lock with tryLock()implementation.
 * false means the lock is available
 */

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class MCSLock implements Lock{
	AtomicReference<QNode> tail;
	ThreadLocal<QNode> myNode;
	
	public MCSLock(){
		tail = new AtomicReference<QNode>(null);
		myNode = new ThreadLocal<QNode>(){
			protected QNode initialValue(){
				return new QNode();
			}
		};
	}
	
	@Override
	public void lock() {
		// TODO Auto-generated method stub
		QNode qnode = myNode.get();
		QNode pred = tail.getAndSet(qnode);
		if(pred!=null){
			qnode.locked = true;
			pred.next = qnode;
			while(qnode.locked){};
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
		QNode qnode = myNode.get();
		QNode pred = tail.getAndSet(qnode);
		if(pred!=null){
			qnode.locked = true;
			pred.next = qnode;
			if(qnode.locked){
				return true;
			}else{
				return false;
			}
		}
		return true;
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
		if(qnode.next == null){
			if(tail.compareAndSet(qnode, null))
				return;
			while(qnode.next == null){};
		}
		qnode.next.locked = false;
		qnode.next = null;
	}
	
	class QNode{
		boolean locked = false;
		QNode next = null;
	}

}
