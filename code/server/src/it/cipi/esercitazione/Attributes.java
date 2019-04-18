package it.cipi.esercitazione;

import com.google.gson.annotations.SerializedName;

// Classe per il parsing degli attributi più interni del json ricevuto dal gateway

public class Attributes {
	@SerializedName("@attributes") // Necessario a causa del carattere particolare nel nome dell'attributo
	private Inner_Attributes inner_attributes;
	private Object Grandezza;
	private Object Elaborazione;
	private Object Limite;
	private Object Valore;
	private Object unitaMisura;
	
	
	// Funzioni di get per ricevere i vari attributi
	
	public Inner_Attributes getInnerAttributes() {
		return inner_attributes;
	}
	
	public Object getGrandezza() {
		return Grandezza;
	}
	
	public Object getElaborazione() {
		return Elaborazione;
	}
	
	public Object getLimite() {
		return Limite;
	}
	
	public Object getValore() {
		return Valore;
	}
	
	public Object getUnitaMisura() {
		return unitaMisura;
	}
	
	// Classe per attributi interni ad attributes
	
	class Inner_Attributes {
		private Object id;
		private Object rowOrder;
		private Object hiddenID_SITO;
		
		// Funzioni di get per ricevere i vari attributi
		
		public Object getId() {
			return id;
		}
		
		public Object getRowOrder() {
			return rowOrder;
		}
		
		public Object getHiddenID_SITO() {
			return hiddenID_SITO;
		}
	}
}
