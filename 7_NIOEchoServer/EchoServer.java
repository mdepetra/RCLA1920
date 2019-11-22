import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Set;

/**
 * 
 * Assegnamento 7 del Laboratorio di Reti di Calcolatori A
 * A.A. 2019/2020
 * @author Mirko De Petra
 *
 */

//MainClass che modellizza il server EchoServer
public class EchoServer {
	
	public static int DEFAULT_PORT = 1919;
	public static int MAX_CAPACITY = 1024;
	
	public static void main(String[] args) {
		
		// Si usa la porta di default
		int port = DEFAULT_PORT;
		
		// Il server è in ascolto
		System.out.println("Listening for connections on port " + port);
		
		ServerSocketChannel serverChannel;
		Selector selector;
		try {
			// Creazione del SocketChannel
			serverChannel = ServerSocketChannel.open();
			ServerSocket ss = serverChannel.socket(); 
			InetSocketAddress address = new InetSocketAddress(port); 
			ss.bind(address); 
			
			// Channel in modalità non bloccante
			serverChannel.configureBlocking(false);
			
			// Creazione del selector
			selector = Selector.open();
			
			// Si registra il server al selector
			serverChannel.register(selector, SelectionKey.OP_ACCEPT);
		} catch (IOException ex) { 
			ex.printStackTrace();
			return;
		}
		
		while (true) { 
			// Seleziona i canali pronti per almeno una delle operazioni di I/O tra quelli registrati con quel selettore
			try {
				selector.select();
			} catch (IOException ex) {
				ex.printStackTrace(); 
				break;
			}
			
			Set <SelectionKey> readyKeys = selector.selectedKeys();
			Iterator <SelectionKey> iterator = readyKeys.iterator();

			while (iterator.hasNext()) {
				SelectionKey key = iterator.next();
				
				// Rimuove la chiave dal Selected Set, ma non dal registered Set 
				iterator.remove();
				
				try {
					if (key.isAcceptable()) {
						// A connection was accepted by a ServerSocketChannel
						ServerSocketChannel server = (ServerSocketChannel) key.channel(); 
						SocketChannel client = server.accept(); 
						System.out.println("Accepted connection from " + client);
						
						// Channel in modalità Non Blocking
						client.configureBlocking(false);
						// Registrazione di un canale su un selettore e impostazione dell'interest set per le operazioni di lettura
						client.register(selector, SelectionKey.OP_READ);
					} else if (key.isReadable()) {
						// A channel is ready for reading
						System.out.println("A channel is READING");
						read(key);
					} else if (key.isWritable()) { 
						// a channel is ready for writing
						System.out.println("A channel is WRITING");
						write(key);
					}
				} catch (IOException ex) { 
					key.cancel();
					try { 
						key.channel().close();
					} catch (IOException cex) {
						
					}
				}
			}
		}
	}
	
	// Metodo per leggere dal client
	public static void read (SelectionKey key) {
		try {
			SocketChannel client = (SocketChannel) key.channel();
			ByteBuffer input = ByteBuffer.allocate(MAX_CAPACITY);
			// Recupero dell'allegato
			Object att = key.attachment();
		
			// Inizializzazione del messaggio di risposta per il client
			String echoedMes = (att == null) ? "" :  (String) att;
			
			// Lettura del messaggio ricevuto dal client
			int bytesRead = client.read(input);
			
			// Decodifica dei bytes letti
			input.flip();
			echoedMes += StandardCharsets.UTF_8.decode(input).toString();
			System.out.println("Number of bytes read: " + bytesRead);
			input.clear();
			
			// Si controlla che la connessione non sia stata interrotta dal client
			if (bytesRead == MAX_CAPACITY){
				System.out.println("Message partially received from " + client); 
				// Si allega output alla SelectionKey
				key.attach(echoedMes);
			}
			else{
				if (bytesRead == -1 && echoedMes.equals("")) {
					// Se il client è terminato senza inviare niente
					System.out.println("Connection interrupted by " + client);
		            client.close();
				}
				else {
					// Il server ha letto tutto dal buffer
					System.out.println("Message completely received from " + client); 
			        
			        // Modifica dell'interest set per le operazioni di scrittura
			        SelectionKey key2 = key.interestOps(SelectionKey.OP_WRITE);
			        
					// Si allega output alla SelectionKey
					key2.attach(echoedMes);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Metodo per scrivere al client
	private static void write (SelectionKey key) {
		try {
			SocketChannel client = (SocketChannel) key.channel();
			String echoedMes = (String) key.attachment();
			
			// Modifica del messaggio di risposta
	        echoedMes += " echoed by server";
	        
			ByteBuffer output = ByteBuffer.wrap(echoedMes.getBytes());
			
			// Invio della risposta al client
			client.write(output);
			System.out.println("Reply sent to " + client + "\n"); 
			
			// Chusura del channel
			client.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
