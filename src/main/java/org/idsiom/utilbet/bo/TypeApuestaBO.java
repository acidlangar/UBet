package org.idsiom.utilbet.bo;

public enum TypeApuestaBO {
	SENCILLA("SENC"),
	COMBINADA("COMB");
	
	private String abreviatura;
	
	private TypeApuestaBO(String abreviatura) {
		this.abreviatura = abreviatura;
	}
	
	public String getAbreviatura() {
		return this.abreviatura;
	}
	
	
}
