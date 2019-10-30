import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TPool {

	private ExecutorService executor;
	private ThreadPoolExecutor tpe;

	// Costruttore
	public TPool () {
		this.executor = Executors.newCachedThreadPool();
	    this.tpe = (ThreadPoolExecutor) executor;
	}
	
	// Esegue un CATask
	public void executeCATask (CATask ca) {
		this.tpe.execute(ca);
	}
	
	// Chiude il Tpool
	public void closePool() {
		this.tpe.shutdown();

		try { 
			this.tpe.awaitTermination(1000, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			System.out.println("ATTENZIONE: i threads non hanno finito in 1000 secondi!");
		}
		
		System.out.println("TPool è chiuso.");
	}
}