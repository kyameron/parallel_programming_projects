import java.util.concurrent.atomic.AtomicInteger;


public class GoQueue implements Runnable{
	private static BoundedPartialQueue<Node> nodeQueue;
	private static UnboundedTotalStack<Node> nodeStack;
	private static BoundedStack<Node> boundedNodeStack;
	private static LockFreeQueueBounded<AtomicNode> lockFreeQueue;
	private static LockFreeStackBounded<AtomicNode> lockFreeStackArray;
	private static LockFreeQueueBoundedArray<AtomicNode> lockFreeQueueArray;
	
	public static void main(String[] args){
		
		Node currNode = null;
		
		nodeQueue = new BoundedPartialQueue<Node>(2);
		nodeStack = new UnboundedTotalStack<Node>();
		boundedNodeStack = new BoundedStack<Node>(2);
		lockFreeQueue = new LockFreeQueueBounded<AtomicNode>();
		lockFreeStackArray = new LockFreeStackBounded<AtomicNode>(2);
		lockFreeQueueArray = new LockFreeQueueBoundedArray<>(2);
				
		Thread one_1 = new Thread(new GoQueue());
		Thread two_1 = new Thread(new GoQueue());
//		Thread three_1 = new Thread(new GoQueue());
		one_1.start();
		two_1.start();
//		three_1.start();
		
		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		Node me = new Node((int)Math.random()*3 + 1);
		AtomicNode me2 = new AtomicNode((int)Math.random()*3 + 1);
		
		nodeQueue.enq(me);
		
		nodeStack.push(me);
		
		boundedNodeStack.push(me);
		
		lockFreeQueue.enq(me2);
		
		lockFreeStackArray.push(me2);
		
		lockFreeQueueArray.enq(me2);
		
		System.out.println(nodeStack.pop());
		
		System.out.println(boundedNodeStack.pop());
		
		System.out.println(lockFreeQueue.deq());
		
		System.out.println(lockFreeStackArray.pop());
		
		System.out.println(lockFreeQueueArray.deq());
	}
}
