import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


public class CATask implements Runnable{
	
	private JSONObject obj;
	private Occorrenza contatori;
	

	public CATask(JSONObject obj, Occorrenza contatori) {
		this.obj = obj;
		this.contatori = contatori;
	}

	@Override
	public void run() {
		JSONArray movimenti = (JSONArray) obj.get("movimenti");
		for (Object obj: movimenti) {
			JSONObject movimento = (JSONObject) obj;
			String causale = (String) movimento.get("causale");
			contatori.increment(causale);
		}
		Occorrenza.incrementCA();
	}

}