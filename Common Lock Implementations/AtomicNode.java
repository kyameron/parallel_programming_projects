import java.util.concurrent.atomic.AtomicReference;


public class AtomicNode<T> {
	public T value;
	public AtomicReference<AtomicNode<T>> next;
	public AtomicNode(T mValue){
		value = mValue;
		next = new AtomicReference<AtomicNode<T>>();
	}
}
