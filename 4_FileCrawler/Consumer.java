import java.io.File;

/**
 * 
 * Assegnamento 4 del Laboratorio di Reti di Calcolatori A
 * A.A. 2019/2020
 * @author Mirko De Petra, 549105
 *
 */

// Classe che modellizza il thread Consumatore
public class Consumer extends Thread { 
	private DirectoryQueue queue;
	private int id;
	
	// Costruttore
	public Consumer(DirectoryQueue queue, int id) { 
		this.queue = queue;
		this.id = id;
	}
	
	public void run() {
		while (!queue.isEnd()) {
			// Si prende un elemento dalla coda
			String s = queue.get();
			if (s != null) {
				File dir = new File(s);
				
				// Si verifica che sia una directory
				if (dir.isDirectory()) {
					File[] files = dir.listFiles();
					// Se ci sono file e non sono directory si stampa il loror path
					if (files != null)
						for (File file : files) {
							if (!file.isDirectory())
								System.out.println("Thread " + this.id + " stampa " + dir.getName() + "\n   Path:   " + file.getPath());
						}
				}
			}
		}
			
	}
}
