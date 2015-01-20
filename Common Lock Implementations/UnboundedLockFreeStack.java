/*
 * Cameron Boyd
 * CECS 327
 * Homework 6
 * 
 * Unbounded, lock-free stack with custom memory management using AtomicReferenceStamp
 */

import java.util.EmptyStackException;
import java.util.concurrent.atomic.AtomicStampedReference;

public class UnboundedLockFreeStack<T> {
	AtomicStampedReference<AtomicStampNode> top = new AtomicStampedReference<AtomicStampNode>(null,0);
	static final int MIN_DELAY = 500;
	static final int MAX_DELAY = 1000;
	Backoff backoff = new Backoff(MIN_DELAY,MAX_DELAY);
	
	protected boolean tryPush(AtomicStampNode node){
		int[] topStamp = new int[1];
		int[] nextStamp = new int[1];
		int stamp[] = new int[1];
		AtomicStampNode oldTop = top.get(topStamp);
		node.next.set(oldTop, topStamp[0]+1);
		return(top.compareAndSet(oldTop, node,topStamp[0],topStamp[0]+1));		
	}
	
	public void push(T value){
		AtomicStampNode node = new AtomicStampNode(value);
		while(true){
			if(tryPush(node)){
				return;
			}else{
				try {
					backoff.backoff();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	protected AtomicStampNode tryPop() throws EmptyStackException{
		int[] topStamp = new int[1];
		int[] nextStamp = new int[1];
		int[] stamp = new int[1];
		AtomicStampNode oldTop = top.get(topStamp);
		if(oldTop == null){
			throw new EmptyStackException();
		}
		AtomicStampNode newTop = (AtomicStampNode) oldTop.next.get(nextStamp);
		if(top.compareAndSet(oldTop, newTop,topStamp[0],topStamp[0]+1)){
//			FREE_MEM
			return oldTop;
		}else{
			return null;
		}
	}
	
	public T pop() throws EmptyStackException{
		while(true){
			AtomicStampNode returnNode = tryPop();
			if(returnNode!=null){
				return (T) returnNode.value;
			}else{
				try {
					backoff.backoff();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
