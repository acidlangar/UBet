package org.idsiom.utilbet.currentuse.bo;

public enum EstadoPyck {
	POR_DEFINIR("PDF"),
	FINALIZADO("FIN"),
	SUSPENDIDO("SUS");
	
	private String abrev;
	
	private EstadoPyck(String abre) {
		this.abrev = abre;
	}
	
	public String getAbreviatura() {
		return this.abrev;
	}
	
	public static EstadoPyck getObjFromAbrev(String abreviatura) {
		
		if(abreviatura.equalsIgnoreCase("PDF")) {
			return POR_DEFINIR;
		}
		
		if(abreviatura.equalsIgnoreCase("FIN")) {
			return FINALIZADO;
		}
		
		if(abreviatura.equalsIgnoreCase("SUS")) {
			return SUSPENDIDO;
		}
		
		return null;
	}
}
