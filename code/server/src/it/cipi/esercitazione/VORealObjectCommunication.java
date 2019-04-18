package it.cipi.esercitazione;

import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.apache.log4j.Logger;
import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.POST;

@Path("/VORealObjectCommunication")
public class VORealObjectCommunication {
	@SuppressWarnings("unused")
	private static Logger log;
	@SuppressWarnings("unused")
	private static ServletContext servletContext;
	
	static void init(ServletContext sc){
		log= Logger.getRootLogger();
		servletContext=sc;		
	}
	
	@GET
	@Path("getinfo")
	@Produces(MediaType.APPLICATION_JSON)
	// Funzione getInfo del VO chiamabile dal gateway per info sul formato inviabile su newData
	public String getinfo () {
		String info ="[{id,name,description,latitude,longitude,attributes[{@attributes{id,rowOrder,hiddenID_SITO},Grandezza,Elaborazione,Limite,Valore,UnitaMisura}],last_update}]";
		return info ;
	}
	
	@POST
	@Path("newdata")
	// Funzione newData del VO utilizzata dal gateway per inviare il json ricevuto dalle AirQualityStation
	public String newdata (String data) {
		/* Invio i dati alla classe onReceiveNewData che estende thread e chiama la funzione run()
		 * per iniziare il processo di valutazione della qualità dell'aria.
		 * Invio i dati alla classe SSSConnection che estende thread e chiama la funzione run()
		 * per implementare il modulo di Spreed Sheet Communication nel VO.
		*/
		onReceiveNewData newData = new onReceiveNewData(data);
		Thread thread_newData = new Thread(newData);
		thread_newData.start();
		return data;
	}
}
