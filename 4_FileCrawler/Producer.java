import java.io.File;

/**
 * 
 * Assegnamento 4 del Laboratorio di Reti di Calcolatori A
 * A.A. 2019/2020
 * @author Mirko De Petra, 549105
 *
 */

public class Producer extends Thread {
	private DirectoryQueue queue;
	private File dir;
	
	public Producer(DirectoryQueue queue, String dir) {
		this.queue = queue;
		this.dir = new File(dir);
	}
	
	public void run(){
		queue.put(this.dir.getPath());
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		File files[] = this.dir.listFiles();
		traverseFolder(files);
		this.queue.setEnd();
	}
	
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
