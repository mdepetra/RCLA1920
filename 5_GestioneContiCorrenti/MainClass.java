import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadLocalRandom;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class MainClass {
	public static void main(String[] args) {
		String fileName = "CA.json";
		generaFileJSON(fileName);
		
		LinkedBlockingQueue<JSONObject> listaCA = new LinkedBlockingQueue<JSONObject>();
		
		CAReader reader = new CAReader(listaCA,fileName);
		reader.start();
		
		Occorrenza contatori = new Occorrenza(); 
		
		TPool tPool = new TPool();
	    int i=0;
	    
	    while (!reader.readNotEnd()|| !listaCA.isEmpty()) {
			try {
				JSONObject obj = listaCA.take();
				if(obj != null) {
					CATask ca = new CATask(obj,contatori);
					tPool.executeCATask(ca);
					i++;
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("i"+i);
		tPool.closePool();
		contatori.printOccorrenze();
	}
	
	private static void generaFileJSON(String fileName) {
		String causali[] = new String[] {"Bonifico", "Accredito", "Bollettino", "F24", "PagoBancomat"};
		String nomi[] = new String[] {"Name1", "Name2", "Name3"};
		String cognomi[] = new String[] {"Surname1", "Surname2", "Surname3", "Surname4", "Surname5", "Surname6", "Surname7"};
		int kYear=2;
		int nAccount = 100;//(int) ((Math.random()*(10-3))+1);
		
		try {
			JSONArray myArray = new JSONArray();
			
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
			
			for (int i=0;i<nAccount; i++) {
				JSONObject myObj = new JSONObject();
				String nome = nomi[(int)(Math.random()*nomi.length)];
				String cognome = cognomi[(int)(Math.random()*cognomi.length)];;
				myObj.put("name", nome+" "+cognome);
				myObj.put("NumeroCC", "IT"+i);
				int nMovimenti=10;//(int) ((Math.random()*9)+1);
				JSONArray movimenti = new JSONArray();
				
				LocalDate today = LocalDate.now();
				long end = today.toEpochDay();
			    
				LocalDate startDate = today.minusYears(kYear);
				
				for (int i1=0;i1<nMovimenti;i1++){
					JSONObject movimentoT = new JSONObject();
					long start = startDate.toEpochDay();
				    
					long randomEpochDay = ThreadLocalRandom.current().longs(start, end).findAny().getAsLong();
				    startDate = LocalDate.ofEpochDay(randomEpochDay);
					movimentoT.put("date", formatter.format(LocalDate.ofEpochDay(randomEpochDay)));
					movimentoT.put("causale", causali[(int)(Math.random()*causali.length)]);
					double balance = (Math.random()*10)*1000;
					movimentoT.put("importo",balance);
					movimenti.add(movimentoT);
				}
				
				myObj.put("movimenti",movimenti);
				myArray.add(myObj);
				
			}
			
			
			ByteBuffer buffer = ByteBuffer.wrap(myArray.toJSONString().getBytes());
			
			Files.deleteIfExists(Paths.get(fileName)); //cancello il file precedentemente creato se esite
			Files.createFile(Paths.get(fileName)); //creo nuovo file
			
			FileChannel outChannel = FileChannel.open(Paths.get(fileName), StandardOpenOption.WRITE);
			
			while(buffer.hasRemaining()) {
				outChannel.write(buffer);
			}
			
			outChannel.close();
			
			
		} catch (IOException e) {

			e.printStackTrace();
		}
		/*
		try {
			File file = new File(fileName);
			file.createNewFile();
			FileWriter fileWriter = new FileWriter(file);

			JSONArray myArray = new JSONArray();
			
			for (int i=0;i<nAccount; i++) {
				JSONObject myObj = new JSONObject();
				String nome = nomi[(int)(Math.random()*nomi.length)];
				String cognome = cognomi[(int)(Math.random()*cognomi.length)];;
				myObj.put("name", nome+" "+cognome);
				int nMovimenti=5000;//(int) ((Math.random()*9)+1);
				JSONArray movimenti = new JSONArray();
				
				for (int i1=0;i1<nMovimenti;i1++){
					JSONObject movimentoT = new JSONObject();
					movimentoT.put("date", "10-10-2019");
					movimentoT.put("causale", causali[(int)(Math.random()*5)]);
					double balance = (Math.random()*10)*1000;
					movimentoT.put("importo",balance);
					movimenti.add(movimentoT);
				}
				
				myObj.put("movimenti",movimenti);
				myArray.add(myObj);
				
			}
			fileWriter.write(myArray.toJSONString());
			fileWriter.flush();
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}*/
	}

}