package it.cipi.esercitazione;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import com.google.gson.Gson;

public class VORegistration {
	public static String VO_REGISTER = "http://localhost.../VOREGISTER"; // Indirizzo VORegister (per registrazione il VO)
	public static String VO_UNREGISTER = "http://localhost.../VOUnRegister"; // Indirizzo VORegister (per rimuovere il VO)
	
	public void registrazione() {
		// Inizializzazione VO
		HashMap<String, String> data = new HashMap<String, String>();
		data.put("name", "AirQualityStation"); // Nome
		data.put("events", "CRITICAL_OZONE_LEVEL,CRITICAL_NO2_LEVEL"); // Eventi inviabili dal VO
		
		Gson json = new Gson();
		
		try {
			// Registrazione VO nel VORegister
			URL url = new URL(VO_REGISTER);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			OutputStream os = conn.getOutputStream();
			os.write(json.toJson(data).getBytes());
			os.flush();
			if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}	
	}	
	
	public void unregister() {
		HashMap<String, Object> data = new HashMap<String, Object>();
		data.put("name", "AirQualityStation");
		Gson json = new Gson();
		
		try {
			// Eliminazione VO dal VORegister
			URL url = new URL(VO_UNREGISTER);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			OutputStream os = conn.getOutputStream();
			os.write(json.toJson(data).getBytes());
			os.flush();
			if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}
