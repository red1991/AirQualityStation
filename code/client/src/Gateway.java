import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Gateway {
	// Indirizzo del servizio newData del VO
	public final static String NEWDATA_URL = "http://localhost:8080/Server_AirQualityStation/rest/VORealObjectCommunication/newdata";
	// Indirizzo del servizio getInfo del VO
	public final static String GETINFO_URL = "http://localhost:8080/Server_AirQualityStation/rest/VORealObjectCommunication/getinfo";
	// Indirizzo alla pagina contenente il json dei dati delle AirQualityStation
	public final static String AIRSTATION_URL = "http://www.iononrischioclout.comune.genova.it/back_end/airsensors.php";

	public static void main(String[] args) {
		try {
			while(true) {			
			// Ricevo il JSON contenente i dati relativi alla qualità dell'aria nelle macroaree della città di Genova
			URL getAirs = new URL(AIRSTATION_URL);
			HttpURLConnection connection = (HttpURLConnection) getAirs.openConnection();
			InputStream is = connection.getInputStream();
		    BufferedReader br = new BufferedReader(new InputStreamReader(is));  
		    String json = org.apache.commons.io.IOUtils.toString(br);
		    
		    // Ricevo da getInfo le informazioni relative all'oggetto virtuale (formato dati inviabile su newData)
			getAirs = new URL(GETINFO_URL);
			connection = (HttpURLConnection) getAirs.openConnection();
			is = connection.getInputStream();
		    br = new BufferedReader(new InputStreamReader(is));  
		    System.out.println("GetInfo -->");
			System.out.println(org.apache.commons.io.IOUtils.toString(br));
			
			// Invio il JSON al VO
		    System.out.println("\nNewData -->");
			URL url = new URL(NEWDATA_URL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");	
			
			DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
			try{
				wr.writeBytes(json);
				wr.flush();
				wr.close();
				System.out.println("Dati inviati correttamente!");
			}catch(Exception e) {
				System.out.println("Servizio REST irragiungibile!!!");
			}
			
			// Risposta dal server
			br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
			String output;
			System.out.println("Risposta dal server:");
			while ((output = br.readLine()) != null) {
				System.out.println(output);
			}
			conn.disconnect();
		
			// Il gateway interroga le air quality station una volta ogni ora
			Thread.sleep(60*60*1000);
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		} 
	}
}
