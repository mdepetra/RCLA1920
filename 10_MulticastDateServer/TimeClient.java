import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

/**
 * 
 * Assegnamento 10 del Laboratorio di Reti di Calcolatori A
 * A.A. 2019/2020
 * @author Mirko De Petra
 *
 */

public class TimeClient {
	private static final int NUM_MSG = 10; // Numero di volte che il client deve ricevere data e ora dal server
	private static final int BUF_SZ = 256; // Dimensione dell'array di byte
	
	public static void main(String[] args) {
		// TimeClient multicast_address port
		// 	multicast_address	(String) indirizzo IP del multicast dategroup
		//  port				(int)	 porta

		InetAddress group = null;
		int port = 0;
		
		// Parsing degli argomenti e controllo di correttezza
		try{
		    group = InetAddress.getByName(args[0]);
		    port = Integer.parseInt(args[1]);
		} catch(Exception e) {
		    System.out.println("Usage:java TimeClient multicast_address port");
		    System.exit(1);
		}
		
		MulticastSocket ms=null;

	    try {
			ms = new MulticastSocket(port);
			ms.setReuseAddress(true);
			
			// Si unisce a group
		    ms.joinGroup(group);
		    System.out.println("Client si è unito al gruppo");
		    
		    // Array di bytes per i dati da ricevere
		    byte[] receivedData = new byte[BUF_SZ];
		    
		    int k = 0;
		    DatagramPacket dp;
		    
		    // Si riceve, per NUM_MSG volte consecutive, data ed ora
		    while (k < NUM_MSG) {
		        dp = new DatagramPacket(receivedData,receivedData.length);
		        ms.receive(dp);
	            String received = new String(dp.getData());
	            
	            // Stampa ciò che ha ricevuto
	            System.out.println("Ho ricevuto: " + received);
	            k++;
		    }
		} catch (IOException ex) {
            System.out.println(ex);
        } finally {
        	// Alla fine se ms non è null, si lascia il gruppo e si chiude ms
            if (ms!= null) {
                try { 
                    ms.leaveGroup(group);
                    ms.close();
                } catch (IOException ex) {}
            }
            
        }
	    
	    // Termina
        System.out.println("END");
		
	}

}
