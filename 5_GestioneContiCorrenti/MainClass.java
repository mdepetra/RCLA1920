import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class MainClass {
	
	enum Casuale {		
		Bonifico, Accredito, Bollettino, F24, PagoBancomat
	}
	
	enum Name {
		Name1, Name2, Name3, Name4, Name5
	}
	
	enum Surname {
		Surname1, Surname2, Surname3, Surname4, Surname5
	}

	public static void main(String[] args) {
		// MainClass fileName
		//	fileName	(String) stringa che individua il file json da cui leggere i dati
		
		String fileName = args[0];
		generaFileJSON(fileName);
		
		// Rileggi
		
		

	}
	
	private static void generaFileJSON(String fileName) {
		List<Name> names = Collections.unmodifiableList(Arrays.asList(Name.values()));
		List<Surname> surnames = Collections.unmodifiableList(Arrays.asList(Surname.values()));
		List<Casuale> casuali = Collections.unmodifiableList(Arrays.asList(Casuale.values()));
		// Casuale o Payment Reason o Motive
		
		int nAccount = (int) ((Math.random()*(10-3))+1);
		try {
			File file = new File(fileName+".json");
			file.createNewFile();
			FileWriter fileWriter = new FileWriter(file);
			JSONArray myArray = new JSONArray();
			
			for (int i=0;i<nAccount; i++) {
				JSONObject myObj = new JSONObject();
				Name n = names.get((int)Math.random()*names.size());
				Surname s = surnames.get((int)Math.random()*surnames.size());
				myObj.put("name", n.toString()+" "+s.toString());
				int nMovimenti=(int) ((Math.random()*9)+1);
				JSONArray movimenti = new JSONArray();
				
				for (int i1=0;i1<nMovimenti;i1++){
					JSONObject movimentoT = new JSONObject();
					movimentoT.put("date", "10/10/2019");
					movimentoT.put("causale", casuali.get((int)Math.random()*casuali.size()).toString());
					double balance = (Math.random()*10);
					movimentoT.put("importo", String.valueOf(balance));
					movimenti.add(movimentoT);
				}
				
				myObj.put("Movimenti",movimenti);
				myArray.add(myObj);
				
			}
			fileWriter.write(myArray.toJSONString());
			fileWriter.flush();
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
