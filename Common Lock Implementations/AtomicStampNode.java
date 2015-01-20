import java.util.concurrent.atomic.AtomicStampedReference;


public class AtomicStampNode<T> {
	public T value;
	public AtomicStampedReference<AtomicStampNode<T>> next;
	public AtomicStampNode(T mValue){
		value = mValue;
		next = new AtomicStampedReference<AtomicStampNode<T>>(null, 0);
	}
}
