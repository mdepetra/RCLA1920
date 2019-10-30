public class Occorrenza {

	private static int nCA=0;
	private static int nBonifici=0;
	private static int nAccrediti=0;
	private static int nBollettini=0;
	private static int nF24=0;
	private static int nPagoBancomat=0;
	
	public Occorrenza (){
		
	}

	public void increment(String causale) {
		
		switch (causale) {
	        case "Bonifico":
	        	incrementBonifici();
	            break;
	        case "Accredito":
	       	 	incrementAccrediti();
	            break;
	        case "Bollettino":
	       	 	incrementBollettini();
	            break;
	        case "F24":
	       	 	incrementF24();
	            break;
	        case "PagoBancomat":
	       	 	incrementPagoBancomat();
	            break;
		}
	}
	
	public static synchronized void incrementCA (){
		nCA++;
	}
	
	public static synchronized void incrementBonifici (){
		nBonifici++;
	}
	
	public static synchronized void incrementAccrediti (){
		nAccrediti++;
	}
	
	public static synchronized void incrementBollettini (){
		nBollettini++;
	}
	
	public static synchronized void incrementF24 (){
		nF24++;
	}
	
	public static synchronized void incrementPagoBancomat (){
		nPagoBancomat++;
	}
	
	public void printOccorrenze() {
		int k =0;
		System.out.println("Bonifici:" + nBonifici);
        k = k + nBonifici;
        System.out.println("Accrediti:" + nAccrediti);
        k = k + nAccrediti;
        System.out.println("Bollettini:" + nBollettini);
        k = k + nBollettini;
        System.out.println("F24:" + nF24);
        k = k + nF24;
        System.out.println("PagoBancomat:" + nPagoBancomat);
        k = k + nPagoBancomat;

		System.out.println("k"+k);
		System.out.println("ca:" +nCA);
	}

}
