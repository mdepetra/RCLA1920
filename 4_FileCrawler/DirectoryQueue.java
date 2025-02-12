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

// Classe per la modelizzazione della coda per la comunicazione tra produttore e consumatori
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
	
	// Metodo per prelevare un elemento dalla coda
	public String get() { 
		lock.lock(); 
		try {
			try{
				while (isEmpty() && !isEnd()) 
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
	
	// Metodo per l'inserimento di un elemento nella coda
	public void put(String s) {
		lock.lock();
		try {
			boolean e = isEmpty();
			nameDir.add(s);
			// Se la coda era vuota, si segnala la presenza del nuovo elemento
			if(e)
				notEmpty.signalAll();
		} finally {
			lock.unlock();
		}
	}
	
	// Metodo per la modifica del flag di fine esplorazione
	public void setEnd() {
		lock.lock();
		try {
			this.flag = true;
			notEmpty.signalAll();
		} finally {
			lock.unlock();
		}
	}
	
	// Metodo per verificare se la coda è vuota o meno
	public boolean isEmpty() {
		return this.nameDir.size() == 0;
	}
	
	// Metodo per verificare lo status del flag di fine esplorazione
	public boolean isEnd() {
		return this.flag;
	}
}
