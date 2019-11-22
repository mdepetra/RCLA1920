import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * 
 * Assegnamento 7 del Laboratorio di Reti di Calcolatori A
 * A.A. 2019/2020
 * @author Mirko De Petra
 *
 */

// MainClass che modellizza un client per il server EchoServer
public class EchoClient {

	public static int DEFAULT_PORT = 1919;
	
	public static void main(String[] args) {
		// Si usa la porta di default
		int port = DEFAULT_PORT;
		
		// Creazione dello Scanner per leggere dalla console
		Scanner stdin = new Scanner(System.in);
		
		try {
			SocketAddress address = new InetSocketAddress("localhost", port);
			SocketChannel client = SocketChannel.open(address);
			
			// Lettura del messaggio da inviare da console
			System.out.print("Message to send: ");
			String inputLine = stdin.nextLine();
			
            ByteBuffer buffer = ByteBuffer.wrap(inputLine.getBytes());
            
	        try {
	        	// Invio del messaggio al server
	            client.write(buffer);
	            System.out.println("Message sent to the server.");
	            buffer.flip();

	            // Iniziallazione della stringa per la risposta del server
		        String response = "";
		        
		        client = client.shutdownOutput();
		        
		        // Lettura dal server
	            System.out.println("Reading from the server...");
	            boolean stop = true;
				while (stop) {
					int bytesRead = client.read(buffer);
					stop = bytesRead > 0;
					buffer.flip();
					while (buffer.hasRemaining()) {
						response += StandardCharsets.UTF_8.decode(buffer).toString();
					}
					buffer.clear();
				}

				// Stampa del messaggio
	            System.out.println("Message from the server: " + response);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		} catch(IOException ex) {
			ex.printStackTrace(); 
		}
		
		// Chiusura dello Scanner
		stdin.close();
	}

}
