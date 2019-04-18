package it.cipi.esercitazione;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import com.google.gson.Gson;

public class onReceiveNewData extends Thread { 
	public final static String OZONE_EVENT = "CRITICAL_OZONE_LEVEL"; // Evento CRITICAL_OZONE_LEVEL
	public final static String NO2_EVENT = "CRITICAL_NO2_LEVEL"; // Evento CRITICAL_NO2_LEVEL
	public final static String URL_ORCHESTRATORE = "URL_Orchestratore"; // Indirizzo dell'orchestratore
	private String json;
	
	// Costruttore
	public onReceiveNewData(String data) {
		super();
		this.json = data;
	}
	
	// Funzione necessaria poichè estendo la classe thread
	@Override
	public void run() {
		// Chiamo funzione per il processing della qualità dell'aria
		this.newData();
	}
	
	public void newData() {
	    Gson gson = new Gson();
	    // Effettuo il parsing del json mediante la classe AirInfo
		AirInfo[] airs = gson.fromJson(json, AirInfo[].class);
		int i = 0;
		
		/* Inizio il processo di verifica della qualità dell'aria.
		 * Poichè conosco la struttura sempre fissa del formato del json a seconda
		 * delle varie aree del comune di Genova (16) in cui mi trovo invio esclusivamente gli
		 * attributi relativi ai livelli di O3 e NO2 alla funzione processing() per la valutazione.
		*/		
		for(AirInfo air: airs) {
				if(i == 0) {
					processing(air, 2, 3, 4, OZONE_EVENT);
					processing(air, 5, 6, 7, NO2_EVENT);
				}
				if(i == 1) {
					processing(air, 2, 3, 4, OZONE_EVENT);
					processing(air, 5, 6, 7, NO2_EVENT);
				}
				if(i == 2) {
					processing(air, 4, 5, 6, OZONE_EVENT);
					processing(air, 7, 8, 9, NO2_EVENT);
				}
				if(i == 3) {
					processing(air, 4, 5, 6, NO2_EVENT);
				}
				if(i == 4) {
					processing(air, 4, 5, 6, NO2_EVENT);
				}
				if(i == 5) {
					processing(air, 4, 5, 6, NO2_EVENT);
				}
				if(i == 6) {
					processing(air, 2, 3, 4, NO2_EVENT);
				}
				if(i == 7) {
					processing(air, 2, 3, 4, NO2_EVENT);
				}
				if(i == 8) {
					processing(air, 2, 3, 4, NO2_EVENT);
				}
				if(i == 9) {
					processing(air, 0, 1, 2, NO2_EVENT);
				}
				if(i == 10) {
					processing(air, 4, 5, 6, NO2_EVENT);
				}
				if(i == 11) {
					processing(air, 2, 3, 4, NO2_EVENT);
				}
				
				/* L'elemento 12 contiene "input non disponibili".
				 * L'elemento 13 non contiene attributi di O3 e di NO2.
				*/
				
				if(i == 14) {
					processing(air, 0, 1, 2, OZONE_EVENT);
				}
				if(i == 15) {
					processing(air, 2, 3, 4, NO2_EVENT);
				}
				if(i == 16) {
					processing(air, 0, 1, 2, OZONE_EVENT);
				}
			i++;
		}
	}
	
	/* Funzione per la valutazione della qualità dell'aria.
	 * Ricevo il json serializzato e controllo se il valore di limiti di O3 o NO2 (a seconda di quello presente)
	 * è superiore a quello effettivo nella varie aree. Se così non fosse invio alla funzione notifyEvent()
	 * il messaggio di allarme che a sua volta si occuperà di avvisare l'orchestratore.
	*/
	public static void processing(AirInfo air, int valoreLimite, int primoValore, int secondoValore, String evento) {
		if(!(Double.parseDouble((String) air.getAttributes()[valoreLimite].getValore()) < Double.parseDouble((String) air.getAttributes()[primoValore].getLimite())) 
				|| (Double.parseDouble((String) air.getAttributes()[valoreLimite].getValore()) < Double.parseDouble((String) air.getAttributes()[secondoValore].getLimite()))) {
			notifyEvent(evento);
		}
	}
	
	private static void notifyEvent(String evento) {
		String urlOrchestratore= System.getProperty(URL_ORCHESTRATORE);
		try {
			    // Invio i dati all'orchestratore
				URL url = new URL(urlOrchestratore);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setDoOutput(true);
				conn.setRequestMethod("POST");
				conn.setRequestProperty("Content-Type", "application/json");
				
				DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
				try{
					wr.writeBytes(evento);
					wr.flush();
					wr.close();
					System.out.println("Dati inviati correttamente all'orchestratore!");
				}catch(Exception e) {
					System.out.println("Errore deurante l'invio dei dati!");
				}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}
