/**
 * 
 * Assegnamento 4 del Laboratorio di Reti di Calcolatori A
 * A.A. 2019/2020
 * @author Mirko De Petra, 549105
 *
 */

public class MainClass {
	public static void main(String[] args) {
		// MainClass filepath
		//	filepath	(String) stringa che individua una directory D
		
		String filepath = args[0];
		DirectoryQueue queue = new DirectoryQueue(); 
		new Producer(queue, filepath).start();
		
		int k = (int)((Math.random() * 5) + 1);
		System.out.println("avvio "+k+" threads");
		for (int i=0; i<k; i++)
			new Consumer(queue,i).start(); 
	}
}
