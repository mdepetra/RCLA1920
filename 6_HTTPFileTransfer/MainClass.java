import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 
 * Assegnamento 6 del Laboratorio di Reti di Calcolatori A
 * A.A. 2019/2020
 * @author Mirko De Petra, 549105
 *
 */

public class MainClass {
	public static void main(String[] args) {
		int port = 6789;
		try (ServerSocket server = new ServerSocket(port)) {
			while (true) {
				Socket connection = server.accept();
				try  {
					InputStreamReader isr =  new InputStreamReader(connection.getInputStream());
					BufferedReader reader = new BufferedReader(isr); 
					String line = reader.readLine(); 
					String resource = "";
					
					while (!line.isEmpty()) { 
						if(line.startsWith("GET"))
		                    resource = line;
						line = reader.readLine(); 
					}
					
					String[] parts = resource.split("\\s+");
			        String filename = parts[1].substring(1);

			        System.out.println(filename);
			        File risorsa = new File(filename);      
			        System.out.println(risorsa.getAbsolutePath());

			        InputStream is = null;
			        DataOutputStream binaryOut = null;
			        try {
			        	binaryOut = new DataOutputStream(connection.getOutputStream());
				        binaryOut.writeBytes("HTTP/1.0 200 OK\r\n");
				        
			        	if (risorsa.exists()) {
			        		is = new FileInputStream(risorsa);
					        byte[] data = new byte[(int) risorsa.length()];
					        is.read(data);
					        
					        binaryOut.writeBytes("Content-Length: " + data.length);
					        binaryOut.writeBytes("\r\n\r\n");
					        binaryOut.write(data);
			        	}
			        	else {
			        		binaryOut.writeBytes("\r\n\r\n");
			        		binaryOut.writeBytes("Il file " + filename + " non esiste.\r\n\r\n");
			        	}
			        	
				    } catch(Exception e){
				    	e.printStackTrace();
				    } finally {
				    	if (is != null)
				    		is.close();
			        	if (binaryOut!= null)
			        		binaryOut.close();
			        }
				} catch (IOException ex) {
					System.out.println(ex.getMessage());
				} finally {
					connection.close();
				}
			}
		} catch (IOException ex) { 
			System.out.println(ex);
		}
	}
}
