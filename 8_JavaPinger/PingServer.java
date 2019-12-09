import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Random;

/**
 * 
 * Assegnamento 8 del Laboratorio di Reti di Calcolatori A
 * A.A. 2019/2020
 * @author Mirko De Petra
 *
 */

public class PingServer {
	
	private static long DEFAULT_SEED = 0;			// Seed di default
	private static double DEFAULT_LOSS_RATE = 0.25; // Probabilità di perdita di pacchetti di default
	private static int BUF_SZ = 128;				// Dimensione degli array di byte
	private static int MAX_DELAY = 200;				// Ritardo massimo
	
	public static void main(String[] args) {
		// PingServer port [seed]
		//  port	(int)	porta su cui è attivo il server
		//	seed	(long)	valore da utilizzare per la generazione di latenze e perdita di pacchetti
		
		// Si controlla  il numero di argomenti ricevuti da linea di comando
		if (args.length == 0) {
			System.out.println("Usage: java PingServer port [seed]"); 
			return; 
		}
		
		int port;
		long seed;
		Random rdn = new Random();
		
		// Parsing dell'argomento per la porta e controllo di correttezza
		try {
			port = Integer.parseInt(args[0]);
		} catch (RuntimeException ex) {
			System.out.println("ERR -arg 1"); 
			return; 
		} 
		
		// Parsing dell'argomento per il seed e controllo di correttezza
		try {
			seed = Long.parseLong(args[1]);
		} catch (RuntimeException ex) {
			seed = DEFAULT_SEED;
			System.out.println("Default seed"); 
		}
		
		// Impostazione del seed
		rdn.setSeed(seed);
		
		DatagramSocket serverSocket;
		try {
			serverSocket = new DatagramSocket(port);
			
			System.out.println("Server pronto sulla porta " + port);

			// Array di bytes per i dati da ricevere e da inviare
			byte[] receiveData = new byte[BUF_SZ];
			byte[] sendData = new byte[BUF_SZ];
			
			DatagramPacket receivePacket = new DatagramPacket(receiveData,receiveData.length);
			
			while (true){
				try {
					// Ricezione di un paccketto dal client
					serverSocket.receive(receivePacket);
					
					// Trasformazione del campo DATA in una stringa
					String bytesToSring = new String(receivePacket.getData(), 0, receivePacket.getLength(), "US-ASCII");
					
					// il server determina se ignorare il pacchetto (simulandone la perdita) o effettuarne l'eco.
					if (rdn.nextDouble() < DEFAULT_LOSS_RATE) {
						// Il server ignora il pacchetto e ne simula la perdita
						
						// Stampa dell'Action not sent
						System.out.println(receivePacket.getAddress().getHostAddress() + ":" + receivePacket.getPort() + "> "+ bytesToSring + " ACTION: not sent");
					}
					else {
						// Il server effettua l'eco del pacchetto
						
						// Generazione dell'intervallo di tempo casuale per simulare la latenza di rete
						int delay = (int) (rdn.nextDouble() * MAX_DELAY);
						
						// il server attende delay ms
						Thread.sleep(delay);
						
						// Trasformazione della stringa in bytes
						sendData = bytesToSring.getBytes();
						
						// Invio della risposta 
						DatagramPacket sendPacket = new DatagramPacket(sendData,sendData.length,receivePacket.getAddress(),receivePacket.getPort());
						serverSocket.send(sendPacket);
						
						// Stampa dell'Action eseguita con il ritardo
						System.out.println(receivePacket.getAddress().getHostAddress() + ":" + receivePacket.getPort() + "> "+ bytesToSring + " ACTION: delayed " + delay + " ms");
					}
				} catch (IOException | InterruptedException e) {
					// The exception UnsupportedEncodingException is already caught by the alternative IOException 
					System.err.println(e);
				}
			}
		} catch (SocketException e) {
			System.err.println(e);
		}
	}
}


