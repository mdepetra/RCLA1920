import java.io.File;

public class Consumer extends Thread { 
	private DirectoryQueue queue;
	
	public Consumer(DirectoryQueue queue) { 
		this.queue = queue;
	}
	
	public void run() {
		while (!queue.isEmpty()) {
			String s = queue.get();
			if (s != null) {
				File dir = new File(s);
				crawl(dir);
			}
		}
	}
	
	public void crawl (File dir) {
		if (dir.isDirectory()) {
			//System.out.println("   DIRECTORY -> " + dir.getName());
			File[] files = dir.listFiles();
			if (files != null)
				for (File file : files) {
					if (!file.isDirectory())
						printInfo(file);
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
