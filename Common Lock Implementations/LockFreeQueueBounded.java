import java.util.EmptyStackException;
import java.util.concurrent.atomic.AtomicReference;


public class LockFreeQueueBounded<T> {
	 private AtomicReference<AtomicNode<T>> tail;
	 private AtomicReference<AtomicNode<T>> head;
	 
	
	public LockFreeQueueBounded(){
		head = new AtomicReference<AtomicNode<T>>(new AtomicNode<T>(null));
		//Need to init tail AtomicReference this way or there are problems
		tail = new AtomicReference<AtomicNode<T>>(head.get());
		
	}
	
	public void enq(T value){
		AtomicNode<T> node = new AtomicNode<T>(value);
		while(true){
			AtomicNode last = tail.get();
			AtomicNode next = (AtomicNode) last.next.get();
			if(last == tail.get()){
				if(next == null){
					if(last.next.compareAndSet(next, node)){
						tail.compareAndSet(last, node);
						return;
					}
				}else{
					tail.compareAndSet(last, next);
				}
			}
		}
		
	}
	
	public AtomicNode deq() throws EmptyStackException{
		while(true){
			AtomicNode<T> first = head.get();
			AtomicNode<T> last = tail.get();
			AtomicNode<T> next = first.next.get();
			if(first == head.get()){
				if(first == last){
					if(next == null){
						throw new EmptyStackException();
					}
					tail.compareAndSet(last, next);
				}else{
					T value = next.value;
					AtomicNode<T> popMe = next;
					if(head.compareAndSet(first, next))
						return popMe;
				}
			}
		}
	}
}
