import java.util.EmptyStackException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;


public class LockFreeQueueBoundedArray<T> {
	 private AtomicReference<AtomicNode<T>> tail;
	 private AtomicReference<AtomicNode<T>> head;
	 
	private int capacity;
	private AtomicNode<T>[] nodeArray;
	private AtomicInteger size;
	private AtomicInteger headIndex;
	 
	public LockFreeQueueBoundedArray(int mCapacity){
		head = new AtomicReference<AtomicNode<T>>(new AtomicNode<T>(null));
		tail = new AtomicReference<AtomicNode<T>>(head.get());
		
		//Change head index if head is deleted
		headIndex = new AtomicInteger(0);
		
		capacity = mCapacity;
		
		//Make room for sentinel node
		nodeArray = new AtomicNode[capacity+1];
		
		size = new AtomicInteger(0);
		
		//Sentinel node
		nodeArray[0] = new AtomicNode<T>(null);
		
	}
	
	public void enq(T value){
		AtomicNode<T> node = new AtomicNode<T>(value);
		while(true){
			//Handle full queue. Can not use Condition class
			int mSize = size.get();
			if(mSize >= capacity){
				continue;
			}
			//Insert into head slot, point head to new, and increment size
			nodeArray[(size.getAndIncrement()+headIndex.get())%capacity] = node;
			return;
		}
		
	}
	
	public AtomicNode deq() throws EmptyStackException{
		while(true){
			int mSize = size.get();
			if(mSize<=0 || mSize>capacity){
				throw new EmptyStackException();
			}else{	
				//Think of array as a circle.
				//Move head index around the array.
				size.getAndDecrement();
				AtomicNode popMe = nodeArray[(headIndex.getAndIncrement())%capacity];
								
				return popMe;
			}
		}
	}
}

