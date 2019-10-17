import java.io.File;

/**
 * 
 * Assegnamento 4 del Laboratorio di Reti di Calcolatori A
 * A.A. 2019/2020
 * @author Mirko De Petra
 *
 */

public class Producer extends Thread {
	private int count = 0;
	private DirectoryQueue queue;
	private File dir;
	
	public Producer(DirectoryQueue queue, String dir) {
		this.queue = queue;
		this.dir = new File(dir);
	}
	
	public void run(){
		queue.put(this.dir.getPath());
		File files[] = this.dir.listFiles();
		traverseFolder(files);
	}
	
	private void traverseFolder(File[] files) {
		if (files != null) {
			for (File file : files) {
				if (file.isDirectory()) {
					queue.put(file.getPath());
					this.count++;
					traverseFolder(file.listFiles());
				}
			}
		}
	}
}
