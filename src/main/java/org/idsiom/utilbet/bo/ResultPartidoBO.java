package org.idsiom.utilbet.bo;

public enum ResultPartidoBO {
	FAVORITO("F"),
	EMPATE("E"),
	PERDEDOR("P");
	
	private String abrev;
	
	private ResultPartidoBO(String abre) {
		this.abrev = abre;
	}
	
	public String getAbreviatura() {
		return this.abrev;
	}
	
	public static ResultPartidoBO getObjFromAbrev(String abreviatura) {
		
		if(abreviatura.equalsIgnoreCase("F")) {
			return FAVORITO;
		}
		
		if(abreviatura.equalsIgnoreCase("P")) {
			return PERDEDOR;
		}
		
		if(abreviatura.equalsIgnoreCase("E")) {
			return EMPATE;
		}
		
		return null;
	}
}
