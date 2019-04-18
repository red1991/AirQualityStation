package it.cipi.esercitazione;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.RollingFileAppender;
import it.cipi.esercitazione.VORegistration;
import it.cipi.esercitazione.utils.ConfigurationFileUtil;

@WebListener
public class ServiceListener implements ServletContextListener{

	@SuppressWarnings("unused")
	private static Logger log = null;
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub	
	}

	@Override
	public void contextInitialized(ServletContextEvent evt) {
		// TODO Auto-generated method stub
		System.out.println("TEST SERVICE con runtime annotations STARTED!!!!");
		
		ServletContext sc = evt.getServletContext();
		String warName = sc.getContextPath().length() == 0 ? "ROOT" : sc.getContextPath().substring(1);
		ConsoleAppender console = new ConsoleAppender(); //create appender
		String PATTERN = "%d{dd MMM yyyy HH:mm:ss} %5p %l %m%n";
		console.setLayout(new PatternLayout(PATTERN)); 
		console.setThreshold(Level.DEBUG);
		console.activateOptions();
		Logger.getRootLogger().addAppender(console);
		RollingFileAppender rfa = new RollingFileAppender();
		rfa.setName(warName);
		rfa.setImmediateFlush(true);
		rfa.setFile(System.getProperty("catalina.home") + "/logs/"+warName+".log");
		rfa.setLayout(new PatternLayout("%d{dd MMM yyyy HH:mm:ss} %5p %l %m%n"));
		rfa.setThreshold(Level.DEBUG);
		rfa.setAppend(true);
		rfa.setMaxFileSize("10MB");
		rfa.setMaxBackupIndex(50);
		rfa.activateOptions();
		Logger.getRootLogger().addAppender(rfa);
		
		log = Logger.getRootLogger();
		ConfigurationFileUtil.readConfigFile(evt);
		
		it.cipi.esercitazione.VORealObjectCommunication.init(sc);
		
		// Creo oggetto "registrazione" per iniziare la registrazione del VO
		VORegistration registrazione = new VORegistration();
		
		// Chiamo funzione per inviare i dati al VORegister in modo da registrare il VO
		registrazione.registrazione();	
	}
}
