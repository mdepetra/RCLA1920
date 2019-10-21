import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 
 * Assegnamento 4 del Laboratorio di Reti di Calcolatori A
 * A.A. 2019/2020
 * @author Mirko De Petra, 549105
 *
 */
public class DirectoryQueue {

	private ReentrantLock lock;
	private final Condition notEmpty;
	private LinkedList<String> nameDir;
	private boolean flag;
	
	public DirectoryQueue() {
		this.lock = new ReentrantLock();
		this.notEmpty = lock.newCondition();
		this.nameDir = new LinkedList<String>();
		this.flag = false;
	}
	
	public String get() { 
		lock.lock(); 
		try {
			try{
				while (isEmpty()) 
					notEmpty.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			String name = this.nameDir.poll();
			return name;
		} finally {
			lock.unlock(); 
		}
	}
	
	public void put(String s) {
		lock.lock();
		try {
			boolean e = isEmpty();
			nameDir.add(s);
			if(e)
				notEmpty.signalAll();
		} finally {
			lock.unlock();
		}
	}
	
	public void setEnd() {
		lock.lock();
		try {
			this.flag = true;
		} finally {
			lock.unlock();
		}
	}
	
	public boolean isEmpty() {
		return this.nameDir.size() == 0;
	}
	
	public boolean isEnd() {
		return this.flag;
	}
}
