import java.io.File;

/**
 * 
 * Assegnamento 4 del Laboratorio di Reti di Calcolatori A
 * A.A. 2019/2020
 * @author Mirko De Petra, 549105
 *
 */

// Classe che modellizza il thread Produttore
public class Producer extends Thread {
	private DirectoryQueue queue;
	private File dir;
	
	// Costruttore
	public Producer(DirectoryQueue queue, String dir) {
		this.queue = queue;
		this.dir = new File(dir);
	}
	
	public void run(){
		// Inserimento della directory principale nella coda
		queue.put(this.dir.getPath());
		File files[] = this.dir.listFiles();
		traverseFolder(files);
		
		// Segnalazione della fine della produzione
		this.queue.setEnd();
	}
	
	// Funzione per l'esplorazione ricorsiva delle directory
	private void traverseFolder(File[] files) {
		if (files != null) {
			for (File file : files) {
				if (file.isDirectory()) {
					queue.put(file.getPath());
					traverseFolder(file.listFiles());
				}
			}
		}
	}
}
