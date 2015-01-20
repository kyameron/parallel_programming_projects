/*
 * Cameron Boyd
 * CECS 327
 * Fine-grain Linked List contains method.
 * 
 * While curr's key is not the key we are searching for,
 * we lock curr and pred, increment in the CS,
 * and then unlock. Once discovered, we have already
 * unlocked the nodes so it will just return our result.
 * We must have a CS at the traversal in case another thread
 * tries to remove or add a node in a way that interferes with our
 * traversal.
 */

import java.util.concurrent.locks.ReentrantLock;

public class FineGrainLinkedList<T> {
	Node curr, pred, head;
	
	public boolean contains(T item){
		int key = item.hashCode();
		Node curr = head;
		
		while(curr.key < key){
			curr.lock();pred.lock();
			pred = curr;
			curr = curr.next;
			curr.unlock();pred.unlock();
		}
		return curr.key == key;
	}
	
	class Node extends ReentrantLock{
		Node me;
		Node next;
		int key;
	}
}
