import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * 
 * Assegnamento 4 del Laboratorio di Reti di Calcolatori A
 * A.A. 2019/2020
 * @author Mirko De Petra, 549105
 *
 */

// Metodo che modelizza il thread lettore di conti correnti (current Account)
public class CAReader extends Thread{

	private MyQueue queue;
	private String fileName;
	
	// Costruttore
	public CAReader(MyQueue queue, String fileName) {
		this.queue = queue;
		this.fileName = fileName;
	}
	
	public void run(){
		
		JSONParser parser = new JSONParser(); 
		try {
			Object obj = parser.parse(new FileReader(fileName)); 
			JSONObject jsonObject = (JSONObject) obj;
			String nameOfCountry = (String) jsonObject.get("Name");
			
		} catch (FileNotFoundException e) { 
			e.printStackTrace(); 
		} catch (IOException e) { 
			e.printStackTrace(); 
		} catch (ParseException e) { 
			e.printStackTrace();
		}
		
		// Inserimento della directory principale nella coda
		queue.put(this.dir.getPath());
		File files[] = this.dir.listFiles();
		traverseFolder(files);
		
		// Segnalazione della fine della produzione
		this.queue.setEnd();
	}
	
	// Funzione per l'esplorazione ricorsiva delle directory
	private void traverseFolder(File[] files) {
		if (files != null) {
			for (File file : files) {
				if (file.isDirectory()) {
					queue.put(file.getPath());
					traverseFolder(file.listFiles());
				}
			}
		}
	}
}
