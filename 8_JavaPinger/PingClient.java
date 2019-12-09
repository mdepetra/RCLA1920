import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.util.Date;

/**
 * 
 * Assegnamento 8 del Laboratorio di Reti di Calcolatori A
 * A.A. 2019/2020
 * @author Mirko De Petra
 *
 */

public class PingClient {
	private static int BUF_SZ = 128;				// Dimensione degli array di byte
	private static final int MAX_TIMEOUT = 2000;	// Timeout in millisecondi
	private static int NUM_PACKETS = 10;			// Numero di messaggi da inviare
	
	public static void main(String[] args) {
		// PingClient hostname port
		// 	hostname	(String) nome del server
		//  port		(int)	 porta del server
		
		// Si controlla  il numero di argomenti ricevuti da linea di comando
		if (args.length == 0) {
			System.out.println("Usage: java PingClient hostname port"); 
			return; 
		}
		
		String hostname;
		int port;
		
		// Parsing dell'argomento per il nome del server e controllo di correttezza
		try {
			hostname = args[0];
		} catch (RuntimeException ex) {
			System.out.println("ERR -arg 1"); 
			return; 
		} 
		
		// Parsing dell'argomento per la porta e controllo di correttezza
		try {
			port = Integer.parseInt(args[1]);
		} catch (RuntimeException ex) {
			System.out.println("ERR -arg 2"); 
			return; 
		}
		
		try {
			DatagramSocket clientSocket = new DatagramSocket();
			InetAddress IPAddress = InetAddress.getByName(hostname);
			
			// Inizializzazione delle variabili per le statistiche
			int nTransmitted = 0, nReceived = 0;	// Numero di pacchetti trasmessi, Numero di pacchetti ricevuti
			long sum = 0, max = 0, min = 10000;		// Somma dei RTT, max dei RTT, min dei RTT
			
			byte[] sendData = new byte[BUF_SZ];
			byte[] receiveData = new byte[BUF_SZ];
			
			for (int seqno=0;seqno<NUM_PACKETS;seqno++) {
				// Timestamp in ms di invio
				Date now = new Date();
				long timestampSend = now.getTime();
				
				// Creazione della stringa da inviare
				String ping = "PING " + seqno + " " + timestampSend;
				
				// Trasferimento in un array
				sendData = ping.getBytes();
				
				// Invio del pacchetto
				DatagramPacket sendPacket = new DatagramPacket(sendData,sendData.length,IPAddress,port);
				clientSocket.send(sendPacket);
				
				// Incremento del numero di pacchetti trasmessi
				nTransmitted++;
				try {
					// Impostazione del timeout a 2000 ms = 2 sec
					clientSocket.setSoTimeout(MAX_TIMEOUT);
					
					// Ricezione della risposta
					DatagramPacket receivePacket = new DatagramPacket(receiveData,receiveData.length);
					clientSocket.receive(receivePacket);
					
					String responsePing = new String(receivePacket.getData(), 0, receivePacket.getLength(), "US-ASCII");
					
					// Se viene ricevuta la risposta
					
					// Incremento del numero di pacchetti ricevuti
					nReceived++;
					
					// timestamp di ricezione del pacchetto
					now = new Date();
					long timestampReceive = now.getTime();
					
					// Calcolo del RTT
					long RTT = (long) (timestampReceive - timestampSend);
					
					// Stampa della risposta ricevuta e del RTT
					System.out.println(responsePing + " RTT: " + RTT);
					
					// Calcolo del min e del max RTT
					min = Math.min(min, RTT);
					max = Math.max(max, RTT);
					
					// Somma per calcolare la media di RTT
					sum += RTT;
					
				} catch (SocketTimeoutException e) {
					// Se non viene ricevuta la risposta entro il timeout
					
					// Stampa del messaggio inviato e del RTT non calcolato
					System.out.println(ping + " RTT: *");
				}
			}
			
			// Stampa delle statistiche
			printStatistics(nTransmitted, nReceived, min, (float)sum/nReceived, max);
			
			// Chiusura della Socket
			clientSocket.close();
		} catch (IOException e) {
			System.err.println(e);
		}
	}
	
	// Metodo che stampa le statistiche (riassunto)
	private static void printStatistics(int nTransmitted, int nReceived, long min, float avg, long max) {
		System.out.println("---- PING Statistics ----");
		System.out.println(nTransmitted+ " packets transmitted, " + nReceived + " packets received, " + (100-nReceived*100/nTransmitted) + "% packet loss");
		System.out.println("round-trip (ms) min/avg/max = " + min + "/" + String.format("%.02f", avg) + "/" + max);
	}
}
