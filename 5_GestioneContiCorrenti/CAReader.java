
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.LinkedBlockingQueue;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * 
 * Assegnamento 5 del Laboratorio di Reti di Calcolatori A
 * A.A. 2019/2020
 * @author Mirko De Petra
 *
 */

// Metodo che modelizza il thread lettore di conti correnti (current Account)
public class CAReader extends Thread{

	private String fileName;
	private LinkedBlockingQueue<JSONObject> listaCA;
	private JSONArray conti;
	private boolean endFlag = false;
	
	// Costruttore
	public CAReader(LinkedBlockingQueue<JSONObject> listaCA, String fileName) {
		this.listaCA = listaCA;
		this.fileName = fileName;
	}

	public void run(){
		
		this.conti = read(this.fileName);
		while (!conti.isEmpty()) {
			try {
				JSONObject obj = (JSONObject) conti.remove(0);
				listaCA.put(obj);
				this.endFlag=true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private JSONArray read(String fileName) {
		try {
			FileChannel inChannel =  FileChannel.open(Paths.get(fileName), StandardOpenOption.READ);
			String s = "";
			ByteBuffer buffer = ByteBuffer.allocate(1024*1024*100);
			long time1=System.currentTimeMillis();
            boolean stop=false;
	
            while (!stop) {
				int bytesRead = inChannel.read(buffer);
				if (bytesRead == -1) stop = true;
				buffer.flip();
				while (buffer.hasRemaining())
					s += StandardCharsets.US_ASCII.decode(buffer).toString();
				buffer.clear();
			}
            
			long time2=System.currentTimeMillis(); 
			System.out.println(time2-time1 + "    " + stop);
			inChannel.close();
			JSONParser parser = new JSONParser(); 
			
			return (JSONArray) parser.parse(s);
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} 
		return null;
	}
	
	public boolean readNotEnd() {
		return endFlag;
	}
}
