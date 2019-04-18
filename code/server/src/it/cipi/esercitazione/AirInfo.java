package it.cipi.esercitazione;

// Classe per il parsing del json ricevuto dal gateway
public class AirInfo {
	// Attributi del json
	private Object id;
	private Object name;
	private Object description;
	private Object latitude;
	private Object longitude;
	private Attributes[] attributes;
	private Object last_update;
	
	// Funzioni di get per ricevere i vari attributi
	
	public Object getId() {
		return id;
	}
	
	public Object getName() {
		return name;
	}
	
	public Object getDescription() {
		return description;
	}
	
	public Object getLatitude() {
		return latitude;
	}
	
	public Object getLongitude() {
		return longitude;
	}
	
	public Attributes[] getAttributes() {
		return attributes;
	}
	
	public Object getLastUpdate() {
		return last_update;
	}
}