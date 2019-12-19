import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Date;

/**
 * 
 * Assegnamento 10 del Laboratorio di Reti di Calcolatori A
 * A.A. 2019/2020
 * @author Mirko De Petra
 *
 */

public class TimeServer {
	private static final int MAX_DELAY = 10000; // Numero massimo di ms da aspettare fra un invio e il successivo
	private static final int MIN_DELAY = 1000; 	// Numero minimo di ms da aspettare fra un invio e il successivo
	private static final int BUF_SZ = 256;		// Dimensione dell'array di byte
	
	public static void main(String[] args) {
		// TimeServer multicast_address port
		// 	multicast_address	(String) indirizzo IP del multicast dategroup
		//  port				(int)	 porta
		
		InetAddress group = null;
		int port = 0;
		
		// Parsing degli argomenti e controllo di correttezza
		try{
			group = InetAddress.getByName(args[0]);
		    port = Integer.parseInt(args[1]);
		} catch(Exception e) {
		    System.out.println("Usage:java TimeServer multicast_address port");
		    System.exit(1);
		}

		DatagramSocket ms = null;
		
		try{
			// Array di bytes per i dati da inviare
		    byte[] sendData = new byte[BUF_SZ];
		    ms = new DatagramSocket(port);
		    ms.setReuseAddress(true);
		    
		    System.out.println("Server pronto sulla porta " + port);
		    
		    DatagramPacket dp;
		    Date date;
		    
		    // Generazione casuale dell'intervallo regolare di tempo da aspettare tra un invio e il successivo
		    int delay = (int)((Math.random() * (MAX_DELAY - MIN_DELAY + 1) + MIN_DELAY));
		    System.out.println("millisecondi tra un invio e il successivo: " + delay);
		    
		    while (true) {
		    	// Creazione della data
		    	date = new Date();
		    	
		    	// Trasformazione della data in bytes
			    sendData = date.toString().getBytes();
			    
			    // Invio del pacchetto
		    	dp = new DatagramPacket(sendData, sendData.length, group, port);
		    	ms.send(dp);
		    	System.out.println("Pacchetto inviato: " + date);
		    	
		    	// Il server attende delay ms prima del prossimo invio
		    	Thread.sleep(delay);
		    }
		} catch(IOException | InterruptedException ex) {
		    System.out.println(ex);
		    if (ms != null)
		    	ms.close();
		}
		
	}

}
