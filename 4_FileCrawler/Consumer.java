import java.io.File;

public class Consumer extends Thread { 
	private DirectoryQueue queue;
	private int id;
	
	public Consumer(DirectoryQueue queue, int id) { 
		this.queue = queue;
		this.id = id;
	}
	
	public void run() {
		while (!queue.isEnd()) {
			this.get();
		}
		while(!queue.isEmpty()) {
			this.get();
		}
	}
	
	public void get() {
		String s = queue.get();
		if (s != null) {
			File dir = new File(s);
			if (dir.isDirectory()) {
				//System.out.println("   DIRECTORY -> " + dir.getName());
				File[] files = dir.listFiles();
				if (files != null)
					for (File file : files) {
						if (!file.isDirectory())
							System.out.println("Thread " + this.id + " stampa " + dir.getName() + "\n   Path:   " + file.getPath());
							//printInfo(file);
					}
			}
		}
	}
	
	private void printInfo(File f) {
		System.out.println("Name:          " + f.getName());
		System.out.println("Path:          " + f.getPath());
		System.out.println("Last modified: " + f.lastModified());
		System.out.println("Length:        " + f.length());
		System.out.println();
	}
}
