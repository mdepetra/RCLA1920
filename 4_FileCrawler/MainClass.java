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
		
		// Creazione della coda per la comunicazione fra produttore e consumatori
		DirectoryQueue queue = new DirectoryQueue(); 
		
		// Creazione e attivazione del Produttore
		new Producer(queue, filepath).start();
		
		// Generatozione di un k random
		int k = (int)((Math.random() * 5) + 1);
		System.out.println("Avvio di "+k+" threads consumatori");
		
		// Creazione e attivazione di k Consumatori
		for (int i=0; i<k; i++)
			new Consumer(queue,i).start(); 
	}
}
