package org.idsiom.utilbet.currentuse.bo;
public enum ResultadoPartidoBO {
	LOCAL("L"),
	EMPATE("E"),
	VISITANTE("V");
	
	private String abrev;
	
	private ResultadoPartidoBO(String abre) {
		this.abrev = abre;
	}
	
	public String getAbreviatura() {
		return this.abrev;
	}
	
	public static ResultadoPartidoBO getObjFromAbrev(String abreviatura) {
		
		if(abreviatura.equalsIgnoreCase("L")) {
			return LOCAL;
		}
		
		if(abreviatura.equalsIgnoreCase("V")) {
			return VISITANTE;
		}
		
		if(abreviatura.equalsIgnoreCase("E")) {
			return EMPATE;
		}
		
		return null;
	}
}