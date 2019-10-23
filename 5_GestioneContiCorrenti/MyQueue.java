import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class MyQueue {

	private ReentrantLock lock;
	private final Condition notEmpty;
	private LinkedList<String> nameDir;
	private boolean endFlag;
	private int
	
	public MyQueue() {
		this.lock = new ReentrantLock();
		this.notEmpty = lock.newCondition();
		this.nameDir = new LinkedList<String>();
		this.endFlag = false;
	}
}
